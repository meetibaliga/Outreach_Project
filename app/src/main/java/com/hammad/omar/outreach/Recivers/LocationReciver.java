package com.hammad.omar.outreach.Recivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.util.Log;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Managers.LocationManager;
import com.hammad.omar.outreach.Managers.MovementDetector;

import java.util.Calendar;

public class LocationReciver extends BroadcastReceiver {

    private static final String TAG = LocationReciver.class.getSimpleName();

    private static LocationManager locationManager;
    private static AlarmManager alarmManager;
    private static MovementDetector movementDetector;

    //consts
    private static final long STABLE_TIME_TO_SWITCH = 1 * 1000 * 60; // 5 mins
    private static final long MAX_CHECK_AGAIN_TIME_INTERVAL = 15 * 1000 * 60; // 15 mins
    private static final long MIN_CHECK_AGAIN_TIME_INTERVAL = 2 * 1000 * 60; // 15 mins
    private static final long MOVING_TIME_INTERVAL = 1 * 1000 * 60; // 1 min
    private static final long CHECK_AGAIN_INCREASE_RATE = 2;

    // for the alram
    private static PendingIntent pendingIntent;
    private static long repeatingInterval = MOVING_TIME_INTERVAL; // default is one minute
    private static long checkAgainTimeInterval = MIN_CHECK_AGAIN_TIME_INTERVAL; // start with 2 mins


    public static LocationReciver instance;

    public static LocationReciver getInstance(Activity context, LocationManager mgr) {
        if(instance == null){
            instance = new LocationReciver(context,mgr);
        }
        return instance;
    }

    private LocationReciver(Activity context,LocationManager mgr){

        this.locationManager = mgr;
        this.alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        this.movementDetector = MovementDetector.getInstance(context);
        this.movementDetector.startDetecting();
    }

    public LocationReciver(){} // to shut up the error

    @Override
    public void onReceive(Context context, Intent intent) {

        // initiate members
        Activity mainActivity = App.mainActivity;
        if(mainActivity == null){return;}

        if(this.locationManager == null)
            this.locationManager = new LocationManager(App.mainActivity,null);

        if(this.alarmManager == null)
            this.alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        if(this.movementDetector == null) {
            this.movementDetector = MovementDetector.getInstance(context);
            this.movementDetector.startDetecting();
        }

        // start here

        boolean getLocation = true;

        // if the device is moving get the location

        if(!movementDetector.isMoving()){

            Log.d(TAG,"not moving");

            long stoppingTime = movementDetector.getStoppingTime();

            Log.d(TAG,"stopping time " + stoppingTime);

            if( stoppingTime >= STABLE_TIME_TO_SWITCH ){

                Log.d(TAG,"stopped more than "+STABLE_TIME_TO_SWITCH+" mins ");

                getLocation = false;

                setRepeatingInterval(checkAgainTimeInterval);

                increaseCheckAgainTimeInterval();

            }

        }

        // and finally get it if he is moving or stop time < min stable time

        if(getLocation){

            Log.d(TAG,"getting loc .. ");
            setRepeatingInterval(MOVING_TIME_INTERVAL);
            resetCheckAgainTimeInterval();
            locationManager.getCurrentLocation();

        }



    }

    private void increaseCheckAgainTimeInterval() {

        checkAgainTimeInterval *= CHECK_AGAIN_INCREASE_RATE;

        if(checkAgainTimeInterval > MAX_CHECK_AGAIN_TIME_INTERVAL){
            checkAgainTimeInterval = MAX_CHECK_AGAIN_TIME_INTERVAL;
        }

    }

    private void resetCheckAgainTimeInterval(){

        if(checkAgainTimeInterval != MIN_CHECK_AGAIN_TIME_INTERVAL){
            checkAgainTimeInterval = MIN_CHECK_AGAIN_TIME_INTERVAL;
        }
    }

    public void initiate(Context context){

        Intent intent = new Intent(context,this.getClass());
        pendingIntent = PendingIntent.getBroadcast(context, App.NOTIFY_ID_3,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        start();
    }

    public void stop(){

        if(pendingIntent == null){
            return;
        }

        alarmManager.cancel(pendingIntent);
    }

    public void start(){

        if(pendingIntent == null){
            return;
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,Calendar.getInstance().getTimeInMillis()+repeatingInterval,repeatingInterval,pendingIntent);

    }

    public void updateAlarm(){
        stop();
        start();
    }

    public void setRepeatingInterval(long intervalInMillis){

        Log.d(TAG,"current repeating interval = " + this.repeatingInterval);
        Log.d(TAG,"set to .. "+ intervalInMillis);

        if(this.repeatingInterval == intervalInMillis){
            Log.d(TAG,"same interval return "+ intervalInMillis);
            return;
        }

        Log.d(TAG,"Alarm is set to " + intervalInMillis);

        this.repeatingInterval = intervalInMillis;
        updateAlarm();
    }

    public long getRepeatingInterval(){
        return repeatingInterval;
    }




}
