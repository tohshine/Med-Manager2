package com.example.znuta.med_manager;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.znuta.med_manager.data.MedContract;
import com.example.znuta.med_manager.data.MedContract.MedEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

public class MedicalManager extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MED_LOADER=0;
    private MedAdapter medAdapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);

        // updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_manager);
        mAuth=FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MedicalManager.this, MainActivity.class));
                }

            }
        };
        ListView listView = (ListView) findViewById(R.id.medlist);
        medAdapter = new MedAdapter(this,null);
        listView.setAdapter(medAdapter);
        getLoaderManager().initLoader(MED_LOADER,null,this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MedicalManager.this, EditorActivity.class);
                Uri currenturi = ContentUris.withAppendedId(MedEntry.CONTENT_URI,id);
                intent.setData(currenturi);
                startActivity(intent);
            }
        });

        FloatingActionButton alpha = (FloatingActionButton) findViewById(R.id.send);
        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alphaint = new Intent(MedicalManager.this, EditorActivity.class);
                startActivity(alphaint);
            }
        });
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
                MedEntry.CONTENT_URI,
                projection,
                null,
                null,
                MedEntry.ENTRYMONTH+" DESC"
        );
       /* ListView listView = (ListView) findViewById(R.id.medlist);
        MedAdapter medAdapter = new MedAdapter(this,cursor);
        listView.setAdapter(medAdapter); */

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection={
                MedEntry._ID,
                MedEntry.MEDNAME,
                MedEntry.MEDDESC,
                MedEntry.MEDSTDATE,
                MedEntry.MEDEDDATE,
                MedEntry.MEDINTERVAL,
                MedEntry.NEXTMED

        };
        return new CursorLoader(this,
                MedEntry.CONTENT_URI,
                projection,
                null,
                null,
                MedEntry.ENTRYMONTH+" DESC"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        medAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        medAdapter.swapCursor(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_logout,menu);
        getMenuInflater().inflate(R.menu.action_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int getItem = item.getItemId();
        if (getItem==R.id.logout){
            mAuth.signOut();
        }
        if (getItem==R.id.account){
            startActivity(new Intent(MedicalManager.this,AcountActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
