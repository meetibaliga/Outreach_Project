package com.hammad.omar.outreach.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Interfaces.CallBackDB;
import com.hammad.omar.outreach.Managers.DynamoDBManager;
import com.hammad.omar.outreach.Managers.SharedPreferencesManager;
import com.hammad.omar.outreach.R;

import java.sql.Timestamp;

public class OneTimeFormCompletedActivity extends AppCompatActivity implements CallBackDB {

    private static final String TAG = OneTimeFormCompletedActivity.class.getSimpleName();

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_completed);

        Log.d(TAG,"Hi there");

        // start progress
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if(App.hasActiveInternetConnection(OneTimeFormCompletedActivity.this)){
                    Log.d(TAG,"connected");
                    onConnected();
                }else{
                    Log.d(TAG,"not connected");
                    Toast.makeText(OneTimeFormCompletedActivity.this,getString(R.string.makeSureConnected),Toast.LENGTH_LONG);
                    finish();
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        //nothing
    }

    public void onConnected(){

        Log.d(TAG,"connected");

        // start progressbar
        textView = findViewById(R.id.textView_complete);

        // set creation date

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        App.user.setCreationDate(timestamp.toString());

        //save to db
        new DynamoDBManager(this).saveUserForm();

    }

    @Override
    public void callbackDB(Object object,int callbackId) {


        // save in the shared pref that the form is completed
        new SharedPreferencesManager(this).setUserFormCompleted(true);

        // change UI
        Log.d("DB","At call back");

        new Handler(Looper.getMainLooper()).post(new Runnable() {

            public void run() {
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        });

        // go back after 2 secs from the main thread

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToMain();
            }
        }, 1500);

    }

    private void navigateToMain() {

        Log.d("DB","at navigate back to main");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
