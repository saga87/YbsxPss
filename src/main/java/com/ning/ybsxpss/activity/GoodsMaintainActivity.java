package com.ning.ybsxpss.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.entity.JsonBean;
import com.ning.ybsxpss.fragment.AllFragment;
import com.ning.ybsxpss.fragment.DaifahuoFragment;
import com.ning.ybsxpss.fragment.DaishouliFragment;
import com.ning.ybsxpss.fragment.ShangJiaFragment;
import com.ning.ybsxpss.fragment.XiaJiaFragment;
import com.ning.ybsxpss.fragment.YifahuoFragment;
import com.ning.ybsxpss.fragment.YiqianshouFragment;
import com.ning.ybsxpss.model.GoodsModel;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.ICallBack;
import com.ning.ybsxpss.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class GoodsMaintainActivity extends BaseActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rb_1,rb_2;
    private List<Fragment> fragments;
    private TextView tv_goods_fb;
    private ImageView iv_back;
    public static ArrayList<JsonBean> bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_maintain);
        Utility.setToolbar(this);
        //初始化控件
        setView();
        //设置listener;
        setOnlistener();
        //设置Adapter
        setAdapter();

    }
    private void setAdapter() {
        fragments = new ArrayList<>();
        fragments.add(new ShangJiaFragment());
        fragments.add(new XiaJiaFragment());

        GoodsMaintainActivity.MyAdapter adapter = new GoodsMaintainActivity.MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
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
                    case R.id.rb_goods_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_goods_2:
                        viewPager.setCurrentItem(1);
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
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tv_goods_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoodsMaintainActivity.this,IssueGoodsListActivity.class);
                startActivity(intent);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GoodsModel model = new GoodsModel();
        model.getClassificaitonTree(new ICallBack() {
            @Override
            public void succeed(Object object) {
                bean = (ArrayList<JsonBean>) object;
            }
            @Override
            public void error(Object object) {
            }
        });
    }

    private void setView() {
        viewPager= (ViewPager) findViewById(R.id.viewPager_sbwh);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_goods);
        rb_1 = (RadioButton) findViewById(R.id.rb_goods_1);
        rb_2 = (RadioButton) findViewById(R.id.rb_goods_2);
        tv_goods_fb = (TextView) findViewById(R.id.tv_goods_fb);
        iv_back = findViewById(R.id.iv_goods_back);
    }
}
