package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String title="";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // textView=findViewById(R.id.textView);
        new Content().execute();
    }

    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //  String url = "https://firebase.google.com/";
                String url = "https://www.cbsnews.com/news/coronavirus-vaccine-trial-oxford-expands-south-africa-brazil/";
                //Connect to the website
                Document document = Jsoup.connect(url).get();
                //Get the logo source of the website
                //Get the title of the website
                //title = document.select("p");
                Elements paragraphs = document.select("p");
                for(Element p : paragraphs)
                    title=title.concat(p.text()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
         //   textView.setText(title);
        }
    }
}
