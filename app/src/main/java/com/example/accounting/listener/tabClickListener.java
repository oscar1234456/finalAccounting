package com.example.accounting.listener;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


/**
 * Class tabClickListener : used to implements onTabSelectedListener
 */
public class tabClickListener implements TabLayout.OnTabSelectedListener{

    private ViewPager mViewPager;

    public tabClickListener(ViewPager viewpager){
        this.mViewPager = viewpager;
    }

    /**
     * This method will be actived when tabSelected
     * it will set the view of viewPager by position of the tab user clicked on
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}