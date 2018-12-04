package com.example.omar.outreach.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.omar.outreach.Interfaces.CallBackMapsConnection;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Managers.MapsConnectionManager;
import com.example.omar.outreach.R;

public class PermissionsActivity extends AppCompatActivity implements CallBackMapsConnection {

    private static final String TAG = PermissionsActivity.class.getSimpleName();

    private static final int MY_PERMISSIONS_REQUEST_COARS_LOCATION= 1000;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION= 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(LocationManager.isLocationEnabled(this)){
            // Create connection with maps
            new MapsConnectionManager(this);
        }

    }

    @Override
    public void callbackMapsConnected() {
        askForLocationPermission();
    }

    private void showDialog() {

        Log.d(TAG,"Showing dialog ");

        // notify user
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Please Enable you device location and allow the app to use the location.");

        dialog.setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                userDidNotEnableLocation();
            }
        });

        dialog.show();
    } // no need

    private void userDidNotEnableLocation() {
    } // no need

    private void goToMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void askForLocationPermission() {

        if ( ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            // NOT GRANTED

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION))

            {

                // Give Grant
                permissionNotGranted();

            }

            else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARS_LOCATION);

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            }

        } else
        {
            // GRANTED
            permissionGranted();
        }

    }

    private void permissionGranted(){
        goToMainScreen();
    }

    private void permissionNotGranted(){
    }

    @Override
    public void callbackMapsFailed() {
        Toast.makeText(this,"Failed Connection with google maps",Toast.LENGTH_SHORT);
    }

    @Override
    public void callbackMapsSuspended() {
    }

    // actions

    public void allowAppClicked(View view) {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);

    }

    public void settingsClicked(View view) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);

    }
}
