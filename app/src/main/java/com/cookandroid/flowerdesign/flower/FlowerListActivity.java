package com.cookandroid.flowerdesign.flower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.Upload;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlowerListActivity extends AppCompatActivity {
    Service service;
    List<Upload> list=new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView=(ListView)findViewById(R.id.listView);
        boardList("");
    }

    public void boardList(String keyword){
        service= Apis.getPersonaService();
        Call<List<Upload>> call=service.getList(keyword);
        call.enqueue(new Callback<List<Upload>>() {
            @Override
            public void onResponse(Call<List<Upload>> call, Response<List<Upload>> response) {
                if(response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new FlowerListAdapter(FlowerListActivity.this
                            ,R.layout.content_main,list));
                }
            }
            @Override
            public void onFailure(Call<List<Upload>> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                boardList(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                boardList(newText);
                return false;
            }
        });
        return true;
    }
}