package com.ning.ybsxpss.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;

import com.ning.ybsxpss.util.UrlUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fxn on 2017/6/19.
 */

public class App extends Application {
    private static OkHttpClient client;
    private static Application contect;
    @Override
    public void onCreate() {
        super.onCreate();
        contect = this;
        client = new OkHttpClient.Builder()
                //设置超时，不设置可能会报异常
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
    public static OkHttpClient getClient(){
        return client;
    }

    public static Context getContext(){
        return contect;
    }

    public static void setBoolean(boolean b){
        //获得SharedPreferences对象
        SharedPreferences preferences1 = App.getContext().getSharedPreferences("boolean", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();
        editor1.putBoolean("bool", b);
        editor1.commit();
    }

    public static boolean getBoolean(){
        //获得SharedPreferences对象
        SharedPreferences preferences = App.getContext().getSharedPreferences("boolean", Context.MODE_PRIVATE);
        boolean bool = preferences.getBoolean("bool", false);
        return bool;
    }

    /**
     * 从新获取登陆 sessionId
     */
    public static void restartApp(){
        SharedPreferences preferences = App.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String une = preferences.getString("username", "");
        String pwd = preferences.getString("password", "");
        FormBody body = new FormBody.Builder()
                .add("account", une)
                .add("password", pwd)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/checkLogin_auto")
                .post(body)
                .build();
        Call call = App.getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                    Headers headers = response.headers();
                    List<String> cookies = headers.values("Set-Cookie");
                    String cookieval = cookies.get(0);
                    String sessionid  = cookieval.substring(0, cookieval.indexOf(";"));
                    //获得SharedPreferences对象
                    SharedPreferences preferences1 = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = preferences1.edit();
                    editor1.putString("sessionid", sessionid);
                    editor1.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
