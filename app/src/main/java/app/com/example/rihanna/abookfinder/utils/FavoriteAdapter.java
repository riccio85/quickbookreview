package app.com.example.rihanna.abookfinder.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import app.com.example.rihanna.abookfinder.FavoriteListFragment;
import app.com.example.rihanna.abookfinder.R;

/**
 * Created by Rihanna on 20/04/2015.
 */
public class FavoriteAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    private int mPositionSelected;
    private Context context;


    public static class ViewHolder {
        public final ImageView bookCover;
        public final TextView title;
        public final TextView authors;
        public final TextView publisher;
        // public final TextView pages;

        public ViewHolder(View view) {
            bookCover = (ImageView) view.findViewById(R.id.bookCover);
            title= (TextView) view.findViewById(R.id.bookTitle);
            authors = (TextView) view.findViewById(R.id.authors);
            publisher = (TextView) view.findViewById(R.id.publisher);
            // pages = (TextView) view.findViewById(R.id.pages);
        }
    }

    public FavoriteAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext=context;
        mInflater=LayoutInflater.from(context);
    }

    public void setPositionSelected(int positionSelected) {
        mPositionSelected = positionSelected;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        layoutId = R.layout.list_item_favorite;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int viewType = getItemViewType(cursor.getPosition());
        String title=cursor.getString(FavoriteListFragment.COL_COLUMN_TITLE);
        String authors=cursor.getString(FavoriteListFragment.COL_COLUMN_AUTHORS);
        String publisher=cursor.getString(FavoriteListFragment.COL_COLUMN_PUBLISHER);
        viewHolder.title.setText(title);
        viewHolder.authors.setText(authors);
        viewHolder.publisher.setText(publisher);
        // viewHolder.pages.setText(publisher);
    }


}
