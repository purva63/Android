package com.example.nirmit.ostapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class page2 extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView address, contact, name, email, occupation,schedule;
    String em,oc;
    int flag=0;
    FirebaseDatabase database;
    Button next,upload,logout;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        Intent out = getIntent();
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        contact = (TextView) findViewById(R.id.contact);
        occupation = (TextView) findViewById(R.id.occupation);
        schedule = (TextView) findViewById(R.id.schedule);
        address = (TextView) findViewById(R.id.address);
        next=(Button)findViewById(R.id.next);
        upload=(Button)findViewById(R.id.upload);
        logout=(Button)findViewById(R.id.logout);
        address.setVisibility(View.GONE);
        schedule.setVisibility(View.GONE);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        Log.d("hello",mAuth.toString());
        em = mAuth.getCurrentUser().getEmail();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(page2.this,Slider.class));
                }
            }
        };
        for (int i = 0; i < em.length(); i++)
            em = em.replace(".", "");
        em = em.substring(0, em.indexOf("@"));
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference Root = myRef.child(em);
        //Query query = myRef.child(em);

        Root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key=dataSnapshot.getKey();
                String value=dataSnapshot.getValue().toString();
                Log.d("bye",key);
                Log.d("bye",value);
                if(key.equals("name"))
                    name.setText(value);
                if(key.equals("email"))
                    email.setText(value);
                if(key.equals("contact"))
                    contact.setText(value);
                if(key.equals("occupation")) {
                    if(value.equals("Artist"))
                        flag=0;
                    else
                    flag=1;
                    oc=value;
                    occupation.setText(value);
                }
                if(key.equals("address")) {
                    address.setVisibility(View.VISIBLE);
                    address.setText(value);
                    upload.setText("Upload Schedule");

                }
                    if(key.equals("schedule")) {
                        schedule.setVisibility(View.VISIBLE);
                        schedule.setText(value);

                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bu",flag+"");
                if(flag==0)
                {
                    Intent in=new Intent("recorder");
                    startActivity(in);
                    Log.d("nu","yeah");
                }
            }
        });
    }
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}