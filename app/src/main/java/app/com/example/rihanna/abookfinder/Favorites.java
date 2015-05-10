package app.com.example.rihanna.abookfinder;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import app.com.example.rihanna.abookfinder.db.*;

public class Favorites extends ActionBarActivity implements FavoriteListFragment.Callback {
    private final String LOG_TAG = Favorites.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private static boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        if (findViewById(R.id.detailed_book) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detailed_book, new FavoriteDetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
        FavoriteListFragment frag=((FavoriteListFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_favorites));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FavoriteDetailFragment df = (FavoriteDetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
    }

    @Override
    public void onItemSelected(String idBook) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putString("idBook", idBook);
            FavoriteDetailFragment fragment = new FavoriteDetailFragment();
            fragment.setArguments(args);

             /*Shows the detailed book next to favorites*/
            FrameLayout frameLT= (FrameLayout) findViewById(R.id.detailed_book);
            frameLT.setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailed_book, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, FavoriteDetail.class)
                    .putExtra("idBook", idBook);
            startActivity(intent);
        }
    }


}
