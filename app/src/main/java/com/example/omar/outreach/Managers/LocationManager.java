package com.example.omar.outreach.Managers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.omar.outreach.Interfaces.CallBackLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.function.BiPredicate;

public class LocationManager {

    private Activity context;
    private CallBackLocation callBack;

    public LocationManager(Activity context) {

        this.context = context;
        this.callBack = (CallBackLocation) context;

    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(){

        final FusedLocationProviderClient currentLocation = LocationServices.getFusedLocationProviderClient(context);

        currentLocation.getLastLocation()
                .addOnSuccessListener(context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d("LocationLog","l"+location);
                        callBack.callbackCurrentLocation(location);
                    }

                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("LocationLog","error");
                callBack.callbackCurrentLocation(e);
            }
        });

    }

    public static boolean isLocationEnabled(Context context){

        android.location.LocationManager locationManager =
                (android.location.LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled ) {
            return false;
        }else{
            return true;
        }

    }

}
