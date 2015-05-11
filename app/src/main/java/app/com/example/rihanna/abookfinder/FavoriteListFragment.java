package app.com.example.rihanna.abookfinder;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import app.com.example.rihanna.abookfinder.db.BookContract;
import app.com.example.rihanna.abookfinder.utils.FavoriteAdapter;

public class FavoriteListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = FavoriteListFragment.class.getSimpleName();
    private FavoriteAdapter mAdapter;
    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";
    private static final int FAVORITE_LOADER = 0;

    private static final String[] FAVORITE_COLUMNS = {
            BookContract.BookEntry.COLUMN_KEY,
            BookContract.BookEntry.COLUMN_IDBOOK,
            BookContract.BookEntry.COLUMN_TITLE,
            BookContract.BookEntry.COLUMN_PUBLISHER,
            BookContract.BookEntry.COLUMN_AUTHORS,
            BookContract.BookEntry.COLUMN_PAGES,
            BookContract.BookEntry.COLUMN_OVERVIEW,
            BookContract.BookEntry.COLUMN_ISBNS,
            BookContract.BookEntry.COLUMN_PRICE,
            BookContract.BookEntry.COLUMN_BUY,
            BookContract.BookEntry.COLUMN_SMALLIM,
            BookContract.BookEntry.COLUMN_BIGIM
    };

    public static final int COL_COLUMN_KEY = 0;
    public static final int COL_COLUMN_IDBOOK=1;
    public static final int COL_COLUMN_TITLE=2;
    public static final int COL_COLUMN_PUBLISHER=3;
    public static final int COL_COLUMN_AUTHORS=4;
    public static final int COL_COLUMN_PAGES=5;
    public static final int COL_COLUMN_OVERVIEW=6;
    public static final int COL_COLUMN_ISBNS=7;
    public static final int COL_COLUMN_PRICE=8;
    public static final int COL_COLUMN_BUY=9;
    public static final int COL_COLUMN_SMALLIM=10;
    public static final int COL_COLUMN_BIGIM=11;

    public interface Callback {
        public void onItemSelected(String bookID);
    }

    public FavoriteListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorite_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAdapter = new FavoriteAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                 /*highlight the selected list item */
                view.getFocusables(position);
                view.setSelected(true);

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    ((Callback) getActivity())
                                 .onItemSelected(cursor.getString(COL_COLUMN_IDBOOK));
                }
                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(FAVORITE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String sortOrder = BookContract.BookEntry.COLUMN_TITLE + " ASC";
        Uri booksUri = BookContract.BookEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                booksUri,
                FAVORITE_COLUMNS,
                null,
                null,
                sortOrder);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            mListView.smoothScrollToPosition(mPosition);
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}