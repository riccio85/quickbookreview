package app.com.example.rihanna.abookfinder.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Rihanna on 17/04/2015.
 */
public class BookContract {

    public static final String CONTENT_AUTHORITY = "app.com.example.rihanna.abookfinder.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOK = "books";

    public static final class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        // Column with the key the books id
        public static final String COLUMN_KEY = "_ID";
        public static String COLUMN_IDBOOK="idBook";
        public static String COLUMN_TITLE="title";
        public static String COLUMN_PUBLISHER="publisher";
        public static String COLUMN_AUTHORS="authors";
        public static String COLUMN_PAGES="pages";
        public static String COLUMN_OVERVIEW="overview";
        public static String COLUMN_ISBNS="isbns";
        public static String COLUMN_BUY="buyLink";
        public static String COLUMN_SMIMAGE="smallThumbnail";
        public static String COLUMN_BIGIMAGE="thumbnail";



    }






}
