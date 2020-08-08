package com.example.newsapp.database;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.RoomDatabase;
@Database(entities = {NewsArticle.class}, version = 1)
@Entity(indices = {@Index(value = {"article_Url"},
        unique = true)})

public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsArticleDao newsArticleDao();
}
