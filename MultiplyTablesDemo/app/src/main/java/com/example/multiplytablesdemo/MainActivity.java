package com.example.multiplytablesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView tableList;

    public void generateTimesTable (int tableNum) {
        ArrayList<String> timesArrayList = new ArrayList<String>();

        for (int j = 1; j <= 100; j++) {
            timesArrayList.add(Integer.toString(j) + " = " + Integer.toString(j*tableNum));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_checked, timesArrayList);

        tableList.setAdapter(arrayAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final SeekBar tables = findViewById(R.id.seekBar);

        final TextView textView = findViewById(R.id.textView);

        tableList = findViewById(R.id.listView);

        tables.setMax(20);
        tables.setProgress(10);

        generateTimesTable(10);

        tables.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int min  = 1;
                int tableNum;

                if (progress < min) {
                    tableNum = min;
                    tables.setProgress(tableNum);
                } else {
                    tableNum = progress;
                }

                Log.i ("Seekbar Value", Integer.toString(progress));

                textView.setText(Integer.toString(tableNum));

                generateTimesTable(tableNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
