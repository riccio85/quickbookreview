package app.com.example.rihanna.abookfinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.util.ArrayList;
import app.com.example.rihanna.abookfinder.utils.BookJsonParse;
import app.com.example.rihanna.abookfinder.utils.BookListViewAdapter;

public class BookSearchView extends ActionBarActivity
        implements AdapterView.OnItemClickListener,SearchResultReceiver.Receiver {
    ProgressDialog progressDialog;
    String jsonStr="";
    public String book;
    private ArrayAdapter<Book> adapter=null;
    ArrayList<Book> bookList;
    private SearchResultReceiver mReceiver;
    int idBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               /* Allow activity to show indeterminate progressbar */
       supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//da rivedere
        setContentView(R.layout.search_view);
        Intent intent = getIntent();
        book = intent.getStringExtra("BookTitle");
        /* Initialize listView */
        ListView listView = (ListView) findViewById(R.id.list);
        final String QUERY_BASE = "https://www.googleapis.com/books/v1/volumes?";
        final String QUERY_PARAM = "q";
        final String FORMAT_PARAM = "mode";
        final String PRINT_TYPE = "printType";
        Uri builtUri = Uri.parse(QUERY_BASE).buildUpon()
                .appendQueryParameter(QUERY_PARAM,book)
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build();
        final String url= builtUri.toString();
        /* Starting Download Service */
        mReceiver = new SearchResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intentTo = new Intent(Intent.ACTION_SYNC, null, this, SearchService.class);
        /* Send optional extras to Download IntentService */
        intentTo.putExtra("url", url);
        intentTo.putExtra("receiver", mReceiver);
        intentTo.putExtra("requestId", 101);
        startService(intentTo);

    }
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        idBook=position;
        setContentView(R.layout.book_search_view);
        BookDetailFragment fragment=new BookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", idBook);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container1,fragment)
                .commit();
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setSupportProgressBarIndeterminate(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,FavoritesActivity.class));
            return true;
        }
        if(id==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) throws JSONException {
        switch (resultCode) {
            case SearchService.STATUS_RUNNING:
                this.setSupportProgressBarIndeterminateVisibility(true);
                progressDialog = ProgressDialog.show(BookSearchView.this,
                        "ProgressDialog", "Searching!");
                break;
            case SearchService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
              this.setSupportProgressBarIndeterminateVisibility(false);
                progressDialog.dismiss();
                bookList=resultData.getParcelableArrayList("result");
                ListView listView = (ListView) findViewById(R.id.list);
                if(bookList.size()==0||bookList==null){
                    Toast toast = Toast.makeText(getApplicationContext(),"Search has no books for you with. Retry!!!",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(bookList.size()==1 && bookList.get(0).getTitle().equals("NO BOOK WITH THIS TITLE")){
                    Toast toast = Toast.makeText(getApplicationContext(),"Search has no books for you with. Retry!!!",Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (bookList != null ) {
                    BookListViewAdapter adapter = new BookListViewAdapter(this,
                            R.layout.search_view, bookList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(this);
                }
                /* Update ListView with result */
                break;
            case SearchService.STATUS_ERROR:
                /* Handle the error */
                progressDialog.dismiss();
                //String error = resultData.getString(Intent.EXTRA_TEXT);
                //Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                Toast toast = Toast.makeText(getApplicationContext(),"Please go back and put a book!",Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }
}
