package com.cookandroid.flowerdesign.flower;

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
import com.cookandroid.flowerdesign.model.Upload;

import java.util.List;

public class FlowerListAdapter extends ArrayAdapter<Upload> implements Filterable {
    private Context context;
    private List<Upload> board;

    public FlowerListAdapter(@NonNull Context context, int resource, @NonNull List<Upload> objects) {
        super(context, resource, objects);
        this.context=context;
        this.board=objects;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater
                =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=layoutInflater.inflate(R.layout.content_main,parent,false);

        TextView title=(TextView)rowView.findViewById(R.id.title);
        ImageView image=rowView.findViewById(R.id.image);

        title.setText(String.format("%s",board.get(position).getTitle()));
        Glide.with(context).load(board.get(position).getFiles()).into(image);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ListDetailActivity.class);
                intent.putExtra("ID",String.valueOf(board.get(position).getId()));
                intent.putExtra("TITLE",board.get(position).getTitle());
                intent.putExtra("CONTENT",board.get(position).getContent());
                intent.putExtra("FILES",board.get(position).getFiles());
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}
