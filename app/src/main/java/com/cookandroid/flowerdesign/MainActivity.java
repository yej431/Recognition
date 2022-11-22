package com.cookandroid.flowerdesign;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cookandroid.flowerdesign.flower.PopularAdapter;
import com.cookandroid.flowerdesign.article.ArticleActivity;
import com.cookandroid.flowerdesign.flower.CameraActivity;
import com.cookandroid.flowerdesign.flower.FlowerListActivity;
import com.cookandroid.flowerdesign.flower.TodaysActivity;
import com.cookandroid.flowerdesign.model.SearchRank;
import com.cookandroid.flowerdesign.search.MainSearchActivity;
import com.cookandroid.flowerdesign.user.LoginActivity;
import com.cookandroid.flowerdesign.user.SessionManager;
import com.cookandroid.flowerdesign.user.UserStoreActivity;
import com.cookandroid.flowerdesign.user.UserDetailActivity;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Service service;
    private CardView cardView1, cardView2, cardView3, cardView4;
    private FloatingActionButton camera;
    private ImageView user, searchbtn;
    private EditText search;
    private List<SearchRank> list=new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        searchRank();

        sessionManager=new SessionManager(getApplicationContext());

        cardView1=findViewById(R.id.cardview1);
        cardView2=findViewById(R.id.cardview2);
        cardView3=findViewById(R.id.cardview3);
        cardView4=findViewById(R.id.cardview4);
        camera=findViewById(R.id.camera);
        user=findViewById(R.id.user);
        search=findViewById(R.id.search);
        searchbtn=findViewById(R.id.searchbtn);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TodaysActivity.class));
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FlowerListActivity.class));
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ArticleActivity.class));
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManager.getLogin()==false){
                    Toast.makeText(MainActivity.this, "로그인 해주세요.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(MainActivity.this,
                            UserStoreActivity.class));
                }
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManager.getLogin()==false){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this,
                            UserDetailActivity.class));
                }
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchtxt=search.getText().toString();
                Intent i=new Intent(MainActivity.this, MainSearchActivity.class);
                i.putExtra("searchtxt", searchtxt);
                startActivity(i);
                finish();
            }
        });
    }

    public void searchRank(){
        service= Apis.getPersonaService();
        Call<List<SearchRank>> call=service.searchRank();
        call.enqueue(new Callback<List<SearchRank>>() {
            @Override
            public void onResponse(Call<List<SearchRank>> call, Response<List<SearchRank>> response) {
                if(response.isSuccessful()) {
                    list = response.body();

                    LinearLayoutManager layoutManager=new LinearLayoutManager(
                            MainActivity.this, LinearLayoutManager.HORIZONTAL,
                            false);

                    RecyclerView recyclerView=findViewById(R.id.searchRank);
                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(new PopularAdapter(MainActivity.this,list));
                }
            }
            @Override
            public void onFailure(Call<List<SearchRank>> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }
}