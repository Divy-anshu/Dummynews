package com.example.dummynews.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dummynews.model.Articles;
import com.example.dummynews.R;
import com.example.dummynews.activity.SplashScreen;
import com.example.dummynews.utility.Utils;
import com.example.dummynews.model.HomeNewsCard;
import com.example.dummynews.model.MarketStatus;
import com.example.dummynews.model.WeatherStatus;
import com.example.dummynews.view_model.ItemViewModel;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    TextView bse_status, bse_diff, nse_status, nse_diff;
    ItemViewModel itemViewModel;
    TextView txt_location, todayTemp, todayDescription, todayWind, todayPressure, todayHumidity;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    ArrayList<Articles> articles;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bse_status = view.findViewById(R.id.bse_status);
        bse_diff = view.findViewById(R.id.bse_diff);
        nse_status = view.findViewById(R.id.nse_status);
        nse_diff = view.findViewById(R.id.nse_diff);
        txt_location = view.findViewById(R.id.location);
        todayTemp = view.findViewById(R.id.todayTemperature);
        todayDescription = view.findViewById(R.id.todayDescription);
        todayWind = view.findViewById(R.id.todayWind);
        todayPressure = view.findViewById(R.id.todayPressure);
        todayHumidity = view.findViewById(R.id.todayHumidity);


        mSwipeView = (SwipePlaceHolderView)view.findViewById(R.id.swipeView);
        mContext = getActivity();
        articles = new ArrayList<>();

        int bottomMargin = Utils.dpToPx(160);
        Point windowSize = Utils.getDisplaySize(getActivity().getWindowManager());
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeMaxChangeAngle(2f)
                        );

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getMarketStatus("9").observe(this, new Observer<MarketStatus>() {
            @Override
            public void onChanged(@Nullable MarketStatus marketStatus) {
                nse_status.setText(marketStatus.getIndices().getLastprice());
                if(Float.parseFloat(marketStatus.getIndices().getChange()) < 0){
                    nse_diff.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_trending_down_black_24dp,0,0,0);
                }

                nse_diff.setText(marketStatus.getIndices().getChange());

            }
        });
        itemViewModel.getMarketStatus("4").observe(this, new Observer<MarketStatus>() {
            @Override
            public void onChanged(@Nullable MarketStatus marketStatus) {
                bse_status.setText(marketStatus.getIndices().getLastprice());
                if(Float.parseFloat(marketStatus.getIndices().getChange()) < 0){
                    bse_diff.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_trending_down_black_24dp,0,0,0);
                }
                bse_diff.setText(marketStatus.getIndices().getChange());

            }
        });

        itemViewModel.getNewsHome(getActivity()).observe(this, new Observer<ArrayList<Articles>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Articles> articles) {
                for(Articles articles1 : articles){
                    mSwipeView.addView(new HomeNewsCard(mContext, articles1, mSwipeView));
                }
            }
        });

        itemViewModel.getWeatherStatus(SplashScreen.lat+"",SplashScreen.lon+"").observe(getActivity(), new Observer<WeatherStatus>() {
            @Override
            public void onChanged(@Nullable WeatherStatus weatherStatus) {
                txt_location.setText(weatherStatus.getName());

                todayTemp.setText(weatherStatus.getMain().getTemp().intValue() - 273+" °C");
                todayDescription.setText(weatherStatus.getWeather().get(0).getDescription());
                todayWind.setText("Wind: "+weatherStatus.getWind().getSpeed()+" m/s");
                todayPressure.setText("Pressure: "+weatherStatus.getMain().getPressure()+" hpa");
                todayHumidity.setText("Humidity: "+weatherStatus.getMain().getHumidity()+"%");

            }
        });

        return view;
    }




}
