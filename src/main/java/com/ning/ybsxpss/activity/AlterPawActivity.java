package com.ning.ybsxpss.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlterPawActivity extends BaseActivity {
    private EditText et_alter_paw_jmm,et_alter_paw_xmm,et_alter_paw_qrxmm;
    private Button btn_settings_tcdl;
    private UserModel model;
    private ImageView iv_back;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                showToast("修改成功");
                et_alter_paw_jmm.setText("");
                et_alter_paw_xmm.setText("");
                et_alter_paw_qrxmm.setText("");
            }
            if (msg.what == 2) {
                showToast("修改失败"+msg1);
            }
        }
    };
    private String msg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_paw);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
    }

    private void getmodel() {
        model = new UserModel();
    }

    private void setListener() {
        btn_settings_tcdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jmm = et_alter_paw_jmm.getText().toString();
                String xmm = et_alter_paw_xmm.getText().toString();
                String qrxmm = et_alter_paw_qrxmm.getText().toString();
                String regEx = "^[A-Za-z0-9_]{6,18}$";
                Pattern pat = Pattern.compile(regEx);
                Matcher mat = pat.matcher(xmm);
                boolean rs = mat.matches();
                if(!rs){
                    showToast("密码只能由字母、数字、下划线组成，长度为6～18个字符");
                    return;
                }
                if(!xmm.equals(qrxmm)){
                    Toast.makeText(AlterPawActivity.this,"密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(jmm.equals("")||xmm.equals("")||qrxmm.equals("")){
                    Toast.makeText(AlterPawActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.updatepassword(jmm, xmm, new ICallBack() {
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
                    public void error(Object object) {}
                });
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void setView() {
        et_alter_paw_jmm = (EditText) findViewById(R.id.et_alter_paw_jmm);
        et_alter_paw_xmm = (EditText) findViewById(R.id.et_alter_paw_xmm);
        et_alter_paw_qrxmm = (EditText) findViewById(R.id.et_alter_paw_qrxmm);
        btn_settings_tcdl = (Button) findViewById(R.id.btn_alter_paw_qd);
        iv_back = findViewById(R.id.iv_alter_paw_back);
    }
}
