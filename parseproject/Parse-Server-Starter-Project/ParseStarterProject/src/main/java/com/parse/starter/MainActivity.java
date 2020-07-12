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

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


//Course: https://samsungu.udemy.com/course/the-complete-android-oreo-developer-course/learn/lecture/8339570#questions/11080474

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
/*
  //DOWNLOAD CODE
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("UYD7NSM7Gs", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if (e == null && object != null) {
          //Updating code
          object.put ("score", 85);
          object.saveInBackground();

          Log.i ("Parse" , object.getString("username") + " " + Integer.toString(object.getInt("score")));
        }
        if (e != null) {
          e.printStackTrace();
        }
      }
    });
*/

/*
  //Download all data from server
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if (e == null) {
          if (objects.size() > 0) {
            for (ParseObject object: objects)
            Log.i("Parse", object.getString("username") + " " + Integer.toString(object.getInt("score")));
          }
        }
        if (e != null) {
          e.printStackTrace();
        }
      }
    });
*/

    //Setup user
    ParseUser user = new ParseUser();
    user.setUsername("RAHUL");
    user.setPassword("password");
    user.setEmail("rahuljain81@gmail.com");

    user.signUpInBackground(new SignUpCallback() {
                              @Override
                              public void done(ParseException e) {
                                if (e == null) {
                                  //OK
                                  Log.i ("Parse", "User is added");
                                } else
                                  e.printStackTrace();
                              }
                            });

            ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}