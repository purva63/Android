package com.example.nirmit.ostapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class Record extends AppCompatActivity {
    Button record,back;
    EditText name;
    TextView rec;
    MediaRecorder mRecorder;
    String mFileName="",em,LOG_TAG="Record_log";
    FirebaseAuth mAuth;
    StorageReference mStorage;
    ProgressDialog PDialog;
    File audio;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Intent out=getIntent();
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        mStorage= FirebaseStorage.getInstance().getReference();
        em = mAuth.getCurrentUser().getEmail();
        for (int i = 0; i < em.length(); i++)
            em = em.replace(".", "");
        em = em.substring(0, em.indexOf("@"));
        PDialog=new ProgressDialog(this);
        record=(Button)findViewById(R.id.record);
        back=(Button)findViewById(R.id.back);
        rec=(TextView)findViewById(R.id.rec);
        name=(EditText)findViewById(R.id.name);
        flag=0;
        audio = new File(Environment.getExternalStorageDirectory(),
                "/"+name+em+".3gp");
        mFileName=Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName+="/"+name+em+".3gp";
        record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
                    Log.d("its","down");
                    startRecording();
                    Log.d("its","**start");
                    rec.setText("Recording!!");

                    if(mRecorder!=null)
                        Log.d("its","not null");
                }
                else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                        Log.d("its","up");
                    if (mRecorder!=null) {
                        Log.d("its", "not null");
                        if (flag == 1) {
                            mRecorder.stop();
                            mRecorder.reset();
                            Log.d("its ", "**stop");
                            mRecorder.release();
                            Log.d("its", "**release");
                            uploadAudio();
                            startRecording();
                        }
                    }
                    rec.setText("TAP AND HOLD TO RECORD");
                }

                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent("page2");
                startActivity(in);
            }
        });
    }
    private void startRecording() {
        if(mRecorder==null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile(audio.getAbsolutePath());
            if (flag == 0) {
                try {
                    mRecorder.prepare();
                    Log.d("its", "**prepare");
                    mRecorder.start();
                    flag=1;
                } catch (IOException e) {
                    Log.d("buuuuu", "start");
                }
            }
        }
    }

    private void stopRecording() {



    }
    public void uploadAudio(){
        PDialog.setMessage("Uploading audio!!");
        PDialog.show();
        //StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(em);
        StorageReference filepath=mStorage.child(em).child(mFileName);
        Uri uri= Uri.fromFile(new File(mFileName));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                PDialog.dismiss();
                Toast.makeText(Record.this,"Uploaded",Toast.LENGTH_LONG).show();
            }
        });
    }
}

