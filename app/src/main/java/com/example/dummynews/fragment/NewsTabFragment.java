package com.example.dummynews.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dummynews.R;
import com.example.dummynews.adapter.MyPagerAdapter;

import java.util.ArrayList;


public class NewsTabFragment extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private String category;
    ArrayList<String> sources;

    public NewsTabFragment(String category) {
        // Required empty public constructor
        this.category = category;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_news_tab, container, false);

        //FindViewByID
        //Toolbar
        //TabLayout
        tabLayout = v.findViewById(R.id.tabLayout);
        //ViewPager
        viewPager = v.findViewById(R.id.viewPager);
        sources = new ArrayList<>();

        //Adding tabs in the Tablayout

        if(category.equals("sports")) {
            tabLayout.addTab(tabLayout.newTab().setText("All"));
            tabLayout.addTab(tabLayout.newTab().setText("Football"));
            tabLayout.addTab(tabLayout.newTab().setText("Cricket"));
            tabLayout.addTab(tabLayout.newTab().setText("Tennis"));
            tabLayout.addTab(tabLayout.newTab().setText("Golf"));
            sources.add("sports");
            sources.add("football");
            sources.add("cricket");
            sources.add("tennis");
            sources.add("golf");
        }
        if(category.equals("business")) {
            tabLayout.addTab(tabLayout.newTab().setText("All"));
            tabLayout.addTab(tabLayout.newTab().setText("Market"));
            tabLayout.addTab(tabLayout.newTab().setText("Money"));
            tabLayout.addTab(tabLayout.newTab().setText("Revenue"));
            tabLayout.addTab(tabLayout.newTab().setText("Fund"));
            sources.add("ecommerce");
            sources.add("market");
            sources.add("money");
            sources.add("revenue");
            sources.add("fund");
        }
        if(category.equals("entertainment")) {
            tabLayout.addTab(tabLayout.newTab().setText("All"));
            tabLayout.addTab(tabLayout.newTab().setText("Hollywood"));
            tabLayout.addTab(tabLayout.newTab().setText("Bollywood"));
            tabLayout.addTab(tabLayout.newTab().setText("Music"));
            tabLayout.addTab(tabLayout.newTab().setText("Shows"));
            tabLayout.addTab(tabLayout.newTab().setText("Chartbuster"));
            sources.add("movie");
            sources.add("hollywood");
            sources.add("bollywood");
            sources.add("music");
            sources.add("Shows");
            sources.add("chartbusters");
        }
        if(category.equals("country")) {
            tabLayout.addTab(tabLayout.newTab().setText("India"));
            tabLayout.addTab(tabLayout.newTab().setText("United State"));
            tabLayout.addTab(tabLayout.newTab().setText("France"));
            tabLayout.addTab(tabLayout.newTab().setText("England"));
            tabLayout.addTab(tabLayout.newTab().setText("China"));
            sources.add("india");
            sources.add("us");
            sources.add("france");
            sources.add("england");
            sources.add("china");
        }
        if(category.equals("technology")) {
            tabLayout.addTab(tabLayout.newTab().setText("All"));
            tabLayout.addTab(tabLayout.newTab().setText("Gadgets"));
            tabLayout.addTab(tabLayout.newTab().setText("Android"));
            tabLayout.addTab(tabLayout.newTab().setText("IOS"));
            tabLayout.addTab(tabLayout.newTab().setText("Window"));
            tabLayout.addTab(tabLayout.newTab().setText("BitCoin"));
            tabLayout.addTab(tabLayout.newTab().setText("Cloud computing"));
            sources.add("technology");
            sources.add("gadgets");
            sources.add("android");
            sources.add("ios");
            sources.add("window");
            sources.add("bitcoin");
            sources.add("cloud-computing");
        }




        //Adapter
        myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),sources);
        viewPager.setAdapter(myPagerAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }


}
