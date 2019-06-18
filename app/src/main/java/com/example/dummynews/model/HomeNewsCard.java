package com.example.dummynews.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.example.dummynews.R;
import com.example.dummynews.utility.Utils;
import com.example.dummynews.room.ArticleDB;
import com.example.dummynews.room.ArticleDao;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeHead;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by janisharali on 19/08/16.
 */
@Layout(R.layout.news_cards)
public class HomeNewsCard {

    @View(R.id.imageView2)
    private ImageView imageView;

    @View(R.id.txt_headline)
    private TextView headline;

    @View(R.id.txt_author)
    private TextView author;

    @View(R.id.txt_date)
    private TextView date;

    @View(R.id.txt_response)
    private TextView txt_response;

    @SwipeView
    private android.view.View cardView;

    private Articles marticles;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private ArticleDB articleDB;
    private ArticleDao articleDao;
    final String DATABASE_NAME = "articles.db";

    public HomeNewsCard(Context context, Articles articles, SwipePlaceHolderView swipeView) {
        mContext = context;
        marticles = articles;
        mSwipeView = swipeView;

        articleDB = Room.databaseBuilder(context, ArticleDB.class,DATABASE_NAME).build();
        articleDao = articleDB.getArticleDao();
    }

    @Resolve
    private void onResolved(){
        MultiTransformation multi = new MultiTransformation(
                new BlurTransformation(mContext, 30),
                new RoundedCornersTransformation(
                        mContext, Utils.dpToPx(7), 0,
                        RoundedCornersTransformation.CornerType.TOP));

        Glide.with(mContext).load(marticles.getUrlToImage())
                .bitmapTransform(multi)
                .into(imageView);
        headline.setText(marticles.getTitle());
        author.setText(marticles.getAuthor());
        date.setText(marticles.getPublishedAt());
    }

    @SwipeHead
    private void onSwipeHeadCard() {
        Glide.with(mContext).load(marticles.getUrlToImage())
                .bitmapTransform(new RoundedCornersTransformation(
                        mContext, Utils.dpToPx(7), 0,
                        RoundedCornersTransformation.CornerType.TOP))
                .into(imageView);
        cardView.invalidate();
    }

    @Click(R.id.imageView2)
    private void onClick(){
        Log.d("EVENT", "profileImageView click");

//        mSwipeView.addView(this);
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        txt_response.setTextColor(Color.RED);
        txt_response.setTextAlignment(android.view.View.TEXT_ALIGNMENT_TEXT_END);
        txt_response.setText("Ignored");
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        txt_response.setTextColor(Color.GREEN);
        txt_response.setTextAlignment(android.view.View.TEXT_ALIGNMENT_TEXT_START);
        txt_response.setText("Saved");
        new InsertTask().execute(marticles);
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }

    class InsertTask extends AsyncTask<Articles, Void, Long> {

        @Override
        protected Long doInBackground(Articles... articles) {
            Articles articles1 = articles[0];
            long uid = articleDao.insert(articles1);

            return uid;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if (aLong == -1)
                Toast.makeText(mContext, "Error in save", Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();

        }
    }
}

