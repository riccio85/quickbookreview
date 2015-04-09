package app.com.example.rihanna.abookfinder;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
// qui manca definire prendere immagine e memorizzarlo sia piccolo che grande e caricarlo dinamicamente
public class Book implements Parcelable {
   public boolean isFullBook;

    Book book; ////questione per parcelable

    private String id;
    private String title;
    private  String publisher;
    private String author;
    private String pages;
    private String overview;
    private String isbns;
    private String price;
    private float rating;
    private Bitmap imageId;

    private ArrayList<Book> bookList;

    public Book(String id,String title,String author,String descrp,String publish,String isbn,String price,String pages,float rating,Bitmap image){
        //(id,rating,title,subtitle,author,series,descrp,publish,sub,pages,image)
        this.id=id;
        this.title=title;
        this.author=author;
        this.overview=descrp;
        this.publisher=publish;
        this.isbns=isbn;
        this.price=price;
        this.pages=pages;
        this.rating=rating;
        this.imageId=image;
    }

    public Book(Book bookToParcel) {
        this.book = bookToParcel;
    }

    public Bitmap getImageLink() {
        return imageId;
    }
    public void setImageLink(Bitmap imageId) {
        this.imageId = imageId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthors() {
        return author;
    }
    public void setAuthors(String authors) {
        this.author=authors;
    }
    public String getPubisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher=publisher;
    }
    public String getPages() {
        return pages;
    }
    public void setPages(String pages) {
        this.pages=pages;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id=id;
    }
    public float getRating() {
        return rating;
    }
    public String getOverview() {
        return overview;
    }
    public String getIsbns() {
        return isbns;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public void setIsbns(String isbns) {
        this.isbns = isbns;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(book);
    }
    // Method to recreate from a Parcel
    public static Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }
        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel in) {
      book = (Book) in.readValue(null);
    }


}
