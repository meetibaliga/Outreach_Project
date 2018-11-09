package com.example.omar.outreach.Recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.app.Activity;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackLocation;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Models.UserLocation;
import com.example.omar.outreach.Provider.LocationsDataSource;

public class LocationReciver extends BroadcastReceiver implements CallBackLocation {

    Context context;
    LocationsDataSource locationsDataSource;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Tracking","in location reciver .. ");
        this.context = context;
        getCurrentLocation();
    }

    public void getCurrentLocation() {
        // set location
        if(LocationManager.isLocationEnabled(context)){
            Log.d("LocationEn","location enabled");
            new LocationManager(App.mainActivity,this).getCurrentLocation();
        }
    }

    @Override
    public void callbackCurrentLocation(Object object) {

        Log.d("Tracking", "In call back");

        // store in locations table
        if(locationsDataSource == null){
            locationsDataSource = new LocationsDataSource(context);
        }

        // Storing in DB
        if(object instanceof Location) {
            Location location = (Location) object;
            UserLocation userLocation = new UserLocation(location.getLatitude() + "", location.getLongitude() + "");
            locationsDataSource.insertItem(userLocation);
            Log.d("Tracking","Stored a new location: "+ userLocation.get_latitude()+" "+userLocation.get_longitude());
        }else{
            Log.d("Tracking","Cannot get Location .. ");
        }
    }
}
