package com.hammad.omar.outreach.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Fragments.CommunityFragment;
import com.hammad.omar.outreach.Fragments.MeFragment;
import com.hammad.omar.outreach.Helping.FormEntries.PassingString;
import com.hammad.omar.outreach.Interfaces.CallBackDB;
import com.hammad.omar.outreach.Interfaces.CallBackMapsConnection;
import com.hammad.omar.outreach.Managers.DynamoDBManager;
import com.hammad.omar.outreach.Managers.EntriesManager;
import com.hammad.omar.outreach.Managers.LocationManager;
import com.hammad.omar.outreach.Managers.MapsConnectionManager;
import com.hammad.omar.outreach.Managers.SharedPreferencesManager;
import com.hammad.omar.outreach.Models.Entry;
import com.hammad.omar.outreach.Models.EntryDO;
import com.hammad.omar.outreach.Models.UpdateDO;
import com.hammad.omar.outreach.Models.UserDO;
import com.hammad.omar.outreach.R;
import com.hammad.omar.outreach.Recivers.NotificationReciever;
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
        setupFormButton();
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

    private void setupFormButton() {

        final Button formButton = findViewById(R.id.formButton);
        formButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {

                            if (App.hasActiveInternetConnection(MainActivity.this)) {
                                new DynamoDBManager(MainActivity.this).getUserFirstForm();
                            }

                        }
                    });
            }
        });
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
                        reason = new PassingString(getString(R.string.cannotAddEntry));
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
            case R.id.CommunityScreen:
                nextIntent = new Intent(this,CommunityActivity.class);
                break;
            case R.id.AboutScreen:
                nextIntent = new Intent(this,AboutActivity.class);
                break;
            case R.id.HowToScreen:
                nextIntent = new Intent(this,HowTo.class);
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

        //check if it needs update
        checkForUpdates();

    }




    //////////////////////////// MAPS /////////////////////////////////

    @Override
    public void callbackMapsConnected(){
        Log.d(TAG,"connected to maps successfully !!");
    }

    @Override
    public void callbackMapsFailed() {
        Toast.makeText(this, R.string.failedConnection,Toast.LENGTH_SHORT);
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
        }else if (callbackid == DynamoDBManager.CALL_BACK_ID_GET_UPDATE){
            callbackUpdate(object);
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

            Log.d(TAG,"Form not completed");
            Intent intent = new Intent(this,OneTimeForm_1.class);
            startActivity(intent);

        }else{
            Log.d(TAG,"Form completed");

            App.user = results.get(0);
            new SharedPreferencesManager(this).setUserFormCompleted(true);
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
            eveningTime = Integer.parseInt(prefMgr.getEveningNotificationTime());
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
        long timeOfAlarm = callendar.getTime().getTime();
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,timeOfAlarm,AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    private void setupLocationReciver() {

        LocationManager locationManager = new LocationManager(this,null);
        locationManager.initiateReciver();

    }

    private void checkForUpdates() {

        Log.d(TAG,"checking for updates..");

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if(App.hasActiveInternetConnection(MainActivity.this)){

                    new DynamoDBManager(MainActivity.this).getUpdates();

                }else{

                    Log.d(TAG,"No internet ..");

                }

            }
        });

    }

    private void callbackUpdate(Object o) {

        // get list

        PaginatedList<UpdateDO> updateDOS = (PaginatedList<UpdateDO>) o;

        // get update

        UpdateDO updateDO = updateDOS.get(0);

        // current version

        String appVersion="";

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = pInfo.versionName;
            Log.d(TAG,"Version "+ appVersion);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // compare

        boolean latest = appVersion.equals(updateDO.getVersionCode());
        boolean required = updateDO.getRequired();

        // decision

        if(!latest && required){

            // needs update

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    createAndShowDialog(
                            "An Update Is Required",
                            "Please update the app by clicking on the following button");
                }
            });


        }else{

            Log.d(TAG,"No need to update");

        }

    }

    private void createAndShowDialog(String title, String text){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(text);

        // Set up the buttons
        builder.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //String url = "market://search?q=pname:com.example.myapp"; // production url
                String url = "https://play.google.com/apps/testing/com.hammad.omar.outreach"; // testing url
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                createAndShowDialog(
                        "An Update Is Required",
                        "Please update the app by clicking on the following button");
            }
        });

        builder.show();

    }




    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("MainActivity","Hi from main");
    }

}
