package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.FoodStyle;
import com.ning.ybsxpss.util.UrlUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ywg on 2016/6/29.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<FoodStyle> mItemList;
    private LayoutInflater mInflater;

    public ImageAdapter(Context context, List<FoodStyle> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_image, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iv_item_image = convertView.findViewById(R.id.iv_item_image);
            viewHolder.tv_tiem_foodname = convertView.findViewById(R.id.tv_tiem_foodname);
            viewHolder.tv_tiem_foodprice = convertView.findViewById(R.id.tv_tiem_foodprice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FoodStyle item = mItemList.get(position);
        String url =  UrlUtil.url+"/"+item.getImgUrl();
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.timg)
                .crossFade()
                .into(viewHolder.iv_item_image);
        viewHolder.tv_tiem_foodname.setText(item.getClassificationName());
        viewHolder.tv_tiem_foodprice.setText(item.getPrice()+"/"+item.getGoodsUnit()+"  ×"+item.getAmount());
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_item_image;
        private TextView tv_tiem_foodname;
        private TextView tv_tiem_foodprice;
    }

    public void refreshData(List<FoodStyle> itemList) {
        if (mItemList.size() != 0 && mItemList != null) {
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<FoodStyle> itemList) {
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

}
