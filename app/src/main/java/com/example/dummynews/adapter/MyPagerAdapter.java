package com.example.dummynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dummynews.fragment.NewsListFragment;

import java.util.ArrayList;


public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private ArrayList<String> sources;

    public MyPagerAdapter(FragmentManager fm, int tabCount, ArrayList<String> sources) {
        super(fm);
        this.tabCount = tabCount;
        this.sources = sources;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NewsListFragment tab0 = new NewsListFragment(sources.get(0));
                return tab0;
            case 1:
                NewsListFragment tab1 = new NewsListFragment(sources.get(1));
                return tab1;
            case 2:
                NewsListFragment tab2 = new NewsListFragment(sources.get(2));
                return tab2;
            case 3:
                NewsListFragment tab3 = new NewsListFragment(sources.get(3));
                return tab3;
            case 4:
                NewsListFragment tab4 = new NewsListFragment(sources.get(4));
                return tab4;
            case 5:
                NewsListFragment tab5 = new NewsListFragment(sources.get(5));
                return tab5;
            case 6:
                NewsListFragment tab6 = new NewsListFragment(sources.get(6));
                return tab6;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
