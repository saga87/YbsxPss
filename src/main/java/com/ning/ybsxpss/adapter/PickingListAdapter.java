package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.DetailList;
import com.ning.ybsxpss.entity.PickingListObj;
import com.ning.ybsxpss.util.UrlUtil;
import com.ning.ybsxpss.util.Utility;

import java.util.List;


/**
 * Created by Ywg on 2016/6/29.
 */
public class PickingListAdapter extends BaseAdapter {

    private Context mContext;
    private List<PickingListObj> mItemList;
    private LayoutInflater mInflater;

    public PickingListAdapter(Context context, List<PickingListObj> itemList) {
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
            convertView = mInflater.inflate(R.layout.list_item_pickinglist, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_picking_name = convertView.findViewById(R.id.item_picking_name);
            viewHolder.item_picking_jin = convertView.findViewById(R.id.item_picking_jin);
            viewHolder.item_picking_list = convertView.findViewById(R.id.item_picking_list);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final PickingListObj item = mItemList.get(position);
        viewHolder.item_picking_name.setText(item.getGoodsName());
        viewHolder.item_picking_jin.setText(item.getSum_amount()+"/"+item.getGoodsUnit());
        viewHolder.item_picking_list.setAdapter(new PickingAdapter(mContext,item.getGoodlist()));
        Utility.setListViewHeightBasedOnChildren(viewHolder.item_picking_list);
        return convertView;
    }

    class ViewHolder {
        private TextView item_picking_name;
        private TextView item_picking_jin;
        private ListView item_picking_list;
    }

    public void refreshData(List<PickingListObj> itemList) {
        if (mItemList.size() != 0 && mItemList != null) {
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<PickingListObj> itemList) {
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

}
