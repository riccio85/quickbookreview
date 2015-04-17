package app.com.example.rihanna.abookfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.db.*;
import app.com.example.rihanna.abookfinder.utils.*;

public class Favorites extends ActionBarActivity implements AdapterView.OnItemClickListener
    // implements AdapterView.OnItemClickListener,LoaderManager.LoaderCallbacks<Cursor>
{
    ImageButton button;
    ListView listview;
    Book book;
    ArrayList<Book> favoriteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
      /* if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        ListView listView = (ListView) findViewById(R.id.listFavorites);
        DbTest dbHelper=new  DbTest(this);
        favoriteList=new ArrayList<Book>();

        favoriteList=dbHelper.getSavedBookList();
        for(int i=0;i<favoriteList.size();i++){
            Drawable myDrawable = getResources().getDrawable(R.drawable.save);
            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
            favoriteList.get(i).setImageLink(myLogo);
        }
        if(favoriteList.size()==0){// nessun data
            Toast.makeText(this, "No book saved!!!!!", Toast.LENGTH_LONG).show();
        }else{
            if (favoriteList != null ) {
                BookListViewAdapter adapter = new BookListViewAdapter(this,
                        R.layout.fragment_book_search, favoriteList);
                Log.i("adapter creates ", "9");
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(this);
            }

        }
    }
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        book = favoriteList.get(position);
        setContentView(R.layout.saved_detail_view);
        ImageView image = (ImageView) findViewById(R.id.s_thumbnail);
        TextView title = (TextView) findViewById(R.id.s_title);
        TextView creators = (TextView) findViewById(R.id.s_creators);
        TextView overview = (TextView) findViewById(R.id.s_overview);
        TextView published = (TextView) findViewById(R.id.s_published);
        TextView isbns = (TextView) findViewById(R.id.s_isbns);
        TextView price = (TextView) findViewById(R.id.s_price);
        TextView size = (TextView) findViewById(R.id.s_size);

        button = (ImageButton) findViewById(R.id.deleteButton);

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(book!=null){
                        DbTest dbTest=new DbTest(getApplication());
                        dbTest.deleteHardCode(book);}
                    startActivity(new Intent(getApplication(),Favorites.class));
                }
            });

        }
        if (favoriteList != null) {
            Book viewBook = favoriteList.get(position);
            book = viewBook;
            Bitmap imageL = viewBook.getImageLink();
            Drawable d = new BitmapDrawable(getResources(), imageL);
            image.setImageDrawable(d);
            title.setText(viewBook.getTitle());
            creators.setText(viewBook.getAuthors());
            overview.setText(viewBook.getOverview());
            published.setText(viewBook.getPubisher());
            isbns.setText(viewBook.getIsbns());
            price.setText(viewBook.getPrice());
            size.setText(viewBook.getPages());
      /*  idBook=position;
        setContentView(R.layout.book_detail_view);
        BookDetailFragment fragment=new BookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", idBook);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container1,fragment)
                .commit();*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //onl
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,Favorites.class));
            return true;
        }
        if(id==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return null;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
            return rootView;
        }
    }
}
