package com.ning.ybsxpss.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.AlterSupplierActivity;
import com.ning.ybsxpss.activity.HtmlActivity;
import com.ning.ybsxpss.activity.LoginActivity;
import com.ning.ybsxpss.activity.RegisterActivity;
import com.ning.ybsxpss.activity.UserCenterActivity;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fxn on 2017/10/17.
 */

public class BasicInfoFragment extends android.support.v4.app.Fragment {
    private View view;
    private Spinner sp_basicinfo_zhlx;
    private EditText et_basicinfo_zh,et_basicinfo_name,et_basicinfo_phone,et_basicinfo_tpyzm,et_basicinfo_yzm,et_basicinfo_email;
    private TextView tv_basicinfo_yzm,tv_basicinfo_xy;
    private ImageView iv_basicinfo_tpyzm;
    private CheckBox cb_basicinfo_ok;
    private Button btn_basicinfo_next;
    private GlideUrl cookie;
    private UserModel model;
    public String msg1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                showToast("发送成功");
            }
            if (msg.what == 2) {
                showToast("发送失败"+msg1);
            }
            if (msg.what == 3) {
                et_basicinfo_zh.getText().toString();
                RegisterActivity.setLocation(1);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basicinfo,null);
        setView();
        getmodel();
        setListener();
        return view;
    }
    private void getmodel() {
        model = new UserModel();
    }
    private void setListener() {
        SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("sessionid", "");
        cookie = new GlideUrl(UrlUtil.url + "/authImage", new LazyHeaders.Builder().addHeader("Cookie", session).build());
        Glide.with(getActivity())
                .load(cookie)
                .error(R.drawable.timg)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv_basicinfo_tpyzm);
        iv_basicinfo_tpyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity())
                        .load(cookie)
                        .error(R.drawable.timg)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(iv_basicinfo_tpyzm);
            }
        });
        List<String> name = new ArrayList<>();
        name.add("配送商");
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, name);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_basicinfo_zhlx.setAdapter(arr_adapter);
        tv_basicinfo_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_basicinfo_phone.getText().toString();
                String yzm = et_basicinfo_tpyzm.getText().toString();
                if(phone.equals("")||yzm.equals("")){
                    Toast.makeText(getActivity(),"手机号,验证码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.generatePhoneYzm(phone, yzm, "phoneYzm_reg","2", new ICallBack() {
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
                    public void error(Object object) {
                        Message message = Message.obtain();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                });
            }
        });
        btn_basicinfo_next.setOnClickListener(new View.OnClickListener() {
            public String name1;
            public String zhanghao;
            public String phone;
            public String email;
            public String tpyzm;
            public String yzm;

            @Override
            public void onClick(View view) {
                 zhanghao = et_basicinfo_zh.getText().toString();
                 name1 = et_basicinfo_name.getText().toString();
                 phone = et_basicinfo_phone.getText().toString();
                 email = et_basicinfo_email.getText().toString();
                 tpyzm = et_basicinfo_tpyzm.getText().toString();
                 yzm = et_basicinfo_yzm.getText().toString();

                String regEx = "^[A-Za-z0-9_]{6,18}$";
                Pattern pat = Pattern.compile(regEx);
                Matcher mat = pat.matcher(zhanghao);
                boolean rs = mat.matches();
                if(!rs){
                    showToast("账号只能由字母、数字、下划线组成，长度为6～18个字符");
                    return;
                }
                String regEx1 = "^[\\u4E00-\\u9FA5]+$";
                Pattern pat1 = Pattern.compile(regEx1);
                Matcher mat1 = pat1.matcher(name1);
                boolean rs1 = mat1.matches();
                if(!rs1){
                    showToast("只能填写中文汉字");
                    return;
                }
                String regEx2 = "^1[3|4|5|7|8]\\d{9}$";
                Pattern pat2 = Pattern.compile(regEx2);
                Matcher mat2 = pat2.matcher(phone);
                boolean rs2 = mat2.matches();
                if(!rs2){
                    showToast("手机号格式不正确");
                    return;
                }
                String regEx3 = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])" +
                        "+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|" +
                        "((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|" +
                        "[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|" +
                        "[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|" +
                        "[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])" +
                        "([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*" +
                        "([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|" +
                        "(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*" +
                        "([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$";
                Pattern pat3 = Pattern.compile(regEx3);
                Matcher mat3 = pat3.matcher(email);
                boolean rs3 = mat3.matches();
                if(!rs3){
                    showToast("邮件格式不正确");
                    return;
                }
                if(zhanghao.equals("")||name1.equals("")||phone.equals("")||email.equals("")||tpyzm.equals("")||yzm.equals("")){
                    showToast("数据不能为空");
                    return;
                }
                if(!cb_basicinfo_ok.isChecked()){
                    showToast("请先同意条款");
                    return;
                }
                model.checkPhoneYzm_reg(yzm, phone, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()) {
                            RegisterActivity.register.setAccount(zhanghao);
                            RegisterActivity.register.setName(name1);
                            RegisterActivity.register.setPhone(phone);
                            RegisterActivity.register.setEmail(email);
                            RegisterActivity.register.setPhoneYzm(yzm);
                            Message message = Message.obtain();
                            message.what = 3;
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
                    }
                });
            }
        });
        tv_basicinfo_xy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HtmlActivity.class);
                startActivity(intent);
            }
        });
    }
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    private void setView() {
        sp_basicinfo_zhlx = view.findViewById(R.id.sp_basicinfo_zhlx);
        et_basicinfo_zh = view.findViewById(R.id.et_basicinfo_zh);
        tv_basicinfo_xy = view.findViewById(R.id.tv_basicinfo_xy);
        et_basicinfo_name = view.findViewById(R.id.et_basicinfo_name);
        et_basicinfo_phone = view.findViewById(R.id.et_basicinfo_phone);
        et_basicinfo_tpyzm = view.findViewById(R.id.et_basicinfo_tpyzm);
        et_basicinfo_yzm = view.findViewById(R.id.et_basicinfo_yzm);
        et_basicinfo_email = view.findViewById(R.id.et_basicinfo_email);
        tv_basicinfo_yzm = view.findViewById(R.id.tv_basicinfo_yzm);
        iv_basicinfo_tpyzm = view.findViewById(R.id.iv_basicinfo_tpyzm);
        cb_basicinfo_ok = view.findViewById(R.id.cb_basicinfo_ok);
        btn_basicinfo_next = view.findViewById(R.id.btn_basicinfo_next);
    }
}
