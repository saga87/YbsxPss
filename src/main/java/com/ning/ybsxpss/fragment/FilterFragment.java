package com.ning.ybsxpss.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.activity.DzdActivity;
import com.ning.ybsxpss.activity.YiQianShouActivity;
import com.ning.ybsxpss.entity.PurchaserName;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：fxn on 2016/10/10 10:07
 */

public class FilterFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerContent;
    private ImageView iv_back;
    private Spinner sp_filter_wdkh;
    private TextView tv_filter_kssj,tv_filter_jssj;
    private Button btn_reset,btn_confirm;
    private SupplierModel model;
    private List<PurchaserName> name;
    private List<String> names = new ArrayList<>();
    private String deptId;
    private int str1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                for (int i =0;i<name.size();i++) {
                    String typename = name.get(i).getPurchaserName();
                    names.add(typename);
                    if(name.get(i).getPurchaserId().equals(DzdActivity.purchaserId)){
                        str1 = i;
                    }
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, names);
                //设置样式
                arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_filter_wdkh.setAdapter(arr_adapter1);
                sp_filter_wdkh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (name.get(position).getPurchaserName().equals(sp_filter_wdkh.getSelectedItem().toString())) {
                            deptId = name.get(position).getPurchaserId();
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
                if(!DzdActivity.purchaserId.equals("")){
                    sp_filter_wdkh.setSelection(str1,true);
                }
                if(!DzdActivity.beginDate.equals("")){
                    tv_filter_kssj.setText(DzdActivity.beginDate);
                }
                if(!DzdActivity.beginDate.equals("")){
                    tv_filter_jssj.setText(DzdActivity.endDate);
                }
            }
            if(msg.what==2) {
            }
        }
    };
    private String beginDate = "";
    private String endDate = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patrol_filter, null);
        initView(view);
        initEvent();
        return view;
    }

    private void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(mDrawerContent);
            }
        });
        model = new SupplierModel();
        model.getMyPurchaserList( new ICallBack() {
            @Override
            public void succeed(Object object) {
                name = (List<PurchaserName>) object;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {
            }
        });
        tv_filter_kssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        beginDate = String.format("%d-%d-%d",i,i1+1,i2);
                        tv_filter_kssj.setText(String.format("%d-%d-%d",i,i1+1,i2));
                    }
                },2017,10,13).show();
            }
        });
        tv_filter_jssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        endDate = String.format("%d-%d-%d",i,i1+1,i2);
                        tv_filter_jssj.setText(String.format("%d-%d-%d",i,i1+1,i2));
                    }
                },2017,10,13).show();
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DzdActivity.purchaserId = "";
                DzdActivity.beginDate = "";
                DzdActivity.endDate = "";
                tv_filter_kssj.setText("");
                tv_filter_jssj.setText("");
                mDrawerLayout.closeDrawer(mDrawerContent);
                Intent intent = new Intent();
                intent.setAction("rfid_card_dzd");
                getActivity().sendBroadcast(intent);
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DzdActivity.purchaserId = deptId;
                DzdActivity.beginDate = beginDate;
                DzdActivity.endDate = endDate;
                mDrawerLayout.closeDrawer(mDrawerContent);
                Intent intent = new Intent();
                intent.setAction("rfid_card_dzd");
                getActivity().sendBroadcast(intent);
            }
        });
    }

    private void initView(View view) {
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawerContent = (FrameLayout) getActivity().findViewById(R.id.drawer_content);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        sp_filter_wdkh = view.findViewById(R.id.sp_filter_wdkh);
        tv_filter_kssj = view.findViewById(R.id.tv_filter_kssj);
        tv_filter_jssj = view.findViewById(R.id.tv_filter_jssj);
        btn_reset = view.findViewById(R.id.btn_reset);
        btn_confirm = view.findViewById(R.id.btn_confirm);
    }

}
