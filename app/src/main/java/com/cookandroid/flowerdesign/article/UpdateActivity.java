package com.cookandroid.flowerdesign.article;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cookandroid.flowerdesign.R;

public class UpdateActivity extends AppCompatActivity {
    EditText title_input, author_input, pages_input;
    Button update_button, delete_button;
    String id,title,author,pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title_input=findViewById(R.id.title_input2);
        author_input=findViewById(R.id.author_input2);
        pages_input=findViewById(R.id.pages_input2);
        update_button=findViewById(R.id.update_button);
        delete_button=findViewById(R.id.delete_button);

        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MyDataHelper mydb=new MyDataHelper(UpdateActivity.this);
//                mydb.updateData(id,title,author,pages);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
            getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
            //Getting Data from Intent
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
            author=getIntent().getStringExtra("author");
            pages=getIntent().getStringExtra("pages");

            //Setting Intent Data
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete "+title+" ?");
        builder.setMessage("Are you sure you want to delete "+title+" ?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                MyDataHelper mydb=new MyDataHelper(UpdateActivity.this);
//                mydb.deleteOneRow(id);
//                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}