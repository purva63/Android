package com.example.nirmit.ostapp;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private GestureDetectorCompat mDetector;
    EditText t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        // Set the gesture detector as the double tap
        // listener.
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Start();
            return true;
        }
        public void Start()
        {
            Toast.makeText(MainActivity.this,
                    "Swipe left -App started",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(
                    MainActivity.this,Slider.class);
            //MainActivity.this, Slider.class);
            //as of now late slider for google sign in
            startActivity(intent);
        }
    }
}