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
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.OrderDetailsActivity;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class DaishouliAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<WaitDisposeList> texts;
    private LayoutInflater inflater;
    private Context context;
    private IndentModel model;
    private Handler handler;
    public static final List<String> list = new ArrayList<>();
    public static final boolean[] flag = new boolean[500];//此处添加一个boolean类型的数组

    public DaishouliAdapter(Context context, List<WaitDisposeList> mList, IndentModel model,Handler handler) {
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
        View view = inflater.inflate(R.layout.list_item_text, parent, false);
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
        hold.item_dsl_jjsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("确定拒绝受理吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        model.dealWithOrder(texts.get(position).getOrderId(), "2", new ICallBack() {
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
                builder.show();
            }
        });
        hold.item_dsl_qrsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("确定受理吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        model.dealWithOrder(texts.get(position).getOrderId(), "1", new ICallBack() {
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
                builder.show();
            }
        });
        //CheckBox混乱问题
        hold.checkBox.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
        hold.checkBox.setChecked(flag[position]);
        hold.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag[position] = b;
                if ( flag[position] ){
                    list.add(texts.get(position).getOrderId());
                    Message message = new Message();
                    message.what = 6;
                    handler.sendMessage(message);
                }else {
                    list.remove(texts.get(position).getOrderId());
                    Message message = new Message();
                    message.what = 6;
                    handler.sendMessage(message);
                }
            }
        });
        hold.item_dsl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("orderId",texts.get(position).getOrderId());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView item_dsl_dsl;
        Button item_dsl_qrsl;
        Button item_dsl_jjsl;
        GridView item_dsl_gridview;
        TextView item_dsl_item;
        TextView item_dsl_xdsj;
        TextView item_dsl_pssj;
        public MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox_item_daishouli);
            item_dsl_dsl = view.findViewById(R.id.item_dsl_dsl);
            item_dsl_xdsj = view.findViewById(R.id.item_dsl_xdsj);
            item_dsl_pssj = view.findViewById(R.id.item_dsl_pssj);
            item_dsl_qrsl = view.findViewById(R.id.item_dsl_qrsl);
            item_dsl_jjsl = view.findViewById(R.id.item_dsl_jjsl);
            item_dsl_gridview = view.findViewById(R.id.item_dsl_gridview);
            item_dsl_item = view.findViewById(R.id.item_dsl_item);
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
