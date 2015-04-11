package app.com.example.rihanna.abookfinder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import app.com.example.rihanna.abookfinder.Book;

/**
 * Created by Rihanna on 10/04/2015.
 */
public class DbTest extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "books";
    // Column with the key the books id
    public static final String COLUMN_KEY = "_ID";
    public static final String COLUMN_IDBOOK="idBook";
    public static final String COLUMN_TITLE="title";
    public static final  String COLUMN_PUBLISHER="publisher";
    public static final String COLUMN_AUTHORS="authors";
    public static final String COLUMN_PAGES="pages";
    public static final String COLUMN_IMAGE="image";
    public static final String COLUMN_OVERVIEW="overview";
    public static final String COLUMN_ISBNS="isbns";
    public static final String COLUMN_PRICE="subjects";


    public DbTest(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create a table to hold locations.
        final String SQL_CREATE_TABLE= "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_KEY + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE,"
                + COLUMN_IDBOOK + " TEXT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_AUTHORS + " TEXT,"
                + COLUMN_OVERVIEW + " TEXT,"
                + COLUMN_PUBLISHER + " TEXT,"
                + COLUMN_ISBNS + " TEXT,"
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_PAGES + " TEXT" +
                // BooksEntry.COLUMN_IMAGE + "BLOB, " +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(BookDbHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insertHardCode(ContentValues values){
        SQLiteDatabase database=this.getWritableDatabase();
        database.insertOrThrow(BookDB.BooksEntry.TABLE_NAME,null,values);
        database.close();
        return true;
    }
    public ArrayList<Book> getSavedBookList(){
        String selectQuery= "SELECT * FROM "+ BookDB.BooksEntry.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        ArrayList<Book> data=new ArrayList<Book>();
        if(cursor.moveToFirst()){
            do{
                String bookid= cursor.getString(cursor.getColumnIndex(COLUMN_IDBOOK));
                String title=cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String authors=cursor.getString(cursor.getColumnIndex(COLUMN_AUTHORS));
                String overview=cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW));
                String publish=cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER));
                String isbns=cursor.getString(cursor.getColumnIndex(COLUMN_ISBNS));
                String price=cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String pages=cursor.getString(cursor.getColumnIndex(COLUMN_PAGES));
                //get the data into array
                Book bo=new Book(bookid,title,authors,overview,publish,isbns,price,pages,0,null);
                data.add(bo);
            }while(cursor.moveToNext());
        }

        return data;
    }



    public boolean deleteHardCode(Book book) {
        SQLiteDatabase db = this.getReadableDatabase();
        String idBook = book.getId();
        db.delete(TABLE_NAME, COLUMN_IDBOOK  + "= '" + idBook + "'", null);
        return true;
    }
   /*     String idDb;
        String sQuery= "SELECT "+  COLUMN_IDBOOK + " FROM "+ TABLE_NAME;
        Cursor cursor= db.rawQuery(sQuery, null);
        if(cursor.moveToFirst()){
            do{
                idDb=cursor.getString(cursor.getColumnIndex(COLUMN_IDBOOK));
                if(idDb.equals(idBook)){
                    try {
                        db.delete(TABLE_NAME, COLUMN_IDBOOK + "=" + idBook, null);
                        cursor.close();
                        db.close();
                        return true;
                    }catch(Exception e){}
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return false;

       // SQLiteDatabase database=this.getWritableDatabase();
      //  database.insertOrThrow(BookDB.BooksEntry.TABLE_NAME,null,values);
      //  database.close();

    }-*/


}





