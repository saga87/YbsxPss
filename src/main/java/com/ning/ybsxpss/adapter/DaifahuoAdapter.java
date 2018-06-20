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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.DaiFaHuoActivity;
import com.ning.ybsxpss.activity.OrderDetailsActivity;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.model.IndentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class DaifahuoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<WaitDisposeList> texts;
    private LayoutInflater inflater;
    private Context context;
    private IndentModel model;
    private Handler handler;
    public static final List<String> list1 = new ArrayList<>();
    public static final boolean[] flag1 = new boolean[500];//此处添加一个boolean类型的数组

    public DaifahuoAdapter(Context context, List<WaitDisposeList> mList, IndentModel model, Handler handler) {
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
        View view = inflater.inflate(R.layout.list_item_daifahuo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder hold = (MyViewHolder) holder;
        switch (texts.get(position).getStatus()){
            case "0":
                hold.item_dsl_dsl.setText("待受理");
                break;
            case "1":
                hold.item_dsl_dsl.setText("待发货");
                break;
            case "2":
                hold.item_dsl_dsl.setText("已拒绝受理");
                break;
            case "3":
                hold.item_dsl_dsl.setText("已发货");
                break;
            case "4":
                hold.item_dsl_dsl.setText("已签收");
                break;
            case "5":
                hold.item_dsl_dsl.setText("已确认签收");
                break;
            case "6":
                hold.item_dsl_dsl.setText("已确认收货");
                break;
        }
        hold.item_dsl_xdsj.setText(texts.get(position).getAddtime());
        hold.item_dsl_pssj.setText(texts.get(position).getDistributionDate()+" "+texts.get(position).getTimeRange());
        hold.item_dsl_gridview.setAdapter(new ImageAdapter(context,texts.get(position).getDetaillist()));
        hold.checkBox.setText(texts.get(position).getCompanyName());
        //CheckBox混乱问题
        hold.checkBox.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
        hold.checkBox.setChecked(flag1[position]);
        hold.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag1[position] = b;
                if ( flag1[position] ){
                    list1.add(texts.get(position).getOrderId());
                    Message message = new Message();
                    message.what = 6;
                    handler.sendMessage(message);
                }else {
                    list1.remove(texts.get(position).getOrderId());
                    Message message = new Message();
                    message.what = 6;
                    handler.sendMessage(message);
                }
            }
        });
        hold.item_dfh_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DaiFaHuoActivity.class);
                intent.putExtra("orderId",texts.get(position).getOrderId());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView item_dsl_dsl;
        GridView item_dsl_gridview;
        TextView item_dsl_xdsj;
        TextView item_dsl_pssj;
        TextView item_dfh_item;
        public MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox_item_daifahuo);
            item_dsl_dsl = view.findViewById(R.id.item_dfh_dfh);
            item_dsl_xdsj = view.findViewById(R.id.item_dfh_xdsj);
            item_dsl_pssj = view.findViewById(R.id.item_dfh_pssj);
            item_dfh_item = view.findViewById(R.id.item_dfh_item);
            item_dsl_gridview = view.findViewById(R.id.item_dfh_gridview);
        }
    }

    public void refreshData(List<WaitDisposeList> itemList) {
        if (texts.size() != 0 && texts != null) {
            texts.clear();
        }
        texts.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<WaitDisposeList> itemList) {
        if(itemList!=null) {
            texts.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
