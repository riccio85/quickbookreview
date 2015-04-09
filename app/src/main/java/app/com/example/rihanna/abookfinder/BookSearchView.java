package app.com.example.rihanna.abookfinder;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.utils.BookJsonParse;
import app.com.example.rihanna.abookfinder.utils.BookListViewAdapter;

public class BookSearchView extends ActionBarActivity
        implements AdapterView.OnItemClickListener,SearchResultReceiver.Receiver {

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
        Log.i("content search layout", "5");
        Intent intent = getIntent();
        book = intent.getStringExtra("BookTitle");
        Log.i("intent read " + book, "6");
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
        Log.i("created serach result receiver  " + book, "7");
        mReceiver.setReceiver(this);

        Intent intentTo = new Intent(Intent.ACTION_SYNC, null, this, SearchService.class);
        Log.i("Intent.ACTION_SYNC" , "8");
        /* Send optional extras to Download IntentService */
        intentTo.putExtra("url", url);
        intentTo.putExtra("receiver", mReceiver);
        intentTo.putExtra("requestId", 101);
        startService(intentTo);

    }
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        idBook=position;
        setContentView(R.layout.book_search_view);
        PlaceholderFragment fragment=new PlaceholderFragment();
       // BookDetailed fragment=new BookDetailed();
        Bundle bundle = new Bundle();
        bundle.putInt("id", idBook);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
   //     Toast toast = Toast.makeText(getApplicationContext(),"Item " + (position + 1) + ": " + bookList.get(position),Toast.LENGTH_SHORT);
   //     toast.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) throws JSONException {
        switch (resultCode) {
            case SearchService.STATUS_RUNNING:
              setSupportProgressBarIndeterminateVisibility(true);
                break;
            case SearchService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
              setSupportProgressBarIndeterminateVisibility(false);
               // ArrayList<BooksList> listBooks=resultData.getParcelableArrayList("result");
                bookList=resultData.getParcelableArrayList("result");
                String noBook="NESSUNLIBRO";
                if(bookList.size()==0){
                    Toast toast = Toast.makeText(getApplicationContext(),"NoBook: "+bookList.size(),Toast.LENGTH_SHORT);
                    toast.show();
                    //modificare il list view in modo che fa vedere che non ce nessun libro
                }
                Log.i("caricato booklist all ritorno del intent service ", "8");
                ListView listView = (ListView) findViewById(R.id.list);
                if (bookList != null ) {
                    BookListViewAdapter adapter = new BookListViewAdapter(this,
                            R.layout.search_view, bookList);
                    Log.i("adapter creates ", "9");
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(this);
                }
                /* Update ListView with result */
                break;
            case SearchService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.book_detail_view, container, false);

            int id=getArguments().getInt("id");

//            Toast toast = Toast.makeText(getActivity(),"Id " + id ,Toast.LENGTH_SHORT);
//            toast.show();

            LinearLayout layout1=(LinearLayout)rootView.findViewById(R.id.book_header_layout);
            ImageView image=(ImageView)rootView.findViewById(R.id.thumbnail);
            RatingBar rate=(RatingBar)rootView.findViewById(R.id.rating);
            TextView title=(TextView)rootView.findViewById(R.id.title);
            TextView creators=(TextView)rootView.findViewById(R.id.creators);
            TextView overview=(TextView)rootView.findViewById(R.id.overview);
            TextView published=(TextView)rootView.findViewById(R.id.published);
            TextView isbns=(TextView)rootView.findViewById(R.id.isbns);
            TextView price=(TextView)rootView.findViewById(R.id.price);
            TextView size=(TextView)rootView.findViewById(R.id.size);
            ArrayList<Book> allBooks=BookJsonParse.getArrayListBook();
            if(allBooks!=null){
                Book viewBook=allBooks.get(id);
                Bitmap imageL=viewBook.getImageLink();
                Drawable d = new BitmapDrawable(getResources(),imageL);
                image.setImageDrawable(d);

                String Title=viewBook.getTitle();
                String Authors=viewBook.getAuthors();
                title.setText(viewBook.getTitle());
                creators.setText(viewBook.getAuthors());
                overview.setText(viewBook.getOverview());
                published.setText(viewBook.getPubisher());
                isbns.setText(viewBook.getIsbns());
                price.setText(viewBook.getPrice());
                size.setText(viewBook.getPages());
            }
            return rootView;
        }

    }

}
