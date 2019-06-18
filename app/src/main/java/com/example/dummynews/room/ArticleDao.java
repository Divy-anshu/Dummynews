package com.example.dummynews.room;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dummynews.model.Articles;

import java.util.List;

//Specify CRUD operations on Database here
@Dao
public interface ArticleDao {

    @Insert
    long insert(Articles articles);

    @Query("Select * from Articles")
    List<Articles> getArticles();

    @Delete
    int delete(Articles article);
}
