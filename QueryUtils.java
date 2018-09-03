package com.example.android.mybook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<BFinder> fetchBookData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "ERROR CLOSING INPUT STREAM", e);

        }
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<BFinder> books = new ArrayList<>();

        try {

            // build up a list of Earthquake objects with the corresponding data.

            JSONObject baseJsonoResponse = new JSONObject(jsonResponse);
            JSONArray bookArray = baseJsonoResponse.getJSONArray("items");


            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentbook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentbook.getJSONObject("volumeInfo");


                // Extract the value for the key called "mag"
                String bname = volumeInfo.getString("title");
                Log.v("BookName------>", bname);

                String author = "";
                if (volumeInfo.has("authors")) {
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    try {
                        for (int j = 0; j < authors.length(); j++) {
                            author = authors.getString(0);
                        }
                    } catch (JSONException j) {
                        Log.e(LOG_TAG, "******author error********");
                    }

                } else {
                    author = "Author is not available";
                }
                Log.v("Authors------>", author);

                String date;
                if (volumeInfo.has("publishedDate")) {
                    date = volumeInfo.getString("publishedDate");
                } else {
                    date = "Publish date is not available";
                }
                Log.v("Date------>", date);

                String brief = "";
                if (volumeInfo.has("description")) {
                    brief = volumeInfo.getString("description");
                } else {
                    brief = "this book has no description";
                }
                Log.v("Description------>", brief);

                String bookUrl;
                if (volumeInfo.has("infoLink")) {
                    bookUrl = volumeInfo.getString("infoLink");
                } else {
                    bookUrl = "NO URL FOUND";
                }
                Log.v("URL----------->", bookUrl);

                String imageUrl;
                if (volumeInfo.has("imageLinks")) {
                    JSONObject innerObject = volumeInfo.getJSONObject("imageLinks");

                    if (innerObject.has("smallThumbnail")) {
                        imageUrl = innerObject.getString("smallThumbnail");

                    } else {
                        imageUrl = "no image found";
                    }
                }else{
                    imageUrl="no image linkds";
                }
                Log.v("IMAGE URL------->",imageUrl);





                BFinder bookFinder = new BFinder(bname, author, date, brief, bookUrl,imageUrl);
                books.add(bookFinder);
            }

        } catch (JSONException j) {
            Log.e(LOG_TAG, "ERROR IN JSON PARSING", j);
        }
        // Return the list of earthquakes
        Log.e(LOG_TAG, "******DATA FETCHED FROM JSON PARSING********");
        return books;
    }

    private static URL createUrl(String stringUrl) {

        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "ERROR WITH CREATING URL", e);

        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the BOOK JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}
