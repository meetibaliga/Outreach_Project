package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.example.omar.outreach.App;
import com.example.omar.outreach.CallBack;
import com.example.omar.outreach.DBManager;
import com.example.omar.outreach.Model.AcceptanceScale;
import com.example.omar.outreach.Model.Activity;
import com.example.omar.outreach.Model.Emotion;
import com.example.omar.outreach.Model.Entery;
import com.example.omar.outreach.Model.EntryDO;
import com.example.omar.outreach.Model.Location;
import com.example.omar.outreach.R;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.ArrayList;

public class FormCompletedActivity extends AppCompatActivity implements CallBack {

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_completed);

        // start progressbar

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView_complete);

        // set creation date

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        App.inputEntry.setCreationDate(timestamp.toString());

        //save to db
        new DBManager(this).saveEntry();

    }

    private void navigateToMain() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void callback() {

        // change UI

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
}
