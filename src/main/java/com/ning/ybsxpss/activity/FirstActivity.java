package com.ning.ybsxpss.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.UpdateApp;
import com.ning.ybsxpss.model.UpdateModel;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UpdateAppHttpUtil;
import com.ning.ybsxpss.util.UrlUtil;
import com.ning.ybsxpss.util.Utility;
import com.vector.update_app.UpdateAppManager;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

public class FirstActivity extends BaseActivity {

    private UserModel model;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        Intent intent = new Intent(FirstActivity.this,UserCenterActivity.class);
                        startActivity(intent);
                        App.setBoolean(true);
                        finish();
                    }
                }, 100);
            }
            if (msg.what == 2) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(FirstActivity.this,LoginActivity.class);
                        startActivity(intent);
                        App.setBoolean(true);
                        finish();
                    }
                }, 100);
            }
            if (msg.what == 3) {
                autoLogin();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Utility.setToolbar(this);

        getSessionId();
    }


    /**
     * 自动登录
     */
    private void autoLogin() {
        model = new UserModel();
        SharedPreferences preferences = App.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String une = preferences.getString("username", "");
        String pwd = preferences.getString("password", "");
        model.autoLogin(une, pwd, new ICallBack() {
            @Override
            public void succeed(Object object) {
                LoginObg obg = (LoginObg) object;
                if(obg.isSuccess()) {
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                }else {
                    Message message = Message.obtain();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void error(Object object) {
                Message message = Message.obtain();
                message.what = 2;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 获取sessionid
     */
    public void getSessionId(){
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/authImage")
                .build();
        Call call = App.getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info_callFailure",e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                    Headers headers = response.headers();
                    Log.d("info_headers", "header " + headers);
                    List<String> cookies = headers.values("Set-Cookie");
                    String cookieval = cookies.get(0);
                    String sessionid  = cookieval.substring(0, cookieval.indexOf(";"));
                    //获得SharedPreferences对象
                    SharedPreferences preferences1 = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = preferences1.edit();
                    editor1.putString("sessionid", sessionid);
                    editor1.commit();
                    Message message = Message.obtain();
                    message.what = 3;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
