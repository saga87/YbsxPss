package com.ning.ybsxpss.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.MadeGoodsList;
import com.ning.ybsxpss.model.MyClientModel;
import com.ning.ybsxpss.util.UrlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fxn on 2017/9/1.
 */

public class GoodsCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static List<MadeGoodsList> texts;
    private LayoutInflater inflater;
    private Context context;
    private MyClientModel model;
    private Handler handler;
    public static final List<String> customList = new ArrayList<>();
    private boolean b;
    // 存储勾选框状态的map集合
    public static final Map<Integer, Boolean> map = new HashMap<>();

    public GoodsCustomAdapter(Context context, List<MadeGoodsList> mList, MyClientModel model, Handler handler) {
        this.texts = mList;
        this.context = context;
        this.model = model;
        this.handler = handler;
        b = true;
        inflater = LayoutInflater.from(context);
        initMap();
    }

    //初始化map集合
    private void initMap() {
        //设置checkBox改变监听
        for (int i = 0; i < texts.size(); i++) {
            if(texts.get(i).getBeSelected().equals("0")){
                map.put(i, false);
            }else {
                map.put(i, true);
            }
        }
        checke();
        Message message = new Message();
        message.what = 4;
        handler.sendMessage(message);
    }
    @Override
    public int getItemCount() {
        return texts.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_goods_custom, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) holder;
        final MadeGoodsList text = texts.get(position);
        String url =  UrlUtil.url+"/"+text.getImgUrl();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.timg)
                .crossFade()
                .into(hold.iv_item_goods_custom);
        hold.tv_item_goods_custom_name.setText(text.getGoodsName());
        hold.tv_item_goods_custom_unit.setText("元/"+text.getGoodsUnit());
        if(text.getStatus().equals("0")){
            hold.tv_item_goods_custom_sj.setText("上架");
        }else {
            hold.tv_item_goods_custom_sj.setText("下架");
        }
        hold.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用map集合保存
                map.put(position, isChecked);
                checke();
                if(!isChecked){
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                }
            }
        });
        hold.checkBox.setChecked(map.get(position));

        if (hold.et.getTag() instanceof TextWatcher) {
            hold.et.removeTextChangedListener((TextWatcher) hold.et.getTag());
        }
        if(b){
            for (int i = 0; i < texts.size(); i++) {
                texts.get(i).setPri(texts.get(i).getPrice()+"");
            }
            b = false;
        }
        hold.et.setText(texts.get(position).getPri()+"");
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                texts.get(position).setPri(hold.et.getText().toString());
            }
        };
        hold.et.addTextChangedListener(textWatcher);
        hold.et.setTag(textWatcher);

    }
    //初始化map集合
    private void checke() {
        customList.clear();
        for (int i = 0; i < map.size(); i++) {
            if ( map.get(i) ){
                customList.add(texts.get(i).getGoodsId());
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            }else {
                customList.remove(texts.get(i).getGoodsId());
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            }
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView iv_item_goods_custom;
        TextView tv_item_goods_custom_unit;
        TextView tv_item_goods_custom_name;
        EditText et;
        TextView tv_item_goods_custom_sj;
        public MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox_item_goods_custom);
            iv_item_goods_custom = view.findViewById(R.id.iv_item_goods_custom);
            tv_item_goods_custom_name = view.findViewById(R.id.tv_item_goods_custom_name);
            tv_item_goods_custom_unit = view.findViewById(R.id.tv_item_goods_custom_unit);
            et = view.findViewById(R.id.et_item_goods_custom_price);
            tv_item_goods_custom_sj = view.findViewById(R.id.tv_item_goods_custom_sj);
        }
    }

    public void refreshData(List<MadeGoodsList> itemList) {
        if (texts.size() != 0 && texts != null) {
            texts.clear();
        }
        texts.addAll(itemList);
        notifyDataSetChanged();
    }

    public void addData(List<MadeGoodsList> itemList) {
        if(itemList!=null) {
            texts.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
