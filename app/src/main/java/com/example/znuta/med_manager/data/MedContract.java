package com.example.znuta.med_manager.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by znuta on 4/9/2018.
 */

public final class MedContract {
    public static final String CONTENT_AUTHORITY = "com.example.znuta.med_manager";
    public static final String CONTENT_BASEURI = "content://com.example.znuta.med_manager";
    public static final String URI_PATH = "medmanager";
    private MedContract(){}


    public static final class MedEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse(CONTENT_BASEURI),URI_PATH);
        public static final String MEDTABLE = "medmanager";
        public static final String _ID = BaseColumns._ID;
        public static final String MEDNAME = "medname";
        public static final String MEDDESC = "meddesc";
        public static final String MEDINTERVAL= "medinterval";
        public static final String MEDSTDATE = "medstdate";
        public static final String MEDEDDATE = "mededdate";
        public static final String MEDMONTH = "medmonth";
        public static final String ENTRYMONTH = "entrymonth";
        public static final String NEXTMED = "nextmed";

        public static final String SEC = "000";
        public static final String MIN = "0000";
        public static final String HR = "00000";
        public static final String DAY = "000000";
        public static final String WEEK= "0000000";
        public static final String MONTH = "00000000";



    }
}
