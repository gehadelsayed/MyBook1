package com.example.android.mybook;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BFinder>> {

    String mUrl;
    public BookLoader (Context context, String url){

        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<BFinder> loadInBackground() {

        BFinder book= new BFinder();
        if (mUrl==null)
        {
            return null;
        }

        Log.v("url--------->",mUrl);
        List<BFinder> Books=QueryUtils.fetchBookData(mUrl);



        return Books;
    }
}
