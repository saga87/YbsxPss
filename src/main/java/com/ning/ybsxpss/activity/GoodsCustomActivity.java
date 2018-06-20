package com.ning.ybsxpss.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DaishouliAdapter;
import com.ning.ybsxpss.adapter.GoodsCustomAdapter;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.MadeGoodsList;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.entity.WaitDisposeObj;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.model.MyClientModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class GoodsCustomActivity extends BaseActivity {
    private TextView tv_dsl_jgdd;
    private Button tv_dsl_plcldd;
    private CheckBox cb_goods_custom;
    private RecyclerView mRecyclerView;
    private ImageView iv_back;
    private GoodsCustomAdapter mAdapter;

    private MyClientModel model;
    private List<MadeGoodsList> lists = new ArrayList<>();
    private String purchaserId;
    private int position;
    private String str = "";
    private String str1 = "";


    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                mAdapter = new GoodsCustomAdapter(GoodsCustomActivity.this,lists,model,handler);
                mRecyclerView.setAdapter(mAdapter);
            }
            if(msg.what==2) {
                Toast.makeText(GoodsCustomActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                GoodsCustomAdapter.customList.clear();
                //设置checkBox改变监听
                GoodsCustomAdapter.map.clear();
                finish();
            }
            if(msg.what==3) {
               cb_goods_custom.setChecked(false);
            }
            if(msg.what==4) {
                tv_dsl_jgdd.setText("共选择了"+GoodsCustomAdapter.customList.size()+"个商品");
            }
            if(msg.what==5) {
                Toast.makeText(GoodsCustomActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_custom);
        Utility.setToolbar(this);
        setView();
        //接收上个界面的数据
        Bundle bundle = getIntent().getExtras();
        purchaserId = bundle.getString("purchaserId");
        position = bundle.getInt("position");

        setListener();
        getmodel();
    }

    private void getmodel() {
        model = new MyClientModel();
        model.getMadeGoodsList(purchaserId, new ICallBack() {
            @Override
            public void succeed(Object object) {
                lists = (List<MadeGoodsList>) object;
                if (lists==null){
                    lists = new ArrayList<>();
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {}
        });
    }
    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GoodsCustomAdapter.customList.clear();
        //设置checkBox改变监听
        GoodsCustomAdapter.map.clear();
    }
    private void setListener() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(GoodsCustomActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        cb_goods_custom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    for (int i = 0; i<GoodsCustomAdapter.map.size();i++){
                        // 设置CheckBox的状态
                        GoodsCustomAdapter.map.put(i, true);
                    }
                    mAdapter.notifyDataSetChanged();
                }else {
                    for (int i = 0; i < GoodsCustomAdapter.map.size(); i++) {
                        // 设置CheckBox的状态
                        if(!GoodsCustomAdapter.map.get(i)){
                            cb_goods_custom.setChecked(false);
                            return;
                        }
                    }
                    for (int i = 0; i < GoodsCustomAdapter.map.size(); i++) {
                        // 设置CheckBox的状态
                        GoodsCustomAdapter.map.put(i, false);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        tv_dsl_plcldd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < GoodsCustomAdapter.customList.size(); i++) {
                    str = str+GoodsCustomAdapter.customList.get(i)+",";
                }
                for (int i = 0; i < GoodsCustomAdapter.texts.size(); i++) {
                    if(GoodsCustomAdapter.map.get(i)) {
                        str1 = str1 + GoodsCustomAdapter.texts.get(i).getPri() + ",";
                    }
                }
                if(str.equals("")||str1.equals("")){
                    Toast.makeText(GoodsCustomActivity.this,"请先选择商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                String s = str.substring(0, str.length() - 1);
                String s1 = str1.substring(0, str1.length() - 1);
                model.addMadeGoods(purchaserId, s, s1, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()){
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);
                        }else {
                            Message message = new Message();
                            message.what = 5;
                            handler.sendMessage(message);
                        }
                    }
                    public void error(Object object) {}
                });
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsCustomAdapter.customList.clear();
                //设置checkBox改变监听
                GoodsCustomAdapter.map.clear();
                finish();
            }
        });
    }
    private void setView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_goods_custom);
        tv_dsl_jgdd = (TextView) findViewById(R.id.tv_goods_custom_dd);
        tv_dsl_plcldd = (Button) findViewById(R.id.tv_goods_custom_bc);
        cb_goods_custom = (CheckBox) findViewById(R.id.cb_goods_custom);
        iv_back = findViewById(R.id.iv_goods_custom_back);
    }
}
