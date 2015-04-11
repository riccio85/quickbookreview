package app.com.example.rihanna.abookfinder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Rihanna on 09/04/2015.
 */
public class BookDB {



    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create a table to hold locations.
        final String SQL_CREATE_TABLE= "CREATE TABLE " + BooksEntry.TABLE_NAME + " (" +
                BooksEntry.COLUMN_KEY + "integer primary key autoincrement ," +
                BooksEntry.COLUMN_IDBOOK + "TEXT UNIQUE NOT NULL, " +
                BooksEntry.COLUMN_TITLE + "TEXT, " +
                BooksEntry.COLUMN_AUTHORS + "TEXT, " +
                BooksEntry.COLUMN_OVERVIEW + "TEXT, " +
                BooksEntry.COLUMN_PUBLISHER + "TEXT, " +
                BooksEntry.COLUMN_ISBNS + "TEXT," +
                BooksEntry.COLUMN_PRICE + "TEXT, " +
                BooksEntry.COLUMN_PAGES + "TEXT, " +
             //   BooksEntry.COLUMN_RATING + "REAL," +
               // BooksEntry.COLUMN_IMAGE + "BLOB, " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(BookDbHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BooksEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public static final class BooksEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        // Column with the key the books id
        public static final String COLUMN_KEY = "_ID";
        public static String COLUMN_IDBOOK="idBook";
        public static String COLUMN_TITLE="title";
        public static String COLUMN_PUBLISHER="publisher";
        public static String COLUMN_AUTHORS="authors";
        public static String COLUMN_PAGES="pages";
        public static String COLUMN_IMAGE="image";
        public static String COLUMN_OVERVIEW="overview";
        public static String COLUMN_ISBNS="isbns";
        public static String COLUMN_PRICE="subjects";
      //  public static String COLUMN_RATING="rating";

    }


}
