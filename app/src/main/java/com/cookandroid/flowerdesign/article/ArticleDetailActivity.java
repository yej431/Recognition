package com.cookandroid.flowerdesign.article;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.Article;
import com.cookandroid.flowerdesign.user.SessionManager;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleDetailActivity extends AppCompatActivity {
    Service service;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtUserid=findViewById(R.id.userid);
        TextView txtWriteDate=findViewById(R.id.writeDate);
        TextView txtTitle=findViewById(R.id.title);
        TextView txtContent=findViewById(R.id.content);
        Button updatebtn=findViewById(R.id.updatebtn);
        Button delbtn=findViewById(R.id.delbtn);

        Bundle bundle=getIntent().getExtras();
        String id=bundle.getString("ID");
        String title=bundle.getString("TITLE");
        String content=bundle.getString("CONTENT");
        String userid=bundle.getString("USERID");
        String writeDate=bundle.getString("WRITEDATE");

        txtTitle.setText(title);
        txtContent.setText(content);
        txtUserid.setText("글쓴이: "+userid);
        txtWriteDate.setText("글쓴 날짜: "+writeDate);

        sessionManager=new SessionManager(getApplicationContext());

        if(sessionManager.getUsername().equals(userid)){
            updatebtn.setVisibility(View.VISIBLE);
            delbtn.setVisibility(View.VISIBLE);
        }

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManager.getLogin()){
                    Intent intent=new Intent(ArticleDetailActivity.this,
                            ArticleUpdateActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("userid", txtUserid.getText());
                    intent.putExtra("writeDate", txtWriteDate.getText());
                    intent.putExtra("title", txtTitle.getText().toString());
                    intent.putExtra("content", txtContent.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(ArticleDetailActivity.this, "로그인 해주세요",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManager.getLogin()){
                    Article article=new Article();
                    article.setId(Integer.parseInt(id));
                    article.setUserid(sessionManager.getUsername());
                    articleDelete(article);
                }else{
                    Toast.makeText(ArticleDetailActivity.this, "로그인 해주세요",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void articleDelete(Article article){
        service= Apis.getPersonaService();
        Call<Article> call=service.articleDelete(article);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if(response.isSuccessful()) {
                    final Article msg = response.body();
                    if(msg.getMsg()==1){
                        Toast.makeText(ArticleDetailActivity.this, "삭제 완료",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ArticleDetailActivity.this,
                                ArticleActivity.class));
                        finish();
                    }else{
                        Toast.makeText(ArticleDetailActivity.this, "삭제 실패",
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