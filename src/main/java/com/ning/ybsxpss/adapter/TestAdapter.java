package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.MadeGoodsList;

import java.util.List;

/**
 * Created by wlp on 2018/3/13.
 */

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MadeGoodsList> goodsLists;
    private Context context;

    public TestAdapter(Context ctx,List<MadeGoodsList> madeGoodsLists) {
        context = ctx;
        goodsLists = madeGoodsLists;
    }

    class MyAdapterHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_goods_custom;
        TextView tv_item_goods_custom_name;

        View goodsView;//为RecyclerView子项加点击事件

        public MyAdapterHolder(View view) {
            super(view);
            goodsView = view;
            //viewholder缓存布局对象的属性
            iv_item_goods_custom = view.findViewById(R.id.iv_item_goods_custom);
            tv_item_goods_custom_name = view.findViewById(R.id.tv_item_goods_custom_name);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //布局
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_goods_custom, parent, false);
        //传自定义viewholder
        final MyAdapterHolder holder = new MyAdapterHolder(v);
        holder.goodsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                MadeGoodsList good = goodsLists.get(position);
                Toast.makeText(view.getContext(),"最外层View点击捕获:"+good.getGoodsName(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.tv_item_goods_custom_name.setOnClickListener(new View.OnClickListener() { //可以子项控件直接设置点击
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                MadeGoodsList good = goodsLists.get(position);
                Toast.makeText(view.getContext(),"货品名称点击捕获:"+good.getGoodsName(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyAdapterHolder myholder = (MyAdapterHolder) holder;
        MadeGoodsList goods = goodsLists.get(position);
        if (null != goods) {
            //对RecyclerView子项赋值
            myholder.tv_item_goods_custom_name.setText(goods.getGoodsName());
        }
    }

    @Override
    public int getItemCount() {
        return goodsLists.size();
    }
}
