package com.ning.ybsxpss.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.RegisterActivity;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.Register;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fxn on 2017/10/17.
 */

public class FoundPawFragment extends android.support.v4.app.Fragment {
    private View view;
    private UserModel model;
    private EditText et_found_paw_paw,et_found_paw_qrpaw;
    private Button btn_found_paw_zc;
    private String msg1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                showToast("注册成功");
                getActivity().finish();
            }
            if (msg.what == 2) {
                showToast("注册失败"+msg1);
            }
            if(msg.what==3) {
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_found_paw,null);
        setView();
        setListener();
        getmodel();
        return view;
    }
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    private void getmodel() {
        model = new UserModel();
    }
    private void setListener() {
        btn_found_paw_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paw = et_found_paw_paw.getText().toString();
                String qrpaw =  et_found_paw_qrpaw.getText().toString();
                String regEx = "^[A-Za-z0-9_]{6,18}$";
                Pattern pat = Pattern.compile(regEx);
                Matcher mat = pat.matcher(paw);
                boolean rs = mat.matches();
                if(!rs){
                    showToast("密码只能由字母、数字、下划线组成，长度为6～18个字符");
                    return;
                }
                if(paw.equals("")||qrpaw.equals("")){
                    Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!paw.equals(qrpaw)){
                    Toast.makeText(getActivity(),"密码请保持一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                RegisterActivity.register.setPassword(paw);
                Register reg = RegisterActivity.register;
                model.regDistribution(reg.getAccount(), reg.getName(), reg.getPhone(), reg.getEmail(), reg.getPassword(), reg.getCompanyName(), reg.getCounty()
                        , reg.getAddr(), reg.getFileurls_yyzj(), reg.getFileurls_frsf(),reg.getPhoneYzm(), new ICallBack() {
                            @Override
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
                            @Override
                            public void error(Object object) {
                            }
                        });
            }
        });
    }

    private void setView() {
        et_found_paw_paw = view.findViewById(R.id.et_found_paw_paw);
        et_found_paw_qrpaw = view.findViewById(R.id.et_found_paw_qrpaw);
        btn_found_paw_zc = view.findViewById(R.id.btn_found_paw_zc);
    }
}
