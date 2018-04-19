package com.example.znuta.med_manager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.znuta.med_manager.data.MedContract.*;

/**
 * Created by znuta on 4/9/2018.
 */

public class MedDbHelper extends SQLiteOpenHelper {
private static final String DATBASE_NAME = "MedManager";
    private static final int VERSION = 1;
    public MedDbHelper(Context context) {
        super(context, DATBASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table = " CREATE TABLE "+ MedEntry.MEDTABLE+" ( "
                +MedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +MedEntry.MEDNAME + " TEXT NOT NULL, "
                +MedEntry.MEDDESC + " TEXT NOT NULL, "
                +MedEntry.MEDINTERVAL + " TEXT NOT NULL, "
                +MedEntry.MEDSTDATE + " DATE NOT NULL, "
                +MedEntry.MEDEDDATE + " DATE NOT NULL, "
                +MedEntry.ENTRYMONTH + " DATE NOT NULL, "
                +MedEntry.NEXTMED + " TEXT NOT NULL);";
        db.execSQL(Create_Table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
