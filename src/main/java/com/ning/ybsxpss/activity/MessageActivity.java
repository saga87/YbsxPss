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
import com.ning.ybsxpss.entity.UserMessage;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageActivity extends BaseActivity {
    private EditText et_message_xm,et_message_sjh,et_message_yx;
    private Button btn_message_bc;
    private ImageView iv_back;
    private UserModel model;
    private UserMessage user;
    private String msg1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                et_message_xm.setText(user.getName());
                et_message_sjh.setText(user.getPhone());
                et_message_yx.setText(user.getEmail());
            }
            if(msg.what==2) {
                Toast.makeText(MessageActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
            if(msg.what==3) {
                Toast.makeText(MessageActivity.this,"保存失败"+msg1,Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
    }

    private void getmodel() {
        model = new UserModel();
        model.getPersonInfo( new ICallBack() {
            @Override
            public void succeed(Object object) {
                user = (UserMessage) object;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
    }

    private void setListener() {
        btn_message_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_message_xm.getText().toString();
                String phone = et_message_sjh.getText().toString();
                String email = et_message_yx.getText().toString();
                String regEx1 = "^[\\u4E00-\\u9FA5]+$";
                Pattern pat1 = Pattern.compile(regEx1);
                Matcher mat1 = pat1.matcher(name);
                boolean rs1 = mat1.matches();
                if(!rs1){
                    showToast("姓名只能填写中文汉字");
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
                if(name.equals("")||phone.equals("")||email.equals("")){
                    Toast.makeText(MessageActivity.this,"数据不能为空，请补全数据",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.updatePersonInfo(name,phone,email, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()) {
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);
                        }else {
                            Message message = new Message();
                            message.what = 3;
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
        et_message_xm = (EditText) findViewById(R.id.et_message_xm);
        et_message_sjh = (EditText) findViewById(R.id.et_message_sjh);
        et_message_yx = (EditText) findViewById(R.id.et_message_yx);
        btn_message_bc = (Button) findViewById(R.id.btn_message_bc);
        iv_back = findViewById(R.id.iv_message_back);
    }
}
