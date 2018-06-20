package com.ning.ybsxpss.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.PickingListAdapter;
import com.ning.ybsxpss.entity.PickingListObj;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class PickingListActivity extends BaseActivity {
    private ListView listview_picking_list;
    private List<PickingListObj> obj;
    private ImageView iv_back;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                PickingListAdapter adapter = new PickingListAdapter(PickingListActivity.this,obj);
                listview_picking_list.setAdapter(adapter);
            }
            if(msg.what==2) {
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_list);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
    }

    private void getmodel() {
        Bundle bundle = getIntent().getExtras();
        String orderId = bundle.getString("orderId");
        IndentModel model = new IndentModel();
        model.getPickingList(orderId, new ICallBack() {
            @Override
            public void succeed(Object object) {
                obj = (List<PickingListObj>) object;
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
        listview_picking_list = (ListView) findViewById(R.id.listview_picking_list);
        iv_back = findViewById(R.id.iv_picking_list_back);
    }
}
