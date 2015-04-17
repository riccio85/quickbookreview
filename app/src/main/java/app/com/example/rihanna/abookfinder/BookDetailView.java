package app.com.example.rihanna.abookfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import app.com.example.rihanna.abookfinder.db.*;
import app.com.example.rihanna.abookfinder.utils.BookJsonParse;


public class BookDetailView extends ActionBarActivity {
    int idSelected;
    ShareActionProvider actionProvider;
    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = getIntent();
        idSelected = intent.getIntExtra("idBook",0);

        ArrayList<Book> allBooks = BookJsonParse.getArrayListBook();
        Book viewBook=allBooks.get(idSelected);
        BookDetailFragment fragment=new BookDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("idBook",idSelected);
        if (savedInstanceState == null) {
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container,fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

         /* MenuItem menuItem = menu.findItem(R.id.action_share);
        actionProvider =(ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
      if (book != null) {
            actionProvider.setShareIntent(createShareBookDetailIntent());
        }*/
        return true;
    }
    /*
        private Intent createShareBookDetailIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, book.getTitle());
            return shareIntent;
        }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    /*    if(id==R.id.action_share){
            if (book != null) {
                actionProvider.setShareIntent(createShareBookDetailIntent());}
        }*/
        return super.onOptionsItemSelected(item);
    }

}