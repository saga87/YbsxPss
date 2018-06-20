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
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.AlterSupplierActivity;
import com.ning.ybsxpss.activity.UserCenterActivity;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.SupplierList;
import com.ning.ybsxpss.entity.UserList;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.List;

/**
 * Created by fxn on 2017/10/18.
 */

public class NumberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<UserList> texts;
    private LayoutInflater inflater;
    private Handler handler;
    private SupplierModel model;
    private Context context;

    public NumberAdapter(Context context, List<UserList> mList,Handler handler,SupplierModel model) {
        this.texts = mList;
        this.context = context;
        this.handler = handler;
        this.model = model;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }


    @Override
    public NumberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_number, parent, false);
        return new NumberAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NumberAdapter.MyViewHolder hold = (NumberAdapter.MyViewHolder) holder;
        final UserList text = texts.get(position);
        if(text.getUser_name()!=null){
            hold.item_number_name.setText(text.getUser_name());
        }else {
            hold.item_number_name.setText("----");
        }
        if(text.getUser_realname()!=null){
            hold.item_number_id.setText(text.getUser_realname());
        }else {
            hold.item_number_id.setText("----");
        }
        if(text.getUser_tel()!=null){
            hold.item_number_phone.setText(text.getUser_tel());
        }else {
            hold.item_number_phone.setText("----");
        }
        if(text.getRole_name()!=null){
            hold.item_number_type.setText(text.getRole_name());
        }else {
            hold.item_number_type.setText("----");
        }
        hold.item_number_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除");
                builder.setMessage("确定要删除吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        model.delUser(text.getUser_id(), new ICallBack() {
                            public void succeed(Object object) {
                                LoginObg obg = (LoginObg) object;
                                if (obg.isSuccess()){
                                    Message message = new Message();
                                    message.what = 4;
                                    message.arg1 = position;
                                    handler.sendMessage(message);
                                }
                            }
                            public void error(Object object) {}
                        });
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_number_name;
        TextView item_number_id;
        TextView item_number_phone;
        TextView item_number_type;
        TextView item_number_delete;
        public MyViewHolder(View view) {
            super(view);
            item_number_name = view.findViewById(R.id.item_number_name);
            item_number_id = view.findViewById(R.id.item_number_id);
            item_number_phone = view.findViewById(R.id.item_number_phone);
            item_number_type = view.findViewById(R.id.item_number_type);
            item_number_delete = view.findViewById(R.id.item_number_delete);
        }
    }


    public void refreshData(List<UserList> itemList) {
        if (texts.size() != 0 && texts != null) {
            texts.clear();
        }
        texts.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<UserList> itemList) {
        if(itemList!=null) {
            texts.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
