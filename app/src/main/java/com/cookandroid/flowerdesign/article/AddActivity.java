package com.cookandroid.flowerdesign.article;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.Article;
import com.cookandroid.flowerdesign.user.SessionManager;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    EditText title, content;
    Button add_button;
    Service service;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager=new SessionManager(getApplicationContext());

        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        add_button=findViewById(R.id.addbtn);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Article article=new Article();
                article.setTitle(title.getText().toString());
                article.setContent(content.getText().toString());
                article.setUserid(sessionManager.getUsername());
                articleSave(article);
            }
        });
    }

    public void articleSave(Article article){
        service= Apis.getPersonaService();
        Call<Article> call=service.articleSave(article);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(AddActivity.this, "저장 완료",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddActivity.this, ArticleActivity.class));
                }
            }
            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }
}