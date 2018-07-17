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
        //測試TabLayout的Tab的setOnClickListener
        //方法1
        //setTablayoutOnClickListenerEnable1();
        //方法2
        //setTablayoutOnClickListenerEnable2();
        //方法3
        //setTablayoutOnClickListenerEnable3();
        //方法4
        //有tablayout的佈局是RelativeLayout的話，tabLayout是無法點擊的，只要將RelativeLayout改成LinearLayout即可。
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
        mTablayout.addTab(mTablayout.newTab().setText("HTTP"));
        mTablayout.addTab(mTablayout.newTab().setText("FIREBASE"));
        mTablayout.addTab(mTablayout.newTab().setText("VIDEO"));
        mTablayout.addTab(mTablayout.newTab().setText("WEBVIEW"));
        mTablayout.addTab(mTablayout.newTab().setText("CALCULATOR"));

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SamplePagerAdapter());
     //   mTablayout.setupWithViewPager(mViewPager);//字會消失..
        mTablayout.bringToFront();
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
    //
    public void setTablayoutOnClickListenerEnable1()
    {
        for (int i = 0; i < mTablayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            if (tab == null) continue;
            //tab.setIcon(R.mipmap.ic_launcher);
            //這裡使用到反射，拿到Tab對像後獲取Class
            Class c = tab.getClass();
            try
            {
                //Filed 「字段、屬性」的意思,c.getDeclaredField 獲取私有屬性。
                //"view"是Tab的私有屬性名稱(可查看TabLayout源碼),類型是 TabView,TabLayout私有內部類。
                Field field = c.getDeclaredField("view");
                if (field == null) continue;
                //值為 true 則指示反射的對象在使用時應該取消 Java 語言訪問檢查。值為 false 則指示反射的對象應該實施 Java 語言訪問檢查。
                field.setAccessible(true); //如果不這樣設定會報錯誤
                final View view = (View) field.get(tab);
                if (view == null) continue;
                // view.setClickable(true);
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int position = (int) view.getTag();
                        //Log.d("TagSampleAppProject","tab pos1="+position);
                        mViewPager.setCurrentItem(position, false);
                    }
                });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    //
    public void setTablayoutOnClickListenerEnable2()
    {
        for (int i = 0; i < mTablayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            if (tab != null)
            {
                //tab.setCustomView(pagerAdapter.getTabView(i));
                //if (tab.getCustomView() != null)
                {
                    //View tabView = (View) tab.getCustomView().getParent();
                    View tabView = (View)tab.view ;
                    //tabView.setClickable(true);
                    tabView.setTag(i);
                    tabView.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            int position = (int) view.getTag();
                            //Log.d("TagSampleAppProject","tab pos2="+position);
                            mViewPager.setCurrentItem(position, false);
                        }
                    });
                }
            }
        }
    }
    //
    public void setTablayoutOnClickListenerEnable3()
    {
        LinearLayout tabStrip = ((LinearLayout)mTablayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++)
        {
            //tabStrip.getChildAt(i).setClickable(true);
            tabStrip.getChildAt(i).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = (int) view.getTag();
                    //Log.d("TagSampleAppProject","tab pos3="+position);
                    mViewPager.setCurrentItem(position);

                }
            });
        }
    }
}