package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //0: yellow, 1: red, 2: empty
    int [] gameState = {2,2,2, 2,2,2, 2,2,2};
    int [] [] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},}
    int activePlayer = 0;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        Log.i("Tag", counter.getTag().toString());
        int tagCounter = Integer.parseInt(counter.getTag().toString());

        gameState[tagCounter] = activePlayer;

        counter.setTranslationY(-500);

        if (activePlayer == 0) {
            counter.setImageResource(R.drawable.yellow);
            activePlayer = 1;
        } else {
            counter.setImageResource(R.drawable.red);
            activePlayer = 0;
        }

        counter.animate().translationY(300).rotation(3600).setDuration(300);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
