package com.example.newsapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitInstance {

   // public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
   // public static final String BASE_URL = "http://mobileappdatabase.in/";
// public static final String BASE_URL = "https://cashzone254.cf/";
 public static final String BASE_URL = "http://10.0.2.2/";
 //public static final String BASE_URL = "http://testbooker.000webhostapp.com/adam_system/adam_system/adam_system/";


    public static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
