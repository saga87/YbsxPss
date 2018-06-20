package com.ning.ybsxpss.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DaifahuoAdapter;
import com.ning.ybsxpss.adapter.PickingListAdapter;
import com.ning.ybsxpss.adapter.SourceGoodsListAdapter;
import com.ning.ybsxpss.entity.SourceGoodsListObj;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;


public class SourceGoodsListActivity extends BaseActivity {
    private SourceGoodsListObj obj;
    private ListView listview_source_goods_list;
    private Button but_source_goods_list;
    private ImageView iv_back;
    private IndentModel model;
    private String orderId;
    private SourceGoodsListAdapter adapter;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                adapter = new SourceGoodsListAdapter(SourceGoodsListActivity.this,obj.getList());
                listview_source_goods_list.setAdapter(adapter);
            }
            if(msg.what==2) {
                shuaxin();
            }
            if(msg.what==3) {
                adapter.refreshData(obj.getList());
            }
        }
    };
    private BroadcastReceiver RFIDFinishReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("rfid_card_found1")){ //来源填报成功，更新填报货物数据
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        }
    };

    /**
     * 监听Back键按下事件,方法1:
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        System.out.println("按下了back键   onBackPressed()");
        Intent intent = new Intent();
        intent.setAction("rfid_card_founda");
        SourceGoodsListActivity.this.sendBroadcast(intent);
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_goods_list);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
        //接收广播
        IntentFilter filter= new IntentFilter();
        filter.addAction("rfid_card_found1");
        registerReceiver(RFIDFinishReceiver , filter);
    }
    private void getmodel() {
        Bundle bundle = getIntent().getExtras();
        orderId = bundle.getString("orderId");
        model = new IndentModel();
        model.getFillSourceGoodsList(orderId, new ICallBack() {
            @Override
            public void succeed(Object object) {
                obj = (SourceGoodsListObj) object;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
    }
    private void shuaxin() {
        model.getFillSourceGoodsList(orderId, new ICallBack() {
            @Override
            public void succeed(Object object) {
                obj = (SourceGoodsListObj) object;
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
    }
    private void setListener() {
        but_source_goods_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SourceGoodsListActivity.this,SupplierInfoActivity.class);
                if(SourceGoodsListAdapter.list2.size()==0){
                    Toast.makeText(SourceGoodsListActivity.this,"请先选择商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("useridd",obj.getOrderId());
                startActivity(intent);
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
        listview_source_goods_list = (ListView) findViewById(R.id.listview_source_goods_list);
        but_source_goods_list = (Button) findViewById(R.id.but_source_goods_list);
        iv_back = findViewById(R.id.iv_source_goods_back);
    }
}
