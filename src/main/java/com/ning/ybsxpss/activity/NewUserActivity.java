package com.ning.ybsxpss.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.QuanbuAdapter;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.Province;
import com.ning.ybsxpss.entity.RoleList;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.entity.WaitDisposeObj;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewUserActivity extends BaseActivity {
    private EditText et_new_user_zh,et_new_user_paw,et_new_user_prpaw,et_new_user_yhm,et_new_user_lxfs;
    private Spinner sp_new_user_js;
    private Button btn_new_user_bc;
    private ImageView iv_back;
    private SupplierModel model;
    private List<RoleList> lists;
    private String deptId;
    private String msg1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                List<String> name = new ArrayList<>();
                for (int i =0;i<lists.size();i++) {
                    String typename = lists.get(i).getRole_name();
                    name.add(typename);
                }
                ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(NewUserActivity.this, android.R.layout.simple_spinner_dropdown_item, name);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_new_user_js.setAdapter(arr_adapter);
                sp_new_user_js.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (lists.get(position).getRole_name().equals(sp_new_user_js.getSelectedItem().toString())) {
                            deptId = lists.get(position).getRole_id();
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            if(msg.what==2) {
                showToast("保存成功");
                Intent intent = new Intent();
                intent.setAction("rfid_card_number");
                NewUserActivity.this.sendBroadcast(intent);
                finish();
            }
            if(msg.what==3) {
                showToast("保存失败"+msg1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Utility.setToolbar(this);
        setView();
        setListener();
        getmodel();
    }

    private void getmodel() {
        model = new SupplierModel();
        model.getRoleList(new ICallBack() {
            @Override
            public void succeed(Object object) {
                lists = (List<RoleList>) object;
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {}
        });
    }
    private void setListener() {
        btn_new_user_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zh = et_new_user_zh.getText().toString();
                String paw = et_new_user_paw.getText().toString();
                String prpaw = et_new_user_prpaw.getText().toString();
                String yhm = et_new_user_yhm.getText().toString();
                String lxfs = et_new_user_lxfs.getText().toString();
                String regEx = "^[A-Za-z0-9_]{6,18}$";
                Pattern pat = Pattern.compile(regEx);
                Matcher mat = pat.matcher(zh);
                boolean rs = mat.matches();
                if(!rs){
                    showToast("账号只能由字母、数字、下划线组成，长度为6～18个字符");
                    return;
                }
                String regEx1 = "^[A-Za-z0-9_]{6,18}$";
                Pattern pat1 = Pattern.compile(regEx1);
                Matcher mat1 = pat1.matcher(paw);
                boolean rs1 = mat1.matches();
                if(!rs1){
                    showToast("密码只能由字母、数字、下划线组成，长度为6～18个字符");
                    return;
                }
                if(zh.equals("")||paw.equals("")||prpaw.equals("")||deptId.equals("")){
                    showToast("数据不能为空");
                    return;
                }
                if(!paw.equals(prpaw)){
                    showToast("两次密码不同,请从新输入");
                    return;
                }
                model.addUser(zh, paw, deptId,yhm,lxfs, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if (obg.isSuccess()) {
                            Message message = Message.obtain();
                            message.what = 2;
                            handler.sendMessage(message);
                        }else {
                            Message message = Message.obtain();
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
        et_new_user_zh = (EditText) findViewById(R.id.et_new_user_zh);
        et_new_user_yhm = (EditText) findViewById(R.id.et_new_user_yhm);
        et_new_user_lxfs = (EditText) findViewById(R.id.et_new_user_lxfs);
        et_new_user_paw = (EditText) findViewById(R.id.et_new_user_paw);
        et_new_user_prpaw = (EditText) findViewById(R.id.et_new_user_prpaw);
        sp_new_user_js = (Spinner) findViewById(R.id.sp_new_user_js);
        btn_new_user_bc = (Button) findViewById(R.id.btn_new_user_bc);
        iv_back = findViewById(R.id.iv_new_user_back);
    }
}
