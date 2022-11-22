package com.cookandroid.flowerdesign.flower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.utils.Service;

public class ListDetailActivity extends AppCompatActivity {
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtTitle=(TextView) findViewById(R.id.title);
        ImageView txtFiles=(ImageView) findViewById(R.id.imageView);
        TextView txtContent=(TextView) findViewById(R.id.content);

        Bundle bundle=getIntent().getExtras();
        final String id=bundle.getString("ID");
        String title=bundle.getString("TITLE");
        String content=bundle.getString("CONTENT");
        String image=bundle.getString("FILES");

        txtTitle.setText(title);
        txtContent.setText(content);
        Glide.with(this).load(image).into(txtFiles);
    }
}