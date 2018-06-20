package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.AmendActivity;
import com.ning.ybsxpss.activity.YiFaHuoActivity;
import com.ning.ybsxpss.entity.FoodStyle;
import com.ning.ybsxpss.entity.GoodsMaintainList;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class ShangJiaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<GoodsMaintainList> texts;
    private LayoutInflater inflater;
    private Context context;
    private GoodsModel model;
    private Handler handler;
    private String price;
    private String goodsUnit;
    private String minAmount;
    java.text.DecimalFormat df=new java.text.DecimalFormat("######0.00");

    public ShangJiaAdapter(Context context, List<GoodsMaintainList> mList, GoodsModel model, Handler handler) {
        this.texts = mList;
        this.context = context;
        this.model = model;
        this.handler = handler;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_shangjia, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder hold = (MyViewHolder) holder;
        final GoodsMaintainList item = texts.get(position);
        String url =  UrlUtil.url+"/"+item.getImgUrl();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.timg)
                .crossFade()
                .into( hold.item_shangjia_image);
        if(item.getPrice()==0){
            price = "--";
        }else {
            price = df.format(item.getPrice());
        }
        if(item.getGoodsUnit()==null){
            goodsUnit = "--";
        }else {
            goodsUnit = item.getGoodsUnit();
        }
        if(item.getMinAmount()==0){
            minAmount = "--";
        }else {
            minAmount = df.format(item.getMinAmount());
        }
        hold.item_shangjia_id.setText(item.getClassificationName());
        hold.item_shangjia_name.setText(item.getGoodsName());
        hold.item_shangjia_price.setText(price +" 元/"+goodsUnit);
        hold.item_shangjia_type.setText("上架");
        hold.item_shangjia_num.setText("起订量："+ minAmount);
        hold.item_shangjia_xiajia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.soldoutOrPutawary(item.getGoodsId(), "1", new ICallBack() {
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if (obg.isSuccess()){
                            Message message = new Message();
                            message.what = 4;
                            message.arg1 = position;
                            handler.sendMessage(message);
                        }else{
                            Message message = new Message();
                            message.what = 5;
                            handler.sendMessage(message);
                        }
                    }
                    public void error(Object object) {
                    }
                });
            }
        });
        hold.item_shangjia_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AmendActivity.class);
                intent.putExtra("goodsId",item.getGoodsId());
                intent.putExtra("goodsName",item.getGoodsName());
                intent.putExtra("price",item.getPrice());
                intent.putExtra("minAmount",item.getMinAmount());
                intent.putExtra("imgUrl",item.getImgUrl());
                intent.putExtra("goodsUnit",item.getGoodsUnit());
                intent.putExtra("classificationName",item.getClassificationName());
                intent.putExtra("classificationId",item.getClassificationId());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_shangjia_image;
        TextView item_shangjia_id;
        TextView item_shangjia_name;
        TextView item_shangjia_price;
        TextView item_shangjia_type;
        TextView item_shangjia_num;
        Button item_shangjia_xiajia;
        Button item_shangjia_xiugai;
        public MyViewHolder(View view) {
            super(view);
            item_shangjia_image = view.findViewById(R.id.item_shangjia_image);
            item_shangjia_id = view.findViewById(R.id.item_shangjia_id);
            item_shangjia_name = view.findViewById(R.id.item_shangjia_name);
            item_shangjia_price = view.findViewById(R.id.item_shangjia_price);
            item_shangjia_type = view.findViewById(R.id.item_shangjia_type);
            item_shangjia_num = view.findViewById(R.id.item_shangjia_num);
            item_shangjia_xiajia = view.findViewById(R.id.item_shangjia_xiajia);
            item_shangjia_xiugai = view.findViewById(R.id.item_shangjia_xiugai);
        }
    }

    public void refreshData(List<GoodsMaintainList> itemList) {
        if (texts.size() != 0 && texts != null) {
            texts.clear();
        }
        texts.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<GoodsMaintainList> itemList) {
        if(itemList!=null) {
            texts.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
