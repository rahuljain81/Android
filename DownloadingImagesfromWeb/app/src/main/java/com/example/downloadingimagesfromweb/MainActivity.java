package com.example.downloadingimagesfromweb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    public void downloadImage (View view) {
        Log.i ("Button Tapped", "ok");
        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;

        try {
            myImage = task.execute("https://4dhm2deucu5yphr93fnr1c2c-wpengine.netdna-ssl.com/wp-content/uploads/2020/04/21304495_web1_200419-BPD-QUIZ-Simpsons-QUIZ_1-1200x800.jpg").get();

            imageView.setImageBitmap(myImage);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
    }

    public class ImageDownloader extends AsyncTask <String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream in =  connection.getInputStream();

                Bitmap myBitMap = BitmapFactory.decodeStream(in);

                return myBitMap;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
