package com.ning.ybsxpss.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.ImageUrl;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.RoleMenuList;
import com.ning.ybsxpss.entity.UnitData;
import com.ning.ybsxpss.entity.UserCenter;
import com.ning.ybsxpss.entity.UserMessage;
import com.ning.ybsxpss.entity.YypzImage;
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
 * Created by fxn on 2017/9/25.
 */

public class UserModel {
    private static final String CONTENT_TYPE = "multipart/form-data"; //内容类型
    private static final String BOUNDARY = "FlPm4LpSXsE" ; //UUID.randomUUID().toString(); //边界标识 随机生成 String PR
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     *  登录
     */
    public void login(String account, String password, String yzm,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("account", account)
                .add("password", password)
                .add("yzm", yzm)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/checkLogin")
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
     *  自动登录
     */
    public void autoLogin(String account, String password,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("account", account)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/checkLogin_auto")
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
     *  获取用户中心数据
     */
    public void getUserCenterInfo(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getUserCenterInfo")
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
                    UserCenter user = gson.fromJson(htmlStr, UserCenter.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取角色菜单权限列表
     */
    public void getRoleMenuList(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/getRoleMenuList")
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
                    ArrayList<RoleMenuList> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, RoleMenuList.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取个人信息
     */
    public void getPersonInfo(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getPersonInfo")
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
                    UserMessage user = gson.fromJson(htmlStr, UserMessage.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取单位基本信息
     */
    public void getCompanyInfo(final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getCompanyInfo")
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
                    UnitData user = gson.fromJson(htmlStr, UnitData.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  获取文件列表图片
     */
    public void getFileList(String type,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("type", type)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/getFileList")
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
                    ArrayList<YypzImage> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, YypzImage.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    App.restartApp();
                }
            }
        });
    }
    /**
     *  上传图片
     */
    public void uploadFile(String mImgUrls, final ICallBack callBack) {
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
                .url(UrlUtil.url + "/distributionMobile/uploadFile")
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
     *  保存更新单位信息
     */
    public void updateCompanyInfo(String companyName,String county,String addr,String fileurls_yyzj,String fileurls_frsf,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("companyName", companyName)
                .add("county", county)
                .add("addr", addr)
                .add("fileurls_yyzj", fileurls_yyzj)
                .add("fileurls_frsf", fileurls_frsf)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/updateCompanyInfo")
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
     *  保存更新个人信息
     */
    public void updatePersonInfo(String name, String phone, String email,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/updatePersonInfo")
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
     *  通过邮箱发送验证码
     */
    public void sendYzmToEmail(String email,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/sendYzmToEmail")
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
     *  验证邮箱验证码
     */
    public void checkEmailYzm(String yzm,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("yzm", yzm)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/checkEmailYzm")
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
     *  修改密码
     */
    public void updatepassword( String oldpwd, String newpwd,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("oldpwd", oldpwd)
                .add("newpwd", newpwd)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/distributionMobile/updatePersonInfo")
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
     *  生成手机验证码
     */
    public void generatePhoneYzm( String phone, String yzm, String type,String reg_type,final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("phone", phone)
                .add("yzm", yzm)
                .add("type", type)
                .add("reg_type", reg_type)
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
     *  配送商注册
     */
    public void regDistribution(String account, String name, String phone, String email, String password, String companyName,
                                String county, String addr, String fileurls_yyzj , String fileurls_frsf,String phoneYzm, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("account", account)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("password", password)
                .add("companyName", companyName)
                .add("county", county)
                .add("addr", addr)
                .add("fileurls_yyzj", fileurls_yyzj)
                .add("fileurls_frsf", fileurls_frsf)
                .add("phoneYzm", phoneYzm)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/regDistribution")
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
     *  验证手机验证码-注册
     */
    public void checkPhoneYzm_reg( String phoneYzm , String phone, final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("phoneYzm", phoneYzm)
                .add("phone", phone)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/checkPhoneYzm_reg")
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
     *  注销退出
     */
    public void loginout(  final ICallBack callBack) {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"/openclassMobileController/loginout")
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
