package com.example.srotingapplicationdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.srotingapplicationdata", Context.MODE_PRIVATE);

        //Saving In Application Preferencs
        sharedPreferences.edit().putString("username", "rahul").apply();

        Toast.makeText(this.getApplicationContext(), "Username : " + sharedPreferences.getString("username", null), Toast.LENGTH_SHORT).show();

    }
}
