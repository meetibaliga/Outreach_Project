package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.omar.outreach.App;
import com.example.omar.outreach.DBManager;
import com.example.omar.outreach.Model.AcceptanceScale;
import com.example.omar.outreach.Model.Activity;
import com.example.omar.outreach.Model.Emotion;
import com.example.omar.outreach.Model.EntryDO;
import com.example.omar.outreach.Model.Location;
import com.example.omar.outreach.R;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the user id and num of entries of this user

        App.USER_ID = IdentityManager.getDefaultIdentityManager().getCachedUserID();

        // if the user has no user id go to the authentication screen

        if(App.USER_ID == ""){
            IdentityManager.getDefaultIdentityManager().signOut();
        }

        // set the number of entries

        new DBManager().setNumberOfEntries();

        Toast.makeText(this,App.USER_ID,Toast.LENGTH_SHORT).show();


    }


    public void btnClicked(View view) {
        Intent intent = new Intent(this, FormActivity_1.class);
        startActivity(intent);

    }

    public void signoutClicked(View view){
        IdentityManager.getDefaultIdentityManager().signOut();
    }



}
