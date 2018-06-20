package com.ning.ybsxpss.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.LoginActivity;
import com.ning.ybsxpss.activity.SetPawActivity;
import com.ning.ybsxpss.activity.UserCenterActivity;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.ICallBack;

/**
 * Created by fxn on 2017/10/17.
 */

public class EmileFragment extends android.support.v4.app.Fragment {
    private View view;
    private UserModel model;
    private EditText et_emile_phone,et_emile_yzm;
    private TextView tv_emile_yzm;
    private String msg1;
    private String msg2;
    private Button btn_emile_next;
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
                showToast("验证成功");
                Intent intent = new Intent(getActivity(), SetPawActivity.class);
                intent.putExtra("yzm",et_emile_yzm.getText().toString());
                intent.putExtra("phoneORemail","email");
                startActivity(intent);
                getActivity().finish();
            }
            if (msg.what == 4) {
                showToast("验证失败"+msg1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emile,null);
        setView();
        setListener();
        getmodel();
        return view;
    }

    private void getmodel() {
        model = new UserModel();
    }
    private void setListener() {
        try {
            tv_emile_yzm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = et_emile_phone.getText().toString();
                    if(email.equals("")){
                        showToast("请填写邮箱");
                        return;
                    }
                    model.sendYzmToEmail(email, new ICallBack() {
                        @Override
                        public void succeed(Object object) {
                            LoginObg obg = (LoginObg) object;
                            if (obg.isSuccess()){
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            }else {
                                Message message = Message.obtain();
                                message.what = 2;
                                msg1 = obg.getMsg();
                                handler.sendMessage(message);
                            }
                        }
                        public void error(Object object) {}
                    });
                }
            });
            btn_emile_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String yzm = et_emile_yzm.getText().toString();
                    if(yzm.equals("")){
                        showToast("请填写验证码");
                        return;
                    }
                    model.checkEmailYzm(yzm, new ICallBack() {
                        @Override
                        public void succeed(Object object) {
                            LoginObg obg = (LoginObg) object;
                            if (obg.isSuccess()){
                                Message message = new Message();
                                message.what = 3;
                                handler.sendMessage(message);
                            }else {
                                Message message = Message.obtain();
                                message.what = 4;
                                msg2 = obg.getMsg();
                                handler.sendMessage(message);
                            }
                        }
                        @Override
                        public void error(Object object) {}
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    private void setView() {
        et_emile_phone = view.findViewById(R.id.et_emile_phone);
        et_emile_yzm = view.findViewById(R.id.et_emile_yzm);
        tv_emile_yzm = view.findViewById(R.id.tv_emile_yzm);
        btn_emile_next = view.findViewById(R.id.btn_emile_next);
    }
}
