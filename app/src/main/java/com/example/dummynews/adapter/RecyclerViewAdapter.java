package com.example.dummynews.adapter;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dummynews.activity.WebActivity;
import com.example.dummynews.model.Articles;
import com.example.dummynews.R;
import com.example.dummynews.room.ArticleDB;
import com.example.dummynews.room.ArticleDao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Articles> articles;
    ArticleDB articleDB;
    final String DATABASE_NAME = "articles.db";
    ArticleDao articleDao;
    int flagSaved;

    public RecyclerViewAdapter(Context context, ArrayList<Articles> articles, int flagSaved) {
        this.context = context;
        this.articles = articles;
        this.flagSaved = flagSaved;
        articleDB = Room.databaseBuilder(context, ArticleDB.class,DATABASE_NAME).build();
        articleDao = articleDB.getArticleDao();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_cards, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Articles articles1 = articles.get(i);
        myViewHolder.txt_date.setText(articles1.getPublishedAt());
        myViewHolder.txt_author.setText(articles1.getAuthor());
        myViewHolder.txt_headline.setText(articles1.getTitle());
        Picasso.with(context).load(articles1.getUrlToImage()).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_headline, txt_date, txt_author;
        ImageView imageView3;
        TextView txt_headline3, txt_date3, txt_author3, txt_description3;
        Button btnShare, btnSave, btnRead;
        Articles a;
        String url, title;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            txt_headline = itemView.findViewById(R.id.txt_headline);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_author = itemView.findViewById(R.id.txt_author);
            final Dialog customView = new Dialog(context);
            customView.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customView.setCancelable(true);
            customView.setContentView(R.layout.articles_layout);
            imageView3 = customView.findViewById(R.id.imageView3);
            txt_headline3 = customView.findViewById(R.id.txt_headline3);
            txt_date3 = customView.findViewById(R.id.txt_date3);
            txt_author3 = customView.findViewById(R.id.txt_author3);
            txt_description3 = customView.findViewById(R.id.txt_description3);
            btnRead = customView.findViewById(R.id.btnRead);
            btnShare = customView.findViewById(R.id.btnShare);
            btnSave = customView.findViewById(R.id.btnSave);
            if(flagSaved == 0){
                btnSave.setText("Saved");
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    a = articles.get(position);
                    Picasso.with(context).load(a.getUrlToImage()).into(imageView3);
                    url = a.getUrl();
                    title = a.getTitle();
                    txt_headline3.setText(title);
                    txt_author3.setText(a.getAuthor());
                    txt_date3.setText(a.getPublishedAt());
                    txt_description3.setText(a.getDescription());
                    customView.show();

                }
            });
            btnRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customView.dismiss();
                    Intent i = new Intent(context, WebActivity.class);
                    i.putExtra("url", url);
                    context.startActivity(i);
                }
            });
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, title + "\n" + url);
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flagSaved == 0){
                        new DeleteArticleTask().execute(a);
                        customView.dismiss();
                    }

                    else if(flagSaved == 1){
                        new InsertTask().execute(a);
                        btnSave.setText("Saved");

                    }


                }
            });
        }


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
                Toast.makeText(context, "Insertion unsuccessful", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Insertion Successful.Row Id is " + aLong, Toast.LENGTH_SHORT).show();

        }
    }
    public class DeleteArticleTask extends AsyncTask<Articles, Void, Integer> {
        @Override
        protected Integer doInBackground(Articles... articles) {

            int d = articleDao.delete(articles[0]);
            return d;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Toast.makeText(context, "deleted" + integer, Toast.LENGTH_SHORT).show();

        }
    }
}