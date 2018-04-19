package com.example.znuta.med_manager.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import static com.example.znuta.med_manager.data.MedContract.*;


/**
 * Created by znuta on 4/9/2018.
 */

public class MedProvider extends ContentProvider{

    public static  final String LOG_TAG = MedProvider.class.getSimpleName();
    private MedDbHelper medDbHelper;
    private static final int MEDS = 101;
    private  static final int MED = 102;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, MedEntry.MEDTABLE, MEDS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, MedEntry.MEDTABLE+"/#", MED);

    }
    @Override
    public boolean onCreate() {
        medDbHelper = new MedDbHelper(getContext());
        return false;
    }




    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = medDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match){
            case MEDS:
                cursor = db.query(MedEntry.MEDTABLE,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MED:
                selection = MedEntry._ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MedEntry.MEDTABLE,projection,selection,selectionArgs,null,null,sortOrder);
                Log.e(LOG_TAG, "Cursor collected by "+uri );
                break;
            default:
                throw new IllegalArgumentException("Can not query UNKNOWN URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        Log.e(LOG_TAG, "Cursor returned by "+uri );
        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {


        int match = sUriMatcher.match(uri);
        switch (match){
            case MEDS:
                Log.i(LOG_TAG, "urlmatch" );
                return insertMed(uri,values);


            default:
                throw new IllegalArgumentException("Can not query UNKNOWN URI" + uri);

        }

    }

    private Uri insertMed(Uri uri, ContentValues values) {
        SQLiteDatabase db = medDbHelper.getWritableDatabase();
        long rowID = db.insert(MedEntry.MEDTABLE,null,values);
        if ( rowID== -1){
            Log.e(LOG_TAG, "Fail to insert row"+ uri );
        }
        Log.e(LOG_TAG, "INSerted" );

        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,rowID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = medDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowID=0;
        switch (match){
            case MED:
                selection = MedEntry._ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
               rowID = db.update(MedEntry.MEDTABLE,values,selection,selectionArgs);
                if ( rowID== -1){
                    Log.e(LOG_TAG, "Fail to insert row"+ uri );
                }
                Log.e(LOG_TAG, "INSerted" );

                getContext().getContentResolver().notifyChange(uri,null);


                Log.e(LOG_TAG, "Cursor collected by "+uri );
                break;
            default:
                throw new IllegalArgumentException("Can not query UNKNOWN URI" + uri);
        }
        return rowID;
    }
}
