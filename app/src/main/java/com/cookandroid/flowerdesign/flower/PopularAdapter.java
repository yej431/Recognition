package com.cookandroid.flowerdesign.flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.SearchRank;
import com.cookandroid.flowerdesign.search.MainSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private List<SearchRank> searchrank;
    private Context context;

    public PopularAdapter(Context context, List<SearchRank> list) {
        this.searchrank = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_listitem,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        holder.keyword.setText(searchrank.get(position).getKeyword());
        holder.keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MainSearchActivity.class);
                intent.putExtra("searchtxt",searchrank.get(position).getKeyword());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchrank.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView keyword;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            keyword=itemView.findViewById(R.id.keyword);
        }
    }
}
