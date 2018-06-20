package com.ning.ybsxpss.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.PassWordModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetPawActivity extends BaseActivity {
    private PassWordModel model;
    private EditText et_found_paw_paw,et_found_paw_qrpaw;
    private Button btn_found_paw_zc;
    private String yzm;
    private ImageView iv_back;
    private String msg1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                showToast("设置成功");
                finish();
            }
            if (msg.what == 2) {
                showToast("失败"+msg1);
            }
        }
    };
    private String phoneORemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_paw);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
    }

    private void getmodel() {
        model = new PassWordModel();
        Bundle bundle = getIntent().getExtras();
        yzm = bundle.getString("yzm");
        phoneORemail = bundle.getString("phoneORemail");
    }
    private void setListener() {
        btn_found_paw_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneORemail.equals("phone")) {
                    String prpaw = et_found_paw_qrpaw.getText().toString();
                    String paw = et_found_paw_paw.getText().toString();
                    String regEx = "^[A-Za-z0-9_]{6,18}$";
                    Pattern pat = Pattern.compile(regEx);
                    Matcher mat = pat.matcher(paw);
                    boolean rs = mat.matches();
                    if(!rs){
                        showToast("密码只能由字母、数字、下划线组成，长度为6～18个字符");
                        return;
                    }
                    if (!paw.equals(prpaw)) {
                        Toast.makeText(SetPawActivity.this, "保持密码一致", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    model.saveNewPwd_phone(yzm, paw, new ICallBack() {
                        @Override
                        public void succeed(Object object) {
                            LoginObg obg = (LoginObg) object;
                            if (obg.isSuccess()) {
                                Message message = Message.obtain();
                                message.what = 1;
                                handler.sendMessage(message);
                            } else {
                                Message message = Message.obtain();
                                message.what = 2;
                                msg1 = obg.getMsg();
                                handler.sendMessage(message);
                            }
                        }
                        public void error(Object object) {}
                    });
                }else {
                    String prpaw = et_found_paw_qrpaw.getText().toString();
                    String paw = et_found_paw_paw.getText().toString();
                    String regEx = "^[A-Za-z0-9_]{6,18}$";
                    Pattern pat = Pattern.compile(regEx);
                    Matcher mat = pat.matcher(paw);
                    boolean rs = mat.matches();
                    if(!rs){
                        showToast("账号只能由字母、数字、下划线组成，长度为6～18个字符");
                        return;
                    }
                    if (!paw.equals(prpaw)) {
                        Toast.makeText(SetPawActivity.this, "保持密码一致", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    model.saveNewPwd(yzm, paw, new ICallBack() {
                        @Override
                        public void succeed(Object object) {
                            LoginObg obg = (LoginObg) object;
                            if (obg.isSuccess()) {
                                Message message = Message.obtain();
                                message.what = 1;
                                handler.sendMessage(message);
                            } else {
                                Message message = Message.obtain();
                                message.what = 2;
                                msg1 = obg.getMsg();
                                handler.sendMessage(message);
                            }
                        }
                        public void error(Object object) {}
                    });
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setView() {
        et_found_paw_paw = (EditText) findViewById(R.id.et_set_paw_paw);
        et_found_paw_qrpaw = (EditText) findViewById(R.id.et_set_paw_qrpaw);
        btn_found_paw_zc = (Button) findViewById(R.id.btn_set_paw_zc);
        iv_back = findViewById(R.id.iv_set_paw_back);
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
