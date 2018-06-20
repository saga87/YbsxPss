package com.ning.ybsxpss.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.ShangJiaAdapter;
import com.ning.ybsxpss.adapter.XiaJiaAdapter;
import com.ning.ybsxpss.adapter.YifahuoAdapter;
import com.ning.ybsxpss.app.App;
import com.ning.ybsxpss.entity.GoodsMaintainList;
import com.ning.ybsxpss.entity.GoodsMaintainObj;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.entity.WaitDisposeObj;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxn on 2017/9/30.
 */

public class XiaJiaFragment extends android.support.v4.app.Fragment {
    private View view;
    private XRecyclerView mRecyclerView;
    private XiaJiaAdapter mAdapter;

    private GoodsModel model;
    private int num = 10;
    private int page = 1;
    private int listAllZise = 0;
    private int pp = 0;
    private List<GoodsMaintainList> lists = new ArrayList<>();
    private LoginBroadcastReceiver mReceiver;
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
                mAdapter = new XiaJiaAdapter(getActivity(),lists,model,handler);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_xiajia,null);
        setView();
        setListener();
        getmodel();
        return view;
    }

    private void getmodel() {
        //注册广播方法
        mReceiver = new XiaJiaFragment.LoginBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("shangxia");
        LocalBroadcastManager.getInstance(App.getContext()).registerReceiver(mReceiver, intentFilter);

        model = new GoodsModel();
        page = 1;
        model.getGoodsList(page, num,"1", new ICallBack() {
            @Override
            public void succeed(Object object) {
                GoodsMaintainObj obj = (GoodsMaintainObj) object;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    }
    /**
     * 自定义广播接受器,用来处理登录广播
     */
    private class LoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("shangxia")) {
                page = 1;
                flush(1);
            }
        }
    }

    /**
     * 下拉加载
     */
    private void onRefreshlist(){
        page = 1;
        model.getGoodsList(page, num,"1", new ICallBack() {
            @Override
            public void succeed(Object object) {
                GoodsMaintainObj obj = (GoodsMaintainObj) object;
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
        model.getGoodsList(page, num,"1", new ICallBack() {
            @Override
            public void succeed(Object object) {
                GoodsMaintainObj obj = (GoodsMaintainObj) object;
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
        model.getGoodsList(page, num,"1", new ICallBack() {
            @Override
            public void succeed(Object object) {
                GoodsMaintainObj obj = (GoodsMaintainObj) object;
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
        mRecyclerView = view.findViewById(R.id.recycler_xiajia);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }
}
