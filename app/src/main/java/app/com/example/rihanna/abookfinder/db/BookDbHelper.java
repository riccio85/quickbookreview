package app.com.example.rihanna.abookfinder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 2;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE= "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " ( "
                + BookContract.BookEntry.COLUMN_KEY + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE,"
                + BookContract.BookEntry.COLUMN_IDBOOK + " TEXT,"
                + BookContract.BookEntry.COLUMN_TITLE + " TEXT,"
                + BookContract.BookEntry.COLUMN_AUTHORS + " TEXT,"
                + BookContract.BookEntry.COLUMN_OVERVIEW + " TEXT,"
                + BookContract.BookEntry.COLUMN_PUBLISHER + " TEXT,"
                + BookContract.BookEntry.COLUMN_ISBNS + " TEXT,"
                + BookContract.BookEntry.COLUMN_PRICE + " TEXT,"
                + BookContract.BookEntry.COLUMN_PAGES + " TEXT,"
                + BookContract.BookEntry.COLUMN_BUY + " TEXT, "
                + BookContract.BookEntry.COLUMN_SMALLIM + " TEXT, "
                + BookContract.BookEntry.COLUMN_BIGIM + " TEXT "+
                ");";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(BookDbHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}

