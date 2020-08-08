package com.example.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class myBusAdapter extends RecyclerView.Adapter<myBusAdapter.myViewHolder> {
    ArrayList<String> newsUrls;
    ArrayList<String> newsDescription;
    ArrayList<String> newsTittle;
    ArrayList<String> newsCompany;
    ArrayList<String> newsImageUrl;
    ArrayList<String> newsContent;



    Context mContext;

public myBusAdapter(ArrayList<String> newsUrls,ArrayList<String> newsDescription,ArrayList<String> newsTittle,
                    ArrayList<String> newsCompany,ArrayList<String> newsImageUrl,ArrayList<String> newsContent,
                    Context mContext, FragmentActivity activity){
    this.newsUrls=newsUrls;
    this.mContext= mContext;
    this.newsDescription=newsDescription;
    this.newsCompany=newsCompany;
    this.newsTittle=newsTittle;
    this.newsImageUrl=newsImageUrl;
    this.newsContent=newsContent;

}
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout,parent,false);
        myViewHolder holder =new myViewHolder(view);
        return holder;    }
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        holder.tvTittle.setText(newsTittle.get(position));
        holder.tvDesc.setText(newsDescription.get(position));
        Picasso.with(mContext).load(newsImageUrl.get(position)).resize(250, 250).centerCrop().into(holder.imgNewsHolder);
        holder.parentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle newsData = new Bundle();
               // newsData.putString("news_company_name",newsCompany.get(position));
                newsData.putString("news_tittle",newsTittle.get(position));
                newsData.putString("news_image_url",newsImageUrl.get(position));
                newsData.putString("news_description",newsDescription.get(position));
                newsData.putString("news_url",newsUrls.get(position));
                newsData.putString("news_content",newsContent.get(position));
                NewsArticleFragment newsArticleFragment =new NewsArticleFragment();
                newsArticleFragment.setArguments(newsData);
                FragmentTransaction fragmentTransaction2 = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.fragment, newsArticleFragment);
                fragmentTransaction2.addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsTittle.size();
    }


    public class myViewHolder extends  RecyclerView.ViewHolder {
        TextView tvTittle,tvDesc;
       ImageView imgNewsHolder;
       ConstraintLayout parentlay;
        public myViewHolder(@NonNull View view) {
            super(view);
            tvTittle=view.findViewById(R.id.textViewTittle);
            tvDesc=view.findViewById(R.id.textViewDesc);
            imgNewsHolder=view.findViewById(R.id.imageViewPhoto);
            parentlay=view.findViewById(R.id.parent);

           // tvduration=view.findViewById(R.id.tvduration);
        }
    }
}
