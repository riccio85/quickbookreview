package app.com.example.rihanna.abookfinder.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class BookContract {

    public static final String CONTENT_AUTHORITY = "app.com.example.rihanna.abookfinder.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOK = "book";

    public static final class BookEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOK).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOK;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOK;

        public static final String TABLE_NAME = "books";
        // Column with auto generated the key the books id
        public static final String COLUMN_KEY = "_id";
        public static final String COLUMN_IDBOOK="idBook";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_PUBLISHER="publisher";
        public static final String COLUMN_AUTHORS="authors";
        public static final String COLUMN_PAGES="pages";
        public static final String COLUMN_OVERVIEW="overview";
        public static final String COLUMN_ISBNS="isbns";
        public static final String COLUMN_PRICE="price";
        public static final String COLUMN_BUY="buyLink";
        public static final String COLUMN_SMALLIM="smallImage";
        public static final String COLUMN_BIGIM="bigImage";

        public static Uri buildBooksUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static String getBookFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildBookWithId(String bookId) {
            return CONTENT_URI.buildUpon().appendPath(bookId).build();
        }
    }

}
