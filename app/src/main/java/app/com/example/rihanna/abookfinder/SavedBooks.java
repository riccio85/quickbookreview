package app.com.example.rihanna.abookfinder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;
import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.db.BookDB;
import app.com.example.rihanna.abookfinder.db.BooksContentProvider;

/**
 * Created by Rihanna on 09/04/2015.
 */


////////////////////////////////////////////////////////////////////test class
public class SavedBooks extends Fragment
        //implements LoaderManager.LoaderCallbacks<Cursor>
{
    SimpleCursorAdapter mAdapter;
    static final String DETAIL_URI = "URI";
    //private static final String FORECAST_SHARE_HASHTAG = "#BookApp";
    private Uri mUri;
    private static final int DETAIL_LOADER = 0;
    private static final String[] DETAIL_COLUMNS = {
            BookDB.BooksEntry.TABLE_NAME,
            BookDB.BooksEntry.COLUMN_KEY,
            BookDB.BooksEntry.COLUMN_IDBOOK,
            BookDB.BooksEntry.COLUMN_TITLE,
            BookDB.BooksEntry.COLUMN_AUTHORS,
            BookDB.BooksEntry.COLUMN_OVERVIEW,
            BookDB.BooksEntry.COLUMN_PUBLISHER,
            BookDB.BooksEntry.COLUMN_ISBNS,
            BookDB.BooksEntry.COLUMN_PRICE,
            BookDB.BooksEntry.COLUMN_PAGES,
         //   BookDB.BooksEntry.COLUMN_RATING,
           // BookDB.BooksEntry.COLUMN_IMAGE,
    };
    // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
    // must change.
        public static final int COL_TABLE_NAME=0;
        public static final int COL_COLUMN_KEY=1;
        public static final int COL_COLUMN_IDBOOK=2;
        public static final int COL_COLUMN_TITLE=3;
        public static final int COL_COLUMN_AUTHORS=4;
        public static final int COL_COLUMN_OVERVIEW=5;
        public static final int COL_COLUMN_PUBLISHER=6;
        public static final int COL_COLUMN_ISBNS=7;
        public static final int COL_COLUMN_PRICE=8;
        public static final int COL_COLUMN_PAGES=9;
        public static final int COL_COLUMN_RATING=10;
     //   public static final int COL_COLUMN_IMAGE=11;

    public SavedBooks() {
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        Book idB=getArguments().getParcelable("Book");
        Toast.makeText(getActivity(),"passato libro:"+idB.getTitle() , Toast.LENGTH_LONG).show();
        ContentValues values = new ContentValues();
                values.put(BookDB.BooksEntry.COLUMN_IDBOOK,idB.getId());
                values.put(BookDB.BooksEntry.COLUMN_TITLE,idB.getTitle());
                values.put(BookDB.BooksEntry.COLUMN_AUTHORS,idB.getAuthors());
                values.put(BookDB.BooksEntry.COLUMN_OVERVIEW,idB.getOverview());
                values.put(BookDB.BooksEntry.COLUMN_PUBLISHER,idB.getPubisher());
                values.put(BookDB.BooksEntry.COLUMN_ISBNS,idB.getIsbns());
                values.put(BookDB.BooksEntry.COLUMN_PRICE,idB.getPrice());
                values.put(BookDB.BooksEntry.COLUMN_PAGES,idB.getPages());
        Toast.makeText(getActivity(), "Contente created", Toast.LENGTH_LONG)
                .show();

     //   Uri uri = getActivity().getContentResolver().insert(BooksContentProvider.CONTENT_URI, values);
      //  Toast.makeText(getActivity(), "New record inserted"+/uri.toString(), Toast.LENGTH_LONG)
        //        .show();
        //QUI DEVO SALVARE


     /*   if (arguments != null) {
            mUri = arguments.getParcelable(SavedBooks.DETAIL_URI);
        }*/
        View rootView = inflater.inflate(R.layout.test_book_saved, container, false);
        /////implementare listAdapter qui

        return rootView;
    }
//    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu, menu);
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

    @Override ////////da vdere/////////////////////////////////////////////////////
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu, menu);
        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);
    }

   /* private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecast + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }*/
/*
   @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<Book> booklist=new ArrayList<Book>();
        /////////////////////////////////////////////////////aggiungere while devo caricare tutti
        if (data != null && data.moveToFirst()) {
            // Read weather condition ID from cursor
           // int weatherId = data.getInt(COL_WEATHER_CONDITION_ID);
 ///////////////////getImage from //////////////////////////////////////////////////////////
            // Blob image=data.getBlob(COL_COLUMN_IMAGE); //must get Drawable
            Bitmap image=null; ///////////////////////
            // Read date from cursor
            int id=data.getInt(COL_COLUMN_KEY);
            String idBook=data.getString(COL_COLUMN_IDBOOK);
            String title=data.getString(COL_COLUMN_TITLE);
            String authors=data.getString(COL_COLUMN_AUTHORS);
            String publisher=data.getString(COL_COLUMN_PUBLISHER);
            String pages=data.getString(COL_COLUMN_PAGES);
            String overview=data.getString(COL_COLUMN_OVERVIEW);
            String isbn=data.getString(COL_COLUMN_ISBNS);
            String price=data.getString(COL_COLUMN_PRICE);
            float rating=data.getFloat(COL_COLUMN_RATING);
            Book book=new Book(idBook,title,authors,overview,publisher,isbn,price,pages,rating,image);
            booklist.add(book);
           // Read description from cursor and update view

            // If onCreateOptionsMenu has already happened, we need to update the share intent now.
          //  if (mShareActionProvider != null) {
            //    mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
        }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
*/
}
