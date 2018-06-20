package com.ning.ybsxpss.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.RoleMenuList;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.ActivityCollector;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UpdateAppHttpUtil;
import com.ning.ybsxpss.util.UrlUtil;
import com.ning.ybsxpss.util.Utility;
import com.vector.update_app.UpdateAppManager;

import org.zackratos.ultimatebar.UltimateBar;

import java.io.Serializable;
import java.util.List;

public class AccountSettingsActivity extends BaseActivity {
    private LinearLayout tv_settings,tv_settings_dwxx,tv_settings_xgmm;
    private TextView tv_settings_xm1,tv_settings_xm2,tv_settings_bbh_name;
    private ImageView iv_back;
    private Button btn_settings_tcdl;
    private String account;
    private String companyName;
    private UserModel modle;
    private List<RoleMenuList> menuList;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                //获得SharedPreferences对象
                SharedPreferences preferences1 = App.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putString("username", "");
                editor1.putString("password", "");
                editor1.commit();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
    }

    private void getmodel() {
        try {
            tv_settings_dwxx.setVisibility(View.GONE);
            tv_settings_xgmm.setVisibility(View.GONE);
            Bundle bundle = getIntent().getExtras();
            account = bundle.getString("account");
            companyName = bundle.getString("companyName");
            menuList = (List<RoleMenuList>) bundle.getSerializable("menuList");
            modle = new UserModel();
            tv_settings_xm1.setText(account);
            tv_settings_xm2.setText(companyName);
            for (int i = 0; i < menuList.size(); i++) {
                switch (menuList.get(i).getMenu_name()) {
                    case "单位信息":
                        tv_settings_dwxx.setVisibility(View.VISIBLE);
                        break;
                    case "修改密码":
                        tv_settings_xgmm.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }catch (Exception e){}
        //显示版本号
        tv_settings_bbh_name.setText("v"+Utility.getLocalVersionName(this));
    }

    private void setListener() {
        tv_settings_dwxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSettingsActivity.this,UnitInformationActivity.class);
                startActivity(intent);
            }
        });
        tv_settings_xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSettingsActivity.this,AlterPawActivity.class);
                startActivity(intent);
            }
        });
        tv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSettingsActivity.this,MessageActivity.class);
                startActivity(intent);
            }
        });
        btn_settings_tcdl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                modle.loginout(new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()){
                            Intent intent = new Intent(AccountSettingsActivity.this,FirstActivity.class);
                            startActivity(intent);
                            handler.sendEmptyMessage(1);
                            ActivityCollector.finishAll();
                        }
                    }
                    public void error(Object object) {}
                });
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setView() {
        tv_settings_xm2 = (TextView) findViewById(R.id.tv_settings_xm2);
        tv_settings_xm1 = (TextView) findViewById(R.id.tv_settings_xm1);
        tv_settings = (LinearLayout) findViewById(R.id.tv_settings);
        tv_settings_dwxx = (LinearLayout) findViewById(R.id.tv_settings_dwxx);
        tv_settings_xgmm = (LinearLayout) findViewById(R.id.tv_settings_xgmm);
        tv_settings_bbh_name = findViewById(R.id.tv_settings_bbh_name);
        btn_settings_tcdl = (Button) findViewById(R.id.btn_settings_tcdl);
        iv_back  = (ImageView) findViewById(R.id.iv_settings_back);
    }
}
