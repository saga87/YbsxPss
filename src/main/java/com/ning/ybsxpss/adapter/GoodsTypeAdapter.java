package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.FoodStyle;
import com.ning.ybsxpss.entity.GoodsThreeType;
import com.ning.ybsxpss.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ywg on 2016/6/29.
 */
public class GoodsTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoodsThreeType> mItemList;
    private LayoutInflater mInflater;
    public static List<String> nums = new ArrayList<>();

    public GoodsTypeAdapter(Context context, List<GoodsThreeType> itemList) {
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_goods_type, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_item_type_name = convertView.findViewById(R.id.tv_item_type_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final GoodsThreeType item = mItemList.get(position);
        viewHolder.tv_item_type_name.setText(item.getName());
        viewHolder.tv_item_type_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag=false;
                for (int i = 0; i < nums.size(); i++) {
                    String str1 = nums.get(i);
                    String str2 = item.getId();
                    if (str1.equals(str2)) {
                        nums.remove(item.getId());
                        viewHolder.tv_item_type_name.setBackgroundColor(Color.WHITE);
                        flag=true;
                        break;
                    } else {
                    }
                }
                if(!flag){
                    nums.add(item.getId());
                    viewHolder.tv_item_type_name.setBackgroundColor(Color.parseColor("#6b947c"));
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tv_item_type_name;
    }

    public void refreshData(List<GoodsThreeType> itemList) {
        if (mItemList.size() != 0 && mItemList != null) {
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }
    public void addData(List<GoodsThreeType> itemList) {
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }
}
