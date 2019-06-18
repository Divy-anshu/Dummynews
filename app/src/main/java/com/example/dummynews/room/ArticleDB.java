package com.example.dummynews.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.dummynews.model.Articles;

//Create database UserDB
@Database(entities = {Articles.class}, version = 1)
public abstract class ArticleDB extends RoomDatabase {
    public abstract ArticleDao getArticleDao();
}
