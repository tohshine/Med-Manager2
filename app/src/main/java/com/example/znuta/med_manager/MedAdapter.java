package com.example.znuta.med_manager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.znuta.med_manager.data.MedContract;

import static com.example.znuta.med_manager.data.MedContract.*;

/**
 * Created by znuta on 4/11/2018.
 */

public class MedAdapter extends CursorAdapter {
    public MedAdapter(Context context, Cursor c) {
        super(context, c, 0 /*flags*/);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.med_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView mmedname = (TextView) view.findViewById(R.id.medname);
        TextView mmeddesc = (TextView) view.findViewById(R.id.meddesc);
        TextView mmedst = (TextView) view.findViewById(R.id.st);
        TextView mmeded = (TextView) view.findViewById(R.id.ed);
        TextView mmedinterval = (TextView) view.findViewById(R.id.interv);
        TextView mmednxt = (TextView) view.findViewById(R.id.nxt);

        int name = cursor.getColumnIndex(MedEntry.MEDNAME);
        int desc = cursor.getColumnIndex(MedEntry.MEDDESC);
        int st = cursor.getColumnIndex(MedEntry.MEDSTDATE);
        int ed = cursor.getColumnIndex(MedEntry.MEDEDDATE);
        int inter = cursor.getColumnIndex(MedEntry.MEDINTERVAL);
        int next = cursor.getColumnIndex(MedEntry.NEXTMED);

        String stringname = cursor.getString(name);
        String stringdesc = cursor.getString(desc);
        String stringst = cursor.getString(st);
        String stringed = cursor.getString(ed);
        String stringinter = cursor.getString(inter);
        String stringnext = cursor.getString(next);

        mmedname.setText(stringname);
        mmeddesc.setText(stringdesc);
        mmedst.setText(stringst);
        mmeded.setText(stringed);
        mmedinterval.setText(stringinter);
        mmednxt.setText(stringnext);



    }
}
