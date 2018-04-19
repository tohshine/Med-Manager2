package com.example.znuta.med_manager;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.znuta.med_manager.data.MedContract;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.znuta.med_manager.data.MedContract.*;


public class EditorActivity extends AppCompatActivity {

    private TextView mmedname;
   private TextView mmeddesc;
   private TextView mmedinterval;
   private DatePicker mmedstdate;
  private   DatePicker mmededdate;
    private TimePicker mmedsttime;
    private Spinner mmedintervalsel;
    private SimpleDateFormat sdf;
    private Date date;
    private String dtime;
    private String entrymonth;
    private static final String LOG_TAG=EditorActivity.class.getSimpleName();
    private Uri currentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
         currentUri = intent.getData();


        mmedname = (TextView)findViewById(R.id.medname);
         mmeddesc = (TextView)findViewById(R.id.meddesc);
         mmedinterval = (TextView)findViewById(R.id.medinterval);
         mmedstdate = (DatePicker) findViewById(R.id.medstdate);
        mmedsttime = (TimePicker) findViewById(R.id.medsttime);
         mmededdate = (DatePicker) findViewById(R.id.mededdate);
         mmedintervalsel = (Spinner) findViewById(R.id.medintervalsel);
        Button send = (Button) findViewById(R.id.save);

        if (currentUri==null){
            setTitle("Add Medication");
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Insert();

                }
            });
        }else{
            setTitle("Edit Medication");
            DisplayMed();
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Update();

                }
            });
        }




    }


    private void Insert(){
        String medname = mmedname.getText().toString().trim();
        String meddesc = mmeddesc.getText().toString().trim();
        String interst = mmedinterval.getText().toString().trim();
        int medinterval = Integer.parseInt(interst);
        int std = mmedstdate.getDayOfMonth();
        int stm = mmedstdate.getMonth();
        int sty = mmedstdate.getYear();
      //  int stmm= mmedsttime.getMinute();
      //  int sth= mmedsttime.getHour();
      //  String medsttime = sth+" / "+stmm+" / 00";
        String medstdate = std+" / "+stm+" / "+sty;
        int edd = mmededdate.getDayOfMonth();
        int edm = mmededdate.getMonth();
        int edy = mmededdate.getYear();
        String mededdate = edd+" / "+edm+" / "+edy;
        entrymonth = getDTime();


        ContentValues values = new ContentValues();
        values.put(MedEntry.MEDNAME,medname);
        values.put(MedEntry.MEDDESC,meddesc);
        values.put(MedEntry.MEDINTERVAL,medinterval);
        values.put(MedEntry.MEDSTDATE,medstdate);
        values.put(MedEntry.MEDEDDATE,mededdate);
        values.put(MedEntry.ENTRYMONTH,entrymonth);
        values.put(MedEntry.NEXTMED,entrymonth);
        Log.e(LOG_TAG, "about to insert row"+medstdate+"--"+mededdate );
        Toast.makeText(EditorActivity.this, "Medication Added", Toast.LENGTH_SHORT).show();
        getContentResolver().insert(MedEntry.CONTENT_URI,values);
        finish();

    }
    public String getDTime(){
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = new Date();
        dtime = sdf.format(date);
        Log.e(LOG_TAG, "about to insert date" );
        return dtime;
    }

    public void DisplayMed(){
        String[] projection={
                MedEntry._ID,
                MedEntry.MEDNAME,
                MedEntry.MEDDESC,
                MedEntry.MEDSTDATE,
                MedEntry.MEDEDDATE,
                MedEntry.MEDINTERVAL,
                MedEntry.NEXTMED

        };
        Cursor cursor = getContentResolver().query(
                Uri.parse(String.valueOf(currentUri)),
                projection,
                null,
                null,
               null
        );
        Log.e(LOG_TAG, "Cursor send by "+ Uri.parse(String.valueOf(currentUri)) );
       /* ListView listView = (ListView) findViewById(R.id.medlist);
        MedAdapter medAdapter = new MedAdapter(this,cursor);
        listView.setAdapter(medAdapter); */
       try {


           int name = cursor.getColumnIndex(MedEntry.MEDNAME);
           int desc = cursor.getColumnIndex(MedEntry.MEDDESC);
           int st = cursor.getColumnIndex(MedEntry.MEDSTDATE);
           int ed = cursor.getColumnIndex(MedEntry.MEDEDDATE);
           int inter = cursor.getColumnIndex(MedEntry.MEDINTERVAL);
           int next = cursor.getColumnIndex(MedEntry.NEXTMED);
           Log.e(LOG_TAG, "columnid  send by "+ Uri.parse(String.valueOf(currentUri))+" name " +desc);
           cursor.moveToNext();
           String stringname = cursor.getString(name);
           String stringdesc = cursor.getString(desc);
           String stringst = cursor.getString(st);
           String stringed = cursor.getString(ed);
           String stringinter = cursor.getString(inter);
           String stringnext = cursor.getString(next);
           Log.e(LOG_TAG, "string send by "+ Uri.parse(String.valueOf(currentUri)) );

           mmedname.setText(stringname);
           mmeddesc.setText(stringdesc);
           //mmedstdate;
           //  mmeded.setText(stringed);
           mmedinterval.setText(stringinter);
           //mmednxt.setText(stringnext);
       }finally {
           cursor.close();
       }

    }

    private void Update(){
        String medname = mmedname.getText().toString().trim();
        String meddesc = mmeddesc.getText().toString().trim();
        String interst = mmedinterval.getText().toString().trim();
        int medinterval = Integer.parseInt(interst);
        int std = mmedstdate.getDayOfMonth();
        int stm = mmedstdate.getMonth();
        int sty = mmedstdate.getYear();
       // int stmm= mmedsttime.getMinute();
       // int sth= mmedsttime.getHour();
     //   String medsttime = sth+" / "+stmm+" / 00";
        String medstdate = std+" / "+stm+" / "+sty;


        int edd = mmededdate.getDayOfMonth();
        int edm = mmededdate.getMonth();
        int edy = mmededdate.getYear();
        String mededdate = edd+" / "+edm+" / "+edy;
        entrymonth = getDTime();


        ContentValues values = new ContentValues();
        values.put(MedEntry.MEDNAME,medname);
        values.put(MedEntry.MEDDESC,meddesc);
        values.put(MedEntry.MEDINTERVAL,medinterval);
        values.put(MedEntry.MEDSTDATE,medstdate);
        values.put(MedEntry.MEDEDDATE,mededdate);
        values.put(MedEntry.ENTRYMONTH,entrymonth);
        values.put(MedEntry.NEXTMED,entrymonth);
        Log.e(LOG_TAG, "about to insert row"+medstdate+"--"+mededdate );
int row = getContentResolver().update(currentUri,values,null,null);

    }



}
