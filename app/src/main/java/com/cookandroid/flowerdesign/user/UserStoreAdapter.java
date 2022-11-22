package com.cookandroid.flowerdesign.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.flower.ListDetailActivity;
import com.cookandroid.flowerdesign.model.UserStore;

import java.util.List;

public class UserStoreAdapter extends ArrayAdapter<UserStore> implements Filterable {
    private static final String TAG = "UserStoreAdapter";
    private Context context;
    private List<UserStore> store;

    public UserStoreAdapter(@NonNull Context context, int resource, @NonNull List<UserStore> objects) {
        super(context, resource, objects);
        this.context=context;
        this.store=objects;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater
                =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=layoutInflater.inflate(R.layout.store_row,parent,false);

        TextView title=(TextView)rowView.findViewById(R.id.title);
        ImageView image=rowView.findViewById(R.id.image);

        title.setText(String.format("%s",store.get(position).getTitle()));
        Glide.with(context).load(store.get(position).getFiles()).into(image);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ListDetailActivity.class);
                intent.putExtra("ID",String.valueOf(store.get(position).getId()));
                intent.putExtra("TITLE",store.get(position).getTitle());
                intent.putExtra("CONTENT",store.get(position).getContent());
                intent.putExtra("FILES",store.get(position).getFiles());
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}