package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.AmendActivity;
import com.ning.ybsxpss.activity.IssueGoodsListActivity;
import com.ning.ybsxpss.entity.GoodsMaintainList;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.UnSetGoodsList;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

import java.util.List;

/**
 * Created by fxn on 2017/9/1.
 */

public class IssueGoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private GoodsModel model;
    private Handler handler;
    List<String> goodsList;

    public IssueGoodsListAdapter(Context context,  GoodsModel model, Handler handler,List<String> goodsList) {
        this.context = context;
        this.model = model;
        this.handler = handler;
        this.goodsList = goodsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return IssueGoodsListActivity.lists.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_issuegoods, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) holder;
        try {
            final UnSetGoodsList item = IssueGoodsListActivity.lists.get(position);
            String url =  UrlUtil.url+"/"+item.getImgUrl();
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into( hold.item_issuegoods_image);

            if (hold.item_issuegoods_name.getTag() instanceof TextWatcher) {
                hold.item_issuegoods_name.removeTextChangedListener((TextWatcher) hold.item_issuegoods_name.getTag());
            }
            if(item.getGoodsName().equals("")){
                hold.item_issuegoods_name.setText("");
            }else{
                hold.item_issuegoods_name.setText(item.getGoodsName());
            }
            TextWatcher textWatcher4 = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    item.setGoodsName(s.toString());
                }
            };
            hold.item_issuegoods_name.addTextChangedListener(textWatcher4);
            hold.item_issuegoods_name.setTag(textWatcher4);

            if (hold.item_issuegoods_danjia.getTag() instanceof TextWatcher) {
                hold.item_issuegoods_danjia.removeTextChangedListener((TextWatcher) hold.item_issuegoods_danjia.getTag());
            }
            if(item.getPrice().equals("")){
                hold.item_issuegoods_danjia.setText("");
            }else{
                hold.item_issuegoods_danjia.setText(item.getPrice());
            }
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    item.setPrice(s.toString());
                }
            };
            hold.item_issuegoods_danjia.addTextChangedListener(textWatcher);
            hold.item_issuegoods_danjia.setTag(textWatcher);

            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, goodsList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hold.item_issuegoods_jin.setAdapter(adapter);
            hold.item_issuegoods_jin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    item.setGoodstype(hold.item_issuegoods_jin.getSelectedItem().toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (hold.item_issuegoods_qdj.getTag() instanceof TextWatcher) {
                hold.item_issuegoods_qdj.removeTextChangedListener((TextWatcher) hold.item_issuegoods_qdj.getTag());
            }
            if(item.getQdj().equals("")){
                hold.item_issuegoods_qdj.setText("");
            }else{
                hold.item_issuegoods_qdj.setText(item.getQdj());
            }
            TextWatcher textWatcher2 = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    item.setQdj(s.toString());
                }
            };
            hold.item_issuegoods_qdj.addTextChangedListener(textWatcher2);
            hold.item_issuegoods_qdj.setTag(textWatcher2);

            hold.item_issuegoods_sctp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message message = Message.obtain();
                    message.what = 2;
                    message.arg1 = position;
                    handler.sendMessage(message);
                }
            });
            hold.item_issuegoods_delect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message message = Message.obtain();
                    message.what = 4;
                    message.obj = item.getGoodsId();
                    message.arg1 = position;
                    handler.sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_issuegoods_image;
        EditText item_issuegoods_name;
        EditText item_issuegoods_danjia;
        Spinner item_issuegoods_jin;
        EditText item_issuegoods_qdj;
        Button item_issuegoods_sctp;
        Button item_issuegoods_delect;
        public MyViewHolder(View view) {
            super(view);
            item_issuegoods_image = view.findViewById(R.id.item_issuegoods_image);
            item_issuegoods_name = view.findViewById(R.id.item_issuegoods_name);
            item_issuegoods_danjia = view.findViewById(R.id.item_issuegoods_danjia);
            item_issuegoods_jin = view.findViewById(R.id.item_issuegoods_jin);
            item_issuegoods_qdj = view.findViewById(R.id.item_issuegoods_qdj);
            item_issuegoods_sctp = view.findViewById(R.id.item_issuegoods_sctp);
            item_issuegoods_delect = view.findViewById(R.id.item_issuegoods_delect);
        }
    }

    public void refreshData(List<UnSetGoodsList> itemList) {
        if (IssueGoodsListActivity.lists.size() != 0 && IssueGoodsListActivity.lists != null) {
            IssueGoodsListActivity.lists.clear();
        }
        IssueGoodsListActivity.lists.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<UnSetGoodsList> itemList) {
        if(itemList!=null) {
            IssueGoodsListActivity.lists.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
