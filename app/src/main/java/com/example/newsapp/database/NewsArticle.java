package com.example.newsapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsArticle {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tittle")
    private String tittle;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "article_Url")
    private String articleUrl;

    @ColumnInfo(name = "image_Url")
    private String imageUrl;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "Author")
    private String author;

    @ColumnInfo(name = "Source_Id")
    private String sourceId;

    @ColumnInfo(name = "Name")
    private String name;
// the getters and seetters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
