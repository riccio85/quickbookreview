package app.com.example.rihanna.abookfinder;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
    ImageView image;
    public BookDetailFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id=getArguments().getInt("idBook",-1);
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);
        LinearLayout layout1=(LinearLayout)rootView.findViewById(R.id.book_header_layout);
        image=(ImageView)rootView.findViewById(R.id.thumbnail);
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

                    /*(new View.OnClickListener() {
                public void onClick(View view) {
                    String ur=book.getBigThumb();
                    Toast toast = Toast.makeText(getActivity(), ""+ur, Toast.LENGTH_SHORT);
                   // showImage();
                }
            });*/

            image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(book.getBigThumb()!=null){
                            Intent browserIntent = new Intent("android.intent.action.VIEW",
                                    Uri.parse(book.getSmallThumb()));
                                startActivity(browserIntent);
                    }
                }
            });

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
                    Context mContext=getActivity();
                   /*Check if the book already exist in the database */
                    Cursor bookCursor=mContext.getContentResolver().query(
                            BookContract.BookEntry.CONTENT_URI,
                            new String[]{BookContract.BookEntry.COLUMN_IDBOOK},
                            BookContract.BookEntry.COLUMN_IDBOOK + " = ? ",
                            new String[]{book.getId()},
                            null);
                    if(bookCursor.moveToFirst()){
                        Toast.makeText(getActivity(), "Book already on favorites", Toast.LENGTH_LONG).show();
                    }else {
                    /*Insert the book inside the db*/
                        ContentValues values = new ContentValues();
                        values.put(BookContract.BookEntry.COLUMN_IDBOOK, book.getId());
                        values.put(BookContract.BookEntry.COLUMN_TITLE, book.getTitle());
                        values.put(BookContract.BookEntry.COLUMN_AUTHORS, book.getAuthors());
                        values.put(BookContract.BookEntry.COLUMN_OVERVIEW, book.getOverview());
                        values.put(BookContract.BookEntry.COLUMN_PUBLISHER, book.getPubisher());
                        values.put(BookContract.BookEntry.COLUMN_ISBNS, book.getIsbns());
                        values.put(BookContract.BookEntry.COLUMN_PRICE, book.getPrice());
                        values.put(BookContract.BookEntry.COLUMN_PAGES, book.getPages());
                        values.put(BookContract.BookEntry.COLUMN_BUY, book.getBuyLink());
                        values.put(BookContract.BookEntry.COLUMN_SMALLIM, book.getSmallThumb());
                        values.put(BookContract.BookEntry.COLUMN_BIGIM, book.getBigThumb());
                        Uri inserUri=mContext.getContentResolver().insert(BookContract.BookEntry.CONTENT_URI,values);
                        Toast.makeText(getActivity(), "Book added to favorites", Toast.LENGTH_LONG).show();
                    }
                    bookCursor.close();
                }
                break;
           // case R.id.thumbnail:
            //    Toast toast = Toast.makeText(getActivity(), ""+book.getBigThumb(), Toast.LENGTH_SHORT);
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

    /*method to see the bookcover in bigThumbnail*/
    public void showImage() {
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });
        ImageView imageView = new ImageView(getActivity());
        if(book!=null){
            String url=book.getBigThumb();
                Bitmap image=BookJsonParse.getBitmapFromURL(url);
                imageView.setImageBitmap(image);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));}
        builder.show();
    }
    // @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), Favorites.class));
            return true;
        }
        if (id==R.id.action_share) {
          ///////////////////////////////////////////////////////////
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
