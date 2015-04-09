package app.com.example.rihanna.abookfinder.db;

import android.graphics.Bitmap;
import android.provider.BaseColumns;

/**
 * Created by Rihanna on 09/04/2015.
 */
public class BooksContract {
    /* Inner class that defines the table contents of the weather table */
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
}
