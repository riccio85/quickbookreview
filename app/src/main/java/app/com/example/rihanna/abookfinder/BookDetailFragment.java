package app.com.example.rihanna.abookfinder;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import app.com.example.rihanna.abookfinder.db.*;
import app.com.example.rihanna.abookfinder.utils.*;

/**
 * Created by Rihanna on 16/04/2015.
 */
public class BookDetailFragment extends Fragment implements View.OnClickListener
{
    int id;
    ShareActionProvider actionProvider;
    Book book;
    ImageButton button;
    public BookDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id=getArguments().getInt("idBook",-1);
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);
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
        TextView link=(TextView)rootView.findViewById(R.id.buyLink);
        RatingBar ratingBar=(RatingBar)rootView.findViewById(R.id.rating);

        ArrayList<Book> allBooks= BookJsonParse.getArrayListBook();
        button =(ImageButton)rootView.findViewById(R.id.saveButton);
        if(button!=null) {
            button.setOnClickListener(this);
        }
        if(allBooks!=null){
            Book viewBook=allBooks.get(id);
            book=viewBook;
            Bitmap imageL=viewBook.getImageLink();
            Drawable d = new BitmapDrawable(getResources(),imageL);
            image.setImageDrawable(d);
            title.setText(viewBook.getTitle());
            creators.setText(viewBook.getAuthors());
            overview.setText(viewBook.getOverview());
            published.setText(viewBook.getPubisher());
            isbns.setText(viewBook.getIsbns());
            price.setText(viewBook.getPrice());
            size.setText(viewBook.getPages());
            link.setText(viewBook.getBuyLink());
            if(viewBook.getRating()>5) {
                ratingBar.setRating(5);
            }else {ratingBar.setRating(viewBook.getRating());}
        }
        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                if (book != null) {
                    DbTest dbHelper = new DbTest(getActivity());
                    if (!dbHelper.checkOnDb(book)) {
                        ContentValues values = new ContentValues();
                        values.put(BookDB.BooksEntry.COLUMN_IDBOOK, book.getId());
                        values.put(BookDB.BooksEntry.COLUMN_TITLE, book.getTitle());
                        values.put(BookDB.BooksEntry.COLUMN_AUTHORS, book.getAuthors());
                        values.put(BookDB.BooksEntry.COLUMN_OVERVIEW, book.getOverview());
                        values.put(BookDB.BooksEntry.COLUMN_PUBLISHER, book.getPubisher());
                        values.put(BookDB.BooksEntry.COLUMN_ISBNS, book.getIsbns());
                        values.put(BookDB.BooksEntry.COLUMN_PRICE, book.getPrice());
                        values.put(BookDB.BooksEntry.COLUMN_PAGES, book.getPages());
                        values.put(BookDB.BooksEntry.COLUMN_BUY, book.getBuyLink());
                        if (dbHelper.insertHardCode(values)) {
                            Toast.makeText(getActivity(), "Book added to favorites", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Book already on favorites", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    //////////////////////////////////////////////////////////
 /*  @Override
   public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        //getActivity().getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        inflater.inflate(R.menu.menu_book_detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
      //actionProvider=new ShareActionProvider(getActivity());
     actionProvider =(ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            if (book != null) {
                actionProvider.setShareIntent(createShareBookDetailIntent());
            }
    }*/
    private Intent createShareBookDetailIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, book.getTitle());
        return shareIntent;
    }
    // @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get the menu item.
        getActivity().getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        // Get the provider and hold onto it to set/change the share intent.
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        // Set history different from the default before getting the action
        // view since a call to MenuItem.getActionView() calls
        // onCreateActionView() which uses the backing file name. Omit this
        // line if using the default share history file is desired.
        actionProvider.setShareIntent(createShareBookDetailIntent());
        return true;
    }

    // Somewhere in the application.
    public void doShare(Intent shareIntent) {
        // When you want to share set the share intent.
        actionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), Favorites.class));
            return true;
        }
        if (id==R.id.action_share) {
            Intent intent=createShareBookDetailIntent();
            doShare(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
