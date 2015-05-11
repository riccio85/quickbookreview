package app.com.example.rihanna.abookfinder.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class BooksContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BookDbHelper mOpenHelper;
    private static final int BOOK = 100;
    private static final int BOOK_WITH_ID= 101;
    private static final SQLiteQueryBuilder booksQueryBuilder;

    static{
        booksQueryBuilder = new SQLiteQueryBuilder();
        booksQueryBuilder.setTables(BookContract.BookEntry.TABLE_NAME);
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BookContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, BookContract.PATH_BOOK ,BOOK);
        matcher.addURI(authority, BookContract.PATH_BOOK + "/*", BOOK_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BookDbHelper(getContext());
        return true;
    }
   private static final String selection =
            BookContract.BookEntry.TABLE_NAME +"." + BookContract.BookEntry.COLUMN_IDBOOK + " = ? ";

  private Cursor getBookFromUri(Uri uri, String[] projection, String sortOrder) {
      String id = BookContract.BookEntry.getBookFromUri(uri);
      Cursor c = booksQueryBuilder.query(mOpenHelper.getReadableDatabase(),
              projection,
              selection,
              new String[]{id},
              null,
              null,
              sortOrder
      );
      return  c;
  }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case BOOK: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BookContract.BookEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case BOOK_WITH_ID: {
                retCursor = getBookFromUri(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case BOOK:
                return BookContract.BookEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case BOOK: {
                long _id = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = BookContract.BookEntry.buildBooksUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);}
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

  @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
           case BOOK:
                rowsDeleted = db.delete(
                        BookContract.BookEntry.TABLE_NAME, selection, selectionArgs);
               break;
            case BOOK_WITH_ID:
                rowsDeleted = db.delete(
                        BookContract.BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case BOOK:
                rowsUpdated = db.update(BookContract.BookEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}





