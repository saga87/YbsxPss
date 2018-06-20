package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.AlterSupplierActivity;
import com.ning.ybsxpss.entity.DzdList;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.SupplierList;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class DzdListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<DzdList> texts;
    private LayoutInflater inflater;
    private Handler handler;
    private SupplierModel model;
    private Context context;
    java.text.DecimalFormat df=new java.text.DecimalFormat("######0.00");

    public DzdListAdapter(Context context, List<DzdList> mList, Handler handler, SupplierModel model) {
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
        View view = inflater.inflate(R.layout.list_item_dzd, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder hold = (MyViewHolder) holder;
        final DzdList text = texts.get(position);
        hold.tv_item_dzd_name.setText(text.getGoodsName());
        hold.tv_item_dzd_price.setText(df.format(text.getPrice())+"元/"+text.getGoodsUnit()+"×"+text.getAmount());
        hold.tv_item_dzd_add.setText("金额："+df.format(text.getMoney()));
        hold.tv_item_dzd_cgr.setText(text.getPurchaserName());
        hold.tv_item_dzd_time.setText(text.getSignTime());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_dzd_name;
        TextView tv_item_dzd_price;
        TextView tv_item_dzd_add;
        TextView tv_item_dzd_cgr;
        TextView tv_item_dzd_time;
        public MyViewHolder(View view) {
            super(view);
            tv_item_dzd_name = view.findViewById(R.id.tv_item_dzd_name);
            tv_item_dzd_price = view.findViewById(R.id.tv_item_dzd_price);
            tv_item_dzd_add = view.findViewById(R.id.tv_item_dzd_add);
            tv_item_dzd_cgr = view.findViewById(R.id.tv_item_dzd_cgr);
            tv_item_dzd_time = view.findViewById(R.id.tv_item_dzd_time);
        }
    }

    public void refreshData(List<DzdList> itemList) {
        if (texts.size() != 0 && texts != null) {
            texts.clear();
        }
        texts.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<DzdList> itemList) {
        if(itemList!=null) {
            texts.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
