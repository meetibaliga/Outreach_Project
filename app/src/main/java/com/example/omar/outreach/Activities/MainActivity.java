package com.example.omar.outreach.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Fragments.CommunityFragment;
import com.example.omar.outreach.Fragments.MeFragment;
import com.example.omar.outreach.Helping.FormEntries.PassingString;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Interfaces.CallBackMapsConnection;
import com.example.omar.outreach.Managers.DynamoDBManager;
import com.example.omar.outreach.Managers.EntriesManager;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Managers.MapsConnectionManager;
import com.example.omar.outreach.Managers.SharedPreferencesManager;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.Models.UserDO;
import com.example.omar.outreach.R;
import com.example.omar.outreach.Recivers.NotificationReciever;
import com.google.android.gms.analytics.HitBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackMapsConnection, CallBackDB,MeFragment.OnFragmentInteractionListener, CommunityFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton addEntryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!App.mainActivityViewd){
            oneTimeCode();
        }


        //action button
        setupDrawer();
        setupActionButton();
        disableButtonIfNotAllowed();
        showHomeFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        disableButtonIfNotAllowed();

        // check location
        if(!LocationManager.isLocationEnabled(this)){
            goToPermissionsScreen();
        }

        App.getDefaultTracker().setScreenName(this.getClass().getSimpleName());
        App.getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void connectToGoogleMapsLocationsService(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MapsConnectionManager.getInstance(MainActivity.this).connectToGooglePlayMapsService();
            }
        });

    }

    private void setupDrawer() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //nothing
        }
    }

    private void setupActionButton() {

        addEntryButton = findViewById(R.id.floatingActionButton);
        addEntryButton.show();
        final Activity activity = this;

        addEntryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PassingString reason = new PassingString("");

                if(!App.entriesManager.canAddEntry(reason)){
                    if(reason == null){
                        reason = new PassingString("You cannot add an entry at this time. Try later");
                    }

                    View mainView = findViewById(R.id.mainView);

                    if(mainView == null){
                        return;
                    }

                    // show snackbar
                    Snackbar.make(mainView,reason.getPassingString(),Snackbar.LENGTH_LONG).show();

                }else{
                    Intent intent = new Intent(activity, PeriodicalFormActivity_1.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void disableButtonIfNotAllowed() {

        // disable button if not allowed
        EntriesManager entriesManager = EntriesManager.getInstance(this);

        // check entry button
        PassingString reason = new PassingString("");

        if(!entriesManager.canAddEntry(reason)){
            addEntryButton.setImageDrawable(getDrawable(R.drawable.ic_access_time_white_24dp));
            addEntryButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        }else{
            addEntryButton.setImageDrawable(getDrawable(R.drawable.ic_add_white_24dp));
            addEntryButton.setBackgroundColor(getColor(R.color.colorAccent));
        }
    }

    private void showHomeFragment() {

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, MeFragment.newInstance()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent nextIntent;

        switch (id){
            case R.id.settings_item:
                nextIntent = new Intent(this,PreferencesActivity.class);
                break;
            default:
                   nextIntent = new Intent(this,this.getClass());
        }

        startActivity(nextIntent);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent nextIntent;

        switch (id){
            case R.id.HomeScreen:
                nextIntent = null;
                break;
            case R.id.CommunityScreen:
                nextIntent = new Intent(this,CommunityActivity.class);
                break;
            default:
                nextIntent = null;
        }

        if(nextIntent != null)
            startActivity(nextIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    private void oneTimeCode() {

        // run code only one time
        App.mainActivityViewd = true;

        // set main activity referece
        App.mainActivity = this;

        // if the user has no user id go to the authentication screen
        if(App.USER_ID == "" || App.USER_ID == null) {
            App.authManager.signout();
        }

        //check if the user has filled the first time form

        if(!new SharedPreferencesManager(this).getUserFormCompleted()){

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    if(App.hasActiveInternetConnection(MainActivity.this)){
                        new DynamoDBManager(MainActivity.this).getUserFirstForm();
                    }

                }
            });

        }

        // connect to google play maps service
        if (LocationManager.isLocationEnabled(this)) {
            connectToGoogleMapsLocationsService();
        }else{
            goToPermissionsScreen();
        }

        // notifications
        setupNotifications();

        //setup location reciver
        setupLocationReciver();


    }


    //////////////////////////// MAPS /////////////////////////////////

    @Override
    public void callbackMapsConnected(){
        Log.d(TAG,"connected to maps successfully !!");
    }

    @Override
    public void callbackMapsFailed() {
        Toast.makeText(this,"Failed Connection",Toast.LENGTH_SHORT);
    }

    @Override
    public void callbackMapsSuspended() {
    }

    private void goToPermissionsScreen() {
        Intent intent = new Intent(this,PermissionsActivity.class);
        startActivity(intent);
    }


    ///////////////////// DB ////////////////////////

    @Override
    public void callbackDB(Object object, int callbackid) {

        if (callbackid == DynamoDBManager.CALL_BACK_ID_GET_ENTRIES){
            callBackEntries(object);
        }else if (callbackid == DynamoDBManager.CALL_BACK_ID_GET_USER){
            callbackidUser(object);
        }else{
            // do nothing
        }

    }

    private void callBackEntries(Object object) {

        List<Entry> entries = Entry.getEntries((PaginatedList<EntryDO>) object);

        if(App.entriesList != null){
            App.entriesList.addAll(entries);
        }else{
            App.entriesList = new ArrayList<Entry>();
            App.entriesList.addAll(entries);
        }

        //num of entries
        App.NUM_OF_ENTRIES = App.entriesList.size();

        // setup list view
        //setupListView(getApplicationContext());

    }

    private void callbackidUser(Object object) {

        PaginatedList<UserDO> results = (PaginatedList<UserDO>) object;

        if(results.size() == 0 || results == null){

            Intent intent = new Intent(this,OneTimeForm_1.class);
            startActivity(intent);

        }else{

            App.user = results.get(0);
            Log.d("Main",App.user.toString());

        }

    }

    ////////////////////// RECIVERS ///////////////////////////////


    private void setupNotifications() {

        //set Alarm for notifications
        SharedPreferencesManager prefMgr = SharedPreferencesManager.getInstance(this);

        int morningTime = 9;
        if(prefMgr.getMorningNotificationTime() != null){
            morningTime = Integer.parseInt(prefMgr.getMorningNotificationTime());
        }else{
            prefMgr.setMorningNotificationTime(morningTime);
        }

        int eveningTime = 18;
        if(prefMgr.getEveningNotificationTime() != null){
            eveningTime = Integer.parseInt(prefMgr.getMorningNotificationTime());
        }else {
            prefMgr.setEveningNotificationTime(eveningTime);
        }

        // 1
        setNotificationAlarm(morningTime,0,App.NOTIFY_ID);

        // 2
        setNotificationAlarm(eveningTime,0,App.NOTIFY_ID_2);

    }

    private void setNotificationAlarm(int hour, int min, int notifyId) {


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
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,callendar.getTime().getTime(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    private void setupLocationReciver() {

        LocationManager locationManager = new LocationManager(this,null);
        locationManager.initiateReciver();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("MainActivity","Hi from main");
    }

}
