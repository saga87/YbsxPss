package com.ning.ybsxpss.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DaifahuoAdapter;
import com.ning.ybsxpss.adapter.DaishouliAdapter;
import com.ning.ybsxpss.adapter.GoodsTypeAdapter;
import com.ning.ybsxpss.entity.GoodsOneType;
import com.ning.ybsxpss.entity.GoodsThreeType;
import com.ning.ybsxpss.entity.GoodsTwoType;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class IssueGoodsActivity extends BaseActivity {
    private Spinner sp_issue_goods_1,sp_issue_goods_2;
    private GridView gridview_issue_goods;
    private TextView tv_issue_goods_sqtj;
    private ImageView iv_back;
    private Button btn_issue_goods_xyb;
    private List<GoodsOneType> oneType;
    private List<String> names = new ArrayList<>();
    private List<String> names1 = new ArrayList<>();
    private List<GoodsTwoType> twoType;
    private List<GoodsThreeType> threeType;
    private GoodsTypeAdapter adapter;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                for (int i =0;i<oneType.size();i++) {
                    String typename = oneType.get(i).getName();
                    names.add(typename);
                }
                ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(IssueGoodsActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_issue_goods_1.setAdapter(arr_adapter);
                sp_issue_goods_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String str1 =  oneType.get(position).getName();
                        String str2 =  sp_issue_goods_1.getSelectedItem().toString();
                        if (str1.equals(str2)) {
                            twoType = oneType.get(position).getChildlist();
                            Message message = Message.obtain();
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            if (msg.what == 2) {
                names1.clear();
                for (int i =0;i<twoType.size();i++) {
                    String typename = twoType.get(i).getName();
                    names1.add(typename);
                }
                ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(IssueGoodsActivity.this, android.R.layout.simple_spinner_dropdown_item, names1);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_issue_goods_2.setAdapter(arr_adapter);
                sp_issue_goods_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String str1 =  twoType.get(position).getName();
                        String str2 =  sp_issue_goods_2.getSelectedItem().toString();
                        if (str1.equals(str2)) {
                            threeType = twoType.get(position).getChildlist();
                            Message message = Message.obtain();
                            message.what = 3;
                            handler.sendMessage(message);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            if (msg.what == 3) {
                adapter = new GoodsTypeAdapter(IssueGoodsActivity.this,threeType);
                gridview_issue_goods.setAdapter(adapter);
            }
        }
    };
    private GoodsModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_goods);
        Utility.setToolbar(this);
        setView();
        setListener();
        getmodel();
    }
    private void getmodel() {
        model = new GoodsModel();
        model.getAllClassificationList(new ICallBack() {
            @Override
            public void succeed(Object object) {
                oneType = ( List<GoodsOneType>) object;
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
    }

    private void setListener() {
        tv_issue_goods_sqtj.setVisibility(View.GONE);
        btn_issue_goods_xyb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str = GoodsTypeAdapter.nums.toString();
                str = str.replace(" ", "");
                final String s = str.substring(1, str.length()-1);
                model.addGoods(s, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()){
                            GoodsTypeAdapter.nums.clear();
                            Intent intent = new Intent();
                            intent.setAction("rfid_card_goodslist");
                            IssueGoodsActivity.this.sendBroadcast(intent);
                            finish();
                        }
                    }
                    @Override
                    public void error(Object object) {}
                });
            }
        });
        tv_issue_goods_sqtj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IssueGoodsActivity.this,AddGoodsActivity.class);
                startActivity(intent);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsTypeAdapter.nums.clear();
                finish();
            }
        });
    }
    /**
     * 监听Back键按下事件,方法1:
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GoodsTypeAdapter.nums.clear();
    }
    private void setView() {
        sp_issue_goods_1 = (Spinner) findViewById(R.id.sp_issue_goods_1);
        sp_issue_goods_2 = (Spinner) findViewById(R.id.sp_issue_goods_2);
        gridview_issue_goods = (GridView) findViewById(R.id.gridview_issue_goods);
        tv_issue_goods_sqtj = (TextView) findViewById(R.id.tv_issue_goods_sqtj);
        btn_issue_goods_xyb = (Button) findViewById(R.id.btn_issue_goods_xyb);
        iv_back = findViewById(R.id.iv_issue_goods_back);
    }
}
