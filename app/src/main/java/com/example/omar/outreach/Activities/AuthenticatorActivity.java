package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.example.omar.outreach.R;

public class AuthenticatorActivity extends AppCompatActivity {

    AuthUIConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);

        // config the ui

        config = new AuthUIConfiguration.Builder()
                        .userPools(true)  // true? show the Email and Password UI
                        .logoResId(R.drawable.word) // Change the logo
                        .fontFamily("roboto-light") // Apply sans-serif-light as the global font
                        .backgroundColor(Color.WHITE)
                        .isBackgroundColorFullScreen(true)
                        .canCancel(true)
                        .build();

        // Add a call to initialize AWSMobileClient

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                showSignIn();
            }
        }).execute();


        // Sign-in Listener

        IdentityManager.getDefaultIdentityManager().addSignInStateChangeListener(new SignInStateChangeListener() {

            @Override
            public void onUserSignedIn() {
                Log.d("MainActivity", "User Signed In");

            }

            // Sign-out listener

            @Override
            public void onUserSignedOut() {
                Log.d("MainActivity", "User Signed Out");
                showSignIn();
            }
        });

        showSignIn();

    }

    private void showSignIn() {

        SignInUI signinUI = (SignInUI) AWSMobileClient.getInstance()
                .getClient(AuthenticatorActivity.this, SignInUI.class);
        signinUI.login(AuthenticatorActivity.this, MainActivity.class)
                .authUIConfiguration(config).execute();
    }
}
