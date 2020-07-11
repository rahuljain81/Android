package com.example.googlesignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class afterSignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sign_in);

        Intent intent = getIntent();
        String mail = intent.getStringExtra("age");
        Toast.makeText (this, mail, Toast.LENGTH_SHORT).show();
    }
}