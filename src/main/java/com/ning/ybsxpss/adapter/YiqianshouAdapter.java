package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.OrderDetailsActivity;
import com.ning.ybsxpss.activity.YiQianShouActivity;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class YiqianshouAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<WaitDisposeList> texts;
    private LayoutInflater inflater;
    private Context context;
    private IndentModel model;
    private Handler handler;

    public YiqianshouAdapter(Context context, List<WaitDisposeList> mList, IndentModel model, Handler handler) {
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
        View view = inflater.inflate(R.layout.list_item_yiqianshou, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder hold = (MyViewHolder) holder;
        switch (texts.get(position).getStatus()){
            case "0":
                hold.item_yqs_dsl.setText("待受理");
                hold.item_yqs_queren.setVisibility(View.GONE);
                break;
            case "1":
                hold.item_yqs_dsl.setText("待发货");
                hold.item_yqs_queren.setVisibility(View.GONE);
                break;
            case "2":
                hold.item_yqs_dsl.setText("已拒绝受理");
                hold.item_yqs_queren.setVisibility(View.GONE);
                break;
            case "3":
                hold.item_yqs_dsl.setText("已发货");
                hold.item_yqs_queren.setVisibility(View.GONE);
                break;
            case "4":
                hold.item_yqs_dsl.setText("已签收");
                hold.item_yqs_queren.setVisibility(View.VISIBLE);
                break;
            case "5":
                hold.item_yqs_dsl.setText("已确认签收");
                hold.item_yqs_queren.setVisibility(View.GONE);
                break;
            case "6":
                hold.item_yqs_dsl.setText("已确认收货");
                hold.item_yqs_queren.setVisibility(View.GONE);
                break;
        }
        hold.item_yqs_xdsj.setText(texts.get(position).getAddtime());
        hold.item_yqs_pssj.setText(texts.get(position).getDistributionDate()+" "+texts.get(position).getTimeRange());
        hold.item_yps_name.setText(texts.get(position).getCompanyName());
        hold.item_dsl_gridview.setAdapter(new ImageAdapter(context,texts.get(position).getDetaillist()));
        hold.item_yqs_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("确认签收吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        model.affirmSignOrder(texts.get(position).getOrderId(), new ICallBack() {
                            public void succeed(Object object) {
                                LoginObg obj= (LoginObg) object;
                                if(obj.isSuccess()) {
                                    Message message = new Message();
                                    message.what = 4;
                                    message.arg1 = position;
                                    handler.sendMessage(message);
                                }
                            }
                            public void error(Object object) {
                            }
                        });
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
        hold.item_yqs_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, YiQianShouActivity.class);
                intent.putExtra("orderId",texts.get(position).getOrderId());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_yqs_dsl;
        Button item_yqs_queren;
        GridView item_dsl_gridview;
        TextView item_yqs_xdsj;
        TextView item_yqs_pssj;
        TextView item_yqs_item;
        TextView item_yps_name;
        public MyViewHolder(View view) {
            super(view);
            item_yqs_dsl = view.findViewById(R.id.item_yqs_dsl);
            item_yqs_queren = view.findViewById(R.id.item_yqs_queren);
            item_yqs_xdsj = view.findViewById(R.id.item_yqs_xdsj);
            item_yqs_pssj = view.findViewById(R.id.item_yqs_pssj);
            item_yqs_item = view.findViewById(R.id.item_yqs_item);
            item_yps_name = view.findViewById(R.id.item_yps_name);
            item_dsl_gridview = view.findViewById(R.id.item_yqs_gridview);
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
