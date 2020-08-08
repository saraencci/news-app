package com.example.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsArticleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  String newsTittle, newsContent,newsDescription,newsImageUrl,newsUrl;;
    public NewsArticleFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsArticleFragment newInstance(String param1, String param2) {
        NewsArticleFragment fragment = new NewsArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            newsTittle=bundle.getString("news_tittle");
            newsImageUrl=bundle.getString("news_image_url");
            newsDescription=bundle.getString("news_description");
            newsUrl=bundle.getString("news_url");
            newsContent=bundle.getString("news_content");


//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_news_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView  tvTittle=getView().findViewById(R.id.textViewTittle);
        TextView  tvNewsContent=getView().findViewById(R.id.textViewNewsContent);
        tvNewsContent.setText(newsContent);

       //TextView  tvTittle=getView().findViewById(R.id.textViewTittle);

        tvTittle.setText(newsTittle);
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

                //Connect to the website
                Document document = Jsoup.connect(newsUrl).get();
                //Get the logo source of the website
                //Get the title of the website
                //title = document.select("p");
                newsContent=" ";
                Elements paragraphs = document.select("p");
                for(Element p : paragraphs)
                    newsContent=newsContent.concat(p.text()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //   textView.setText(title);
           // TextView  tvNewsContent=getView().findViewById(R.id.textViewNewsContent);
            TextView  textViewSmall=getView().findViewById(R.id.textView);
            ImageView ImageViewNewsImage=getView().findViewById(R.id.imageViewDetail);
           // tvNewsContent.setText(newsContent);
            Picasso.with(getContext()).load(newsImageUrl).fit().into(ImageViewNewsImage);
            //textViewSmall.setText("this "+newsImageUrl);

        }
    }
}