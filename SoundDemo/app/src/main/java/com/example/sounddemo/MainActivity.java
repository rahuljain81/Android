package com.example.sounddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public void play(View view) {
        mediaPlayer.start();
    }

    public void pause (View view) {
        mediaPlayer.pause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);



        mediaPlayer = MediaPlayer.create(this, R.raw.airplanelanding);

        SeekBar volumeSeekBar =  findViewById(R.id.volumeSeekBar);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);


        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("SeekBar Changed ", "currentVolume" + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + " new " + Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("SeekBar", "Touched");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("SeekBar", "Untouched");

            }

        });

        final SeekBar scrubSeekBar = (SeekBar) findViewById(R.id.scrubSeekBar);

        scrubSeekBar.setMax(mediaPlayer.getDuration());

        scrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.i("Scrub seekbar moved", Integer.toString(i));

                mediaPlayer.seekTo(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());

            }
        }, 0, 300);

    }
}
