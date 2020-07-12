/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
/*
  //UPLOAD CODE
    ParseObject score = new ParseObject("Score");
    score.put("username", "rahul");
    score.put("score", 45);

    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          //OK
          Log.i("Success", "we saved the score");
        } else {
          e.printStackTrace();
        }
      }
    });
*/

  //DOWNLOAD CODE
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("UYD7NSM7Gs", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if (e == null && object != null) {
          Log.i ("Parse" , object.getString("username") + " " + Integer.toString(object.getInt("score")));
        }
        if (e != null) {
          e.printStackTrace();
        }
      }
    });

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}