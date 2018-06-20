package com.ning.ybsxpss.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.ImageUrl;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.MySupplierList;
import com.ning.ybsxpss.entity.OrderDetailsObj;
import com.ning.ybsxpss.entity.PickingListObj;
import com.ning.ybsxpss.entity.SourceGoodsListObj;
import com.ning.ybsxpss.entity.SupplierInfodfh;
import com.ning.ybsxpss.entity.WaitDisposeObj;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fxn on 2017/9/26. 订单处理model
 */

public class IndentModel {
    private static final String CONTENT_TYPE = "multipart/form-data"; //内容类型
    private static final String BOUNDARY = "FlPm4LpSXsE" ; //UUID.randomUUID().toString(); //边界标识 随机生成 String PR
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     *  待处理菜单
     */
    public void getOrderList_ddsl(int currentPaperNo, int nums,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getOrderList_ddsl")
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
                    WaitDisposeObj user = gson.fromJson(htmlStr, WaitDisposeObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  待处理订单
     */
    public void dealWithOrder(String orderId, String auditflag,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String  session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("orderId", orderId)
                .add("auditflag", auditflag)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/dealWithOrder")
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
     *  待发货菜单
     */
    public void getOrderList_ddfh(int currentPaperNo, int nums,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getOrderList_ddfh")
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
                    WaitDisposeObj user = gson.fromJson(htmlStr, WaitDisposeObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  生成备货单
     */
    public void getPickingList(String orderId, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("orderId", orderId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getPickingList")
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
                    ArrayList<PickingListObj> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, PickingListObj.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  填报来源数据
     */
    public void getFillSourceGoodsList(String orderId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("orderId", orderId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getFillSourceGoodsList")
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
                    SourceGoodsListObj user = gson.fromJson(htmlStr, SourceGoodsListObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取供货商数据
     */
    public void getMySupplierList(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getMySupplierList")
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
                    ArrayList<MySupplierList> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, MySupplierList.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取供货商详细数据
     */
    public void getSupplierInfo_dfh(String supplierId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("supplierId", supplierId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getSupplierInfo_dfh")
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
                    SupplierInfodfh user = gson.fromJson(htmlStr, SupplierInfodfh.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  保存数据
     */
    public void savaSource(String goodsId,String orderId,String supplierId,String pzUrl,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("goodsId", goodsId)
                .add("orderId", orderId)
                .add("supplierId", supplierId)
                .add("pzUrl", pzUrl)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/savaSource")
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
     *  上传图片
     */
    public void uploadImg_pz(String mImgUrls, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File f = new File(mImgUrls);
        if (f != null) {
            builder.addFormDataPart("photo", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
        }
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(UrlUtil.url + "/distributionMobile/uploadImg_pz")
                .header("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY)
                .post(requestBody)
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
                    String htmlStr = response.body().string();
                    Gson gson = new Gson();
                    ImageUrl user = gson.fromJson(htmlStr, ImageUrl.class);
                    callBack.succeed(user);
                }catch (Exception e){
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  订单出库
     */
    public void outboundOrder(String orderId, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("orderId", orderId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/outboundOrder")
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
     *  已发货菜单数据
     */
    public void getOrderList_yfh(int currentPaperNo, int nums,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getOrderList_yfh")
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
                    WaitDisposeObj user = gson.fromJson(htmlStr, WaitDisposeObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  已签收菜单数据
     */
    public void getOrderList_yqs(int currentPaperNo, int nums,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getOrderList_yqs")
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
                    WaitDisposeObj user = gson.fromJson(htmlStr, WaitDisposeObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  确认签收
     */
    public void affirmSignOrder(String orderId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("orderId", orderId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/affirmSignOrder")
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
     *  已签收菜单数据
     */
    public void getOrderList_ywc(int currentPaperNo, int nums,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getOrderList_ywc")
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
                    WaitDisposeObj user = gson.fromJson(htmlStr, WaitDisposeObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  订单详情
     */
    public void getOrderDetail(String orderId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("orderId", orderId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getOrderDetail")
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
                    OrderDetailsObj user = gson.fromJson(htmlStr, OrderDetailsObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
}
