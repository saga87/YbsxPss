package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.QuanBuActivity;
import com.ning.ybsxpss.activity.YiFaHuoActivity;
import com.ning.ybsxpss.entity.WaitDisposeList;

import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class QuanbuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<WaitDisposeList> texts;
    private LayoutInflater inflater;
    private Context context;

    public QuanbuAdapter(Context context, List<WaitDisposeList> mList) {
        this.texts = mList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_quanbu, parent, false);
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
        hold.item_quanbu_name.setText(texts.get(position).getCompanyName());
        hold.item_dsl_gridview.setAdapter(new ImageAdapter(context,texts.get(position).getDetaillist()));
        hold.item_quanbu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuanBuActivity.class);
                intent.putExtra("orderId",texts.get(position).getOrderId());
                context.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_dsl_dsl;
        GridView item_dsl_gridview;
        TextView item_dsl_xdsj;
        TextView item_dsl_pssj;
        TextView item_quanbu_name;
        TextView item_quanbu_item;
        public MyViewHolder(View view) {
            super(view);
            item_dsl_dsl = view.findViewById(R.id.item_quanbu_dsl);
            item_dsl_xdsj = view.findViewById(R.id.item_quanbu_xdsj);
            item_dsl_pssj = view.findViewById(R.id.item_quanbu_pssj);
            item_quanbu_item = view.findViewById(R.id.item_quanbu_item);
            item_quanbu_name = view.findViewById(R.id.item_quanbu_name);
            item_dsl_gridview = view.findViewById(R.id.item_quanbu_gridview);
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
