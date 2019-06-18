package com.example.dummynews.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dummynews.model.Articles;
import com.example.dummynews.R;
import com.example.dummynews.adapter.RecyclerViewAdapter;
import com.example.dummynews.view_model.ItemViewModel;

import java.util.ArrayList;

public class NewsListFragment extends Fragment {
    public static final String Api_Key = "b4418087a008452daf903ec2800b8409";
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    ItemViewModel itemViewModel;
    private String source;


    public NewsListFragment(String source) {
        this.source = source;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_news_list, container, false);
        recyclerView = v.findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

       if(source.equals("saved")){
           itemViewModel.getSavedItem(getActivity()).observe(this, new Observer<ArrayList<Articles>>() {
               @Override
               public void onChanged(@Nullable ArrayList<Articles> articles) {
                   recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), articles, 0);
                   recyclerView.setAdapter(recyclerViewAdapter);
               }
           });
       }
       else {
           itemViewModel.getItem(source).observe(this, new Observer<ArrayList<Articles>>() {
               @Override
               public void onChanged(@Nullable ArrayList<Articles> articles) {
                   recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), articles,1);
                   recyclerView.setAdapter(recyclerViewAdapter);
               }
           });
       }


//        apiInterface = ApiClient.getRetrofitInstance().create(ApiInterfeace.class);
//        Call<Item> call = apiInterface.getNews(source,"a5b563d244c844d1b3caddd914b96b75");
//
//        call.enqueue(new Callback<Item>() {
//            @Override
//            public void onResponse(Call<Item> call, Response<Item> response) {
//                Toast.makeText(NewsListFragment.this, "called......", Toast.LENGTH_SHORT).show();
//                Item item = response.body();
//                articles = item.getArrayList();
//                recyclerViewAdapter = new RecyclerViewAdapter(NewsListFragment.this, articles);
//                recyclerView.setAdapter(recyclerViewAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<Item> call, Throwable t) {
//                Toast.makeText(NewsListFragment.this, "failed", Toast.LENGTH_SHORT).show();
//            }
//        });
        return v;
    }
}
