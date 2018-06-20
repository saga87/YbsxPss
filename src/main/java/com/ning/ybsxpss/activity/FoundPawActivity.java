package com.ning.ybsxpss.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.fragment.BasicInfoFragment;
import com.ning.ybsxpss.fragment.EmileFragment;
import com.ning.ybsxpss.fragment.FoundPawFragment;
import com.ning.ybsxpss.fragment.PhoneFragment;
import com.ning.ybsxpss.fragment.UnitInfoFragment;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class FoundPawActivity extends BaseActivity {
    private ViewPager viewPager;
    private TextView tv_register_1,tv_register_2;
    private LinearLayout ll_found_paw_1,ll_found_paw_2;
    private ImageView iv_back;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_paw);
        Utility.setToolbar(this);
        //初始化控件
        setView();
        //设置listener;
        setOnlistener();
        //设置Adapter
        setAdapter();
        tv_register_1.setVisibility(View.VISIBLE);
        tv_register_2.setVisibility(View.GONE);
    }
    private void setAdapter() {
        try {
            fragments = new ArrayList<>();
            fragments.add(new PhoneFragment());
            fragments.add(new EmileFragment());

            MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tv_register_1.setVisibility(View.VISIBLE);
                        tv_register_2.setVisibility(View.GONE);
                        break;
                    case 1:
                        tv_register_1.setVisibility(View.GONE);
                        tv_register_2.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ll_found_paw_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        ll_found_paw_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setView() {
        viewPager= (ViewPager) findViewById(R.id.viewPager_found_paw_1);
        tv_register_1= (TextView) findViewById(R.id.tv_found_paw_1);
        tv_register_2= (TextView) findViewById(R.id.tv_found_paw_2);
        ll_found_paw_1 = (LinearLayout) findViewById(R.id.ll_found_paw_1);
        ll_found_paw_2 = (LinearLayout) findViewById(R.id.ll_found_paw_2);
        iv_back = findViewById(R.id.iv_found_paw_back);
    }
}
