package com.ning.ybsxpss.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DzdListAdapter;
import com.ning.ybsxpss.entity.FillSourceObj;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.ActivityCollector;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;
import com.ning.ybsxpss.util.Utility;

public class FillSourceActivity extends AppCompatActivity {
    private TextView tv_fillsource_ghs,tv_fillsource_ghslx,tv_fillsource_ghsdz,tv_fillsource_fzr,tv_fillsource_lxdh;
    private ImageView iv_back,iv_fillsource_image;
    private GoodsModel modle;
    private String orderDetailId;
    private FillSourceObj obj;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                try {
                    tv_fillsource_ghs.setText(obj.getSupplierName());
                    switch (obj.getType()) {
                        case "0":
                            tv_fillsource_ghslx.setText("种植基地");
                            break;
                        case "1":
                            tv_fillsource_ghslx.setText("养殖基地");
                            break;
                        case "2":
                            tv_fillsource_ghslx.setText("食品加工企业");
                            break;
                        case "3":
                            tv_fillsource_ghslx.setText("批发市场");
                            break;
                        case "4":
                            tv_fillsource_ghslx.setText("专业合作社");
                            break;
                        case "5":
                            tv_fillsource_ghslx.setText("农户");
                            break;
                    }
                    tv_fillsource_ghsdz.setText(obj.getSupplierAddress());
                    tv_fillsource_fzr.setText(obj.getFzr());
                    tv_fillsource_lxdh.setText(obj.getPhone());
                    String url = UrlUtil.url + "/" + obj.getPzUrl();
                    Glide.with(FillSourceActivity.this)
                            .load(url)
                            .placeholder(R.drawable.timg)
                            .crossFade()
                            .into(iv_fillsource_image);
                }catch (Exception e){}
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_source);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
    }

    private void getmodel() {
        Bundle bundle = getIntent().getExtras();
        orderDetailId = bundle.getString("orderDetailId");

        modle = new GoodsModel();
        modle.getSource(orderDetailId, new ICallBack() {
            @Override
            public void succeed(Object object) {
                obj = (FillSourceObj) object;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {

            }
        });
    }

    private void setListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setView() {
        tv_fillsource_ghs = (TextView) findViewById(R.id.tv_fillsource_ghs);
        tv_fillsource_ghslx = (TextView) findViewById(R.id.tv_fillsource_ghslx);
        tv_fillsource_ghsdz = (TextView) findViewById(R.id.tv_fillsource_ghsdz);
        tv_fillsource_fzr = (TextView) findViewById(R.id.tv_fillsource_fzr);
        tv_fillsource_lxdh = (TextView) findViewById(R.id.tv_fillsource_lxdh);
        iv_fillsource_image = (ImageView) findViewById(R.id.iv_fillsource_image);
        iv_back  = (ImageView) findViewById(R.id.iv_fillsource_back);
    }
}
