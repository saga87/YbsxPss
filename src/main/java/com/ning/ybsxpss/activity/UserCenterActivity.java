package com.ning.ybsxpss.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.SourceGoodsListAdapter;
import com.ning.ybsxpss.entity.RoleMenuList;
import com.ning.ybsxpss.entity.SupplierInfodfh;
import com.ning.ybsxpss.entity.SupplierObj;
import com.ning.ybsxpss.entity.UpdateApp;
import com.ning.ybsxpss.entity.UserCenter;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.model.UpdateModel;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.ActivityCollector;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UpdateAppHttpUtil;
import com.ning.ybsxpss.util.UrlUtil;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.CommonDialog;
import com.vector.update_app.UpdateAppManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UserCenterActivity extends BaseActivity {
    private TextView tv_user_center_qbqd,tv_user_center_xm1,tv_user_center_xm2
            ,tv_user_center_dsl,tv_user_center_dfh,tv_user_center_yfh,tv_user_center_yqc;
    private LinearLayout tv_user_center_spwh,tv_user_center_ghsgl,tv_user_center_dzd,tv_user_center_wdkh
            ,tv_user_center,tv_user_center_zhgl;
    private ImageView iv_back;
    private UserModel model;
    private UserCenter userCenter;
    private List<RoleMenuList> menuList;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                tv_user_center_xm1.setText(userCenter.getAccount());
                tv_user_center_xm2.setText(userCenter.getCompanyName());
                tv_user_center_dsl.setText(userCenter.getCount_dsl()+"");
                tv_user_center_dfh.setText(userCenter.getCount_dfh()+"");
                tv_user_center_yfh.setText(userCenter.getCount_yfh()+"");
                tv_user_center_yqc.setText(userCenter.getCount_yqs()+"");
                tv_user_center_qbqd.setText(userCenter.getCount_ywc()+"");
            }
            if(msg.what==2) {
                for(int i=0;i<menuList.size();i++){
                    switch (menuList.get(i).getMenu_name()){
                        case "商品维护":
                            tv_user_center_spwh.setVisibility(View.VISIBLE);
                            break;
                        case "账号管理":
                            tv_user_center_zhgl.setVisibility(View.VISIBLE);
                            break;
                        case "我的客户":
                            tv_user_center_wdkh.setVisibility(View.VISIBLE);
                            break;
                        case "供货商管理":
                            tv_user_center_ghsgl.setVisibility(View.VISIBLE);
                            break;
                        case "对账单":
                            tv_user_center_dzd.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
            if (msg.what == 4) {
                if (!checkUnknownPermission()){
                    return;
                }
                new UpdateAppManager
                        .Builder()
                        //当前Activity
                        .setActivity(UserCenterActivity.this)
                        //更新地址
                        .setUpdateUrl(UrlUtil.urlUpateApp)
                        //实现httpManager接口的对象
                        .setHttpManager(new UpdateAppHttpUtil())
                        .build()
                        .update();
            }
        }
    };

    private boolean checkUnknownPermission() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                Toast.makeText(UserCenterActivity.this,"安装应用需要打开未知来源权限，请去设置中开启权限",Toast.LENGTH_LONG).show();
                //https://blog.csdn.net/changmu175/article/details/78906829
                //https://www.jianshu.com/p/af37c1c588c4
                return false;
            }
        }
        return true;
    }

    private BroadcastReceiver RFIDFinishReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("rfid_card_userCenter")){
                model.getUserCenterInfo( new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        userCenter = (UserCenter) object;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                    @Override
                    public void error(Object object) {}
                });
            }
        }
    };
    private AlertDialog dialog;
    private UpdateApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
        try {
            //接收广播
            IntentFilter filter = new IntentFilter();
            filter.addAction("rfid_card_userCenter");
            registerReceiver(RFIDFinishReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //更新app
        updateApp();
    }

    /**
     * 更新app
     */
    private void updateApp() {
        UpdateModel model = new UpdateModel();
        model.update("pss", new ICallBack() {
            @Override
            public void succeed(Object object) {
                try {
                    app = (UpdateApp) object;
                    String version = Utility.getLocalVersionName(UserCenterActivity.this);
                    double newVersion = Double.parseDouble(app.getNew_version());
                    double oldVersion = Double.parseDouble(version);
                    if(newVersion>oldVersion){
                        handler.sendEmptyMessage(4);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void error(Object object) {
                handler.sendEmptyMessage(5);
            }
        });
    }

    private void getmodel() {
        tv_user_center_spwh.setVisibility(View.GONE);
        tv_user_center_zhgl.setVisibility(View.GONE);
        tv_user_center_wdkh.setVisibility(View.GONE);
        tv_user_center_ghsgl.setVisibility(View.GONE);
        tv_user_center_dzd.setVisibility(View.GONE);
        model = new UserModel();
        model.getUserCenterInfo( new ICallBack() {
            @Override
            public void succeed(Object object) {
                userCenter = (UserCenter) object;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {}
        });
        model.getRoleMenuList(new ICallBack() {
            @Override
            public void succeed(Object object) {
                menuList = (List<RoleMenuList>) object;
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
            public void error(Object object) {}
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(UserCenterActivity.this);
        builder.setTitle("退出程序");
        builder.setMessage("确定要退出程序吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog = builder.create();
    }

    /**
     * 设置监听
     */
    private void setListener() {
//        tv_user_center_qbqd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(UserCenterActivity.this,MainActivity.class);
//                intent.putExtra("pp","1");
//                startActivity(intent);
//            }
//        });
        tv_user_center_spwh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,GoodsMaintainActivity.class);
                startActivity(intent);
            }
        });
        tv_user_center_ghsgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,SupplierListActivity.class);
                startActivity(intent);
            }
        });
        tv_user_center_dzd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,DzdActivity.class);
                startActivity(intent);
            }
        });
        tv_user_center_wdkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,MyClientActivity.class);
                startActivity(intent);
            }
        });
        tv_user_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,AccountSettingsActivity.class);
                intent.putExtra("account",userCenter.getAccount());
                intent.putExtra("companyName",userCenter.getCompanyName());
                intent.putExtra("menuList", (Serializable) menuList);
                startActivity(intent);
            }
        });
        tv_user_center_zhgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,NumberActivity.class);
                startActivity(intent);
            }
        });

        tv_user_center_dsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,MainActivity.class);
                intent.putExtra("pp","1");
                startActivity(intent);
            }
        });
        tv_user_center_dfh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,MainActivity.class);
                intent.putExtra("pp","2");
                startActivity(intent);
            }
        });
        tv_user_center_yfh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,MainActivity.class);
                intent.putExtra("pp","3");
                startActivity(intent);
            }
        });
        tv_user_center_yqc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,MainActivity.class);
                intent.putExtra("pp","4");
                startActivity(intent);
            }
        });
        tv_user_center_qbqd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,MainActivity.class);
                intent.putExtra("pp","5");
                startActivity(intent);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        dialog.show();
    }

    private void setView() {
        tv_user_center_qbqd = findViewById(R.id.tv_user_center_qbqd);
        tv_user_center_xm1 = findViewById(R.id.tv_user_center_xm1);
        tv_user_center_xm2 = findViewById(R.id.tv_user_center_xm2);
        tv_user_center_dsl = findViewById(R.id.tv_user_center_dsl);
        tv_user_center_dfh = findViewById(R.id.tv_user_center_dfh);
        tv_user_center_yfh = findViewById(R.id.tv_user_center_yfh);
        tv_user_center_yqc = findViewById(R.id.tv_user_center_yqc);
        tv_user_center = findViewById(R.id.tv_user_center);
        tv_user_center_spwh = findViewById(R.id.tv_user_center_spwh);
        tv_user_center_ghsgl = findViewById(R.id.tv_user_center_ghsgl);
        tv_user_center_dzd = findViewById(R.id.tv_user_center_dzd);
        tv_user_center_wdkh = findViewById(R.id.tv_user_center_wdkh);
        tv_user_center_zhgl = findViewById(R.id.tv_user_center_zhgl);
        iv_back = findViewById(R.id.iv_user_center_back);
    }
}
