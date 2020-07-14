package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp.dataModels.NewsArticles;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                    }
                    myRecycler=getActivity().findViewById(R.id.newsRecycler);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    myRecycler.setLayoutManager(mLayoutManager);
                    myBusAdapter adapter = new myBusAdapter(newsUrls,newsDescription,newsTittle,newsCompany,newsImageUrl,getContext(),getActivity());
                    myRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<NewsArticles> call, Throwable t) {
                Toast.makeText(getActivity(),t.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }
}