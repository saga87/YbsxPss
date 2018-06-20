package com.ning.ybsxpss.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DaishouliAdapter;
import com.ning.ybsxpss.adapter.DetailsAdapter;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.OrderDetailsObj;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

public class OrderDetailsActivity extends BaseActivity {
    private TextView tv_order_username,tv_order_userphone,tv_order_useraddress,tv_order_pssname,tv_order_pssphone
            ,tv_order_pssaddress,tv_order_ddbh,tv_order_ddzt,tv_order_xdren,tv_order_xdsj,tv_order_pssj;
    private ListView listview_order;
    private ImageView iv_back;
    private Button btn_order_qrsl,btn_order_jjsl;
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
                DetailsAdapter adapter = new DetailsAdapter(OrderDetailsActivity.this,obj.getDetailList());
                listview_order.setAdapter(adapter);
            }
            if(msg.what==2) {
                showToast("受理成功");
                Intent intent = new Intent();
                intent.setAction("rfid_card_found");
                intent.putExtra("position",position);
                OrderDetailsActivity.this.sendBroadcast(intent);
                finish();
            }
        }
    };
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
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
        position = bundle.getInt("position");
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
        btn_order_qrsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定受理吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        model.dealWithOrder(obj.getOrderId(), "1", new ICallBack() {
                            @Override
                            public void succeed(Object object) {
                                LoginObg obj= (LoginObg) object;
                                if(obj.isSuccess()) {
                                    Message message = new Message();
                                    message.what = 2;
                                    handler.sendMessage(message);
                                }
                            }
                            public void error(Object object) {
                            }
                        });
                    }
                });
                builder.show();
            }
        });
        btn_order_jjsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定拒绝受理吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        model.dealWithOrder(obj.getOrderId(), "2", new ICallBack() {
                            @Override
                            public void succeed(Object object) {
                                LoginObg obj= (LoginObg) object;
                                if(obj.isSuccess()) {
                                    Message message = new Message();
                                    message.what = 2;
                                    handler.sendMessage(message);
                                }
                            }
                            public void error(Object object) {
                            }
                        });
                    }
                });
                builder.show();
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
        tv_order_username = (TextView) findViewById(R.id.tv_order_username);
        tv_order_userphone = (TextView) findViewById(R.id.tv_order_userphone);
        tv_order_useraddress = (TextView) findViewById(R.id.tv_order_useraddress);
        tv_order_pssname = (TextView) findViewById(R.id.tv_order_pssname);
        tv_order_pssphone = (TextView) findViewById(R.id.tv_order_pssphone);
        tv_order_pssaddress = (TextView) findViewById(R.id.tv_order_pssaddress);
        tv_order_ddbh = (TextView) findViewById(R.id.tv_order_ddbh);
        tv_order_ddzt = (TextView) findViewById(R.id.tv_order_ddzt);
        tv_order_xdren = (TextView) findViewById(R.id.tv_order_xdren);
        tv_order_xdsj = (TextView) findViewById(R.id.tv_order_xdsj);
        tv_order_pssj = (TextView) findViewById(R.id.tv_order_pssj);
        btn_order_qrsl = (Button) findViewById(R.id.btn_order_qrsl);
        btn_order_jjsl = (Button) findViewById(R.id.btn_order_jjsl);
        listview_order = (ListView) findViewById(R.id.listview_order);
        iv_back = findViewById(R.id.iv_order_back);
    }
}
