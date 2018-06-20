package com.ning.ybsxpss.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.CheckReportObj;
import com.ning.ybsxpss.entity.FillSourceObj;
import com.ning.ybsxpss.entity.GoodsMaintainObj;
import com.ning.ybsxpss.entity.GoodsOneType;
import com.ning.ybsxpss.entity.GoodsType;
import com.ning.ybsxpss.entity.ImageUrl;
import com.ning.ybsxpss.entity.JsonBean;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.MySupplierList;
import com.ning.ybsxpss.entity.UnSetGoodsList;
import com.ning.ybsxpss.entity.WaitDisposeObj;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fxn on 2017/9/30.
 */

public class GoodsModel {
    private static final String CONTENT_TYPE = "multipart/form-data"; //内容类型
    private static final String BOUNDARY = "FlPm4LpSXsE" ; //UUID.randomUUID().toString(); //边界标识 随机生成 String PR
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     *  获取商品列表
     */
    public void getGoodsList(int currentPaperNo, int nums,String status,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("currentPaperNo", currentPaperNo+"")
                .add("nums", nums+"")
                .add("status",status)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getGoodsList")
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
                    GoodsMaintainObj user = gson.fromJson(htmlStr, GoodsMaintainObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *  下架/批量下架/上架
     */
    public void soldoutOrPutawary(String goodsId,String status,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("goodsId", goodsId)
                .add("status",status)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/soldoutOrPutawary")
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
     *  修改单个商品
     */
    public void updateOneGoods(String goodsId,String goodsUnit,String goodsName,String price,String minAmount,String classificationId,String mImgUrls, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("goodsId", goodsId)
                .add("goodsUnit", goodsUnit)
                .add("goodsName", goodsName)
                .add("price", price)
                .add("minAmount", minAmount)
                .add("classificationId", classificationId)
                .add("imgUrl", mImgUrls)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/updateOneGoods")
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
     *  获取商品分类
     */
    public void getClassificaitonTree(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/getClassificaitonTree")
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
                    ArrayList<JsonBean> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, JsonBean.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  修改批量商品
     */
    public void batchUpdateGoods(String goodsId,String goodsName,String goodsUnit,String price,String minAmount,String mImgUrls, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("goodsId", goodsId)
                .add("goodsName", goodsName)
                .add("goodsUnit", goodsUnit)
                .add("price", price)
                .add("minAmount", minAmount)
                .add("imgUrl", mImgUrls)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/batchUpdateGoods")
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
    public void uploadImg(String mImgUrls, final ICallBack callBack) {
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
                .url(UrlUtil.url + "/distributionMobile/uploadImg")
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
     *  配送时间段/计量单位
     */
    public void getTimeOrUnit(String typecode, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("typecode", typecode)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/getDictionary")
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
                    ArrayList<GoodsType> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, GoodsType.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取未设置的商品列表
     */
    public void getUnSetGoodsList(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getUnSetGoodsList")
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
                    ArrayList<UnSetGoodsList> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, UnSetGoodsList.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取三级商品类型的列表
     */
    public void getAllClassificationList(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getAllClassificationList")
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
                    ArrayList<GoodsOneType> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, GoodsOneType.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  增加新商品
     */
    public void applyAddClassification(String name,String parentId,String imgUrl,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("name", name)
                .add("parentId", parentId)
                .add("imgUrl", imgUrl)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/applyAddClassification")
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
     *  保存商品
     */
    public void addGoods(String classificationId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("classificationId", classificationId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/addGoods")
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
     *  删除商品
     */
    public void delGoods(String goodsId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("goodsId", goodsId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/delGoods")
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
     *  获取查看来源信息
     */
    public void getSource(String orderDetailId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("orderDetailId", orderDetailId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getSource")
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
                    FillSourceObj user = gson.fromJson(htmlStr, FillSourceObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取检测详细信息
     */
    public void getCheckReport(String orderDetailId,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("orderDetailId", orderDetailId)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getCheckReport")
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
                    CheckReportObj user = gson.fromJson(htmlStr, CheckReportObj.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
}
