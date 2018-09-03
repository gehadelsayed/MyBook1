package com.example.android.mybook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button searchButton=(Button)findViewById(R.id.bsearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(MainActivity.this,ListActivity.class);
                i.putExtra("URL",getUrl());

                startActivity(i);
            }
        });
    }

    public String getUrl(){

        EditText searchEntry= (EditText)findViewById(R.id.bedit) ;
         String BOOK_URL="https://www.googleapis.com/books/v1/volumes?q=intitle:"+searchEntry.getText();

         return BOOK_URL;

    }
}
