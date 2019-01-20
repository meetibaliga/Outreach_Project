package com.example.omar.outreach.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.outreach.App;
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


        boolean locationEnabled = true;

        // check if the device location is not enabled
        if(!LocationManager.isDeviceLocationEnabled(this)){
            Log.d(TAG,"device not allowd");
            locationEnabled = false;
            showDeviceLocationButton();
        }

        // check if the app allows location
        if(!LocationManager.isAppLocationPermissionLocationEnabled(this)){
            Log.d(TAG,"app not allowed");
            locationEnabled = false;
            askForLocationPermission();
        }

        if(locationEnabled){
            Log.d(TAG,"location enabled");
            goToMainScreen();
        }else{
            Log.d(TAG,"location not enabled");
        }


    }

    private void showDeviceLocationButton(){
        Button button = findViewById(R.id.settingsButton);
        TextView text = findViewById(R.id.settingsText);
        button.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
    }

    @Override
    public void callbackMapsConnected() {

    }

    public void askForLocationPermission() {

        if ( ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG,"case 1");

            // NOT GRANTED
            if ( ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION))

            {
                Log.d(TAG,"case 1-a");

                // Give Grant
                permissionNotGranted();

            }

            else {

                Log.d(TAG,"case 1-b");

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            }

        } else
        {
            Log.d(TAG,"case 2");

            // GRANTED
            permissionGranted();
        }

    }

    private void permissionGranted(){
        if(LocationManager.isDeviceLocationEnabled(this)){
            goToMainScreen();
        }
    }


    private void goToMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void permissionNotGranted(){
        showAppPermissionButtons();
    }

    private void showAppPermissionButtons(){
        Button button = findViewById(R.id.allowAppButton);
        TextView text = findViewById(R.id.allowAppText);
        button.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
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
