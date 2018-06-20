package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.PickingListObj;
import com.ning.ybsxpss.entity.SourceGoodsList;
import com.ning.ybsxpss.util.Utility;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ywg on 2016/6/29.
 */
public class SourceGoodsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<SourceGoodsList> mItemList;
    private LayoutInflater mInflater;
    public static final List<String> list2 = new ArrayList<>();
    public static final boolean[] flag2 = new boolean[500];//此处添加一个boolean类型的数组

    public SourceGoodsListAdapter(Context context, List<SourceGoodsList> itemList) {
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
            convertView = mInflater.inflate(R.layout.list_item_sourcegoodslist, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = convertView.findViewById(R.id.item_sourcegoods_id);
            viewHolder.item_sourcegoods_name = convertView.findViewById(R.id.item_sourcegoods_name);
            viewHolder.item_sourcegoods_dui = convertView.findViewById(R.id.item_sourcegoods_dui);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SourceGoodsList item = mItemList.get(position);
        viewHolder.item_sourcegoods_name.setText(item.getClassificationName());
        if(item.getIsFillSource().equals("0")){
            viewHolder.item_sourcegoods_dui.setVisibility(View.GONE);
        }else {
            viewHolder.item_sourcegoods_dui.setVisibility(View.VISIBLE);
        }
        //CheckBox混乱问题
        viewHolder.checkBox.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
        viewHolder.checkBox.setChecked(flag2[position]);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag2[position] = b;
                if ( flag2[position] ){
                    list2.add(item.getGoodsId());
                }else {
                    list2.remove(item.getGoodsId());
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private CheckBox checkBox;
        private TextView item_sourcegoods_name;
        private ImageView item_sourcegoods_dui;
    }

    public void refreshData(List<SourceGoodsList> itemList) {
        if (mItemList.size() != 0 && mItemList != null) {
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<SourceGoodsList> itemList) {
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

}
