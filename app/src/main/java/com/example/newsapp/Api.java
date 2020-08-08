package com.example.newsapp;



import com.example.newsapp.dataModels.NewsArticles;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
   // @GET("sample.php")
   // @GET("https://newsapi.org/v2/everything?q=kenya&apiKey=e5942f1fdd2444fba92e7905a80f61ac")
//    @GET("news%20api/")
   @GET("https://testbooker.000webhostapp.com/newsApp/")
    Call<NewsArticles>readNews();



}
