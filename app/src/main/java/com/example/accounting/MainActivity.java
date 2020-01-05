package com.example.accounting;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.accounting.listener.tabClickListener;
import com.example.accounting.mainView.PageView;
import com.example.accounting.mainView.myCostView;
import com.example.accounting.mainView.myInvoiceView;
import com.example.accounting.mainView.settingView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<PageView> pageList;
    private TabLayout mTablayout;
    public myInvoiceView myInvoiceView;
    private SamplePagerAdapter pagerAdapter;


    /**
     * Needed for Android System
     *
     * @param savedInstanceState the System State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView(); //Initialize View
        initTab(); //Initialize TabLayout and viewPager
    }


    @Override
    protected void onStart() {
        super.onStart();

        myInvoiceView.addData();

    }


    /**
     * The method used to initialize the tabLayout and viewPager
     * :addTab
     * :setAdapter for viewPager
     * :call initListener
     */
    private void initTab() {
        mTablayout = (TabLayout) findViewById(R.id.tabs);

        // Set the tab on the Tablayout
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.myCost_tabItem)));
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.myInvoice_tabItem)));
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.setting_tabItem)));


        mViewPager = (ViewPager) findViewById(R.id.pager);
        // Use a attribute to save pagerAdapter
        pagerAdapter = new SamplePagerAdapter();
        // Set adapter to mViewPager
        mViewPager.setAdapter(pagerAdapter);
        initListener();
    }

    /**
     * The method used to initialize the view in pageList
     */
    private void initView() {
        pageList = new ArrayList<>();

        // Add the view items to pageList
        pageList.add(new myCostView(MainActivity.this));

        // Use a attribute to save myInvoiceView
        myInvoiceView = new myInvoiceView(MainActivity.this);
        // Add the view items to pageList
        pageList.add(myInvoiceView);

        // Add the view items to pageList
        pageList.add(new settingView(MainActivity.this));

    }

    /**
     * The method used to initialize Listener
     * :tabLayout =>addOnTabSelectedListener
     * :viewPager =>addOnPageChangeListener
     */
    private void initListener() {
        mTablayout.addOnTabSelectedListener(new tabClickListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
    }


    private class SamplePagerAdapter extends PagerAdapter {

        //get numbers of pageList elements(:view)
        @Override
        public int getCount() {
            return pageList.size();
        }

        //make sure the object we will instantiated is View
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        //instantiating the view be the viewpager's items
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            //System.out.println("IN");  :for test use
            return pageList.get(position);
        }

        //remove the view be the viewpager's items
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            //System.out.println("OUT");  :for test use
        }
    }

}
