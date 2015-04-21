package app.com.example.rihanna.abookfinder.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.Book;

public class BookJsonParse {

    public static ArrayList<Book> bookList;

    public static int totalItems(String jsonStr)throws JSONException {
        JSONObject booksJson = (JSONObject) new JSONObject(jsonStr);
        int items = booksJson.getInt("totalItems");
        return items;
    }
	    public static ArrayList<Book> getBooksDataFromJson(String jsonStr)
                throws JSONException {
            ArrayList<Book> searchRlt = new ArrayList<Book>();
//These are the names of the JSON objects that need to be extracted.
            final String KIND = "books#volumes";
            final String TOTAL_COUNT = "totalItems";
            final String MAIN_TABLE = "items";
            final String VOLUME_INFO = "volumeInfo";
            final String ID = "id";
            final String TITLE = "title";
            final String AUTHORS = "authors";
            final String PUBLISHER = "publisher";
            final String PUBLISH_DATE = "publishedDate";
            final String DESCRIPTION = "description";
            final String IMAGE_LINK ="imageLinks";
            final String SMALL_IMAGE="smallThumbnail";
            final String BIG_IMAGE="thumbnail";
            final String PAGES = "pageCount";
            final String SALE_INFO="saleInfo";
            final String SALEABILITY="saleability";
            final String LIST_PRICE="listPrice";
            final String AMOUNT="amount";
            final String CURRENCY="currencyCode";
            final String INDUSTRY_ID="industryIdentifiers";
            final String ISBN_TYPE="type";
            final String ISBN_ID="identifier";
            final String RATING= "averageRating";
            final String BUY_LINK="buyLink";
            final String PREV_LINK="previewLink";

            if (isJSONValid(jsonStr)) {
                JSONObject booksJson = (JSONObject) new JSONObject(jsonStr);
                JSONArray booksArray = booksJson.getJSONArray(MAIN_TABLE);
                ArrayList<Book> bookL = new ArrayList<Book>();
                for(int i = 0; i < booksArray.length(); i++) {
                    JSONObject bookInfo = booksArray.getJSONObject(i);
                    String id_b = bookInfo.getString(ID);
                    JSONObject volInfo = bookInfo.getJSONObject(VOLUME_INFO);
                    String title_b = volInfo.getString(TITLE);
                    String authours_b="";
                       try{
                           JSONArray auth = volInfo.getJSONArray(AUTHORS);
                           authours_b=auth.toString();
                       }catch(Exception e){}

               String publisher_b="";
                                try{
                                  if(volInfo.getString(PUBLISHER)!=null){
                                   publisher_b=volInfo.getString(PUBLISHER);} else {publisher_b="---"; }
                                }catch(Exception e){}
               String publishDate="";
                                try{
                                    if(volInfo.getString(PUBLISH_DATE)!=null){
                                        publisher_b=volInfo.getString(PUBLISH_DATE);} else {publisher_b="---"; }
                                }catch(Exception e){}
               String publish=publisher_b+publishDate;

               String pages_b="";
                               try{
                                   int pages=volInfo.getInt(PAGES);
                                   pages_b=pages+" pages";
                               }catch(Exception e){ }
               float rates=0;
                                try{
                                    rates=volInfo.getLong(RATING);
                                }catch(Exception e){}
               String description_b="";
                        try{
                           description_b=volInfo.getString(DESCRIPTION);
                        }catch(Exception e){}
               JSONObject image=volInfo.getJSONObject(IMAGE_LINK);
               String previewLink="";
                    try{
                        previewLink=volInfo.getString(PREV_LINK);
                    }catch(Exception e){}
               String smallIM="";
               String bigIM="";
                       Bitmap bitmap=null;
                       try{
                           smallIM=image.getString(SMALL_IMAGE);
                           bigIM=image.getString(BIG_IMAGE);
                           bitmap=getBitmapFromURL(smallIM);
                       }catch(Exception e){}
               String isbn="";
                     try{
                         JSONArray indsID=volInfo.getJSONArray(INDUSTRY_ID);
                         for(int j=0; j<indsID.length();j++){
                         JSONObject indOb=indsID.getJSONObject(i);
                                String isbnIdt=indOb.getString(ISBN_ID);
                                isbn=isbnIdt+"  "+isbn;
                         }
                     }catch(Exception e){}
               String price="";
 /*Google api doesn't work for buy link"
               String buylink="";
                   try{
                       JSONObject saleInfo=bookInfo.getJSONObject(SALE_INFO);
                       String forSale=saleInfo.getString(SALEABILITY);
                       if(forSale.equals("FOR_SALE")){
                           try{
                               JSONObject listPrice=saleInfo.getJSONObject(LIST_PRICE);
                               String lprice=listPrice.getString(AMOUNT);
                               String curr=listPrice.getString(CURRENCY);
                               price=lprice+curr;
                               buylink=saleInfo.getString(BUY_LINK);
                           }catch(Exception e){}
                       }else{
                       price="NOT_FOR_SALE";}
                    }catch(Exception e){}*/
 /* Create the book and add it to the arraylist*/
                 bookL.add(new Book(id_b,title_b,authours_b,description_b,publish,
                                isbn,price,pages_b,rates,previewLink, bitmap,smallIM,bigIM));
              }
                bookList=bookL;
                return bookL;
             } else {
/* If it is not possible to parse the json turns an arraylist with size 0 */
            ArrayList<Book> bookL = new ArrayList<Book>();
            bookList=bookL;
            return bookL;
            }
    }//end method parse

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
/*method to check if it is a json string */
        public static boolean isJSONValid(String test) {
            try {
                new JSONObject(test);
                return true;
            } catch (JSONException ex) {
                try {
                    new JSONArray(test);
                } catch (JSONException ex1) {
                    return false;
                }
            }
            return true;
        }
        public static ArrayList<Book> getArrayListBook(){ return bookList;}
	}// end of the class

