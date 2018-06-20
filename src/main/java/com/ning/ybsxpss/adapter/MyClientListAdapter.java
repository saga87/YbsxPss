package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.GoodsCustomActivity;
import com.ning.ybsxpss.entity.DzdList;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.MyClientList;
import com.ning.ybsxpss.model.MyClientModel;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.view.MyDialog;

import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class MyClientListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<MyClientList> texts;
    private LayoutInflater inflater;
    private Handler handler;
    private MyClientModel model;
    private Context context;

    public MyClientListAdapter(Context context, List<MyClientList> mList, Handler handler, MyClientModel model) {
        this.texts = mList;
        this.context = context;
        this.handler = handler;
        this.model = model;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_myclientlist, parent, false);
        return new MyViewHolder(view);
    }
    //正常   商品定制
    //无效   删除   申请合作
    //null   申请合作
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder hold = (MyViewHolder) holder;
        final MyClientList text = texts.get(position);
        hold.tv_item_myclient_name.setText(text.getCompanyName());
        hold.tv_item_myclient_add.setText(text.getAddr());
        hold.tv_item_myclient_username.setText(text.getName());
        hold.tv_item_myclient_tel.setText(text.getPhone());
        hold.tv_item_myclient_time.setText(text.getCoop_term());
        if(texts.get(position).getStatus()!=null) {
            switch (texts.get(position).getStatus()) {
                case "0":
                    hold.tv_item_myclient_zc.setText("待处理");
                    hold.tv_item_myclient_zc.setTextColor(Color.parseColor("#359f01"));
                    hold.btn_item_myclient_hz.setVisibility(View.GONE);
                    hold.btn_item_myclient_sc.setVisibility(View.GONE);
                    hold.btn_item_myclient_dz.setVisibility(View.GONE);
                    break;
                case "1":
                    hold.tv_item_myclient_zc.setText("正常");
                    hold.tv_item_myclient_zc.setTextColor(Color.parseColor("#359f01"));
                    hold.btn_item_myclient_hz.setVisibility(View.GONE);
                    hold.btn_item_myclient_sc.setVisibility(View.GONE);
                    hold.btn_item_myclient_dz.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    hold.tv_item_myclient_zc.setText("已拒绝");
                    hold.tv_item_myclient_zc.setTextColor(Color.RED);
                    hold.btn_item_myclient_hz.setVisibility(View.GONE);
                    hold.btn_item_myclient_sc.setVisibility(View.GONE);
                    hold.btn_item_myclient_dz.setVisibility(View.GONE);
                    break;
                case "3":
                    hold.tv_item_myclient_zc.setText("无效");
                    hold.tv_item_myclient_zc.setTextColor(Color.RED);
                    hold.btn_item_myclient_hz.setVisibility(View.VISIBLE);
                    hold.btn_item_myclient_sc.setVisibility(View.VISIBLE);
                    hold.btn_item_myclient_dz.setVisibility(View.GONE);
                    break;
            }
        } else {
            hold.tv_item_myclient_zc.setText("- -");
            hold.btn_item_myclient_hz.setVisibility(View.VISIBLE);
            hold.btn_item_myclient_sc.setVisibility(View.GONE);
            hold.btn_item_myclient_dz.setVisibility(View.GONE);
        }
        hold.btn_item_myclient_hz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog dialog = new MyDialog(context,text.getPurchaserId(),text.getCompanyName(),model,handler,position);
                dialog.show();
            }
        });
        hold.btn_item_myclient_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.delApply(text.getCooperationId(), new ICallBack() {
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()){
                            Message message = new Message();
                            message.what = 4;
                            message.arg1 = position;
                            handler.sendMessage(message);
                        }
                    }
                    public void error(Object object) {}
                });
            }
        });
        hold.btn_item_myclient_dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsCustomActivity.class);
                intent.putExtra("purchaserId",text.getPurchaserId());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_myclient_name;
        TextView tv_item_myclient_add;
        TextView tv_item_myclient_username;
        TextView tv_item_myclient_tel;
        TextView tv_item_myclient_time;
        TextView tv_item_myclient_zc;
        Button btn_item_myclient_hz;
        Button btn_item_myclient_sc;
        Button btn_item_myclient_dz;
        public MyViewHolder(View view) {
            super(view);
            tv_item_myclient_name = view.findViewById(R.id.tv_item_myclient_name);
            tv_item_myclient_add = view.findViewById(R.id.tv_item_myclient_add);
            tv_item_myclient_username = view.findViewById(R.id.tv_item_myclient_username);
            tv_item_myclient_tel = view.findViewById(R.id.tv_item_myclient_tel);
            tv_item_myclient_time = view.findViewById(R.id.tv_item_myclient_time);
            tv_item_myclient_zc = view.findViewById(R.id.tv_item_myclient_zc);
            btn_item_myclient_hz = view.findViewById(R.id.btn_item_myclient_hz);
            btn_item_myclient_sc = view.findViewById(R.id.btn_item_myclient_sc);
            btn_item_myclient_dz = view.findViewById(R.id.btn_item_myclient_dz);
        }
    }

    public void refreshData(List<MyClientList> itemList) {
        if (texts.size() != 0 && texts != null) {
            texts.clear();
        }
        texts.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<MyClientList> itemList) {
        if(itemList!=null) {
            texts.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
