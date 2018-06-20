package com.ning.ybsxpss.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DaifahuoAdapter;
import com.ning.ybsxpss.camera.PhotoUtils;
import com.ning.ybsxpss.camera.ToastUtils;
import com.ning.ybsxpss.entity.ImageUrl;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.Province;
import com.ning.ybsxpss.entity.Supplier;
import com.ning.ybsxpss.entity.UnitData;
import com.ning.ybsxpss.entity.YypzImage;
import com.ning.ybsxpss.model.SupplierModel;
import com.ning.ybsxpss.model.UserModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.UrlUtil;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.BottomMenu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UnitInformationActivity extends BaseActivity {
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri;
    private Uri imageUri;
    private Uri cropImageUri;
    private List<String> urls = new ArrayList<>();
    private Bitmap img;
    private BottomMenu menuWindow;
    //营业凭证相片
    private ImageView iv1,iv2,iv3;
    private ImageView delete1,delete2,delete3;
    //法人身份证
    private ImageView iv_1,iv_2,iv_3;
    private ImageView delete_1,delete_2,delete_3;
    private List<String> urls1 = new ArrayList<>();
    private ImageView iv_back;
    private boolean imageBoolean = false;
    private boolean imageBoolean1 = false;

    private Spinner sp_new_supplier_dq1,sp_new_supplier_dq2,sp_new_supplier_dq3;
    private EditText et_unit_information_dwxx,et_unit_information_xxdz;
    private Button btn_new_supplier_bc;
    private SupplierModel model;
    private List<Province> provinces;
    private List<Province> cities;
    private List<Province> regions;
    private List<String> names = new ArrayList<>();
    private List<String> names1 = new ArrayList<>();
    private List<String> names2 = new ArrayList<>();
    private String regionsId;
    private int str1,str2,str3;
    private UserModel model1;
    private UnitData unitData;

    private List<YypzImage> yypzImages1;
    private List<YypzImage> yypzImages2;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                for (int i =0;i<provinces.size();i++) {
                    String typename = provinces.get(i).getDept_name();
                    names.add(typename);
                    if(provinces.get(i).getDept_id().equals(unitData.getProvience_code())){
                        str1 = i;
                    }
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(UnitInformationActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
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
                                public void error(Object object) {
                                }
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
                    if(cities.get(i).getDept_id().equals(unitData.getCity_code())){
                        str2 = i;
                    }
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(UnitInformationActivity.this, android.R.layout.simple_spinner_dropdown_item, names1);
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
                                @Override
                                public void error(Object object) {}
                            });
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
                sp_new_supplier_dq2.setSelection(str2,true);
            }
            if(msg.what==3) {
                names2.clear();
                str3 = 0;
                for (int i =0;i<regions.size();i++) {
                    String typename = regions.get(i).getDept_name();
                    names2.add(typename);
                    if(regions.get(i).getDept_id().equals(unitData.getCounty_code())){
                        str3 = i;
                    }
                }
                if(regions.size()==0){
                    regionsId = "";
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(UnitInformationActivity.this, android.R.layout.simple_spinner_dropdown_item, names2);
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
                Toast.makeText(UnitInformationActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                finish();
            }
            if(msg.what==5) {
                et_unit_information_xxdz.setText(unitData.getAddr());
                et_unit_information_dwxx.setText(unitData.getCompanyName());
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
                getImage1();
            }
            if(msg.what==7) {
                getImage2();
            }
            if(msg.what==8) {
                judge();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_information);
        Utility.setToolbar(this);
        setView();
        getModel();
        setListener();
    }

    private void getModel() {
        model1 = new UserModel();
        model = new SupplierModel();
        model1.getCompanyInfo( new ICallBack() {
            @Override
            public void succeed(Object object) {
                unitData = (UnitData) object;
                Message message = new Message();
                message.what = 5;
                handler.sendMessage(message);
            }
            public void error(Object object) {
            }
        });
        model1.getFileList("1",new ICallBack() {
            @Override
            public void succeed(Object object) {
                yypzImages1 = (List<YypzImage>) object;
                Message message = new Message();
                message.what = 6;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {}
        });
        model1.getFileList("2",new ICallBack() {
            @Override
            public void succeed(Object object) {
                yypzImages2 = (List<YypzImage>) object;
                Message message = new Message();
                message.what = 7;
                handler.sendMessage(message);
            }
            @Override
            public void error(Object object) {}
        });
    }
    /**
     * 网上获取图片
     */
    private void getImage1(){
        if (yypzImages1.size()==0){
            judge();
        }
        if(yypzImages1.size()==1){
            urls.add(yypzImages1.get(0).getUrl());
            judge();
        }
        if(yypzImages1.size()==2){
            urls.add(yypzImages1.get(0).getUrl());
            urls.add(yypzImages1.get(1).getUrl());
            judge();
        }
        if(yypzImages1.size()>=3){
            urls.add(yypzImages1.get(0).getUrl());
            urls.add(yypzImages1.get(1).getUrl());
            urls.add(yypzImages1.get(2).getUrl());
            judge();
        }
    }
    /**
     * 网上获取图片
     */
    private void getImage2(){
        if (yypzImages2.size()==0){
            judge();
        }
        if(yypzImages2.size()==1){
            urls1.add(yypzImages2.get(0).getUrl());
            judge();
        }
        if(yypzImages2.size()==2){
            urls1.add(yypzImages2.get(0).getUrl());
            urls1.add(yypzImages2.get(1).getUrl());
            judge();
        }
        if(yypzImages2.size()>=3){
            urls1.add(yypzImages2.get(0).getUrl());
            urls1.add(yypzImages2.get(1).getUrl());
            urls1.add(yypzImages2.get(2).getUrl());
            judge();
        }
    }
    private void setListener() {
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBoolean = true;
                imageBoolean1 = false;
                if (urls.size() == 0) {
                    menuWindow = new BottomMenu(UnitInformationActivity.this, clickListener);
                    menuWindow.show();
                    iv2.setClickable(true);
                    hintKbTwo();
                }
            }
        });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UnitInformationActivity.this);
                builder.setTitle("删除");
                builder.setMessage("是否删除该图片");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(0);
                    }
                });
                builder.show();
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBoolean = true;
                imageBoolean1 = false;
                if (urls.size() == 1) {
                    menuWindow = new BottomMenu(UnitInformationActivity.this, clickListener);
                    menuWindow.show();
                    iv3.setClickable(true);
                    hintKbTwo();
                }
            }
        });
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UnitInformationActivity.this);
                builder.setTitle("删除");
                builder.setMessage("是否删除该图片");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(1);
                    }
                });
                builder.show();
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBoolean = true;
                imageBoolean1 = false;
                if (urls.size() == 2) {
                    menuWindow = new BottomMenu(UnitInformationActivity.this, clickListener);
                    menuWindow.show();
                    hintKbTwo();
                }
            }
        });
        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UnitInformationActivity.this);
                builder.setTitle("删除");
                builder.setMessage("是否删除该图片");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(2);
                    }
                });
                builder.show();
            }
        });
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBoolean1 = true;
                imageBoolean = false;
                if (urls1.size() == 0) {
                    menuWindow = new BottomMenu(UnitInformationActivity.this, clickListener);
                    menuWindow.show();
                    iv_2.setClickable(true);
                    hintKbTwo();
                }
            }
        });
        delete_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UnitInformationActivity.this);
                builder.setTitle("删除");
                builder.setMessage("是否删除该图片");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove1(0);
                    }
                });
                builder.show();
            }
        });
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBoolean1 = true;
                imageBoolean = false;
                if (urls1.size() == 1) {
                    menuWindow = new BottomMenu(UnitInformationActivity.this, clickListener);
                    menuWindow.show();
                    iv_3.setClickable(true);
                    hintKbTwo();
                }
            }
        });
        delete_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UnitInformationActivity.this);
                builder.setTitle("删除");
                builder.setMessage("是否删除该图片");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove1(1);
                    }
                });
                builder.show();
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBoolean1 = true;
                imageBoolean = false;
                if (urls1.size() == 2) {
                    menuWindow = new BottomMenu(UnitInformationActivity.this, clickListener);
                    menuWindow.show();
                    hintKbTwo();
                }
            }
        });
        delete_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UnitInformationActivity.this);
                builder.setTitle("删除");
                builder.setMessage("是否删除该图片");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove1(2);
                    }
                });
                builder.show();
            }
        });
        btn_new_supplier_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dwmc = et_unit_information_dwxx.getText().toString();
                String xxdz = et_unit_information_xxdz.getText().toString();
                String str = urls.toString();
                str = str.replace(" ", "");
                String s = str.substring(1, str.length()-1);
                String str1 =urls1.toString();
                str1 = str1.replace(" ", "");
                String s1 = str1.substring(1, str1.length()-1);
                if(dwmc.equals("")||regionsId.equals("")||xxdz.equals("")) {
                    Toast.makeText(UnitInformationActivity.this,"数据不能为空，请填写数据",Toast.LENGTH_SHORT).show();
                    return;
                }
                model1.updateCompanyInfo(dwmc, regionsId, xxdz, s, s1, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()){
                            Message message = new Message();
                            message.what = 4;
                            handler.sendMessage(message);
                        }
                    }
                    @Override
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

    /**
     * 删除图片
     * @param i
     */
    private void remove(int i){
        switch (i){
            case 0:
                try {
                    urls.remove(0);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    urls.remove(1);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    urls.remove(2);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    /**
     * 删除图片
     * @param i
     */
    private void remove1(int i){
        switch (i){
            case 0:
                try {
                    urls1.remove(0);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    urls1.remove(1);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    urls1.remove(2);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 刷新图片
     */
    private void judge(){
        iv1.setImageDrawable(null);
        iv2.setImageDrawable(null);
        iv3.setImageDrawable(null);
        delete1.setVisibility(View.GONE);
        delete2.setVisibility(View.GONE);
        delete3.setVisibility(View.GONE);
        if(urls.size()==0){
            iv1.setImageResource(R.drawable.jia);
        }
        if (urls.size()==1){
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls.get(0))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv1);
            delete1.setVisibility(View.VISIBLE);
            iv2.setImageResource(R.drawable.jia);
        }
        if (urls.size()==2){
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls.get(0))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv1);
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls.get(1))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv2);
            delete1.setVisibility(View.VISIBLE);
            delete2.setVisibility(View.VISIBLE);
            iv3.setImageResource(R.drawable.jia);
        }
        if (urls.size()==3){
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls.get(0))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv1);
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls.get(1))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv2);
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls.get(2))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv3);
            delete1.setVisibility(View.VISIBLE);
            delete2.setVisibility(View.VISIBLE);
            delete3.setVisibility(View.VISIBLE);
        }
        iv_1.setImageDrawable(null);
        iv_2.setImageDrawable(null);
        iv_3.setImageDrawable(null);
        delete_1.setVisibility(View.GONE);
        delete_2.setVisibility(View.GONE);
        delete_3.setVisibility(View.GONE);
        if(urls1.size()==0){
            iv_1.setImageResource(R.drawable.jia);
        }
        if (urls1.size()==1){
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls1.get(0))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv_1);
            delete_1.setVisibility(View.VISIBLE);
            iv_2.setImageResource(R.drawable.jia);
        }
        if (urls1.size()==2){
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls1.get(0))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv_1);
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls1.get(1))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv_2);
            delete_1.setVisibility(View.VISIBLE);
            delete_2.setVisibility(View.VISIBLE);
            iv_3.setImageResource(R.drawable.jia);
        }
        if (urls1.size()==3){
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls1.get(0))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv_1);
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls1.get(1))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv_2);
            Glide.with(this)
                    .load(UrlUtil.url+"/"+urls1.get(2))
                    .placeholder(R.drawable.timg)
                    .crossFade()
                    .into(iv_3);
            delete_1.setVisibility(View.VISIBLE);
            delete_2.setVisibility(View.VISIBLE);
            delete_3.setVisibility(View.VISIBLE);
        }
    }
    private View.OnClickListener clickListener = new View.OnClickListener(){

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    autoObtainCameraPermission();
                    break;
                case R.id.btn2:
                    autoObtainStoragePermission();
                    break;
            }
        }
    };
    /**
     *  此方法只是关闭软键盘
     */
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&this.getCurrentFocus()!=null){
            if (this.getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    public static int main() {
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < 10; i++) {
            return r.nextInt();
        }
        return 0;
    }
    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(UnitInformationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(UnitInformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(UnitInformationActivity.this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(UnitInformationActivity.this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(UnitInformationActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(UnitInformationActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(UnitInformationActivity.this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(UnitInformationActivity.this, "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(UnitInformationActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(UnitInformationActivity.this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(UnitInformationActivity.this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(UnitInformationActivity.this, "请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(UnitInformationActivity.this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(UnitInformationActivity.this, "请允许打操作SDCard！！");
                }
                break;
        }
    }

    private static final int output_X = 480;
    private static final int output_Y = 480;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo"+main()+".jpg");
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(UnitInformationActivity.this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo"+main()+".jpg");
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(UnitInformationActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(UnitInformationActivity.this, "com.zz.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(UnitInformationActivity.this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);

                    } else {
                        ToastUtils.showShort(UnitInformationActivity.this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    String u = fileCropUri.toString();
                    img = PhotoUtils.getBitmapFromUri(cropImageUri, UnitInformationActivity.this);
                    if(imageBoolean) {
                        if (urls.size() < 4) {
                            imageBoolean = false;
                            model1.uploadFile(u, new ICallBack() {
                                @Override
                                public void succeed(Object object) {
                                    ImageUrl imageUrl1 = (ImageUrl) object;
                                    urls.add(imageUrl1.getObj());
                                    Message message = new Message();
                                    message.what = 8;
                                    handler.sendMessage(message);
                                }
                                @Override
                                public void error(Object object) {
                                }
                            });
                        }
                    }
                    if(imageBoolean1) {
                        if (urls1.size() < 4) {
                            judge();
                            imageBoolean1 = false;
                            model1.uploadFile(u, new ICallBack() {
                                @Override
                                public void succeed(Object object) {
                                    ImageUrl imageUrl1 = (ImageUrl) object;
                                    urls1.add(imageUrl1.getObj());
                                    Message message = new Message();
                                    message.what = 8;
                                    handler.sendMessage(message);
                                }
                                @Override
                                public void error(Object object) {
                                }
                            });
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(UnitInformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UnitInformationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(UnitInformationActivity.this, CODE_GALLERY_REQUEST);
        }
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    private void setView() {
        sp_new_supplier_dq1 = (Spinner) findViewById(R.id.sp_unit_information_dq1);
        sp_new_supplier_dq2 = (Spinner) findViewById(R.id.sp_unit_information_dq2);
        sp_new_supplier_dq3 = (Spinner) findViewById(R.id.sp_unit_information_dq3);
        et_unit_information_dwxx = (EditText) findViewById(R.id.et_unit_information_dwxx);
        et_unit_information_xxdz = (EditText) findViewById(R.id.et_unit_information_xxdz);
        btn_new_supplier_bc = (Button) findViewById(R.id.btn_unit_information_bc);
        iv1 = (ImageView) findViewById(R.id.iv_unit_image1);
        iv2 = (ImageView) findViewById(R.id.iv_unit_image2);
        iv3 = (ImageView) findViewById(R.id.iv_unit_image3);
        delete1 = (ImageView) findViewById(R.id.delete_unit_1);
        delete2 = (ImageView) findViewById(R.id.delete_unit_2);
        delete3 = (ImageView) findViewById(R.id.delete_unit_3);
        iv_1 = (ImageView) findViewById(R.id.iv_information_image1);
        iv_2 = (ImageView) findViewById(R.id.iv_information_image2);
        iv_3 = (ImageView) findViewById(R.id.iv_information_image3);
        delete_1 = (ImageView) findViewById(R.id.delete_information_1);
        delete_2 = (ImageView) findViewById(R.id.delete_information_2);
        delete_3 = (ImageView) findViewById(R.id.delete_information_3);
        iv_back = findViewById(R.id.iv_unit_information_back);
    }
//    /**
//     * 根据图片的url路径获得Bitmap对象
//     * @param url
//     * @return
//     */
//    private Bitmap decodeUriAsBitmapFromNet(String url) {
//        URL fileUrl = null;
//        Bitmap bitmap = null;
//        try {
//            fileUrl = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
}
