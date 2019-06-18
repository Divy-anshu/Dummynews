package com.example.dummynews.network;

import com.example.dummynews.model.Item;
import com.example.dummynews.model.MarketStatus;
import com.example.dummynews.model.WeatherStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfeace {

@GET("top-headlines")
   Call<Item> getNews(@Query("country") String country, @Query("category") String category, @Query("apiKey") String api_key);

@GET("everything")
   Call<Item> getNews2(@Query("q") String sources,@Query("sortBy") String sortBy, @Query("apiKey") String api_key);

  //  @GET("top-headlines?sources=financial-post&apiKey=b4418087a008452daf903ec2800b8409")
   // Call<Item> getData(


    //);
    @GET("market/indices")
    Call<MarketStatus> getMarketStatus(@Query("ind_id") String type);

    @GET("weather")
    Call<WeatherStatus> getWeatherStatus(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);

}
