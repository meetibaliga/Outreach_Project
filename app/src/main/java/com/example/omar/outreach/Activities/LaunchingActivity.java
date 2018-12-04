package com.example.omar.outreach.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackAuth;
import com.example.omar.outreach.Managers.AuthManager;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Managers.SharedPreferencesManager;

public class LaunchingActivity extends AppCompatActivity implements CallBackAuth {

    private static final String TAG = LaunchingActivity.class.getSimpleName();

    AuthManager authManager;
    Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check for cached user
        authManager = AuthManager.getInstance(this);

        boolean hasCachedCred = authManager.checkCachedLogin();

        if(!hasCachedCred){

            Log.d(TAG,"Going to auth .. no cache");
            goTo(AuthActivity.class);

        }else{

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    if(App.hasActiveInternetConnection(LaunchingActivity.this)){
                        AuthManager.getInstance(LaunchingActivity.this).attemptLoginUsingCachedCredentials(LaunchingActivity.this);
                    }else{
                        App.USER_ID = SharedPreferencesManager.getInstance(LaunchingActivity.this).getUserId();
                        goToMainScreen();
                    }
                }
            });

        }

    }

    @Override
    public void callbackAuth(Object object, int callbackId) {

        if (callbackId == AuthManager.CALL_BACK_ID_LOGIN){

            if (object instanceof Exception){
                Log.d(TAG,"Going to auth du to error");
                goTo(AuthActivity.class);
                return;
            }

            CognitoUser user = (CognitoUser)object;
            App.USER_ID = user.getUserId();
            Log.d(TAG,"Going to main");
            goToMainScreen();

        }else{

            Log.d(TAG,"Going to auth");
            goTo(AuthActivity.class);

        }

    }

    private void goTo(Class<?> activity){

        nextActivity = new Intent(this,activity);
        startActivity(nextActivity);
    }

    private void goToMainScreen(){

        // check location
        if(LocationManager.isLocationEnabled(this)){
            goTo(MainActivity.class);
        }else{
            goTo(PermissionsActivity.class);
        }

    }
}
