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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.DaiFaHuoActivity;
import com.ning.ybsxpss.activity.OrderDetailsActivity;
import com.ning.ybsxpss.activity.PickingListActivity;
import com.ning.ybsxpss.activity.SourceGoodsListActivity;
import com.ning.ybsxpss.adapter.DaifahuoAdapter;
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

public class DaifahuoFragment extends android.support.v4.app.Fragment {
    private View view;
    private TextView tv_dfh_jgdd;
    private Button btn_dfh_scbhd,btn_dfh_tbly,btn_dfh_scyjd,btn_dfh_jdck;
    private XRecyclerView mRecyclerView;
    private DaifahuoAdapter mAdapter;

    private IndentModel model;
    private int num = 10;
    private int page = 1;
    private int listAllZise = 0;
    private int pp = 0;
    private List<WaitDisposeList> lists = new ArrayList<>();
    private String ckmassage = "";
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
                mAdapter = new DaifahuoAdapter(getActivity(),lists,model,handler);
                mRecyclerView.setAdapter(mAdapter);
            }
            if(msg.what==2) {
                mAdapter.refreshData(lists);
                mRecyclerView.refreshComplete();
                for (int i = 0; i<DaifahuoAdapter.flag1.length;i++){
                    if(DaifahuoAdapter.flag1[i]==true){
                        DaifahuoAdapter.flag1[i]=false;
                    }
                }
                DaifahuoAdapter.list1.clear();
                tv_dfh_jgdd.setText("共选择了"+DaifahuoAdapter.list1.size()+"个订单");
            }
            if(msg.what==3) {
                mAdapter.addData(lists);
                mRecyclerView.loadMoreComplete();
            }
            if(msg.what==4) {
                flush(msg.arg1);
                mRecyclerView.scrollToPosition(msg.arg1);
                for (int i = 0; i<DaifahuoAdapter.flag1.length;i++){
                    if(DaifahuoAdapter.flag1[i]==true){
                        DaifahuoAdapter.flag1[i]=false;
                    }
                }
                DaifahuoAdapter.list1.clear();
                tv_dfh_jgdd.setText("共选择了"+DaifahuoAdapter.list1.size()+"个订单");
            }
            if(msg.what==5) {
                mAdapter.refreshData(lists);
            }
            if(msg.what==6) {
                tv_dfh_jgdd.setText("共选择了"+DaifahuoAdapter.list1.size()+"个订单");
            }
            if(msg.what==7) {
                for (int i = 0; i<DaifahuoAdapter.flag1.length;i++){
                    if(DaifahuoAdapter.flag1[i]==true){
                        DaifahuoAdapter.flag1[i]=false;
                    }
                }
                DaifahuoAdapter.list1.clear();
                tv_dfh_jgdd.setText("共选择了"+DaifahuoAdapter.list1.size()+"个订单");
                onRefreshlist();
                Toast.makeText(getContext(),"出库成功",Toast.LENGTH_SHORT).show();
            }
            if(msg.what==8) {
                for (int i = 0; i<DaifahuoAdapter.flag1.length;i++){
                    if(DaifahuoAdapter.flag1[i]==true){
                        DaifahuoAdapter.flag1[i]=false;
                    }
                }
                DaifahuoAdapter.list1.clear();
                tv_dfh_jgdd.setText("共选择了"+DaifahuoAdapter.list1.size()+"个订单");
                onRefreshlist();
            }
            if(msg.what==9) {
                Toast.makeText(getContext(),"出库失败"+ckmassage,Toast.LENGTH_SHORT).show();
            }
        }
    };

    private BroadcastReceiver RFIDFinishReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("rfid_card_found2")){
                int position = intent.getExtras().getInt("position");
                Message message = new Message();
                message.what = 4;
                message.arg1 = position;
                handler.sendMessage(message);
            }
            if(intent.getAction().equals("rfid_card_founda")){ //填报来源按了返回键，清空选中
                Message message = new Message();
                message.what = 8;
                handler.sendMessage(message);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daifahuo,null);
        setView();
        setListener();
        getmodel();
        //接收广播
        IntentFilter filter= new IntentFilter();
        filter.addAction("rfid_card_found2");
        filter.addAction("rfid_card_founda");
        getActivity().registerReceiver(RFIDFinishReceiver , filter);
        return view;
    }

    private void getmodel() {
        model = new IndentModel();
        page = 1;
        model.getOrderList_ddfh(page, num, new ICallBack() {
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
            public void error(Object object) {
            }
        });
    }
    private void setListener() {
        btn_dfh_scyjd.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        btn_dfh_scbhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DaifahuoAdapter.list1.size()==0){
                    Toast.makeText(getContext(),"请先选择商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), PickingListActivity.class);
                String str = DaifahuoAdapter.list1.toString();
                str = str.replace(" ", "");
                final String s = str.substring(1, str.length()-1);
                intent.putExtra("orderId",s);
                startActivity(intent);
            }
        });
        btn_dfh_tbly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DaifahuoAdapter.list1.size()==0){
                    Toast.makeText(getContext(),"请先选择商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), SourceGoodsListActivity.class);
                String str = DaifahuoAdapter.list1.toString();
                str = str.replace(" ", "");
                final String s = str.substring(1, str.length()-1);
                intent.putExtra("orderId",s);
                startActivity(intent);
            }
        });
        btn_dfh_jdck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DaifahuoAdapter.list1.size()==0){
                    Toast.makeText(getContext(),"请先选择商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                String str = DaifahuoAdapter.list1.toString();
                str = str.replace(" ", "");
                final String s = str.substring(1, str.length()-1);
                model.outboundOrder(s, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()) {
                            Message message = new Message();
                            message.what = 7;
                            handler.sendMessage(message);
                        }else {
                            Message message = new Message();
                            message.what = 9;
                            ckmassage = obg.getMsg();
                            handler.sendMessage(message);
                        }
                    }
                    @Override
                    public void error(Object object) {
                    }
                });
            }
        });
    }

    /**
     * 下拉加载
     */
    private void onRefreshlist(){
        page = 1;
        model.getOrderList_ddfh(page, num, new ICallBack() {
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
     * 下拉加载
     */
    private void onLoadMorelist(){
        page ++;
        model.getOrderList_ddfh(page, num, new ICallBack() {
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
        model.getOrderList_ddfh(page, num, new ICallBack() {
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
        mRecyclerView = view.findViewById(R.id.recycler_daifahuo);
        tv_dfh_jgdd = view.findViewById(R.id.tv_dfh_jgdd);
        btn_dfh_scbhd = view.findViewById(R.id.btn_dfh_scbhd);
        btn_dfh_tbly = view.findViewById(R.id.btn_dfh_tbly);
        btn_dfh_scyjd = view.findViewById(R.id.btn_dfh_scyjd);
        btn_dfh_jdck = view.findViewById(R.id.btn_dfh_jdck);
    }
}
