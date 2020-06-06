package com.example.braintimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton, playAgainButton;
    ConstraintLayout gameLayout;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    ArrayList <Integer> answers = new ArrayList<Integer>();
    TextView resultTextVoew, scoreTextView, timerTextView;
    TextView sumTextView;
    int locationOCorrectAsnwer;
    int score = 0;
    int numberOfQuestions  = 0;
    CountDownTimer countDownTimer;

    public void playAgain (View view) {
        score = 0;
        numberOfQuestions = 0;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));


        timerTextView.setText("30s");
        playAgainButton.setVisibility(View.INVISIBLE);
        newQuestion();

        new CountDownTimer(30100, 1000 ) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextVoew.setText("Done!!");
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void start (View view) {
        goButton.setVisibility(View.INVISIBLE);
        playAgain(findViewById(R.id.timerTextView));
        gameLayout.setVisibility(View.VISIBLE);
    }

    public void newQuestion () {
        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOCorrectAsnwer = random.nextInt(4);

        answers.clear();
        for (int i = 0; i < 4; i++) {

            if (i == locationOCorrectAsnwer)
                answers.add(a+b);
            else {
                int wrongAnswer =  random.nextInt(41);
                while (wrongAnswer == (a+b))
                    wrongAnswer =  random.nextInt(41);
                answers.add(wrongAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer (View view) {

        Log.i ("Tag", view.getTag().toString());
        if (Integer.toString(locationOCorrectAsnwer).equals(view.getTag().toString())) {
            Log.i ("Corect","You got it");
            resultTextVoew.setText("Correct !!");
            score++;
        } else {
            Log.i ("Wrong","Try Again !!");
            resultTextVoew.setText("Wrong :(");
        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = findViewById(R.id.gameLayout);

        sumTextView = findViewById(R.id.sumTextView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgain);

        resultTextVoew = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);

        goButton = findViewById(R.id.goButton);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
    }
}
