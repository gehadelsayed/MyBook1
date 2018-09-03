package com.example.android.mybook;

public class BFinder {

    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String mDiscription;
    private String mUrl;
    private String mImageUrl;

    public BFinder(String title,String author,String date,String discription,String url,String imageUrl){
        mTitle=title;
        mAuthor=author;
        mDate=date;
        mDiscription=discription;
        mUrl=url;
        mImageUrl=imageUrl;
    }
    public BFinder(){}
    public String getTitle(){
        return mTitle;
    }
    public String getAuthor(){
        return mAuthor;
    }
    public String getDate(){
        return mDate;
    }
    public String getDiscription(){
        return mDiscription;
    }
    public String getUrl(){return mUrl;}
    public String getImageUrl(){return mImageUrl;}

}
