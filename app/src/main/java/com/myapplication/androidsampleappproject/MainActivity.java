package com.myapplication.androidsampleappproject;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private List<PageView> pageList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initData()
    {
        pageList = new ArrayList<>();
        pageList.add(new PageOne(MainActivity.this));
        pageList.add(new PageTwo(MainActivity.this));
        pageList.add(new PageThree(MainActivity.this));
        pageList.add(new PageFour(MainActivity.this));
        pageList.add(new PageFive(MainActivity.this));
    }

    private void initView()
    {
        mTablayout = (TabLayout) findViewById(R.id.tabs);
        mTablayout.setupWithViewPager(mViewPager);
        mTablayout.addTab(mTablayout.newTab().setText("HTTP").setIcon(R.mipmap.ic_launcher));
        mTablayout.addTab(mTablayout.newTab().setText("FIREBASE").setIcon(R.mipmap.ic_launcher));
        mTablayout.addTab(mTablayout.newTab().setText("VIDEO").setIcon(R.mipmap.ic_launcher));
        mTablayout.addTab(mTablayout.newTab().setText("WEBVIEW").setIcon(R.mipmap.ic_launcher));
        mTablayout.addTab(mTablayout.newTab().setText("CALCULATOR").setIcon(R.mipmap.ic_launcher));

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SamplePagerAdapter());
    }

    private void initListener()
    {
        mTablayout.addOnTabSelectedListener (new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                //Log.d("TagSampleAppProject","onTabSelected");
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                //Log.d("TagSampleAppProject","onTabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                //Log.d("TagSampleAppProject","onTabReselected");
            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
    }

    private class SamplePagerAdapter extends PagerAdapter
    {
        @Override
        public int getCount()
        {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o)
        {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}