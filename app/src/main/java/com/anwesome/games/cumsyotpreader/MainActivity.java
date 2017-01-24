package com.anwesome.games.cumsyotpreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,OtpReaderService.class));
        //addContentView(new OtpView(this,"Hello",(WindowManager)getSystemService(WINDOW_SERVICE)),new ViewGroup.LayoutParams(400,400));
    }
}
