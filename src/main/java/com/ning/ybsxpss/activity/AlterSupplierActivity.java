package com.ning.ybsxpss.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.Province;
import com.ning.ybsxpss.entity.Supplier;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class AlterSupplierActivity extends BaseActivity {
    private Spinner sp_new_supplier_ghslx,sp_new_supplier_dq1,sp_new_supplier_dq2,sp_new_supplier_dq3;
    private EditText et_new_supplier_ghsmc,et_new_supplier_xxdz,et_new_supplier_fzr,et_new_supplier_lxdh;
    private Button btn_new_supplier_bc;
    private ImageView iv_back;
    private SupplierModel model;
    private List<Province> provinces;
    private List<Province> cities;
    private List<Province> regions;
    private List<String> names = new ArrayList<>();
    private List<String> names1 = new ArrayList<>();
    private List<String> names2 = new ArrayList<>();
    private String regionsId;
    private String ghslxId;
    private String supplierId;
    private Supplier supplier;
    private int str1,str2,str3;
    private String msg1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                for (int i =0;i<provinces.size();i++) {
                    String typename = provinces.get(i).getDept_name();
                    names.add(typename);
                    if(provinces.get(i).getDept_id().equals(supplier.getProvience())){
                        str1 = i;
                    }
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(AlterSupplierActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                //设置样式
                arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_new_supplier_dq1.setAdapter(arr_adapter1);
                sp_new_supplier_dq1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (provinces.get(position).getDept_name().equals(sp_new_supplier_dq1.getSelectedItem().toString())) {
                            String deptId = provinces.get(position).getDept_id();
                            model.getDepartmentList(deptId, new ICallBack() {
                                @Override
                                public void succeed(Object object) {
                                    cities = (List<Province>) object;
                                    Message message = Message.obtain();
                                    message.what = 2;
                                    handler.sendMessage(message);
                                }
                                @Override
                                public void error(Object object) {}
                            });
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                sp_new_supplier_dq1.setSelection(str1,true);
            }
            if(msg.what==2) {
                names1.clear();
                str2 = 0;
                for (int i = 0; i < cities.size(); i++) {
                    String typename = cities.get(i).getDept_name();
                    names1.add(typename);
                    if (cities.get(i).getDept_id().equals(supplier.getCity())) {
                        str2 = i;
                    }
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(AlterSupplierActivity.this, android.R.layout.simple_spinner_dropdown_item, names1);
                //设置样式
                arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_new_supplier_dq2.setAdapter(arr_adapter1);

                if(cities.size()==0){
                    regions = new ArrayList<Province>();
                    Message message = Message.obtain();
                    message.what = 3;
                    handler.sendMessage(message);
                }
                sp_new_supplier_dq2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (cities.get(position).getDept_name().equals(sp_new_supplier_dq2.getSelectedItem().toString())) {
                            String deptId = cities.get(position).getDept_id();
                            model.getDepartmentList(deptId, new ICallBack() {
                                @Override
                                public void succeed(Object object) {
                                    regions = (List<Province>) object;
                                    Message message = Message.obtain();
                                    message.what = 3;
                                    handler.sendMessage(message);
                                }
                                public void error(Object object) {}
                            });
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                sp_new_supplier_dq2.setSelection(str2, true);
            }
            if(msg.what==3) {
                names2.clear();
                str3 = 0;
                for (int i =0;i<regions.size();i++) {
                    String typename = regions.get(i).getDept_name();
                    names2.add(typename);
                    if(regions.get(i).getDept_id().equals(supplier.getCounty())){
                        str3 = i;
                    }
                }
                if(regions.size()==0){
                    regionsId = "";
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(AlterSupplierActivity.this, android.R.layout.simple_spinner_dropdown_item, names2);
                //设置样式
                arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_new_supplier_dq3.setAdapter(arr_adapter1);
                sp_new_supplier_dq3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (regions.get(position).getDept_name().equals(sp_new_supplier_dq3.getSelectedItem().toString())) {
                            regionsId = regions.get(position).getDept_id();
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
                sp_new_supplier_dq3.setSelection(str3,true);
            }
            if(msg.what==4) {
                Toast.makeText(AlterSupplierActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("rfid_card_supplierlist");
                AlterSupplierActivity.this.sendBroadcast(intent);
                finish();
            }
            if(msg.what==5) {
                et_new_supplier_ghsmc.setText(supplier.getSupplierName());
                et_new_supplier_xxdz.setText(supplier.getSupplierAddress());
                et_new_supplier_fzr.setText(supplier.getFzr());
                et_new_supplier_lxdh.setText(supplier.getPhone());
                sp_new_supplier_ghslx.setSelection(Integer.valueOf(supplier.getType()),true);
                model.getDepartmentList("china",new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        provinces = (List<Province>) object;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                    @Override
                    public void error(Object object) {}
                });
            }
            if(msg.what==6) {
                Toast.makeText(AlterSupplierActivity.this,"保存失败"+msg1,Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_supplier);
        Utility.setToolbar(this);
        setView();
        getModel();
        setListener();
    }
    private void setListener() {
        List<String> name = new ArrayList<>();
        name.add("种植基地");
        name.add("养殖基地");
        name.add("食品加工企业");
        name.add("批发市场");
        name.add("专业合作社");
        name.add("农户");
        ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(AlterSupplierActivity.this, android.R.layout.simple_spinner_dropdown_item, name);
        //设置样式
        arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_new_supplier_ghslx.setAdapter(arr_adapter1);

        btn_new_supplier_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ghsmc = et_new_supplier_ghsmc.getText().toString();
                String ghslx = sp_new_supplier_ghslx.getSelectedItem().toString();
                String xxdz = et_new_supplier_xxdz.getText().toString();
                String fzr = et_new_supplier_fzr.getText().toString();
                String lxdh = et_new_supplier_lxdh.getText().toString();
                switch (ghslx){
                    case "种植基地":
                        ghslxId = "0";
                        break;
                    case "养殖基地":
                        ghslxId = "1";
                        break;
                    case "食品加工企业":
                        ghslxId = "2";
                        break;
                    case "批发市场":
                        ghslxId = "3";
                        break;
                    case "专业合作社":
                        ghslxId = "4";
                        break;
                    case "农户":
                        ghslxId = "5";
                        break;
                }
                if(ghsmc.equals("")||ghslxId.equals("")||regionsId.equals("")||xxdz.equals("")||fzr.equals("")||lxdh.equals("")) {
                    Toast.makeText(AlterSupplierActivity.this,"数据不能为空，请填写数据",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.updateSupplier(supplierId,ghsmc, ghslxId, regionsId, xxdz, fzr, lxdh, new ICallBack() {
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()){
                            Message message = new Message();
                            message.what = 4;
                            handler.sendMessage(message);
                        }else {
                            Message message = new Message();
                            message.what = 6;
                            msg1 = obg.getMsg();
                            handler.sendMessage(message);
                        }
                    }
                    public void error(Object object) {}
                });
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getModel() {
        Bundle bundle = getIntent().getExtras();
        supplierId = bundle.getString("supplierId");
        model = new SupplierModel();
        model.getSupplierInfo(supplierId, new ICallBack() {
            @Override
            public void succeed(Object object) {
                supplier = (Supplier) object;
                Message message = new Message();
                message.what = 5;
                handler.sendMessage(message);
            }
            public void error(Object object) {
            }
        });
    }
    private void setView() {
        sp_new_supplier_ghslx = (Spinner) findViewById(R.id.sp_alter_supplier_ghslx);
        sp_new_supplier_dq1 = (Spinner) findViewById(R.id.sp_alter_supplier_dq1);
        sp_new_supplier_dq2 = (Spinner) findViewById(R.id.sp_alter_supplier_dq2);
        sp_new_supplier_dq3 = (Spinner) findViewById(R.id.sp_alter_supplier_dq3);
        et_new_supplier_ghsmc = (EditText) findViewById(R.id.et_alter_supplier_ghsmc);
        et_new_supplier_xxdz = (EditText) findViewById(R.id.et_alter_supplier_xxdz);
        et_new_supplier_fzr = (EditText) findViewById(R.id.et_alter_supplier_fzr);
        et_new_supplier_lxdh = (EditText) findViewById(R.id.et_alter_supplier_lxdh);
        btn_new_supplier_bc = (Button) findViewById(R.id.btn_alter_supplier_bc);
        iv_back = (ImageView) findViewById(R.id.iv_alter_supplier_back);
    }
}
