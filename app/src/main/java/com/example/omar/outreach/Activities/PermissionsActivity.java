package com.example.omar.outreach.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.omar.outreach.Interfaces.CallBackMapsConnection;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Managers.MapsConnectionManager;
import com.example.omar.outreach.R;

public class PermissionsActivity extends AppCompatActivity implements CallBackMapsConnection {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!LocationManager.isLocationEnabled(this)){
            showDialog();
        }else{
            // Create connection with maps
            new MapsConnectionManager(this);
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
        Toast.makeText(this,"You have to enable location to continue",Toast.LENGTH_SHORT);
    }


    private void goToMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
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
        goToMainScreen();
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
}
