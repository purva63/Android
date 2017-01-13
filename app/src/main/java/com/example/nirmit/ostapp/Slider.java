package com.example.nirmit.ostapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Slider extends AppCompatActivity {
Fragment login=null;
Class FragmentClass;
TextView sg;
    int flag=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Intent out = getIntent();
        Firebase.setAndroidContext(this);
        sg=(TextView)findViewById(R.id.signup);
        FragmentClass=Login.class;
        try
        {
            login=(Fragment)FragmentClass.newInstance();
        }
        catch(Exception e){}
        Firebase.setAndroidContext(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout,login)
                .commit();
        sg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1)
                {
                    flag=0;
                    sg.setText("Already have an account!!");
                    FragmentClass=Signup.class;
                    try
                    {
                        login=(Fragment)FragmentClass.newInstance();
                    }
                    catch(Exception e){}
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout,login)
                            .commit();
                }
                else
                {
                    flag=1;
                    sg.setText("Dont have an account!!");
                    FragmentClass=Login.class;
                    try
                    {
                        login=(Fragment)FragmentClass.newInstance();
                    }
                    catch(Exception e){}
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout,login)
                            .commit();
                }
            }
        });
    }

}