package com.ning.ybsxpss.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.DzdNum;
import com.ning.ybsxpss.entity.DzdObj;
import com.ning.ybsxpss.entity.GoodsMaintainObj;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.Province;
import com.ning.ybsxpss.entity.PurchaserName;
import com.ning.ybsxpss.entity.RoleList;
import com.ning.ybsxpss.entity.Supplier;
import com.ning.ybsxpss.entity.SupplierObj;
import com.ning.ybsxpss.entity.UnSetGoodsList;
import com.ning.ybsxpss.entity.UserObj;
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
 * Created by fxn on 2017/10/11.
 */

public class SupplierModel {
    private static final String CONTENT_TYPE = "multipart/form-data"; //内容类型
    private static final String BOUNDARY = "FlPm4LpSXsE" ; //UUID.randomUUID().toString(); //边界标识 随机生成 String PR
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     *  获取供货商列表
     */
    public void getSupplierList(int currentPaperNo, int nums,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getSupplierList")
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
                    SupplierObj user = gson.fromJson(htmlStr, SupplierObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  添加供货商
     */
    public void addSupplier(String supplierName, String type,String area, String supplierAddress,String fzr, String phone,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("supplierName", supplierName)
                .add("type",type)
                .add("area", area)
                .add("supplierAddress",supplierAddress)
                .add("fzr", fzr)
                .add("phone",phone)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/addSupplier")
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
     *  修改供货商
     */
    public void updateSupplier(String supplierId,String supplierName, String type,String area, String supplierAddress,String fzr, String phone,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("supplierId", supplierId)
                .add("supplierName", supplierName)
                .add("type",type)
                .add("area", area)
                .add("supplierAddress",supplierAddress)
                .add("fzr", fzr)
                .add("phone",phone)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/updateSupplier")
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
     *  获取供货商信息
     */
    public void getSupplierInfo(String supplierId, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("supplierId", supplierId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getSupplierInfo")
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
                    Supplier user = gson.fromJson(htmlStr, Supplier.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  删除供货商信息
     */
    public void delSupplier(String supplierId, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("supplierId", supplierId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/delSupplier")
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
     *  获取区县地址
     */
    public void getDepartmentList(String parentid,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("parentid", parentid)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/getDepartmentList")
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
                    ArrayList<Province> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, Province.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取账单列表
     */
    public void getZdList(int currentPaperNo, int nums,String purchaserId,String beginDate,String endDate,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .add("purchaserId", purchaserId)
                .add("beginDate", beginDate)
                .add("endDate", endDate)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getZdList")
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
                    DzdObj user = gson.fromJson(htmlStr, DzdObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取我的采购商列表
     */
    public void getMyPurchaserList(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getMyPurchaserList")
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
                    ArrayList<PurchaserName> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, PurchaserName.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *   获取账单汇总
     */
    public void getZdHz(String purchaserId,String beginDate,String endDate,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("purchaserId", purchaserId)
                .add("beginDate", beginDate)
                .add("endDate", endDate)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getZdHz")
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
                    DzdNum user = gson.fromJson(htmlStr, DzdNum.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     * * 获取账号列表
     */
    public void getUserList(int currentPaperNo, int nums,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getUserList")
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
                    UserObj user = gson.fromJson(htmlStr, UserObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     * * 删除账号
     */
    public void delUser(String user_id, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("user_id", user_id)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/delUser")
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
     * * 添加用户
     */
    public void addUser(String user_name,String password,String role_id,String user_realname,String user_tel, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("user_name", user_name)
                .add("password", password)
                .add("role_id", role_id)
                .add("user_realname", user_realname)
                .add("user_tel", user_tel)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/addUser")
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
     * * 获取角色列表
     */
    public void getRoleList(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getRoleList")
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
                    ArrayList<RoleList> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, RoleList.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
}
