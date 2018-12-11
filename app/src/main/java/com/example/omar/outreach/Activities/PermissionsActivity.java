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

        // check if both are connected

        if (LocationManager.isLocationEnabled(this)){
            goToMainScreen();
            return;
        }

        // check if the device location is not enabled

        if(!LocationManager.isDeviceLocationEnabled(this)){
            showDeviceLocationButton();
        }

        // check if the app allows location

        if(!LocationManager.isAppLocationPermissionLocationEnabled(this)){
            askForLocationPermission();
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
