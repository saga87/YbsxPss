package com.ning.ybsxpss.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ning.ybsxpss.R;
import com.ning.ybsxpss.camera.PhotoUtils;
import com.ning.ybsxpss.camera.ToastUtils;
import com.ning.ybsxpss.entity.GoodsType;
import com.ning.ybsxpss.entity.ImageUrl;
import com.ning.ybsxpss.entity.JsonBean;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.LocalBroadcastUtil;
import com.ning.ybsxpss.util.UrlUtil;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.BottomMenu;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AmendActivity extends BaseActivity {
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri;
    private Uri imageUri;
    private Uri cropImageUri;
    private List<Bitmap> images = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private Bitmap photoBmp;
    private Bitmap img;
    private BottomMenu menuWindow;
    private ImageView iv1;
    private ImageView delete1;
    private ImageView iv_back;

    private TextView tv_amend_type;
    private EditText et_amend_price,et_amend_qdj,tv_amend_spmc;
    private Spinner sp_amend_jldw;
    private List<String> goodsList = new ArrayList<>();
    private String classificationName;
    private List<GoodsType> type;
    private String goodsUnit;

    private Button btn_amend_save;
    private GoodsModel model;
    private String goodsId;
    private String goodsName;
    private int position;
    private ImageUrl imageUrl1 = new ImageUrl();
    //三级联动 品种
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private String classificationId;

    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                Toast.makeText(AmendActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                LocalBroadcastUtil.sendshwhBroadcast("shangxia");
                finish();
            }
            if(msg.what==2) {
                int position = 0;
                for(int i=0;i<type.size();i++){
                    goodsList.add(type.get(i).getTypename());
                    if (goodsUnit!=null&&goodsUnit.equals(type.get(i).getTypename())){
                        position = i;
                    }
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(AmendActivity.this,android.R.layout.simple_spinner_item, goodsList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_amend_jldw .setAdapter(adapter);
                sp_amend_jldw.setSelection(position);
            }
            if(msg.what==3) {
                OptionsPickerView pvOptions = new  OptionsPickerView.Builder(AmendActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                    public void onOptionsSelect(int options1, int options2, int options3 ,View v) {
                        String tx = options3Items.get(options1).get(options2).get(options3);
                        classificationId = options1Items.get(options1).getChildren().get(options2).getChildren().get(options3).getClassificationId();
                        tv_amend_type.setText(tx);
                    }
                }).build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
        delete1.setVisibility(View.GONE);
    }
    private void getmodel() {
        Bundle bundle = getIntent().getExtras();
        goodsId = bundle.getString("goodsId");
        goodsName = bundle.getString("goodsName");
        double price = bundle.getDouble("price");
        int minAmount = (int) bundle.getDouble("minAmount");
        goodsUnit = bundle.getString("goodsUnit");
        String imgUrl = bundle.getString("imgUrl");
        position = bundle.getInt("position");
        classificationName = bundle.getString("classificationName");
        classificationId = bundle.getString("classificationId");
        tv_amend_type.setText(classificationName);
        tv_amend_spmc.setText(goodsName);
        et_amend_price.setText(price+"");
        et_amend_qdj.setText(minAmount+"");
        if(imgUrl!=null&&!imgUrl.equals("")){
            imageUrl1.setObj(imgUrl);
        }
        String url =  UrlUtil.url+"/"+imgUrl;
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.timg)
                .crossFade()
                .into(iv1);
        model = new GoodsModel();

        model.getTimeOrUnit("jldw", new ICallBack() {
            @Override
            public void succeed(Object object) {
                type = (List<GoodsType>) object;
                handler.sendEmptyMessage(2);
            }
            @Override
            public void error(Object object) {}
        });
        tv_amend_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_amend_type.setClickable(false);
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        tv_amend_type.setClickable(true);
                    }
                }, 2000);
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 写子线程中的操作,解析省市区数据
                        initJsonData();
                    }
                });
                thread.start();
            }
        });
    }

    private void setListener() {
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.size() == 0) {
                    menuWindow = new BottomMenu(AmendActivity.this, clickListener);
                    menuWindow.show();
                    hintKbTwo();
                }
            }
        });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AmendActivity.this);
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
        btn_amend_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = et_amend_price.getText().toString();
                String qdj = et_amend_qdj.getText().toString();
                String jldw = sp_amend_jldw.getSelectedItem().toString();
                String goodsName = tv_amend_spmc.getText().toString();
                if(price.equals("")||qdj.equals("")||jldw.equals("")){
                    Toast.makeText(AmendActivity.this,"请填写数据",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(imageUrl1==null){
                    Toast.makeText(AmendActivity.this,"请选择图片",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.updateOneGoods(goodsId , jldw,goodsName, price, qdj ,classificationId,imageUrl1.getObj(), new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if (obg.isSuccess()){
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }else {
                            Message message = new Message();
                            message.what = 4;
                            handler.sendMessage(message);
                        }
                    }
                    @Override
                    public void error(Object object) {
                    }
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
                    images.remove(0);
                    urls.remove(0);
                    imageUrl1 = null;
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
        delete1.setVisibility(View.GONE);
        if(images.size()==0){
            iv1.setImageResource(R.drawable.image);
        }
        if (images.size()==1){
            iv1.setImageBitmap(images.get(0));
            delete1.setVisibility(View.VISIBLE);
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
        if (ContextCompat.checkSelfPermission(AmendActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(AmendActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(AmendActivity.this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(AmendActivity.this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(AmendActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(AmendActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(AmendActivity.this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(AmendActivity.this, "设备没有SD卡！");
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
                            imageUri = FileProvider.getUriForFile(AmendActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(AmendActivity.this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(AmendActivity.this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(AmendActivity.this, "请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(AmendActivity.this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(AmendActivity.this, "请允许打操作SDCard！！");
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
                    PhotoUtils.cropImageUri(AmendActivity.this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo"+main()+".jpg");
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(AmendActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(AmendActivity.this, "com.zz.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(AmendActivity.this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);

                    } else {
                        ToastUtils.showShort(AmendActivity.this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    String u = fileCropUri.toString();
                    img = PhotoUtils.getBitmapFromUri(cropImageUri, AmendActivity.this);
                    urls.clear();
                    if(images.size()<2){
                        images.add(img);
                        urls.add(u);
                    }
                    if(images.size()==1) {
                        iv1.setImageBitmap(img);
                        delete1.setVisibility(View.VISIBLE);
                        model.uploadImg(u, new ICallBack() {
                            @Override
                            public void succeed(Object object) {
                                imageUrl1 = (ImageUrl) object;
                            }
                            @Override
                            public void error(Object object) {
                            }
                        });
                    }
                    break;
            }
        }
    }
    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(AmendActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AmendActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(AmendActivity.this, CODE_GALLERY_REQUEST);
        }
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private void initJsonData() {//解析数据
        ArrayList<JsonBean> jsonBean = GoodsMaintainActivity.bean;//用Gson 转成实体
        /**
         * 添加一级数据
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c=0; c<jsonBean.get(i).getChildren().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getChildren().get(c).getClassificationName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getChildren().get(c).getChildren() == null
                        ||jsonBean.get(i).getChildren().get(c).getChildren().size()==0) {
                    City_AreaList.add("");
                }else {
                    for (int d=0; d < jsonBean.get(i).getChildren().get(c).getChildren().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getChildren().get(c).getChildren().get(d).getClassificationName();
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加二级数据
             */
            options2Items.add(CityList);
            /**
             * 添加三级数据
             */
            options3Items.add(Province_AreaList);
        }
        handler.sendEmptyMessage(3);
    }

    private void setView() {
        sp_amend_jldw = findViewById(R.id.sp_amend_jldw);
        tv_amend_spmc = findViewById(R.id.tv_amend_spmc);
        tv_amend_type = findViewById(R.id.tv_amend_type);
        et_amend_price = (EditText) findViewById(R.id.et_amend_price);
        et_amend_qdj = (EditText) findViewById(R.id.et_amend_qdj);
        btn_amend_save = (Button) findViewById(R.id.btn_amend_save);
        iv1 = (ImageView) findViewById(R.id.iv_amend_image1);
        delete1 = (ImageView) findViewById(R.id.delete_amend_1);
        iv_back = findViewById(R.id.et_amend_back);
    }
}
