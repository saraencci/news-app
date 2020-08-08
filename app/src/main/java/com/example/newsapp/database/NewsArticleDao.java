package com.example.newsapp.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface NewsArticleDao {
    @Query("SELECT * FROM NewsArticle")
    List<NewsArticle> getAll();

    @Insert
    void insert(NewsArticle article);

    @Delete
    void delete(NewsArticle article);

    @Update
    void update(NewsArticle article);
    @Query("DELETE FROM NewsArticle")
    void  delete();
}
