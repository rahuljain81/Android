package com.example.timersdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000, 100) {
            public void onTick(long ms) {
                Log.i( "Seconds Left !!!", String.valueOf(ms/1000));

            }
            public void onFinish(){
                Log.i( "Seconds Left !!!", "We are done !!");

            }
        }.start();

        /*
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("Timer", "A second has passed");
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
        */

    }


}
