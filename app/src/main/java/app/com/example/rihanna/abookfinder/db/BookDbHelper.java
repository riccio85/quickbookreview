package app.com.example.rihanna.abookfinder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rihanna on 09/04/2015.
 */
public class BookDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BookDB.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BookDB.onUpgrade(db, oldVersion, newVersion);
    }


}

