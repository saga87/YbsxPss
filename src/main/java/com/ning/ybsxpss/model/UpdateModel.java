package com.ning.ybsxpss.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.UpdateApp;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fxn on 2017/12/22.
 */

public class UpdateModel {
    /**
     *  app更新
     */
    public void update(String appType,final ICallBack callBack) {
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("appType", appType)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/getLatestAppInfo")
                .post(body)
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
                    UpdateApp user = gson.fromJson(htmlStr, UpdateApp.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                }
            }
        });
    }
}
