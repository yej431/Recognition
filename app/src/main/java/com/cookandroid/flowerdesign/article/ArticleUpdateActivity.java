package com.cookandroid.flowerdesign.article;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.Article;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleUpdateActivity extends AppCompatActivity {
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtUserid=findViewById(R.id.userid);
        TextView txtWriteDate=findViewById(R.id.writeDate);
        EditText txtTitle=findViewById(R.id.title);
        EditText txtContent=findViewById(R.id.content);
        Button updatebtn=findViewById(R.id.updatebtn);

        Bundle bundle=getIntent().getExtras();
        String id=bundle.getString("id");
        String title=bundle.getString("title");
        String content=bundle.getString("content");
        String userid=bundle.getString("userid");
        String writeDate=bundle.getString("writeDate");

        txtTitle.setText(title);
        txtContent.setText(content);
        txtUserid.setText("글쓴이: "+userid);
        txtWriteDate.setText("글쓴 날짜: "+writeDate);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Article article=new Article();
                article.setId(Integer.parseInt(id));
                article.setTitle(txtTitle.getText().toString());
                article.setContent(txtContent.getText().toString());
                articleUpdate(article);
            }
        });
    }

    public void articleUpdate(Article article){
        service= Apis.getPersonaService();
        Call<Article> call=service.articleUpdate(article);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if(response.isSuccessful()) {
                    final Article msg = response.body();
                    if(msg.getMsg()==1){
                        Toast.makeText(ArticleUpdateActivity.this, "수정 완료",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ArticleUpdateActivity.this,
                                ArticleActivity.class));
                        finish();
                    }else{
                        Toast.makeText(ArticleUpdateActivity.this, "수정 실패",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }
}