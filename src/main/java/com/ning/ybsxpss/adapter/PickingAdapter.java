package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.PickingList;
import com.ning.ybsxpss.entity.PickingListObj;

import java.util.List;


/**
 * Created by Ywg on 2016/6/29.
 */
public class PickingAdapter extends BaseAdapter {

    private Context mContext;
    private List<PickingList> mItemList;
    private LayoutInflater mInflater;

    public PickingAdapter(Context context, List<PickingList> itemList) {
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
            convertView = mInflater.inflate(R.layout.list_item_picking, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_picking_id = convertView.findViewById(R.id.item_pick_id);
            viewHolder.item_picking_name = convertView.findViewById(R.id.item_pick_name);
            viewHolder.item_picking_jin = convertView.findViewById(R.id.item_pick_jin);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final PickingList item = mItemList.get(position);
        viewHolder.item_picking_id.setText(item.getOrderNo());
        viewHolder.item_picking_name.setText(item.getCompanyName());
        viewHolder.item_picking_jin.setText(item.getAmount()+"/"+item.getGoodsUnit());
        return convertView;
    }

    class ViewHolder {
        private TextView item_picking_id;
        private TextView item_picking_name;
        private TextView item_picking_jin;
    }

    public void refreshData(List<PickingList> itemList) {
        if (mItemList.size() != 0 && mItemList != null) {
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<PickingList> itemList) {
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

}
