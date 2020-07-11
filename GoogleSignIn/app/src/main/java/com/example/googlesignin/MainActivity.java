package com.example.googlesignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


//https://developers.google.com/identity/sign-in/android/sign-in
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    GoogleSignInClient mGoogleSignInClient;

    FirebaseAuth mAuth;

    public void onGoogleSignInClicked (View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sign_in_button).setOnClickListener(this);


        // for the requestIdToken, use getString(R.string.default_web_client_id), this is in the values.xml file that
        // is generated from your google-services.json file (data from your firebase project), uses the google-sign-in method
        // web api key
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(signInButton.SIZE_WIDE);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }



    public void onStart() {
        super.onStart();

        //Check if user is signed in (non-null)  and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Log.d(TAG, "Current User signed in" + currentUser.getEmail());
            Toast.makeText( MainActivity.this, "Currently logged in" + currentUser.getEmail(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    //Start the sign-in flow
    public void onClick(View v) {
        int i  = v.getId();
        //In the activity's onClick method,
        // handle sign-in button taps by creating a sign-in intent
        // with the getSignInIntent method, and starting the intent with startActivityForResult.
        if (i ==  R.id.sign_in_button) {
            Log.d (TAG, "Google Sign In Button Clicked");
            signInToGoogle();
        }else if (i == R.id.signOutButton) {
            signOut();
        }
        else if (i == R.id.disconnectButton) {
            revokeAccess();
        }
    }

    //Starting the intent prompts the user to select a Google account to sign in with.
    // If you requested scopes beyond profile, email, and openid, the user is also prompted to grant access to the requested resources.
    private void signInToGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Log.d (TAG, "Prompts the user to select a Google account to sign in with");
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //After the user signs in, you can get a GoogleSignInAccount object for the user in the activity's onActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data);

        Log.d (TAG, "User signs In");

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode ==  RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task  =  GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In successful, Authenticate with firebase
                GoogleSignInAccount account  = task.getResult(ApiException.class);
                Toast.makeText(this, "Google Sign in Succeeded", Toast.LENGTH_LONG).show();
                firebaseAuthWithGoogle (account);
            } catch (ApiException e) {
                //Google Sign In failed, update UI accordingly
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Google Sign In failed " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    //The GoogleSignInAccount object contains information about the signed-in user, such as the user's name.
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthwithGoogle " + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d (TAG, "signInWithCredential: success " + user.getEmail());
                            Toast.makeText(MainActivity.this, "Firebase Authentication Succeeded", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d (TAG, "signInWithCredential: fail " + task.getException());
                            Toast.makeText(MainActivity.this, "Firebase Authentication Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void signOut() {

        Log.d (TAG, "User signs Out");
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately
                        Log.w(TAG, "Signed out of google");
                    }
                });
    }

    private void revokeAccess() {
        Log.d (TAG, "User signs Out and Disconnect");
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately
                        Log.w(TAG, "Revoked Access");
                    }
                });
    }

}