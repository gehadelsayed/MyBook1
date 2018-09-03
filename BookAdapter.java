package com.example.android.mybook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<BFinder> {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    ImageView imageView;

    public BookAdapter (Context context, ArrayList<BFinder> book){

        super(context,0,book);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

         //super.getView(position, convertView, parent);

        View listItemView = convertView;

        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.blue_print,parent,false);
        }

        BFinder currentBook=getItem(position);

        TextView bookTitle=listItemView.findViewById(R.id.btitle);
        bookTitle.setText(currentBook.getTitle());

        Log.v("booktitle ----->",bookTitle.toString());

        TextView authorText= listItemView.findViewById(R.id.bauthor);
        authorText.setText(currentBook.getAuthor());

        TextView dateText= listItemView.findViewById(R.id.bdate);
        dateText.setText(currentBook.getDate());

        TextView description= listItemView.findViewById(R.id.bBrief);
        description.setText(currentBook.getDiscription());


        imageView = listItemView.findViewById(R.id.bimage);
        Picasso.with(getContext()).load(currentBook.getImageUrl()).into(imageView);



        return listItemView;
    }


}
