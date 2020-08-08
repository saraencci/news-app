package com.example.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp.dataModels.NewsArticles;
import com.example.newsapp.database.DatabaseClient;
import com.example.newsapp.database.NewsArticle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> newsUrls =new ArrayList<>();
    ArrayList<String> newsDescription =new ArrayList<>();
    ArrayList<String> newsTittle=new ArrayList<>();
    ArrayList<String> newsCompany=new ArrayList<>();
    ArrayList<String> newsImageUrl=new ArrayList<>();
    ArrayList<String> newsContent=new ArrayList<>();
    private List<NewsArticle> newsList;

    ArrayList<String> newsSourceId=new ArrayList<>();
    ArrayList<String> newsSourceNane=new ArrayList<>();
    RecyclerView myRecycler;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readNews();
    }

    private void readNews(){
        Api jsonApi= retrofitInstance.getClient().create(Api.class);
        Call<NewsArticles> call=jsonApi.readNews();
        call.enqueue(new Callback<NewsArticles>() {
            @Override
            public void onResponse(Call<NewsArticles> call, Response<NewsArticles> response) {
                if(response.isSuccessful()){
                    int no=response.body().getArticles().size();
                    for (int i = 0; i <no-1 ; i++) {
                        newsTittle.add(response.body().getArticles().get(i).getTitle());
                        newsUrls.add(response.body().getArticles().get(i).getUrl());
                        newsDescription.add(response.body().getArticles().get(i).getDescription());
                        newsImageUrl.add(response.body().getArticles().get(i).getUrlToImage());
                        newsSourceId.add(response.body().getArticles().get(i).getSource().getId());
                        newsSourceNane.add(response.body().getArticles().get(i).getSource().getName());
                        newsContent.add(response.body().getArticles().get(i).getContent().toString());
                        //creating news article object
                        NewsArticle newsArticle=new NewsArticle();
                        newsArticle.setTittle(response.body().getArticles().get(i).getTitle());
                        newsArticle.setDescription(response.body().getArticles().get(i).getDescription());
                        newsArticle.setAuthor(response.body().getArticles().get(i).getAuthor());
                        newsArticle.setImageUrl(response.body().getArticles().get(i).getUrlToImage());
                        newsArticle.setArticleUrl(response.body().getArticles().get(i).getUrl());
                       // newsArticle.setContent((String) response.body().getArticles().get(i).getContent());
                        newsArticle.setName(response.body().getArticles().get(i).getSource().getName());
                        newsArticle.setSourceId(response.body().getArticles().get(i).getSource().getId());
                        //newsArticle.set(response.body().getArticles().get(i).getTitle());

                        //reading news article html content
                        Document document = Jsoup.parse((String) response.body().getArticles().get(i).getContent());
                        String newsContent="";
                        //Elements paragraphs = document.select("p");
                        Elements paragraphs = document.select("p");
                        for(Element p : paragraphs)
                            newsContent=newsContent.concat(p.text()+"\n");
                        newsArticle.setContent(newsContent);
                    //save news item to db
                        saveArticles(newsArticle, (i*100)/no);
                    }
                    //
                   displayRecyclerViewData();
                }
            }
            @Override
            public void onFailure(Call<NewsArticles> call, Throwable t) {
                Toast.makeText(getActivity(),t.toString(), Toast.LENGTH_LONG).show();
                readFromDb();
            }
        });
    }

    private void readFromDb() {
        getNotes();
    }

    private void displayRecyclerViewData() {
        myRecycler= Objects.requireNonNull(getActivity()).findViewById(R.id.newsRecycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        myRecycler.setLayoutManager(mLayoutManager);
        myBusAdapter adapter = new myBusAdapter(newsUrls,newsDescription,newsTittle,newsCompany,newsImageUrl,newsContent,getContext(),getActivity());
        myRecycler.setAdapter(adapter);
    }

    public void saveArticles(final NewsArticle newsArticle, final double percent) {
        class Savenote extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                DatabaseClient.getInstance(getContext()).getAppDatabase()
                        .newsArticleDao() .insert(newsArticle);
                Log.i(TAG, " maim doInBackground:      "+percent);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(percent>98)
                Toast.makeText(getContext(), "Note Saved   "+percent+" %" , Toast.LENGTH_LONG).show();
                Log.i(TAG, "maim onPostExecute: call  "+percent+" completed");
            }
        }
        Savenote st = new Savenote();
        st.execute();
    }

    private void getNotes() {
        class GetNotes extends AsyncTask<Void, Void, List<NewsArticle>> {

            @Override
            protected List<NewsArticle> doInBackground(Void... voids) {
                newsList = DatabaseClient
                        .getInstance(getActivity().getApplicationContext())
                        .getAppDatabase()
                        .newsArticleDao()
                        .getAll();
                return newsList;
            }

            @Override
            protected void onPostExecute(List<NewsArticle> newsArticles) {
                super.onPostExecute(newsArticles);
                for (int i = 0; i < newsArticles.size(); i++) {
                    newsTittle.add(newsArticles.get(i).getTittle());
                    newsUrls.add(newsArticles.get(i).getArticleUrl());
                    newsImageUrl.add(newsArticles.get(i).getImageUrl());
                    newsCompany.add(newsArticles.get(i).getAuthor());
                    newsContent.add(newsArticles.get(i).getContent());
                    newsDescription.add(newsArticles.get(i).getDescription());

                }
                displayRecyclerViewData();
            }
        }
        GetNotes gn = new GetNotes();
        gn.execute();
    }
}
