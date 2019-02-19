package com.hammad.omar.outreach.Managers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.hammad.omar.outreach.Interfaces.CallBackLocation;
import com.hammad.omar.outreach.Models.UserLocation;
import com.hammad.omar.outreach.Provider.LocationsDataSource;
import com.hammad.omar.outreach.Recivers.LocationReciver;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationManager implements CallBackLocation {

    private static final String TAG = LocationManager.class.getSimpleName();

    private Activity mContext;
    private CallBackLocation mCallBack;
    private LocationsDataSource locationsDataSource;
    private LocationReciver locationReciver;

    // last point
    private static Location lastLocation = null;

    public LocationManager(Activity context, @Nullable CallBackLocation callBack) {

        this.mContext = context;
        this.locationsDataSource = new LocationsDataSource(context);
        this.locationReciver = LocationReciver.getInstance(context,this);

        if(callBack == null){
            this.mCallBack = this;
        }else{
            this.mCallBack = callBack;
        }

        Log.d(TAG,"end of const");
    }

    public void initiateReciver(){
        locationReciver.initiate(mContext);
    }


    @SuppressLint("MissingPermission")
    public void getCurrentLocation(){

        Log.d(TAG,"IN GET CURRENT LOCATION");

        if(mContext == null){
            return;
        }

        final FusedLocationProviderClient currentLocation = LocationServices.getFusedLocationProviderClient(mContext);

        currentLocation.getLastLocation()
                .addOnSuccessListener(mContext, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        Log.d(TAG,"SUCCESS");
                        Log.d(TAG,"l"+location);

                        mCallBack.callbackCurrentLocation(location);
                    }

                }).addOnFailureListener(mContext, new OnFailureListener() {

                    @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"error");
                getLocationUsingGPS();
            }
        });

    }

    public static boolean isLocationEnabled(Context context){

        if(context == null){return false;}

        boolean deviceLocationEnabled = isDeviceLocationEnabled(context);
        boolean locationPermissionEnabled = isAppLocationPermissionLocationEnabled(context);

        return deviceLocationEnabled && locationPermissionEnabled;
    }

    public static boolean isDeviceLocationEnabled(Context context){

        android.location.LocationManager locationManager =
                (android.location.LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = false;

        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps") || provider.contains("network")){
            gps_enabled = true;
        }

        return gps_enabled;
    }

    public static boolean isAppLocationPermissionLocationEnabled(Context context){

        return ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public void callbackCurrentLocation(Object object) {

        // Storing in DB
        if(object instanceof Location) {

            Location location = (Location) object;

            if(lastLocation != null){

                double distanceInMeters = distance(location.getLatitude(),location.getLongitude(),lastLocation.getLatitude(),lastLocation.getLongitude()) * 1000;

                Log.d(TAG,"Distance between last location and this is " + distanceInMeters + " meters");

                // dont store the location if the distance is less than 5 meters
                
                if(distanceInMeters <= 5) {
                    return;
                }

            }

            lastLocation = location;
            UserLocation userLocation = new UserLocation(location.getLatitude() + "", location.getLongitude() + "");
            locationsDataSource.insertItem(userLocation);
            Log.d("Tracking","Stored a new location: "+ userLocation.get_latitude()+" "+userLocation.get_longitude());

        }else{

            Log.d("Tracking","Cannot get Location .. ");
            getLocationUsingGPS();

        }

    }

    @SuppressLint("MissingPermission")
    public void getLocationUsingGPS() {

        // Acquire a reference to the system Location Manager
        final android.location.LocationManager locationManager = (android.location.LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);

// Define a listener that responds to location updates


        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {

                if(location != null) {

                    Log.d("Tracking","From GPS .. ");
                    mCallBack.callbackCurrentLocation(location);

                }else{

                    Log.d("Tracking","Cannot get Location From GPS .. ");
                    mCallBack.callbackCurrentLocation(new Exception("cannot get location from GPS"));

                }

                locationManager.removeUpdates(this);


            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {
                mCallBack.callbackCurrentLocation(new Exception());
            }

        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 2, locationListener);



    }

    // helping methods

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
