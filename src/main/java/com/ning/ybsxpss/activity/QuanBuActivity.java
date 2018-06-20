package com.ning.ybsxpss.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DetailsAdapter;
import com.ning.ybsxpss.entity.OrderDetailsObj;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

public class QuanBuActivity extends BaseActivity {
    private TextView tv_order_username,tv_order_userphone,tv_order_useraddress,tv_order_pssname,tv_order_pssphone
            ,tv_order_pssaddress,tv_order_ddbh,tv_order_ddzt,tv_order_xdren,tv_order_xdsj,tv_order_pssj;
    private ListView listview_order;
    private ImageView iv_back;
    private OrderDetailsObj obj;
    private String orderId;
    private IndentModel model;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                tv_order_username.setText(obj.getAddr_name());
                tv_order_userphone.setText(obj.getAddr_phone());
                tv_order_useraddress.setText(obj.getAddress());
                tv_order_pssname.setText(obj.getDistributionName());
                tv_order_pssphone.setText(obj.getDistributionPhone());
                tv_order_pssaddress.setText(obj.getDistributionAddr());
                tv_order_ddbh.setText(obj.getOrderNo());
                switch (obj.getStatus()){
                    case "0":
                        tv_order_ddzt.setText("待受理");
                        break;
                    case "1":
                        tv_order_ddzt.setText("待发货");
                        break;
                    case "2":
                        tv_order_ddzt.setText("拒绝受理");
                        break;
                    case "3":
                        tv_order_ddzt.setText("已发货");
                        break;
                    case "4":
                        tv_order_ddzt.setText("签收");
                        break;
                    case "5":
                        tv_order_ddzt.setText("确认签收");
                        break;
                    case "6":
                        tv_order_ddzt.setText("确认收货");
                        break;
                }
                tv_order_xdren.setText(obj.getAddUser());
                tv_order_xdsj.setText(obj.getAddtime());
                tv_order_pssj.setText(obj.getDistributionDate()+" "+obj.getTimeRange());
                listview_order.setFocusable(false);
                DetailsAdapter adapter = new DetailsAdapter(QuanBuActivity.this,obj.getDetailList());
                listview_order.setAdapter(adapter);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_bu);
        Utility.setToolbar(this);
        setView();
        setListener();
        getmodel();
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void getmodel() {
        Bundle bundle = getIntent().getExtras();
        orderId = bundle.getString("orderId");
        model = new IndentModel();
        model.getOrderDetail(orderId, new ICallBack() {
            @Override
            public void succeed(Object object) {
                obj= (OrderDetailsObj) object;
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
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setView() {
        tv_order_username = (TextView) findViewById(R.id.tv_quanbu_username);
        tv_order_userphone = (TextView) findViewById(R.id.tv_quanbu_userphone);
        tv_order_useraddress = (TextView) findViewById(R.id.tv_quanbu_useraddress);
        tv_order_pssname = (TextView) findViewById(R.id.tv_quanbu_pssname);
        tv_order_pssphone = (TextView) findViewById(R.id.tv_quanbu_pssphone);
        tv_order_pssaddress = (TextView) findViewById(R.id.tv_quanbu_pssaddress);
        tv_order_ddbh = (TextView) findViewById(R.id.tv_quanbu_ddbh);
        tv_order_ddzt = (TextView) findViewById(R.id.tv_quanbu_ddzt);
        tv_order_xdren = (TextView) findViewById(R.id.tv_quanbu_xdren);
        tv_order_xdsj = (TextView) findViewById(R.id.tv_quanbu_xdsj);
        tv_order_pssj = (TextView) findViewById(R.id.tv_quanbu_pssj);
        listview_order = (ListView) findViewById(R.id.listview_quanbu);
        iv_back = findViewById(R.id.iv_quanbu_back);
    }
}
