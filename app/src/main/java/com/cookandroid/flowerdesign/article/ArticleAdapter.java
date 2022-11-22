package com.cookandroid.flowerdesign.article;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.Article;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article>{
    private static final String TAG = "ArticleAdapter";
    private Context context;
    private List<Article> article;

    public ArticleAdapter(@NonNull Context context, int resource, @NonNull List<Article> objects) {
        super(context, resource, objects);
        this.context=context;
        this.article=objects;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater
                =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=layoutInflater.inflate(R.layout.my_row,parent,false);

        TextView id=rowView.findViewById(R.id.id);
        TextView title=(TextView)rowView.findViewById(R.id.title);
        TextView content=(TextView)rowView.findViewById(R.id.content);
        TextView userid=(TextView)rowView.findViewById(R.id.writer);
        TextView writeDate=(TextView)rowView.findViewById(R.id.writeDate);

        id.setText(String.format("%s", article.get(position).getId()));
        title.setText(String.format("%s",article.get(position).getTitle()));
        content.setText(String.format("%s",article.get(position).getContent()));
        userid.setText(String.format("%s",article.get(position).getUserid()));
        writeDate.setText(String.format("%s",article.get(position).getWriteDate()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("ID",String.valueOf(article.get(position).getId()));
                intent.putExtra("TITLE",article.get(position).getTitle());
                intent.putExtra("CONTENT",article.get(position).getContent());
                intent.putExtra("USERID",article.get(position).getUserid());
                intent.putExtra("WRITEDATE",article.get(position).getWriteDate());
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}