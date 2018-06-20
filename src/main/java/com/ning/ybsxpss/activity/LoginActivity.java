package com.ning.ybsxpss.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;
import com.ning.ybsxpss.util.Utility;


public class LoginActivity extends BaseActivity {
    private LinearLayout ll_login_register;
    private EditText et_login_username,et_login_password,et_login_code;
    private TextView tv_login_wjmm;
    private ImageView iv_login_code;
    private Button btn_login;
    private UserModel model;
    private String username;
    private String password;
    private String code;
    private String session;
    private GlideUrl cookie;
    private String msg1;
    private boolean isbool = false;

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                showToast("登录成功");
                Intent intent = new Intent(LoginActivity.this,UserCenterActivity.class);
                startActivity(intent);
                btn_login.setClickable(true);
                finish();
            }
            if (msg.what == 2) {
                showToast("登录失败"+msg1);
                btn_login.setClickable(true);
            }
            if (msg.what == 3) {
                try {
                    SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
                    session = preferences.getString("sessionid", "");
                    //加载需要验证的图片
                    cookie = new GlideUrl(UrlUtil.url+"/authImage", new LazyHeaders.Builder().addHeader("Cookie", session).build());
                    Glide.with(LoginActivity.this)
                            .load(cookie)
                            .error(R.drawable.timg)
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(iv_login_code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utility.setToolbar(this);
        setView();
        getMdoel();
        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isbool) {
            Glide.with(LoginActivity.this)
                    .load(cookie)
                    .error(R.drawable.timg)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(iv_login_code);
        }
        isbool = true;
    }

    private void setListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    btn_login.setClickable(false);
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            btn_login.setClickable(true);
                        }
                    }, 4000);
                    username = et_login_username.getText().toString();
                    password = et_login_password.getText().toString();
                    code = et_login_code.getText().toString();
                    if(username.equals("")||password.equals("")||code.equals("")){
                        showToast("请填写数据");
                        return;
                    }
                    //获得SharedPreferences对象
                    SharedPreferences preferences1 = App.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = preferences1.edit();
                    editor1.putString("username", username);
                    editor1.putString("password", password);
                    editor1.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                model.login(username, password, code, new ICallBack() {
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
                            msg1 = obg.getMsg();
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
        });
        iv_login_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(LoginActivity.this)
                        .load(cookie)
                        .error(R.drawable.timg)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(iv_login_code);
            }
        });
        ll_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        tv_login_wjmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,FoundPawActivity.class);
                startActivity(intent);
            }
        });
        Message message = Message.obtain();
        message.what = 3;
        handler.sendMessage(message);
    }
    private void getMdoel() {
        try {
            model = new UserModel();
            SharedPreferences preferences = App.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            String une = preferences.getString("username", "");
            String pwd = preferences.getString("password", "");
            et_login_username.setText(une);
            et_login_password.setText(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setView() {
        et_login_username = (EditText) findViewById(R.id.et_login_username);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        et_login_code = (EditText) findViewById(R.id.et_login_code);
        iv_login_code = (ImageView) findViewById(R.id.iv_login_code);
        btn_login = (Button) findViewById(R.id.btn_login);
        ll_login_register = (LinearLayout) findViewById(R.id.ll_login_register);
        tv_login_wjmm = (TextView) findViewById(R.id.tv_login_wjmm);
    }
}
