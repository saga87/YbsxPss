package com.ning.ybsxpss.activity;

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
import com.ning.ybsxpss.entity.Register;
import com.ning.ybsxpss.fragment.AllFragment;
import com.ning.ybsxpss.fragment.BasicInfoFragment;
import com.ning.ybsxpss.fragment.DaifahuoFragment;
import com.ning.ybsxpss.fragment.DaishouliFragment;
import com.ning.ybsxpss.fragment.FoundPawFragment;
import com.ning.ybsxpss.fragment.UnitInfoFragment;
import com.ning.ybsxpss.fragment.YifahuoFragment;
import com.ning.ybsxpss.fragment.YiqianshouFragment;
import com.ning.ybsxpss.util.BaseActivity;
import com.ning.ybsxpss.util.Utility;
import com.ning.ybsxpss.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends BaseActivity {
    private static NoScrollViewPager viewPager;
    private TextView tv_register_1,tv_register_2,tv_register_3;
    private List<Fragment> fragments;
    private ImageView iv_back;
    public static Register register = new Register();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Utility.setToolbar(this);
        //初始化控件
        setView();
        //设置listener;
        setOnlistener();
        //设置Adapter
        setAdapter();
        tv_register_1.setVisibility(View.VISIBLE);
        tv_register_2.setVisibility(View.GONE);
        tv_register_3.setVisibility(View.GONE);
    }
    public static void setLocation(int location) {
        viewPager.setCurrentItem(location);
    }
    private void setAdapter() {
        fragments = new ArrayList<>();
        fragments.add(new BasicInfoFragment());
        fragments.add(new UnitInfoFragment());
        fragments.add(new FoundPawFragment());

        RegisterActivity.MyAdapter adapter = new RegisterActivity.MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
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
                        tv_register_3.setVisibility(View.GONE);
                        break;
                    case 1:
                        tv_register_1.setVisibility(View.GONE);
                        tv_register_2.setVisibility(View.VISIBLE);
                        tv_register_3.setVisibility(View.GONE);
                        break;
                    case 2:
                        tv_register_1.setVisibility(View.GONE);
                        tv_register_2.setVisibility(View.GONE);
                        tv_register_3.setVisibility(View.VISIBLE);
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
                finish();
            }
        });
    }

    private void setView() {
        viewPager= (NoScrollViewPager) findViewById(R.id.viewPager_register);
        tv_register_1= (TextView) findViewById(R.id.tv_register_1);
        tv_register_2= (TextView) findViewById(R.id.tv_register_2);
        tv_register_3= (TextView) findViewById(R.id.tv_register_3);
        iv_back = findViewById(R.id.iv_register_back);
    }
}
