package app.com.example.rihanna.abookfinder.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Rihanna on 09/04/2015.
 */

/*
public class BooksContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BookDbHelper mOpenHelper;

    private static final int BOOK = 100;
    private static final int BOOK_WITH_TITLE= 101;

    private static final SQLiteQueryBuilder BooksQueryBuilder;

    static{
        BooksQueryBuilder = new SQLiteQueryBuilder();
        BooksQueryBuilder.setTables(
                BookContract.BookEntry.TABLE_NAME);
    }


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BookContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, BookContract.PATH_BOOK ,BOOK);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BookDbHelper(getContext());
        return true;
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
                //return BookContract.BookEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        long _id = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);
        if ( _id > 0 ){
            Uri _uri = ContentUris.withAppendedId(uri, _id);
             getContext().getContentResolver().notifyChange(_uri, null);
         return _uri;
        }
        throw new android.database.SQLException("Failed to insert row into " + uri);
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


}*/





