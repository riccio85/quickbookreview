package app.com.example.rihanna.abookfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


public class BookSearchView extends ActionBarActivity
             {
    private boolean mTwoPane;
    private static final String DETAILSEARCH_TAG = "SEARCHTAG";
    String book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_book_search);

        Intent intent = getIntent();
        book = intent.getStringExtra("BookTitle");

        if (findViewById(R.id.detail_container) != null) {
           mTwoPane = true;
            if (savedInstanceState == null) {
                int id=1;

                BookDetailFragment fragment2=new  BookDetailFragment();
                Bundle bundle2=new Bundle();
                bundle2.putInt("idBook",0);
                fragment2.setArguments(bundle2);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, fragment2, DETAILSEARCH_TAG)
                        .commit();
        } else {
                mTwoPane = false;
                }
        }
        BookSearchFragment fragment=new BookSearchFragment();
        Bundle bundle=new Bundle();
        bundle.putString("bookTitle",book);
        bundle.putBoolean("mTwoPane",mTwoPane);
        if (savedInstanceState == null) {
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_search,fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_search, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return true;
    }
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
        return super.onOptionsItemSelected(item);
    }
/* Case tablet methods to call*/
     public  boolean ismTwoPane() {
         return mTwoPane;
     }
     public void setmTwoPane(boolean mTwoPane) {
         this.mTwoPane = mTwoPane;
     }

}
