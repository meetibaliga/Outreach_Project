package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Managers.DynamoDBManager;
import com.example.omar.outreach.R;

import java.sql.Timestamp;

public class OneTimeFormCompletedActivity extends AppCompatActivity implements CallBackDB {

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_completed);
    }

    @Override
    protected void onResume() {

        super.onResume();

        // start progressbar

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView_complete);

        // set creation date

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        App.user.setCreationDate(timestamp.toString());

        //save to db
        new DynamoDBManager(this).saveUserForm();


    }

    @Override
    public void callbackDB(Object object,int callbackId) {
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
