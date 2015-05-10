package app.com.example.rihanna.abookfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.com.example.rihanna.abookfinder.db.BookContract;

public class FavoriteDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,View.OnClickListener {

    private static final String LOG_TAG = FavoriteDetailFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private static final String BOOK_SHARE_HASHTAG = " #QuickBookReviewApp";

    private ShareActionProvider mShareActionProvider;

    private Uri mUri;

    private String previewString="";

    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
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

    private ImageView bookCover;
    private TextView title;
    private TextView authors;
    private TextView overview;
    private TextView published;
    private TextView isbns;
    private TextView price;
    private TextView size;
    private ImageButton button;
    String bookTitle;
    String bookId;

    public FavoriteDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            bookId=arguments.getString("idBook");
        }
        View rootView = inflater.inflate(R.layout.favorite_fragment_detail, container, false);



        bookCover = (ImageView) rootView.findViewById(R.id.s_thumbnail);
        title = (TextView) rootView.findViewById(R.id.s_title);
        authors = (TextView) rootView.findViewById(R.id.s_authors);
        overview = (TextView) rootView.findViewById(R.id.s_overview);
        published = (TextView) rootView.findViewById(R.id.s_published);
        isbns = (TextView) rootView.findViewById(R.id.s_isbns);
        price = (TextView) rootView.findViewById(R.id.s_price);
        size = (TextView) rootView.findViewById(R.id.s_size);
        button =(ImageButton)rootView.findViewById(R.id.deleteButton);
        if(button!=null) {
            button.setOnClickListener(this);
        }
        return rootView;
    }
    @Override
    public void onClick(View v) {
       Uri deleteUri= BookContract.BookEntry.buildBookWithId(bookId);
        String selection=BookContract.BookEntry.COLUMN_IDBOOK  + "= '" + bookId + "'";
        switch (v.getId()) {
            case R.id.deleteButton:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete Book?")
                        .setMessage("Sure you want to remove this book from favorites?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                int de = getActivity().getContentResolver()
                                        .delete(BookContract.BookEntry.CONTENT_URI,
                                                BookContract.BookEntry.COLUMN_IDBOOK+ "=?",
                                                new String[]{bookId});
                                Toast.makeText(getActivity(),"Book deleted for favorite List", Toast.LENGTH_LONG).show();

                                getActivity().finish();
                /*End the display page and reload activity favorite*/
                                startActivity(new Intent(getActivity(), Favorites.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.mipmap.ic_delete)
                        .show();

                break;
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorite_book_fragment, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (bookTitle != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, bookTitle + "\n"+ previewString + "\n"+ BOOK_SHARE_HASHTAG );
        return shareIntent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri bookUri = BookContract.BookEntry.buildBookWithId(bookId);
        if ( null != bookUri ) {
            return new CursorLoader(
                    getActivity(),
                    bookUri,
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
        if (data != null && data.moveToFirst()) {
            bookCover.setImageDrawable(getResources().getDrawable(R.drawable.browse));
            previewString=data.getString(COL_COLUMN_BUY);
            final String preview=data.getString(COL_COLUMN_BUY);
            bookCover.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(preview!=null){
                        Intent browserIntent = new Intent("android.intent.action.VIEW",
                                Uri.parse(preview));
                        startActivity(browserIntent);
                    }
                }
            });
            title.setText(data.getString(COL_COLUMN_TITLE));
            bookTitle=data.getString(COL_COLUMN_TITLE);
            authors.setText(data.getString(COL_COLUMN_AUTHORS));
            overview.setText(data.getString(COL_COLUMN_OVERVIEW));
            published.setText(data.getString(COL_COLUMN_PUBLISHER));
            isbns.setText(data.getString(COL_COLUMN_ISBNS));
            price.setText(data.getString(COL_COLUMN_PRICE));
            size.setText(data.getString(COL_COLUMN_PAGES));
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareIntent());
            }
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
}