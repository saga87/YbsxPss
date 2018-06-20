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
import android.widget.GridView;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.AlterSupplierActivity;
import com.ning.ybsxpss.activity.YiFaHuoActivity;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.SupplierList;
import com.ning.ybsxpss.entity.WaitDisposeList;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class SupplierListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<SupplierList> texts;
    private LayoutInflater inflater;
    private Handler handler;
    private SupplierModel model;
    private Context context;

    public SupplierListAdapter(Context context, List<SupplierList> mList,Handler handler,SupplierModel model) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_supplierlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder hold = (MyViewHolder) holder;
        final SupplierList text = texts.get(position);
        hold.tv_item_supplierlist_jidi.setText(text.getSupplierName());
        switch (text.getType()){
            case "0":
                hold.tv_item_supplierlist_zzjd.setText("种植基地");
                break;
            case "1":
                hold.tv_item_supplierlist_zzjd.setText("养殖基地");
                break;
            case "2":
                hold.tv_item_supplierlist_zzjd.setText("食品加工企业");
                break;
            case "3":
                hold.tv_item_supplierlist_zzjd.setText("批发市场");
                break;
            case "4":
                hold.tv_item_supplierlist_zzjd.setText("专业合作社");
                break;
            case "5":
                hold.tv_item_supplierlist_zzjd.setText("农户");
                break;
        }
        hold.tv_item_supplierlist_address.setText(text.getSupplierAddress());
        hold.tv_item_supplierlist_name.setText(text.getFzr());
        hold.tv_item_supplierlist_phone.setText(text.getPhone());
        hold.tv_item_supplierlist_xg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlterSupplierActivity.class);
                intent.putExtra("supplierId",text.getSupplierId());
                context.startActivity(intent);
            }
        });
        hold.tv_item_supplierlist_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除");
                builder.setMessage("确定要删除吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        model.delSupplier(text.getSupplierId(), new ICallBack() {
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
        TextView tv_item_supplierlist_jidi;
        TextView tv_item_supplierlist_zzjd;
        TextView tv_item_supplierlist_address;
        TextView tv_item_supplierlist_name;
        TextView tv_item_supplierlist_phone;
        TextView tv_item_supplierlist_xg;
        TextView tv_item_supplierlist_sc;
        public MyViewHolder(View view) {
            super(view);
            tv_item_supplierlist_jidi = view.findViewById(R.id.tv_item_supplierlist_jidi);
            tv_item_supplierlist_zzjd = view.findViewById(R.id.tv_item_supplierlist_zzjd);
            tv_item_supplierlist_address = view.findViewById(R.id.tv_item_supplierlist_address);
            tv_item_supplierlist_name = view.findViewById(R.id.tv_item_supplierlist_name);
            tv_item_supplierlist_phone = view.findViewById(R.id.tv_item_supplierlist_phone);
            tv_item_supplierlist_xg = view.findViewById(R.id.tv_item_supplierlist_xg);
            tv_item_supplierlist_sc = view.findViewById(R.id.tv_item_supplierlist_sc);
        }
    }

    public void refreshData(List<SupplierList> itemList) {
        if (texts.size() != 0 && texts != null) {
            texts.clear();
        }
        texts.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<SupplierList> itemList) {
        if(itemList!=null) {
            texts.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
