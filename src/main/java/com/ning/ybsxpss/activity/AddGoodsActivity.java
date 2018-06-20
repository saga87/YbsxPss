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
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.camera.PhotoUtils;
import com.ning.ybsxpss.camera.ToastUtils;
import com.ning.ybsxpss.entity.GoodsOneType;
import com.ning.ybsxpss.entity.GoodsTwoType;
import com.ning.ybsxpss.entity.ImageUrl;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.BottomMenu;

import org.zackratos.ultimatebar.UltimateBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddGoodsActivity extends BaseActivity {
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
    private Bitmap img;
    private BottomMenu menuWindow;
    private ImageView iv1;
    private ImageView delete1;
    private ImageView iv_back;

    private Spinner sp_add_goods_1,sp_add_goods_2;
    private EditText et_add_goods_spmc;
    private Button btn_add_goods_tj;
    private GoodsModel model;
    private ImageUrl imageUrl1;
    private List<GoodsOneType> oneType;
    private List<String> names = new ArrayList<>();
    private List<String> names1 = new ArrayList<>();
    private List<GoodsTwoType> twoType;
    private String goodsId;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                for (int i =0;i<oneType.size();i++) {
                    String typename = oneType.get(i).getName();
                    names.add(typename);
                }
                ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(AddGoodsActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_add_goods_1.setAdapter(arr_adapter);
                sp_add_goods_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String str1 =  oneType.get(position).getName();
                        String str2 =  sp_add_goods_1.getSelectedItem().toString();
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
                ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(AddGoodsActivity.this, android.R.layout.simple_spinner_dropdown_item, names1);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_add_goods_2.setAdapter(arr_adapter);
                sp_add_goods_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String str1 =  twoType.get(position).getName();
                        String str2 =  sp_add_goods_2.getSelectedItem().toString();
                        if (str1.equals(str2)) {
                            goodsId = twoType.get(position).getId();
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            if(msg.what==3) {
                et_add_goods_spmc.setText("");
                remove(0);
                Toast.makeText(AddGoodsActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
            }
            if(msg.what==4) {
                Toast.makeText(AddGoodsActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
        delete1.setVisibility(View.GONE);
    }
    private void getmodel() {
        model = new GoodsModel();
        model.getAllClassificationList( new ICallBack() {
            @Override
            public void succeed(Object object) {
                oneType = ( List<GoodsOneType>) object;
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
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.size() == 0) {
                    menuWindow = new BottomMenu(AddGoodsActivity.this, clickListener);
                    menuWindow.show();
                    hintKbTwo();
                }
            }
        });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddGoodsActivity.this);
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
        btn_add_goods_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_add_goods_spmc.getText().toString();
                if(imageUrl1==null){
                    Toast.makeText(AddGoodsActivity.this,"请先选择图片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.equals("")){
                    Toast.makeText(AddGoodsActivity.this,"请先输入名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.applyAddClassification(name, goodsId , imageUrl1.getObj(), new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if (obg.isSuccess()){
                            Message message = new Message();
                            message.what = 3;
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
        if (ContextCompat.checkSelfPermission(AddGoodsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(AddGoodsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(AddGoodsActivity.this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(AddGoodsActivity.this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(AddGoodsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(AddGoodsActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(AddGoodsActivity.this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(AddGoodsActivity.this, "设备没有SD卡！");
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
                            imageUri = FileProvider.getUriForFile(AddGoodsActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(AddGoodsActivity.this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(AddGoodsActivity.this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(AddGoodsActivity.this, "请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(AddGoodsActivity.this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(AddGoodsActivity.this, "请允许打操作SDCard！！");
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
                    PhotoUtils.cropImageUri(AddGoodsActivity.this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo"+main()+".jpg");
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(AddGoodsActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(AddGoodsActivity.this, "com.zz.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(AddGoodsActivity.this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);

                    } else {
                        ToastUtils.showShort(AddGoodsActivity.this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    String u = fileCropUri.toString();
                    img = PhotoUtils.getBitmapFromUri(cropImageUri, AddGoodsActivity.this);
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
        if (ContextCompat.checkSelfPermission(AddGoodsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddGoodsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(AddGoodsActivity.this, CODE_GALLERY_REQUEST);
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
        sp_add_goods_1 = (Spinner) findViewById(R.id.sp_add_goods_1);
        sp_add_goods_2 = (Spinner) findViewById(R.id.sp_add_goods_2);
        et_add_goods_spmc = (EditText) findViewById(R.id.et_add_goods_spmc);
        btn_add_goods_tj = (Button) findViewById(R.id.btn_add_goods_tj);
        iv1 = (ImageView) findViewById(R.id.iv_add_goods_image);
        delete1 = (ImageView) findViewById(R.id.delete_add_goods);
        iv_back = findViewById(R.id.iv_add_goods_back);
    }
}
