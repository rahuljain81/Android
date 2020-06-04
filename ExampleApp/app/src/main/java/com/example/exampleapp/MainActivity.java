package com.example.exampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean clicked =  true;
    public void fade (View view) {
        Log.i("Info", "Image View Tapped");

        ImageView bartimageView = findViewById(R.id.bartImageView);
        ImageView homerimageView = findViewById(R.id.homerImageView);

        if (clicked ==  true) {
            bartimageView.animate().alpha(0).setDuration(2000);
            homerimageView.animate().alpha(1).setDuration(2000);
            clicked = false;
        }
        else
        {
            bartimageView.animate().alpha(1).setDuration(2000);
            homerimageView.animate().alpha(0).setDuration(2000);
            clicked =  true;
        }

    }

/*
    public void clickFunction(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        Log.i("Info", "Button Pressed" );
        Log.i("info", nameEditText.getText().toString());

        Toast.makeText(this, "Hello" + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();

    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
