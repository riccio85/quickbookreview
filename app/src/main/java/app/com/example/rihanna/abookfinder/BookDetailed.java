package app.com.example.rihanna.abookfinder;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.utils.BookJsonParse;
/**
 * Created by Rihanna on 03/04/2015.
 */
public class BookDetailed extends Fragment {
     String idBook;
     Book selectedBook;

    public BookDetailed(){
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


