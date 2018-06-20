package com.ning.ybsxpss.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.MadeGoodsList;
import com.ning.ybsxpss.entity.MyClientObj;
import com.ning.ybsxpss.entity.UnSetGoodsList;
import com.ning.ybsxpss.entity.WaitDisposeObj;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fxn on 2017/10/12.
 */

public class MyClientModel {

    /**
     *  获取我的客户列表
     */
    public void getCooperationList(int currentPaperNo, int nums,String purchaserName, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .add("purchaserName", purchaserName)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getCooperationList")
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
                    MyClientObj user = gson.fromJson(htmlStr, MyClientObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  提交合作申请
     */
    public void addCooperationApply(String purchaserId, String begindate,String enddate, String isLongRelation,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("purchaserId", purchaserId)
                .add("begindate", begindate)
                .add("enddate", enddate)
                .add("isLongRelation", isLongRelation)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/addCooperationApply")
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
     *  删除申请
     */
    public void delApply(String cooperationId, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("cooperationId", cooperationId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/delApply")
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
     *  获取我的客户列表
     */
    public void getMadeGoodsList(String purchaserId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("purchaserId", purchaserId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getMadeGoodsList")
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
                    Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
                    ArrayList<JsonObject> jsonObjects = new Gson().fromJson(htmlStr, type);
                    ArrayList<MadeGoodsList> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, MadeGoodsList.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  保存商品定制
     */
    public void addMadeGoods(String purchaserId,String goodsId,String price,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("purchaserId", purchaserId)
                .add("goodsId", goodsId)
                .add("price", price)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/addMadeGoods")
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
