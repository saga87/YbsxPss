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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.RegisterActivity;
import com.ning.ybsxpss.activity.SetPawActivity;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.PassWordModel;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

/**
 * Created by fxn on 2017/10/17.
 */

public class PhoneFragment extends android.support.v4.app.Fragment {
    private View view;
    private PassWordModel model;
    private EditText et_phonefrag_phone,et_phonefrag_tpyzm,et_phonefrag_yzm;
    private ImageView iv_phonefrag_tpyzm;
    private TextView tv_phonefrag_yzm;
    private Button btn_phonefrag_next;
    private GlideUrl cookie;
    private String msg1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                showToast("发送成功");
            }
            if (msg.what == 2) {
                showToast("失败"+msg1);
            }
            if (msg.what == 3) {
                Intent intent = new Intent(getActivity(), SetPawActivity.class);
                intent.putExtra("yzm",et_phonefrag_yzm.getText().toString());
                intent.putExtra("phoneORemail","phone");
                startActivity(intent);
                getActivity().finish();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phone,null);
        setView();
        setListener();
        getmodel();
        return view;
    }

    private void getmodel() {
        model = new PassWordModel();
    }
    private void setListener() {
        try {
            SharedPreferences preferences = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
            String session = preferences.getString("sessionid", "");
            cookie = new GlideUrl(UrlUtil.url + "/authImage", new LazyHeaders.Builder().addHeader("Cookie", session).build());
            Glide.with(getActivity())
                    .load(cookie)
                    .error(R.drawable.timg)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(iv_phonefrag_tpyzm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        iv_phonefrag_tpyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity())
                        .load(cookie)
                        .error(R.drawable.timg)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(iv_phonefrag_tpyzm);
            }
        });
        btn_phonefrag_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = null;
                String yzm = null;
                try {
                    phone = et_phonefrag_phone.getText().toString();
                    yzm = et_phonefrag_yzm.getText().toString();
                    if(phone.equals("")||yzm.equals("")){
                        Toast.makeText(getActivity(),"手机号,验证码不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                model.checkPhoneYzm(yzm, phone, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()) {
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
        tv_phonefrag_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phonefrag_phone.getText().toString();
                String yzm = et_phonefrag_tpyzm.getText().toString();
                if(phone.equals("")||yzm.equals("")){
                    Toast.makeText(getActivity(),"手机号,验证码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.generatePhoneYzm(phone, yzm, "findPwd", new ICallBack() {
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
    }

    private void setView() {
        et_phonefrag_phone = view.findViewById(R.id.et_phonefrag_phone);
        et_phonefrag_tpyzm = view.findViewById(R.id.et_phonefrag_tpyzm);
        et_phonefrag_yzm = view.findViewById(R.id.et_phonefrag_yzm);
        iv_phonefrag_tpyzm = view.findViewById(R.id.iv_phonefrag_tpyzm);
        tv_phonefrag_yzm = view.findViewById(R.id.tv_phonefrag_yzm);
        btn_phonefrag_next = view.findViewById(R.id.btn_phonefrag_next);
    }
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
