package com.ning.ybsxpss.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.LoginObg;
import com.ning.ybsxpss.model.MyClientModel;
import com.ning.ybsxpss.util.ICallBack;

import java.util.Calendar;

/**
 * Created by fxn on 2017/10/13.
 */

public class MyDialog extends Dialog{
    private Context context;
    private String purchaserId;
    private String companyName;
    private Handler handler;
    private String cq ="0";
    private MyClientModel model;
    int position;

    //可以看到两个构造器，想自定义样式的就用第二个啦
    public MyDialog(Context context,String purchaserId,String companyName,MyClientModel model,Handler handler,int position) {
        super(context);
        this.position = position;
        this.context = context;
        this.handler = handler;
        this.model = model;
        this.purchaserId = purchaserId;
        this.companyName = companyName;
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    //控件的声明
    TextView dialog_name;
    TextView dialog_qssj_time;
    TextView dialog_jssj_time;
    CheckBox dialog_cq;
    Button dialog_tijiao;
    Button dialog_quxiao;

    private void init() {
        //以view的方式引入，然后回调activity方法，setContentView，实现自定义布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_my, null);
        setContentView(view);
        //radiobutton的初始化
        dialog_name = (TextView) view.findViewById(R.id.dialog_name);
        dialog_qssj_time = (TextView) view.findViewById(R.id.dialog_qssj_time);
        dialog_jssj_time = (TextView) view.findViewById(R.id.dialog_jssj_time);
        dialog_cq = (CheckBox) view.findViewById(R.id.dialog_cq);
        dialog_tijiao = (Button) view.findViewById(R.id.dialog_tijiao);
        dialog_quxiao = (Button) view.findViewById(R.id.dialog_quxiao);
        //设置dialog大小，这里是一个小赠送，模块好的控件大小设置
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(params);
        setListener();
    }

    private void setListener() {
        dialog_name.setText(companyName);
        dialog_qssj_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dialog_qssj_time.setText(String.format("%d-%d-%d",i,i1+1,i2));
                    }
                },2017,10,13).show();
            }
        });
        dialog_jssj_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dialog_jssj_time.setText(String.format("%d-%d-%d",i,i1+1,i2));
                    }
                },2017,10,13).show();
            }
        });
        dialog_cq.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cq = "1";
                }else {
                    cq = "0";
                }
            }
        });
        dialog_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qssj = dialog_qssj_time.getText().toString();
                String jssj = dialog_jssj_time.getText().toString();
                model.addCooperationApply(purchaserId, qssj, jssj, cq, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        LoginObg obg = (LoginObg) object;
                        if(obg.isSuccess()){
                            Message message = new Message();
                            message.what = 4;
                            message.arg1 = position;
                            handler.sendMessage(message);
                            dismiss();
                        }
                    }
                    @Override
                    public void error(Object object) {
                    }
                });
            }
        });
        dialog_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
