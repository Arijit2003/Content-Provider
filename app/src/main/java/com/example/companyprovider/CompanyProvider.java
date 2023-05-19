package com.example.companyprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CompanyProvider extends ContentProvider {
    public CompanyProvider() {
    }
    SQLiteDatabase myDB;
    private static final String TABLE_NAME="EMPLOYEE";
    private static final String DB_NAME="COMPANY";
    private static final int DB_VERSION=1;
    private static final String ID="ID";
    public static final String EMP_NAME="EMP_NAME";
    public static final String POS="POSITION";

    public static final String AUTHORITY="com.example.companyprovider.provider";
    public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/"+TABLE_NAME);

    //uri matcher
    static int EMP=1;
    static int EMP_ID=2;
    static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,TABLE_NAME,EMP);
        uriMatcher.addURI(AUTHORITY,TABLE_NAME+"/#",EMP_ID);
    }
    private class MyDBHandler extends SQLiteOpenHelper{
        public MyDBHandler(Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table "+TABLE_NAME+
                    " ("+
                    ID+" integer primary key autoincrement," +
                    EMP_NAME+" TEXT,"+
                    POS+" TEXT"+
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row=myDB.insert(TABLE_NAME,null,values);
        if(row>0) {
            uri = ContentUris.withAppendedId(CONTENT_URI,row);
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        MyDBHandler myDBHandler = new MyDBHandler(getContext());
        myDB=myDBHandler.getWritableDatabase();
        if(myDB!=null) return true;
        else return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(TABLE_NAME);
        Cursor cursor=sqLiteQueryBuilder.query(myDB,null,null,null,null,null,ID);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}