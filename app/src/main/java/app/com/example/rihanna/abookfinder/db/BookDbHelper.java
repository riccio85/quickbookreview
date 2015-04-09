package app.com.example.rihanna.abookfinder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Rihanna on 09/04/2015.
 */
public class BookDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "weather.db";

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create a table to hold locations.
        final String SQL_CREATE_TABLE= "CREATE TABLE " + BooksEntry.TABLE_NAME + " (" +
                BooksEntry.COLUMN_KEY + "TEXT UNIQUE NOT NULL, " +
                BooksEntry.COLUMN_TITLE + "TEXT, " +
                BooksEntry.COLUMN_PUBLISHER + "TEXT, " +
                BooksEntry.COLUMN_AUTHORS + "TEXT, " +
                BooksEntry.COLUMN_PAGES + "TEXT, " +
                BooksEntry.COLUMN_SMALLIMAGE + "TEXT, " +
                BooksEntry.COLUMN_BIGIMAGE + "BLOB, " +
                BooksEntry.COLUMN_SUBTITLE + "BLOB," +
                BooksEntry.COLUMN_OVERVIEW + "TEXT, " +
                BooksEntry.COLUMN_ISBNS + "TEXT," +
                BooksEntry.COLUMN_SUNJECTS + "TEXT," +
                BooksEntry.COLUMN_RATING + "REAL," +
                BooksEntry.COLUMN_SERIES + "TEXT," +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    public static final class BooksEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        // Column with the key the books id
        public static final String COLUMN_KEY = "id";
        public static String COLUMN_TITLE="title";
        public static String COLUMN_PUBLISHER="publisher";
        public static String COLUMN_AUTHORS="authors";
        public static String COLUMN_PAGES="pages";
        public static String COLUMN_SMALLIMAGE="smallIM";
        public static String COLUMN_BIGIMAGE="bigIM";
        public static String COLUMN_SUBTITLE="subtitle";
        public static String COLUMN_OVERVIEW="overview";
        public static String COLUMN_ISBNS="isbns";
        public static String COLUMN_SUNJECTS="subjects";
        public static String COLUMN_RATING="rating";
        public static String COLUMN_SERIES="series";
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        onCreate(sqLiteDatabase);
    }

}
