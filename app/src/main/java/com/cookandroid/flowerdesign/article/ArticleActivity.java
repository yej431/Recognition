package com.cookandroid.flowerdesign.article;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.Article;
import com.cookandroid.flowerdesign.user.SessionManager;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleActivity extends AppCompatActivity {
    FloatingActionButton add_button;
    Service service;

    List<Article> list=new ArrayList<>();
    ListView listView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager=new SessionManager(getApplicationContext());

        listView=(ListView)findViewById(R.id.listView);
        add_button=findViewById(R.id.add_button);
        articleList();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManager.getLogin()==false){
                    Toast.makeText(ArticleActivity.this, "로그인 해주세요.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(ArticleActivity.this,
                            AddActivity.class));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            recreate();
        }
    }

    public void articleList(){
        service= Apis.getPersonaService();
        Call<List<Article>> call=service.articleList();
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new ArticleAdapter(ArticleActivity.this
                            ,R.layout.my_row,list));
                }
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataHelper mydb=new MyDataHelper(ArticleActivity.this);
                mydb.deleteAllData();
                Intent intent=new Intent(ArticleActivity.this, ArticleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }*/
}