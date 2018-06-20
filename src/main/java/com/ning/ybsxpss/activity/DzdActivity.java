package com.ning.ybsxpss.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DzdListAdapter;
import com.ning.ybsxpss.adapter.SupplierListAdapter;
import com.ning.ybsxpss.entity.DzdList;
import com.ning.ybsxpss.entity.DzdNum;
import com.ning.ybsxpss.entity.DzdObj;
import com.ning.ybsxpss.entity.SupplierList;
import com.ning.ybsxpss.entity.SupplierObj;
import com.ning.ybsxpss.fragment.FilterFragment;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DzdActivity extends BaseActivity {
    private XRecyclerView mRecyclerView;
    private DzdListAdapter mAdapter;
    private ImageView iv_back;
    private View header;
    private TextView tv_filter;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerContent;

    private SupplierModel model;
    private int num = 10;
    private int page = 1;
    private int listAllZise = 0;
    private int pp = 0;
    private int count;
    private double money;
    private List<DzdList> lists = new ArrayList<>();
    java.text.DecimalFormat df=new java.text.DecimalFormat("######0.00");

    public static String purchaserId ="";
    public static String beginDate ="";
    public static String endDate ="";
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
                mAdapter = new DzdListAdapter(DzdActivity.this,lists,handler,model);
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
            if(msg.what==6) {
                TextView name = header.findViewById(R.id.tv_item_dzd_handle_name);
                TextView price = header.findViewById(R.id.tv_item_dzd_handle_add);
                name.setText("共查到了"+count+"条记录");
                price.setText("总金额:￥"+df.format(money));
            }
            if(msg.what==7) {
                mAdapter.refreshData(lists);
            }
            if(msg.what==8) {
                onRefresh();
            }
        }
    };
    private BroadcastReceiver RFIDFinishReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("rfid_card_dzd")){
                Message message = new Message();
                message.what = 8;
                handler.sendMessage(message);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzd);
        Utility.setToolbar(this);
        setView();
        setListener();
        getmodel();
        purchaserId ="";
        beginDate ="";
        endDate ="";
        if (beginDate.equals("")){
            tv_filter.setTextColor(Color.WHITE);
        }else {
            tv_filter.setTextColor(Color.RED);
        }
        try {
            //接收广播
            IntentFilter filter= new IntentFilter();
            filter.addAction("rfid_card_dzd");
            registerReceiver(RFIDFinishReceiver , filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getmodel() {
        model = new SupplierModel();
        page = 1;
        model.getZdList(page, num,purchaserId,beginDate,endDate, new ICallBack() {
            @Override
            public void succeed(Object object) {
                DzdObj obj = (DzdObj) object;
                lists = obj.getList();
                if (lists==null){
                    lists = new ArrayList<>();
                }
                listAllZise = Integer.valueOf(obj.getAllRecordNums());
                pp = lists.size();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
        model.getZdHz(purchaserId,beginDate,endDate,new ICallBack() {
            public void succeed(Object object) {
                DzdNum num = (DzdNum) object;
                count = num.getSum_count();
                money = num.getSum_money();
                Message message = new Message();
                message.what = 6;
                handler.sendMessage(message);
            }
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

        header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header_dzd, (ViewGroup)findViewById(android.R.id.content),false);
        mRecyclerView.addHeaderView(header);

        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开抽屉
                mDrawerLayout.openDrawer(mDrawerContent);
            }
        });

        Fragment fragment = new FilterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.drawer_content, fragment).commit();

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
        model.getZdList(page, num,purchaserId,beginDate,endDate, new ICallBack() {
            @Override
            public void succeed(Object object) {
                DzdObj obj = (DzdObj) object;
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
        model.getZdList(page, num,purchaserId,beginDate,endDate, new ICallBack() {
            @Override
            public void succeed(Object object) {
                DzdObj obj = (DzdObj) object;
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
        model.getZdList(page, num,purchaserId,beginDate,endDate, new ICallBack() {
            @Override
            public void succeed(Object object) {
                DzdObj obj = (DzdObj) object;
                lists = obj.getList();
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
     * 筛选
     */
    private void onRefresh(){
        page = 1;
        model.getZdList(page, num,purchaserId,beginDate,endDate, new ICallBack() {
            @Override
            public void succeed(Object object) {
                DzdObj obj = (DzdObj) object;
                lists = obj.getList();
                if (lists==null){
                    lists = new ArrayList<>();
                }
                pp = lists.size();
                Message message = new Message();
                message.what = 7;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
        model.getZdHz(purchaserId,beginDate,endDate,new ICallBack() {
            public void succeed(Object object) {
                DzdNum num = (DzdNum) object;
                count = num.getSum_count();
                money = num.getSum_money();
                Message message = new Message();
                message.what = 6;
                handler.sendMessage(message);
            }
            public void error(Object object) {
            }
        });
        if (purchaserId.equals("")){
            tv_filter.setTextColor(Color.WHITE);
        }else {
            tv_filter.setTextColor(Color.RED);
        }
    }
    private void setView() {
        mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_dzd);
        tv_filter = (TextView) findViewById(R.id.tv_filter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerContent = (FrameLayout) findViewById(R.id.drawer_content);
        iv_back = findViewById(R.id.iv_dzd_back);
    }
}
