package com.example.android.mybook;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.design.widget.TabItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BFinder>> {

    BookAdapter mAdapter;
    String query;
    private static final int Book_LOADER_ID = 1;
    private TextView mEmptyText;
    Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        mEmptyText=(TextView) findViewById(R.id.noInternet);


         query= getIntent().getStringExtra("URL");
         Log.v("URL---------------->", query);

        mAdapter=new BookAdapter(this,new ArrayList<BFinder>());

        ListView bookFinderListView=(ListView) findViewById(R.id.bList);

        bookFinderListView.setEmptyView(mEmptyText);
        bookFinderListView.setAdapter(mAdapter);
        LoaderManager loaderManager=getLoaderManager();

        if (isNetworkAvailable()) {
            loaderManager.initLoader(Book_LOADER_ID, null, this);
        }
        else
        {
            mEmptyText.setText("NO INTERNET AVAILABLE");

        }

        bookFinderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BFinder book=mAdapter.getItem(position);
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(book.getUrl()));
                startActivity(i);
            }
        });


    }

    @Override
    public Loader<List<BFinder>> onCreateLoader(int id, Bundle args) {

        return new BookLoader(this,query);
    }

    @Override
    public void onLoadFinished(Loader<List<BFinder>> loader, List<BFinder> data) {
        mAdapter.clear();

        mEmptyText.setText("No Data Found");
        if (data !=null & !data.isEmpty()){

            mAdapter.addAll(data);



        }

    }

    @Override
    public void onLoaderReset(Loader<List<BFinder>> loader) {

        mAdapter.clear();
    }

    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}
