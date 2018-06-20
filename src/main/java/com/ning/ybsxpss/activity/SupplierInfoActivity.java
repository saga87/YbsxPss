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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DaifahuoAdapter;
import com.ning.ybsxpss.adapter.SourceGoodsListAdapter;
import com.ning.ybsxpss.camera.PhotoUtils;
import com.ning.ybsxpss.camera.ToastUtils;
import com.ning.ybsxpss.entity.ImageUrl;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.MySupplierList;
import com.ning.ybsxpss.entity.SourceGoodsListObj;
import com.ning.ybsxpss.entity.SupplierInfodfh;
import com.ning.ybsxpss.model.IndentModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.BottomMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SupplierInfoActivity extends BaseActivity {
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

    private List<MySupplierList> obj;
    private Spinner sp_supplier_ghs;
    private TextView tv_supplier_ghslx,tv_supplier_ghsdz,tv_supplier_fzr,tv_supplier_lxdh;
    private Button btn_supplier_bc;
    private List<String> names = new ArrayList<>();
    private IndentModel model;
    private SupplierInfodfh dfh;
    private ImageUrl imageUrl1;
    private String orderId;
    private String typeId;
    String imgurl;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                for (int i =0;i<obj.size();i++) {
                    String typename = obj.get(i).getSupplierName();
                    names.add(typename);
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(SupplierInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                //设置样式
                arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_supplier_ghs.setAdapter(arr_adapter1);
                sp_supplier_ghs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for (int i=0;i<obj.size();i++) {
                            if (obj.get(position).getSupplierName().equals(sp_supplier_ghs.getSelectedItem().toString())) {
                                typeId = obj.get(position).getSupplierId();
                                model.getSupplierInfo_dfh(typeId, new ICallBack() {
                                    @Override
                                    public void succeed(Object object) {
                                        dfh = (SupplierInfodfh) object;
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
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            if(msg.what==2) {
                switch (dfh.getType()){
                    case "0":
                        tv_supplier_ghslx.setText("种植基地");
                        break;
                    case "1":
                        tv_supplier_ghslx.setText("养殖基地");
                        break;
                    case "2":
                        tv_supplier_ghslx.setText("食品加工企业");
                        break;
                    case "3":
                        tv_supplier_ghslx.setText("批发市场");
                        break;
                    case "4":
                        tv_supplier_ghslx.setText("专业合作社");
                        break;
                    case "5":
                        tv_supplier_ghslx.setText("农户");
                        break;
                }
                tv_supplier_ghsdz.setText(dfh.getSupplierAddress());
                tv_supplier_fzr.setText(dfh.getFzr());
                tv_supplier_lxdh.setText(dfh.getPhone());
            }
            if(msg.what==3) {
                Toast.makeText(SupplierInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("rfid_card_found1");
                SupplierInfoActivity.this.sendBroadcast(intent);
                for (int i = 0; i<SourceGoodsListAdapter.flag2.length;i++){
                    if(SourceGoodsListAdapter.flag2[i]==true){
                        SourceGoodsListAdapter.flag2[i]=false;
                    }
                }
                SourceGoodsListAdapter.list2.clear();
                finish();
            }
            if(msg.what==4) {
                Toast.makeText(SupplierInfoActivity.this,"保存失败，请重试",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_info);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
        delete1.setVisibility(View.GONE);
    }
    private void getmodel() {
        Bundle bundle = getIntent().getExtras();
        orderId = bundle.getString("useridd");
        model = new IndentModel();
        model.getMySupplierList( new ICallBack() {
            @Override
            public void succeed(Object object) {
                obj = (List<MySupplierList>) object;
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
                    menuWindow = new BottomMenu(SupplierInfoActivity.this, clickListener);
                    menuWindow.show();
                    hintKbTwo();
                }
            }
        });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SupplierInfoActivity.this);
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
        btn_supplier_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SourceGoodsListAdapter.list2.size()==0){
                    Toast.makeText(SupplierInfoActivity.this,"请先选择商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                String str = SourceGoodsListAdapter.list2.toString();
                str = str.replace(" ", "");
                final String s = str.substring(1, str.length()-1);
                if(imageUrl1==null){
                   imgurl = "";
                }else {
                    imgurl = imageUrl1.getObj();
                }
                model.savaSource(s, orderId, typeId, imgurl, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if (obg.isSuccess()){
                            Message message = Message.obtain();
                            message.what = 3;
                            handler.sendMessage(message);
                        }else {
                            Message message = Message.obtain();
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
        if (ContextCompat.checkSelfPermission(SupplierInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(SupplierInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SupplierInfoActivity.this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(SupplierInfoActivity.this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(SupplierInfoActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(SupplierInfoActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(SupplierInfoActivity.this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(SupplierInfoActivity.this, "设备没有SD卡！");
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
                            imageUri = FileProvider.getUriForFile(SupplierInfoActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(SupplierInfoActivity.this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(SupplierInfoActivity.this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(SupplierInfoActivity.this, "请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(SupplierInfoActivity.this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(SupplierInfoActivity.this, "请允许打操作SDCard！！");
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
                    PhotoUtils.cropImageUri(SupplierInfoActivity.this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo"+main()+".jpg");
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(SupplierInfoActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(SupplierInfoActivity.this, "com.zz.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(SupplierInfoActivity.this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);

                    } else {
                        ToastUtils.showShort(SupplierInfoActivity.this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    String u = fileCropUri.toString();
                    img = PhotoUtils.getBitmapFromUri(cropImageUri, SupplierInfoActivity.this);
                    if(images.size()<2){
                        images.add(img);
                        urls.add(u);
                    }
                    if(images.size()==1) {
                        iv1.setImageBitmap(img);
                        delete1.setVisibility(View.VISIBLE);
                        model.uploadImg_pz(u, new ICallBack() {
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
        if (ContextCompat.checkSelfPermission(SupplierInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SupplierInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(SupplierInfoActivity.this, CODE_GALLERY_REQUEST);
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
        sp_supplier_ghs = (Spinner) findViewById(R.id.sp_supplier_ghs);
        tv_supplier_ghslx = (TextView) findViewById(R.id.tv_supplier_ghslx);
        tv_supplier_ghsdz = (TextView) findViewById(R.id.tv_supplier_ghsdz);
        tv_supplier_fzr = (TextView) findViewById(R.id.tv_supplier_fzr);
        tv_supplier_lxdh = (TextView) findViewById(R.id.tv_supplier_lxdh);
        btn_supplier_bc = (Button) findViewById(R.id.btn_supplier_bc);
        iv1 = (ImageView) findViewById(R.id.iv_image1);
        delete1 = (ImageView) findViewById(R.id.delete_1);
        iv_back = findViewById(R.id.iv_supplier_back);
    }
}
