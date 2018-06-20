package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.CheckReportActivity;
import com.ning.ybsxpss.activity.FillSourceActivity;
import com.ning.ybsxpss.entity.DetailList;
import com.ning.ybsxpss.entity.FoodStyle;
import com.ning.ybsxpss.util.UrlUtil;

import java.util.List;


/**
 * Created by Ywg on 2016/6/29.
 */
public class DetailsAdapter extends BaseAdapter {

    private Context mContext;
    private List<DetailList> mItemList;
    private LayoutInflater mInflater;

    public DetailsAdapter(Context context, List<DetailList> itemList) {
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
            convertView = mInflater.inflate(R.layout.list_item_details, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iv_item_details_photo = convertView.findViewById(R.id.iv_item_details_photo);
            viewHolder.tv_item_details_name = convertView.findViewById(R.id.tv_item_details_name);
            viewHolder.tv_item_details_price = convertView.findViewById(R.id.tv_item_details_price);
            viewHolder.tv_item_details_num = convertView.findViewById(R.id.tv_item_details_num);
            viewHolder.tv_item_details_ly = convertView.findViewById(R.id.tv_item_details_ly);
            viewHolder.tv_item_details_jc = convertView.findViewById(R.id.tv_item_details_jc);
            viewHolder.tv_item_details_jishu = convertView.findViewById(R.id.tv_item_details_jishu);
            viewHolder.et_item_details_ly = convertView.findViewById(R.id.et_item_details_ly);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DetailList item = mItemList.get(position);
        String url =  UrlUtil.url+"/"+item.getImgUrl();
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.timg)
                .crossFade()
                .into(viewHolder.iv_item_details_photo);
        viewHolder.tv_item_details_name.setText(item.getClassificationName());
        viewHolder.tv_item_details_price.setText(item.getPrice()+"元"+"/"+item.getGoodsUnit());
        viewHolder.tv_item_details_num.setText("×"+item.getAmount());
        viewHolder.tv_item_details_jishu.setText("实际成交量： "+item.getActualAmount());
        viewHolder.et_item_details_ly.setText(item.getMessage());
        if(item.getIsFillSource().equals("已填报")) {
            viewHolder.tv_item_details_ly.setTextColor(Color.parseColor("#0e8c43"));
            viewHolder.tv_item_details_ly.setText("已填报");
            viewHolder.tv_item_details_ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FillSourceActivity.class);
                    intent.putExtra("orderDetailId", item.getOrderDetailId());
                    mContext.startActivity(intent);
                }
            });
        }else {
            viewHolder.tv_item_details_ly.setTextColor(Color.RED);
            viewHolder.tv_item_details_ly.setText("未填报");
            viewHolder.tv_item_details_ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"未填报",Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(item.getIsCheck().equals("合格")) {
            viewHolder.tv_item_details_jc.setTextColor(Color.parseColor("#0e8c43"));
            viewHolder.tv_item_details_jc.setText("合格");
            viewHolder.tv_item_details_jc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CheckReportActivity.class);
                    intent.putExtra("orderDetailId", item.getOrderDetailId());
                    mContext.startActivity(intent);
                }
            });
        }else {
        }
        if(item.getIsCheck().equals("不合格")){
            viewHolder.tv_item_details_jc.setTextColor(Color.RED);
            viewHolder.tv_item_details_jc.setText("不合格");
        }else {
        }
        if(item.getIsCheck().equals("未检测")){
            viewHolder.tv_item_details_jc.setTextColor(Color.RED);
            viewHolder.tv_item_details_jc.setText("未检测");
            viewHolder.tv_item_details_jc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"未检测",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_item_details_photo;
        private TextView tv_item_details_name;
        private TextView tv_item_details_price;
        private TextView tv_item_details_num;
        private TextView tv_item_details_ly;
        private TextView tv_item_details_jc;
        private TextView tv_item_details_jishu;
        private TextView et_item_details_ly;

    }

    public void refreshData(List<DetailList> itemList) {
        if (mItemList.size() != 0 && mItemList != null) {
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<DetailList> itemList) {
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

}
