package com.ning.ybsxpss.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fxn on 2017/10/17.
 */

public class PassWordModel {
    /**
     *  获取手机号验证码
     */
    public void generatePhoneYzm(String phone, String yzm, String type,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("phone", phone)
                .add("yzm", yzm)
                .add("type", type)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/generatePhoneYzm")
                .post(body)
                .addHeader("cookie",session)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Gson gson = new Gson();
                    LoginObg user = gson.fromJson(htmlStr, LoginObg.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  验证手机验证码
     */
    public void checkPhoneYzm(String phoneYzm, String phone, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("phoneYzm", phoneYzm)
                .add("phone", phone)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/checkPhoneYzm")
                .post(body)
                .addHeader("cookie",session)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Gson gson = new Gson();
                    LoginObg user = gson.fromJson(htmlStr, LoginObg.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  手机保存新密码
     */
    public void saveNewPwd_phone(String yzm, String password, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("yzm", yzm)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/saveNewPwd_phone")
                .post(body)
                .addHeader("cookie",session)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Gson gson = new Gson();
                    LoginObg user = gson.fromJson(htmlStr, LoginObg.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  邮箱保存新密码
     */
    public void saveNewPwd(String yzm, String password, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("yzm", yzm)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/saveNewPwd")
                .post(body)
                .addHeader("cookie",session)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Gson gson = new Gson();
                    LoginObg user = gson.fromJson(htmlStr, LoginObg.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
}
