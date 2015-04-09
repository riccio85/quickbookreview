package app.com.example.rihanna.abookfinder.utils;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.example.rihanna.abookfinder.Book;
import app.com.example.rihanna.abookfinder.R;

public class BookListViewAdapter extends ArrayAdapter<Book> {
	 Context context;
	
	public BookListViewAdapter (Context context, int resourceId,List<Book> items) {
        super(context, resourceId, items);
        this.context = context;
    }
	
	 private class ViewHolder {
	        ImageView imageView;
	        TextView title;
	        TextView authors;
	        TextView publisher;
	        TextView pages;
	    }
	 
	 public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder = null;
	        Book bookItem = getItem(position);
	         
	        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.search_list_item, null);
	            holder = new ViewHolder();
	            holder.imageView = (ImageView) convertView.findViewById(R.id.bookCover);
	            holder.title = (TextView) convertView.findViewById(R.id.bookTitle);
	            holder.authors = (TextView) convertView.findViewById(R.id.authors);
	            holder.publisher = (TextView) convertView.findViewById(R.id.publisher);
	            holder.pages = (TextView) convertView.findViewById(R.id.pages);
	            convertView.setTag(holder);
	        } else
	            holder = (ViewHolder) convertView.getTag();
	                 
	        holder.title.setText(bookItem.getTitle());
	        holder.authors.setText(bookItem.getAuthors());
	        holder.publisher.setText(bookItem.getPubisher());
	        holder.pages.setText(bookItem.getPages());
            Bitmap bitmap=bookItem.getImageLink();
            Drawable d = new BitmapDrawable(context.getResources(),bitmap);
	        holder.imageView.setImageDrawable(d);
	         
	        return convertView;
	    } 
	
	
}
