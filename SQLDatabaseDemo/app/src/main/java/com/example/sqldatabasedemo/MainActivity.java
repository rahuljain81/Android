package com.example.sqldatabasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

            myDatabase.execSQL("DROP TABLE users");
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age int(3), id INTEGER PRIMARY KEY)");

            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Rahul', 38)");
            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Neha', 36)");

            Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);

            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");

            c.moveToFirst();

            while (!c.isAfterLast()) {
                Log.i("name", "id = " + Integer.toString((c.getInt(idIndex))) + " name " + c.getString(nameIndex) + " Age = " + Integer.toString((c.getInt(ageIndex))));
                c.moveToNext();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }


    }
}