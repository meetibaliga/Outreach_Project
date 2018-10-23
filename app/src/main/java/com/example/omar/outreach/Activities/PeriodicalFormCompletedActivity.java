package com.example.omar.outreach.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Interfaces.CallBackLocation;
import com.example.omar.outreach.Managers.DynamoDBManager;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Provider.EntriesDataSource;
import com.example.omar.outreach.R;

import java.sql.Timestamp;

public class PeriodicalFormCompletedActivity extends AppCompatActivity implements CallBackDB, CallBackLocation{

    private ProgressBar progressBar;
    private TextView textView;
    private Location location;
    private EntriesDataSource entriesDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_completed);
        entriesDataSource = new EntriesDataSource(this);
    }

    @Override
    protected void onResume() {

        super.onResume();

        // start progressbar

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView_complete);

        // set creation date

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        App.inputEntry.setCreationDate(timestamp.toString());

        // set location
        if(LocationManager.isLocationEnabled(this)){
            new LocationManager(this).getCurrentLocation();
        }else{
            showDialog();
        }
    }

    private void showDialog() {
        // notify user
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Please Enable you location");

        dialog.setPositiveButton("Enable Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });

        dialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                userDidNotEnableLocation();
            }
        });

        dialog.show();
    }

    private void userDidNotEnableLocation() {

    }

    private void navigateToMain() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void callbackCurrentLocation(Object object) {

        if(object instanceof Location){
            location = (Location) object;
            App.inputEntry.setLatLng(location.getLatitude()+"",location.getLongitude()+"");

            //save to db
            //new DynamoDBManager(this).saveEntry();
            entriesDataSource.insertItem(App.inputEntry);

        }else{
            //new DynamoDBManager(this).saveEntry();
            entriesDataSource.insertItem(App.inputEntry);
        }
    }

    @Override
    public void callbackDB(Object object,int callbackId) {

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
