package com.hammad.omar.outreach.Managers;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.hammad.omar.outreach.Interfaces.CallBackLocation;
import com.hammad.omar.outreach.Interfaces.CallBackMapsConnection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MapsConnectionManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static MapsConnectionManager instance;

    private Activity context;
    private CallBackMapsConnection callBack;
    private GoogleApiClient mLocationClient;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public static MapsConnectionManager getInstance(Activity context) {

        if(instance == null){
            instance = new MapsConnectionManager(context);
        }

        return instance;

    }

    private MapsConnectionManager(Activity context){

        // initialize members
        this.callBack = (CallBackMapsConnection) context;
        this.context = context;

    }

    public void connectToGooglePlayMapsService() {

        // no context
        if(context == null){
            callBack.callbackMapsFailed();
            return;
        }

        // already connected
        if(mLocationClient != null){
            return;
        }

        if (isServiceOK()) {
            mLocationClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mLocationClient.connect();
        }
    }

    private boolean isServiceOK() {

        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(context, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(context, "Success Google Maps", Toast.LENGTH_SHORT).show();
        callBack.callbackMapsConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {
        callBack.callbackMapsSuspended();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        callBack.callbackMapsFailed();
    }
}
