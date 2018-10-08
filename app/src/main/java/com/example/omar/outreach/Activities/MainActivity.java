package com.example.omar.outreach.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Interfaces.CallBackMapsConnection;
import com.example.omar.outreach.Managers.DBManager;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Managers.MapsConnectionManager;
import com.example.omar.outreach.Models.UserDO;
import com.example.omar.outreach.R;

public class MainActivity extends AppCompatActivity implements CallBackMapsConnection, CallBackDB {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!App.mainActivityViewd){
            oneTimeCode();
        }

        // set the number of entries for the entry id

        new DBManager().setNumberOfEntries();

        Toast.makeText(this,App.USER_ID,Toast.LENGTH_SHORT).show();


    }

    private void oneTimeCode() {
        // run code only one time
        App.mainActivityViewd = true;

        // set the user id and num of entries of this user
        App.USER_ID = IdentityManager.getDefaultIdentityManager().getCachedUserID();

        // if the user has no user id go to the authentication screen
        if(App.USER_ID == ""){
            IdentityManager.getDefaultIdentityManager().signOut();
        }

        // check location
        if(!LocationManager.isLocationEnabled(this)){
            goToPermissionsScreen();
        }

        // Create connection with maps
        new MapsConnectionManager(this);


        //check if the user has filled the first time form
        new DBManager(this).getUserFirstForm();

    }


    public void btnClicked(View view) {
        Intent intent = new Intent(this, PeriodicalFormActivity_1.class);
        startActivity(intent);

    }

    public void signoutClicked(View view){
        IdentityManager.getDefaultIdentityManager().signOut();
    }

    @Override
    public void callbackMapsConnected() {
        askForLocationPermission();
    }

    @Override
    public void callbackMapsFailed() {
        Toast.makeText(this,"Failed Connection",Toast.LENGTH_SHORT);
    }

    @Override
    public void callbackMapsSuspended() {

    }

    public void askForLocationPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            // NOT GRANTED

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION))

            {

                // Give Grant
                permissionNotGranted();

            }

            else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // Request Grant

            }

        } else
        {
            // GRANTED
            permissionGranted();
        }

    }

    private void permissionGranted(){
    }

    private void permissionNotGranted(){
        goToPermissionsScreen();
    }

    private void goToPermissionsScreen() {
        Intent intent = new Intent(this,PermissionsActivity.class);
        startActivity(intent);
    }


    @Override
    public void callbackDB(Object object) {
        PaginatedList<UserDO> results = (PaginatedList<UserDO>) object;
        if(results.size() == 0){
            Intent intent = new Intent(this,OneTimeForm_1.class);
            startActivity(intent);
        }
    }
}
