package com.ning.ybsxpss.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DaishouliAdapter;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.entity.WaitDisposeObj;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxn on 2017/9/22.
 */

public class DaishouliFragment extends android.support.v4.app.Fragment{
    private View view;
    private TextView tv_dsl_jgdd,tv_dsl_plcldd;
    private XRecyclerView mRecyclerView;
    private DaishouliAdapter mAdapter;

    private IndentModel model;
    private int num = 10;
    private int page = 1;
    private int listAllZise = 0;
    private int pp = 0;
    private List<WaitDisposeList> lists = new ArrayList<>();
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
                                onRefreshlist(); //下拉刷新第一页，更新最新内容
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
                mAdapter = new DaishouliAdapter(getActivity(),lists,model,handler);
                mRecyclerView.setAdapter(mAdapter);

            }
            if(msg.what==2) { //下拉将选中移除
                mAdapter.refreshData(lists);
                mRecyclerView.refreshComplete();
                for (int i = 0; i<DaishouliAdapter.flag.length;i++){
                    if(DaishouliAdapter.flag[i]==true){
                        DaishouliAdapter.flag[i]=false;
                    }
                }
                DaishouliAdapter.list.clear();
                tv_dsl_jgdd.setText("共选择了"+DaishouliAdapter.list.size()+"个订单");
            }
            if(msg.what==3) {
                mAdapter.addData(lists);
                mRecyclerView.loadMoreComplete();
            }
            if(msg.what==4) { //单项进行了受理或拒绝，刷新，选中移除
                flush(msg.arg1);
                mRecyclerView.scrollToPosition(msg.arg1);
                for (int i = 0; i<DaishouliAdapter.flag.length;i++){
                    if(DaishouliAdapter.flag[i]==true){
                        DaishouliAdapter.flag[i]=false;
                    }
                }
                DaishouliAdapter.list.clear();
            }
            if(msg.what==5) {
                mAdapter.refreshData(lists);//专门刷新列表
            }
            if(msg.what==6) {
                tv_dsl_jgdd.setText("共选择了"+DaishouliAdapter.list.size()+"个订单");
            }
            if(msg.what==7) {//批量受理选择后也移除选中
                for (int i = 0; i<DaishouliAdapter.flag.length;i++){
                    if(DaishouliAdapter.flag[i]==true){
                        DaishouliAdapter.flag[i]=false;
                    }
                }
                DaishouliAdapter.list.clear();
                tv_dsl_jgdd.setText("共选择了"+DaishouliAdapter.list.size()+"个订单");
                onRefreshlist(); //回归第一页数据
            }
        }
    };

    private BroadcastReceiver RFIDFinishReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("rfid_card_found")){
                int position = intent.getExtras().getInt("position");
                Message message = new Message();
                message.what = 4;
                message.arg1 = position;
                handler.sendMessage(message);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daishouli,null);
        setView();
        setListener();
        getmodel(); //初始化第一页数据，数量，总页数信息
        //接收广播
        IntentFilter filter= new IntentFilter();
        filter.addAction("rfid_card_found"); //待受理进入订单详情后被受理等操作，传回位置，重新加载
        getActivity().registerReceiver(RFIDFinishReceiver , filter);
        return view;
    }

    private void getmodel() {
        model = new IndentModel();
        page = 1;
        model.getOrderList_ddsl(page, num, new ICallBack() {
            @Override
            public void succeed(Object object) {
                WaitDisposeObj obj = (WaitDisposeObj) object;
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
            public void error(Object object) {}
        });
    }
    private void setListener() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        tv_dsl_plcldd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = DaishouliAdapter.list.toString();
                str = str.replace(" ", "");
                final String s = str.substring(1, str.length()-1);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("请选择受理情况");
                builder.setPositiveButton("确认受理", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        model.dealWithOrder(s, "1", new ICallBack() {
                            @Override
                            public void succeed(Object object) {
                                LoginObg obj= (LoginObg) object;
                                if(obj.isSuccess()) {
                                    Message message = new Message();
                                    message.what = 7;
                                    handler.sendMessage(message);
                                }
                            }
                            public void error(Object object) {
                            }
                        });
                    }
                });
                builder.setNegativeButton("拒绝受理", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        model.dealWithOrder(s, "2", new ICallBack() {
                            @Override
                            public void succeed(Object object) {
                                LoginObg obj= (LoginObg) object;
                                if(obj.isSuccess()) {
                                    Message message = new Message();
                                    message.what = 7;
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
    }
    /**
     * 下拉加载
     */
    private void onRefreshlist(){
        page = 1;
        model.getOrderList_ddsl(page, num, new ICallBack() {
            @Override
            public void succeed(Object object) {
                WaitDisposeObj obj = (WaitDisposeObj) object;
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
     * 上拉加载
     */
    private void onLoadMorelist(){
        page ++;
        model.getOrderList_ddsl(page, num, new ICallBack() {
            @Override
            public void succeed(Object object) {
                WaitDisposeObj obj = (WaitDisposeObj) object;
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
        model.getOrderList_ddsl(page, num, new ICallBack() {
            @Override
            public void succeed(Object object) {
                WaitDisposeObj obj = (WaitDisposeObj) object;
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
    private void setView() {
        mRecyclerView = view.findViewById(R.id.recycler_daishouli);
        tv_dsl_jgdd = view.findViewById(R.id.tv_dsl_jgdd);
        tv_dsl_plcldd = view.findViewById(R.id.tv_dsl_plcldd);
    }
}
