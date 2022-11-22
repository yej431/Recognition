package com.cookandroid.flowerdesign.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.article.ArticleAdapter;
import com.cookandroid.flowerdesign.flower.FlowerListAdapter;
import com.cookandroid.flowerdesign.model.Article;
import com.cookandroid.flowerdesign.model.Upload;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSearchActivity extends AppCompatActivity {
    private static final String TAG = "MainSearchActivity";
    Service service;
    TextView flowertitle, articletitle;
    ListView flowerlist, articlelist;
    View flowerView, articleView;
    List<Upload> list=new ArrayList<>();
    List<Article> list2=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flowerlist=findViewById(R.id.flowerlist);
        articlelist=findViewById(R.id.articlelist);
        flowertitle=findViewById(R.id.flowertitle);
        articletitle=findViewById(R.id.articletitle);
        flowerView=findViewById(R.id.flowerView);
        articleView=findViewById(R.id.articleView);

        Intent intent = getIntent();
        String searchtxt = intent.getStringExtra("searchtxt");
        flowerSearch(searchtxt);
        articleSearch(searchtxt);
    }

    private void flowerSearch(String searchtxt){
        service= Apis.getPersonaService();
        Call<List<Upload>> call=service.flowerSearch(searchtxt);
        call.enqueue(new Callback<List<Upload>>() {
            @Override
            public void onResponse(Call<List<Upload>> call, Response<List<Upload>> response) {
                if(response.isSuccessful()) {
                    list = response.body();
                    if(list.isEmpty()){
                        flowertitle.setVisibility(View.GONE);
                        flowerlist.setVisibility(View.GONE);
                        flowerView.setVisibility(View.GONE);
                    }else{
                        flowerlist.setAdapter(new FlowerListAdapter(MainSearchActivity.this
                                ,R.layout.content_main,list));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Upload>> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }

    public void articleSearch(String searchtxt){
        service= Apis.getPersonaService();
        Call<List<Article>> call=service.articleSearch(searchtxt);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful()) {
                    list2 = response.body();
                    if(list2.isEmpty()){
                        articletitle.setVisibility(View.GONE);
                        articlelist.setVisibility(View.GONE);
                        articleView.setVisibility(View.GONE);
                    }else{
                        articlelist.setAdapter(new ArticleAdapter(MainSearchActivity.this
                                ,R.layout.my_row,list2));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }
}