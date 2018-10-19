package com.example.omar.outreach.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.example.omar.outreach.Adapters.EntriesAdapter;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Interfaces.CallBackMapsConnection;
import com.example.omar.outreach.Managers.DBManager;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Managers.MapsConnectionManager;
import com.example.omar.outreach.Managers.NotificationReciever;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.Models.UserDO;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements CallBackMapsConnection, CallBackDB {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;
    private ListView listView;
    private EntriesAdapter entriesAdapter;
    private ArrayList<EntryDO> entriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!App.mainActivityViewd){
            oneTimeCode();
        }

        // set the number of entries for the entry id

        new DBManager(this).getEntries();
        Toast.makeText(this,App.USER_ID,Toast.LENGTH_SHORT).show();

        // fake data


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

        //set Alarm for notifications

        // 1
        setNotificationAlarm(Calendar.getInstance().getTime().getHours(),Calendar.getInstance().getTime().getMinutes(),App.NOTIFY_ID);

        // 2
        setNotificationAlarm(9,0,App.NOTIFY_ID_2);

    }


    ////////////////// EVENTS /////////////////////////


    public void btnClicked(View view) {
        Intent intent = new Intent(this, PeriodicalFormActivity_1.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Main","hi");
        return true;
    }

    public void signoutClicked(View view){
        IdentityManager.getDefaultIdentityManager().signOut();
    }


    //////////////////////////// MAPS /////////////////////////////////

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


    ///////////////////// DB ////////////////////////

    @Override
    public void callbackDB(Object object, int callbackid) {

        if (callbackid == DBManager.CALL_BACK_ID_GET_ENTRIES){
            callBackEntries(object);
        }else if (callbackid == DBManager.CALL_BACK_ID_GET_USER){
            callbackidUser(object);
        }else{
            // do nothing
        }

    }

    private void callBackEntries(Object object) {

        PaginatedList<EntryDO> results = (PaginatedList<EntryDO>) object;


        if(entriesList != null){
            entriesList.addAll(results);
        }else{
            entriesList = new ArrayList<EntryDO>();
            entriesList.addAll(results);
        }

        App.NUM_OF_ENTRIES = entriesList.size();
        Log.d("Main", entriesList.toString());

        // setup list view
        setupListView(entriesList,getApplicationContext());

    }

    private void setupListView(final ArrayList<EntryDO> paginatedList, final Context context) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                listView = findViewById(R.id.listView);
                entriesAdapter = new EntriesAdapter(context, entriesList);
                listView.setAdapter(entriesAdapter);
            }
        });


    }

    private void callbackidUser(Object object) {

        PaginatedList<UserDO> results = (PaginatedList<UserDO>) object;

        if(results.size() == 0){
            Intent intent = new Intent(this,OneTimeForm_1.class);
            startActivity(intent);
        }else{
            App.user = results.get(0);
            Log.d("Main",App.user.toString());
        }

    }


    ///////////////// NOTIFICATIONS //////////////////////


    private void setNotificationAlarm(int hour, int min, int notifyId) {

        Log.d("Notif","in set notif");

        //time to repeat
        Calendar callendar = Calendar.getInstance();
        callendar.set(Calendar.HOUR_OF_DAY,hour);
        callendar.set(Calendar.MINUTE,min);

        // notification reciver intent
        Intent intent = new Intent(getApplicationContext(),NotificationReciever.class);
        intent.putExtra("notifyID", notifyId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),notifyId,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        // Set alarm
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,callendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }
}
