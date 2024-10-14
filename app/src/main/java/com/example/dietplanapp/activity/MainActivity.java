package com.example.dietplanapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.dietplanapp.R;
import com.example.dietplanapp.adapter.MyViewPager;
import com.example.dietplanapp.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbsActivity implements ViewPager.OnPageChangeListener{

    private MyViewPager mViewPager;
    private MainFragment mMainFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void main() {
        super.main();

        mViewPager = (MyViewPager) findViewById(R.id.viewPager);
        mViewPager.addOnPageChangeListener(this);
        mMainFragment = new MainFragment();
        final List<Fragment> list = new ArrayList<>();
        list.add(mMainFragment);
        mViewPager.setCanScroll(false);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}