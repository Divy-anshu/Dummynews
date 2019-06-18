package com.example.dummynews.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.dummynews.model.Articles;
import com.example.dummynews.model.Item;
import com.example.dummynews.model.MarketStatus;
import com.example.dummynews.model.WeatherStatus;
import com.example.dummynews.network.ApiClient;
import com.example.dummynews.network.ApiInterfeace;
import com.example.dummynews.room.ArticleDB;
import com.example.dummynews.room.ArticleDao;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//ViewModels are entities that are free of the Activity/Fragment lifecycle.
//They can retain their state/data even during an orientation change.
//ViewModel's only responsibility is to manage the data for the UI.

public class ItemViewModel extends ViewModel {
    private ApiInterfeace apiInterface;
    private String source;
    ArticleDB articleDB;
    final String DATABASE_NAME = "articles.db";
    ArticleDao articleDao;

    //Data that will be fetched asynchronously
    private MutableLiveData<ArrayList<Articles>> articlesLiveData;

    //to get data
    public LiveData<ArrayList<Articles>> getItem(String source) {
        this.source = source;
        //if list is null
        if (articlesLiveData == null) {
            articlesLiveData = new MutableLiveData<>();
            //Load data async
            // ronously from the server
            loadProjects();
        }
        //return the list
        return articlesLiveData;
    }

    //Laod data from URL
    public void loadProjects() {
        apiInterface = ApiClient.getRetrofitInstance().create(ApiInterfeace.class);
        Call<Item> call = apiInterface.getNews2(source, "publishedAt", "b4418087a008452daf903ec2800b8409");
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                articlesLiveData.setValue(item.getArrayList());
            }
            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });


    }

    //headline news
    public LiveData<ArrayList<Articles>> getNewsHome(Context context) {

        //if list is null
        switch ((int)(Math.random()*8)){
            case 0:
                source = " ";
                break;
            case 1:
                source = "Technology";
                break;
            case 2:
                source = "Business";
                break;
            case 3:
                source = "Entertainment";
                break;
            case 5:
                source = "Science";
                break;
            case 6:
                source = "Health";
                break;
            case 7:
                source = "sports";
                break;
            case 8:
                source = " ";
                break;

        }
        if (articlesLiveData == null) {
            articlesLiveData = new MutableLiveData<>();
            apiInterface = ApiClient.getRetrofitInstance().create(ApiInterfeace.class);
            Call<Item> call = apiInterface.getNews("in", source, "b4418087a008452daf903ec2800b8409");
            Toast.makeText(context, "Your best topic is here "+source, Toast.LENGTH_SHORT).show();
            call.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    Item item = response.body();
                    articlesLiveData.setValue(item.getArrayList());
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {

                }
            });
        }
        //return the list
        return articlesLiveData;
    }

    public LiveData<ArrayList<Articles>> getSavedItem(Context context){
        if (articlesLiveData == null) {
            articlesLiveData = new MutableLiveData<>();
            //Load data asynchronously from the server
            articleDB = Room.databaseBuilder(context, ArticleDB.class,DATABASE_NAME).build();
            articleDao = articleDB.getArticleDao();
            new ViewTask().execute();
        }
        //return the list
        return articlesLiveData;
    }

    //Market Status method
    public LiveData<MarketStatus> getMarketStatus(String type){
        final MutableLiveData<MarketStatus> mutableLiveData = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://appfeeds.moneycontrol.com/jsonapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterfeace apiInterfeace1 = retrofit.create(ApiInterfeace.class);
        Call<MarketStatus> marketStatusCall = apiInterfeace1.getMarketStatus(type);
        marketStatusCall.enqueue(new Callback<MarketStatus>() {
            @Override
            public void onResponse(Call<MarketStatus> call, Response<MarketStatus> response) {
                MarketStatus marketStatus = response.body();
                mutableLiveData.setValue(marketStatus);
            }

            @Override
            public void onFailure(Call<MarketStatus> call, Throwable t) {

            }
        });
        return mutableLiveData;

    }

    public LiveData<WeatherStatus> getWeatherStatus(String lat, String lon){
        final MutableLiveData<WeatherStatus> mutableLiveData = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterfeace apiInterface1 = retrofit.create(ApiInterfeace.class);
        Call<WeatherStatus> weatherStatusCall = apiInterface1.getWeatherStatus(lat,lon,"a3e50c3c4bf69571425ba0b95ee0541c");
        weatherStatusCall.enqueue(new Callback<WeatherStatus>() {
            @Override
            public void onResponse(Call<WeatherStatus> call, Response<WeatherStatus> response) {
                WeatherStatus weatherStatus = response.body();
                mutableLiveData.setValue(weatherStatus);
            }

            @Override
            public void onFailure(Call<WeatherStatus> call, Throwable t) {

            }
        });
        return mutableLiveData;

    }

    class ViewTask extends AsyncTask<Void, Void, ArrayList<Articles>>{

        @Override
        protected ArrayList<Articles> doInBackground(Void... voids) {

            return (ArrayList<Articles>) articleDao.getArticles();
        }

        @Override
        protected void onPostExecute(ArrayList<Articles> articles) {
            super.onPostExecute(articles);
            articlesLiveData.setValue(articles);
        }
    }
}
