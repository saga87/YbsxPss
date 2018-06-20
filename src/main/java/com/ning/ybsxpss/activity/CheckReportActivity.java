package com.ning.ybsxpss.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.CheckReportObj;
import com.ning.ybsxpss.entity.FillSourceObj;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;

public class CheckReportActivity extends AppCompatActivity {
    private TextView tv_checkreport_jclx,tv_checkreport_jcjg,tv_checkreport_jcxm,tv_checkreport_bzz,tv_checkreport_scz;
    private ImageView iv_back;
    private GoodsModel modle;
    private String orderDetailId;
    private CheckReportObj obj;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                try {
                    tv_checkreport_jclx.setText(obj.getCheck_type());
                    tv_checkreport_jcjg.setText(obj.getCheck_result());
                    tv_checkreport_jcxm.setText(obj.getCheck_item());
                    tv_checkreport_bzz.setText(obj.getCheck_standardVal()+"");
                    tv_checkreport_scz.setText(obj.getCheck_testVal()+"");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);
        setView();
        getmodel();
        setListener();
    }

    private void getmodel() {
        Bundle bundle = getIntent().getExtras();
        orderDetailId = bundle.getString("orderDetailId");

        modle = new GoodsModel();
        modle.getCheckReport(orderDetailId, new ICallBack() {
            @Override
            public void succeed(Object object) {
                obj = (CheckReportObj) object;
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
        tv_checkreport_jclx = (TextView) findViewById(R.id.tv_checkreport_jclx);
        tv_checkreport_jcjg = (TextView) findViewById(R.id.tv_checkreport_jcjg);
        tv_checkreport_jcxm = (TextView) findViewById(R.id.tv_checkreport_jcxm);
        tv_checkreport_bzz = (TextView) findViewById(R.id.tv_checkreport_bzz);
        tv_checkreport_scz = (TextView) findViewById(R.id.tv_checkreport_scz);
        iv_back  = (ImageView) findViewById(R.id.iv_checkreport_back);
    }
}
