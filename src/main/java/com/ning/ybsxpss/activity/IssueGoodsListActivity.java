package com.ning.ybsxpss.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.GoodsTypeAdapter;
import com.ning.ybsxpss.adapter.IssueGoodsListAdapter;
import com.ning.ybsxpss.camera.PhotoUtils;
import com.ning.ybsxpss.camera.ToastUtils;
import com.ning.ybsxpss.entity.GoodsType;
import com.ning.ybsxpss.entity.ImageUrl;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.entity.UnSetGoodsList;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.LocalBroadcastUtil;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.BottomMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IssueGoodsListActivity extends BaseActivity {
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri;
    private Uri imageUri;
    private Uri cropImageUri;
    private Bitmap img;
    private BottomMenu menuWindow;
    private ImageUrl imageUrl1;
    private ImageView iv_back;

    private TextView tv_issue_goods_list_sqtj;
    public static List<UnSetGoodsList> lists;
    private RecyclerView mRecyclerView;
    private GoodsModel model;
    private TextView tv_issue_goods_dd;
    private Button btn_issue_goods_bc;
    private int arg1;
    private IssueGoodsListAdapter adapter;
    private int arg2;
    private List<String> goodsList = new ArrayList<>();
    private List<GoodsType> type;

    private String goodsId;
    private String goodsName;
    private String goodsUnit;
    private String price;
    private String qdl;
    private String imgUrl;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                for(int i=0;i<type.size();i++){
                    goodsList.add(type.get(i).getTypename());
                }
                mRecyclerView.setLayoutManager(new LinearLayoutManager(IssueGoodsListActivity.this));
                adapter = new IssueGoodsListAdapter(IssueGoodsListActivity.this,model,handler,goodsList);
                mRecyclerView.setAdapter(adapter);
                tv_issue_goods_dd.setText("共发布"+lists.size()+"个商品");
            }
            if (msg.what == 2) {
                arg1 = msg.arg1;
                menuWindow = new BottomMenu(IssueGoodsListActivity.this, clickListener);
                menuWindow.show();
                hintKbTwo();
            }
            if (msg.what == 3) {
                adapter.notifyDataSetChanged();
                tv_issue_goods_dd.setText("共发布了"+lists.size()+"个商品");
            }
            if (msg.what == 4) {
                arg2 = msg.arg1;
                String goodsid = (String) msg.obj;
                delGoods(goodsid);
            }
            if (msg.what == 5) {
                lists.remove(arg2);
                tv_issue_goods_dd.setText("共发布了"+lists.size()+"个商品");
                adapter.notifyDataSetChanged();
            }
            if (msg.what == 6) {
                Toast.makeText(IssueGoodsListActivity.this,"保存失败，请重试",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private BroadcastReceiver RFIDFinishReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("rfid_card_goodslist")){
                model.getUnSetGoodsList(new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        lists = (List<UnSetGoodsList>) object;
                        Message message = Message.obtain();
                        message.what = 3;
                        handler.sendMessage(message);
                    }
                    public void error(Object object) {
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_goods_list);
        Utility.setToolbar(this);
        setView();
        getmodel();
        setListener();
        //接收广播
        IntentFilter filter= new IntentFilter();
        filter.addAction("rfid_card_goodslist");
        registerReceiver(RFIDFinishReceiver , filter);
    }
    private void getmodel() {
        model = new GoodsModel();
        model.getUnSetGoodsList(new ICallBack() {
            @Override
            public void succeed(Object object) {
                lists = (List<UnSetGoodsList>) object;
                model.getTimeOrUnit("jldw", new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        type = (List<GoodsType>) object;
                        handler.sendEmptyMessage(1);
                    }
                    @Override
                    public void error(Object object) {}
                });
            }
            public void error(Object object) {
            }
        });
    }

    private void setListener() {
        tv_issue_goods_list_sqtj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IssueGoodsListActivity.this,IssueGoodsActivity.class);
                startActivity(intent);
            }
        });
        btn_issue_goods_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goodsId = "";
                    goodsName = "";
                    goodsUnit = "";
                    price = "";
                    qdl = "";
                    imgUrl = "";
                    for (int i = 0; i < lists.size(); i++) {
                        if (lists.get(i).getGoodsId().equals("") || lists.get(i).getGoodstype().equals("")|| lists.get(i).getPrice().equals("") ||
                                lists.get(i).getQdj().equals("") || lists.get(i).getImgUrl() .equals("")) {
                            Toast.makeText(IssueGoodsListActivity.this, "请补全数据，数据不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            goodsId = goodsId + lists.get(i).getGoodsId() + ",";
                            goodsName = goodsName + lists.get(i).getGoodsName() + ",";
                            goodsUnit = goodsUnit + lists.get(i).getGoodstype() + ",";
                            price = price + lists.get(i).getPrice() + ",";
                            qdl = qdl + lists.get(i).getQdj() + ",";
                            imgUrl = imgUrl + lists.get(i).getImgUrl() + ",";
                        }
                    }
                    goodsId = goodsId.substring(0, goodsId.length() - 1);
                    goodsName = goodsName.substring(0, goodsName.length() - 1);
                    goodsUnit = goodsUnit.substring(0, goodsUnit.length() - 1);
                    price = price.substring(0, price.length() - 1);
                    qdl = qdl.substring(0, qdl.length() - 1);
                    imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
                    model.batchUpdateGoods(goodsId,goodsName, goodsUnit, price, qdl, imgUrl, new ICallBack() {
                        public void succeed(Object object) {
                            LoginObg obg = (LoginObg) object;
                            if (obg.isSuccess()) {
                                LocalBroadcastUtil.sendshwhBroadcast("shangxia");
                                finish();
                            }else {
                                Message message = Message.obtain();
                                message.what = 6;
                                handler.sendMessage(message);
                            }
                        }
                        public void error(Object object) {}
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void delGoods(String id) {
       model.delGoods(id, new ICallBack() {
           @Override
           public void succeed(Object object) {
               LoginObg obg = (LoginObg) object;
               if(obg.isSuccess()) {
                   Message message = Message.obtain();
                   message.what = 5;
                   handler.sendMessage(message);
               }
           }
           @Override
           public void error(Object object) {}
       });
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
        if (ContextCompat.checkSelfPermission(IssueGoodsListActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(IssueGoodsListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(IssueGoodsListActivity.this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(IssueGoodsListActivity.this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(IssueGoodsListActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(IssueGoodsListActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(IssueGoodsListActivity.this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(IssueGoodsListActivity.this, "设备没有SD卡！");
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
                            imageUri = FileProvider.getUriForFile(IssueGoodsListActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(IssueGoodsListActivity.this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(IssueGoodsListActivity.this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(IssueGoodsListActivity.this, "请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(IssueGoodsListActivity.this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(IssueGoodsListActivity.this, "请允许打操作SDCard！！");
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
                    PhotoUtils.cropImageUri(IssueGoodsListActivity.this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo"+main()+".jpg");
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(IssueGoodsListActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(IssueGoodsListActivity.this, "com.zz.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(IssueGoodsListActivity.this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);

                    } else {
                        ToastUtils.showShort(IssueGoodsListActivity.this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    String u = fileCropUri.toString();
                    img = PhotoUtils.getBitmapFromUri(cropImageUri, IssueGoodsListActivity.this);
                    model.uploadImg(u, new ICallBack() {
                        @Override
                        public void succeed(Object object) {
                            imageUrl1 = (ImageUrl) object;
                            lists.get(arg1).setImgUrl(imageUrl1.getObj());
                            Message message = Message.obtain();
                            message.what = 3;
                            handler.sendMessage(message);
                        }
                        @Override
                        public void error(Object object) {}
                    });
                    break;
            }
        }
    }
    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(IssueGoodsListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IssueGoodsListActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(IssueGoodsListActivity.this, CODE_GALLERY_REQUEST);
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
        tv_issue_goods_list_sqtj = (TextView) findViewById(R.id.tv_issue_goods_list_sqtj);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_issue_goods_list);
        tv_issue_goods_dd = (TextView) findViewById(R.id.tv_issue_goods_list_dd);
        btn_issue_goods_bc = (Button) findViewById(R.id.btn_issue_goods_list_bc);
        iv_back = (ImageView) findViewById(R.id.iv_issue_good_list_back);
    }
}
