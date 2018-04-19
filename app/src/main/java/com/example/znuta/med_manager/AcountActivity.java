package com.example.znuta.med_manager;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AcountActivity extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private TextView name;
    private TextView email;
    private TextView number;
    private Button acsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);

        name = (TextView)findViewById(R.id.name);
        number = (TextView)findViewById(R.id.number);
        email = (TextView)findViewById(R.id.email);
        acsave = (Button) findViewById(R.id.acountsave);


        mAuth=FirebaseAuth.getInstance();
         user = mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String sname = user.getDisplayName();
            String semail = user.getEmail();

            Uri sphotoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String suid = user.getUid();
            name.setText(sname);
            email.setText(semail);
        }
    }
}
