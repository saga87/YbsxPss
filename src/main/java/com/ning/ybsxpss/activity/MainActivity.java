package com.ning.ybsxpss.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.ning.ybsxpss.R;
import com.ning.ybsxpss.adapter.DaifahuoAdapter;
import com.ning.ybsxpss.adapter.DaishouliAdapter;
import com.ning.ybsxpss.fragment.AllFragment;
import com.ning.ybsxpss.fragment.DaifahuoFragment;
import com.ning.ybsxpss.fragment.DaishouliFragment;
import com.ning.ybsxpss.fragment.YifahuoFragment;
import com.ning.ybsxpss.fragment.YiqianshouFragment;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private ImageView iv_back;
    private RadioButton rb_1,rb_2,rb_3,rb_4,rb_5;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utility.setToolbar(this);
        //初始化控件
        setView();
        //设置listener;
        setOnlistener();
        //设置Adapter
        setAdapter();

        Bundle bundle = getIntent().getExtras();
        String p = bundle.getString("pp");
        if(p.equals("1")){
            viewPager.setCurrentItem(0);
        }
        if(p.equals("2")){
            viewPager.setCurrentItem(1);
        }
        if(p.equals("3")){
            viewPager.setCurrentItem(2);
        }
        if(p.equals("4")){
            viewPager.setCurrentItem(3);
        }
        if(p.equals("5")){
            viewPager.setCurrentItem(4);
        }
    }
    private void setAdapter() {
        fragments = new ArrayList<>();
        fragments.add(new DaishouliFragment());
        fragments.add(new DaifahuoFragment());
        fragments.add(new YifahuoFragment());
        fragments.add(new YiqianshouFragment());
        fragments.add(new AllFragment());

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
    }
    private class  MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    private void setOnlistener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_4:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.rb_5:
                        viewPager.setCurrentItem(4);
                        break;
                }
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rb_1.setChecked(true);
                        break;
                    case 1:
                        rb_2.setChecked(true);
                        break;
                    case 2:
                        rb_3.setChecked(true);
                        break;
                    case 3:
                        rb_4.setChecked(true);
                        break;
                    case 4:
                        rb_5.setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i< DaishouliAdapter.flag.length; i++){
                    if(DaishouliAdapter.flag[i]==true){
                        DaishouliAdapter.flag[i]=false;
                    }
                }
                DaishouliAdapter.list.clear();
                for (int i = 0; i<DaifahuoAdapter.flag1.length;i++){
                    if(DaifahuoAdapter.flag1[i]==true){
                        DaifahuoAdapter.flag1[i]=false;
                    }
                }
                DaifahuoAdapter.list1.clear();
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
        for (int i = 0; i< DaishouliAdapter.flag.length; i++){
            if(DaishouliAdapter.flag[i]==true){
                DaishouliAdapter.flag[i]=false;
            }
        }
        DaishouliAdapter.list.clear();
        for (int i = 0; i< DaifahuoAdapter.flag1.length; i++){
            if(DaifahuoAdapter.flag1[i]==true){
                DaifahuoAdapter.flag1[i]=false;
            }
        }
        DaifahuoAdapter.list1.clear();
    }
    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setAction("rfid_card_userCenter");
        this.sendBroadcast(intent);
        super.onDestroy();
    }

    private void setView() {
        viewPager= findViewById(R.id.viewPager_ddsl);
        radioGroup = findViewById(R.id.radioGroup);
        rb_1 = findViewById(R.id.rb_1);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);
        rb_4 = findViewById(R.id.rb_4);
        rb_5 =  findViewById(R.id.rb_5);
        iv_back = findViewById(R.id.iv_main_back);
    }
}
