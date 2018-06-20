package com.ning.ybsxpss.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.MyClientListAdapter;
import com.ning.ybsxpss.adapter.SupplierListAdapter;
import com.ning.ybsxpss.entity.MyClientList;
import com.ning.ybsxpss.entity.MyClientObj;
import com.ning.ybsxpss.entity.SupplierList;
import com.ning.ybsxpss.entity.SupplierObj;
import com.ning.ybsxpss.model.MyClientModel;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class MyClientActivity extends BaseActivity {
    private XRecyclerView mRecyclerView;
    private MyClientListAdapter mAdapter;
    private EditText et_myclient;
    private ImageView iv_myclient,iv_back;

    private MyClientModel model;
    private int num = 10;
    private int page = 1;
    private int listAllZise = 0;
    private int pp = 0;
    private List<MyClientList> lists = new ArrayList<>();
    private String purchaserName  = "";

    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onRefreshlist();
                            }
                        }, 1000);
                    }
                    @Override
                    public void onLoadMore() {
                        if(pp < listAllZise){
                            mRecyclerView.postDelayed(new Runnable() {
                                public void run() {
                                    onLoadMorelist();
                                }
                            }, 2000);
                        } else {
                            mRecyclerView.setNoMore(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                mAdapter = new MyClientListAdapter(MyClientActivity.this,lists,handler,model);
                mRecyclerView.setAdapter(mAdapter);
            }
            if(msg.what==2) {
                mAdapter.refreshData(lists);
                mRecyclerView.refreshComplete();
            }
            if(msg.what==3) {
                mAdapter.addData(lists);
                mRecyclerView.loadMoreComplete();
            }
            if(msg.what==4) {
                flush(msg.arg1);
                mRecyclerView.scrollToPosition(msg.arg1);
            }
            if(msg.what==5) {
                mAdapter.refreshData(lists);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_client);
        Utility.setToolbar(this);
        setView();
        setListener();
        getmodel();
    }

    private void getmodel() {
        model = new MyClientModel();
        page = 1;
        model.getCooperationList(page, num,purchaserName, new ICallBack() {
            @Override
            public void succeed(Object object) {
                MyClientObj obj = (MyClientObj) object;
                lists = obj.getList();
                if (lists==null){
                    lists = new ArrayList<>();
                }
                listAllZise = (int) obj.getAllRecordNums();
                pp = lists.size();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        iv_myclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchaserName = et_myclient.getText().toString();
                if(purchaserName.equals("")){
                    Toast.makeText(MyClientActivity.this,"请输入客户名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                shaixuan(purchaserName);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 下拉加载
     */
    private void onRefreshlist(){
        page = 1;
        model.getCooperationList(page, num,purchaserName, new ICallBack() {
            @Override
            public void succeed(Object object) {
                MyClientObj obj = (MyClientObj) object;
                lists = obj.getList();
                if (lists==null){
                    lists = new ArrayList<>();
                }
                pp = lists.size();
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
    }
    /**
     * 下拉加载
     */
    private void onLoadMorelist(){
        page ++;
        model.getCooperationList(page, num,purchaserName, new ICallBack() {
            @Override
            public void succeed(Object object) {
                MyClientObj obj = (MyClientObj) object;
                lists = obj.getList();
                pp= pp +lists.size();
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
    }
    /**
     * 刷新
     */
    private void flush(int position){
        page = position/10+1;
        model.getCooperationList(page, num,purchaserName, new ICallBack() {
            @Override
            public void succeed(Object object) {
                MyClientObj obj = (MyClientObj) object;
                lists = obj.getList();
                pp = lists.size();
                Message message = new Message();
                message.what = 5;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
    }
    /**
     * 刷新
     */
    private void shaixuan(String purchaserName){
        page = 1;
        model.getCooperationList(page, num,purchaserName, new ICallBack() {
            @Override
            public void succeed(Object object) {
                MyClientObj obj = (MyClientObj) object;
                lists = obj.getList();
                pp = lists.size();
                Message message = new Message();
                message.what = 5;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
    }
    private void setView() {
        mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_myclient);
        et_myclient = (EditText) findViewById(R.id.et_myclient);
        iv_myclient = (ImageView) findViewById(R.id.iv_myclient);
        iv_back = findViewById(R.id.iv_myclient_back);
    }
}
