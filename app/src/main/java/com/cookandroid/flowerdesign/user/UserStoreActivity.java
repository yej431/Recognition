package com.cookandroid.flowerdesign.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.UserStore;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserStoreActivity extends AppCompatActivity {
    Service service;
    List<UserStore> list=new ArrayList<>();
    ListView listView;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager=new SessionManager(getApplicationContext());

        listView=(ListView)findViewById(R.id.listView);
        userStoreList(sessionManager.getUsername());
    }

    public void userStoreList(String userid){
        service= Apis.getPersonaService();
        Call<List<UserStore>> call=service.userStoreList(userid);
        call.enqueue(new Callback<List<UserStore>>() {
            @Override
            public void onResponse(Call<List<UserStore>> call, Response<List<UserStore>> response) {
                if(response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new UserStoreAdapter(UserStoreActivity.this
                            ,R.layout.content_main,list));
                }
            }
            @Override
            public void onFailure(Call<List<UserStore>> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }
}