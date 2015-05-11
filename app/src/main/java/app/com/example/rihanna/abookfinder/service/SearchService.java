package app.com.example.rihanna.abookfinder.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.Book;
import app.com.example.rihanna.abookfinder.utils.*;


public class SearchService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    private static final String TAG = "DownloadService";
    public SearchService() {
        super(SearchService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Service Started!");
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("url");

        Bundle bundle = new Bundle();

        if (!TextUtils.isEmpty(url)) {
            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
            try {
                ArrayList<Book> results = downloadData(url);
                Log.i("done results", "300");
                /* Sending result back to activity */
                if (null != results && results.size() > 0) {
                    bundle.putParcelableArrayList("result", results);
                    receiver.send(STATUS_FINISHED, bundle);
                }
            } catch (Exception e) {
                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        }
        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }
    private ArrayList<Book> downloadData(String requestUrl) throws IOException, DownloadException {
        Log.i(TAG, "downloadData Started!");
        ArrayList<Book> books=null;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        URL url = new URL(requestUrl);
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            // set the connection timeout to 5 seconds and the read timeout to 10 seconds
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(10000);
        } catch (java.net.SocketTimeoutException e){
            books=new ArrayList<Book>();
            books.add(new Book ("SEARCH TAKES TO MUCH TIME"));
            return books;
                   }
        int statusCode = urlConnection.getResponseCode();
        /* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String response = convertInputStreamToString(inputStream);
          try {
              int nums=BookJsonParse.totalItems(response);
              if(nums==0){    //there is no book wih this title
                  books=new ArrayList<Book>();
                  books.add(new Book ("NO BOOK WITH THIS TITLE"));
                  return books;
              } else {
                  books = parseResult(response);
                  return books;
              }

                }catch(Exception e){}
        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
        return books;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }
        return result;
    }

    private ArrayList<Book> parseResult(String result)throws JSONException {
        ArrayList<Book> bookL =BookJsonParse.getBooksDataFromJson(result);
        return bookL;
    }

    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
