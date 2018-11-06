package com.example.omar.outreach.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.example.omar.outreach.Adapters.EntriesAdapter;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Helping.FormEntries.PassingString;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Interfaces.CallBackMapsConnection;
import com.example.omar.outreach.Managers.DynamoDBManager;
import com.example.omar.outreach.Managers.EntriesManager;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Managers.MapsConnectionManager;
import com.example.omar.outreach.Managers.RewardManager;
import com.example.omar.outreach.Managers.SharedPreferencesManager;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.User;
import com.example.omar.outreach.Recivers.LocationReciver;
import com.example.omar.outreach.Recivers.NotificationReciever;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.Models.UserDO;
import com.example.omar.outreach.Provider.EntriesDataSource;
import com.example.omar.outreach.R;
import com.example.omar.outreach.Recivers.PowerReciver;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackMapsConnection, CallBackDB {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;
    private ListView listView;
    private EntriesAdapter entriesAdapter;
    private Button addEntryButton;
    private TextView rewardTV;
    private EntriesManager entMgr;
    private EntriesDataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!App.mainActivityViewd){
            oneTimeCode();
        }

        //init ui
        addEntryButton = findViewById(R.id.button2);
        rewardTV = findViewById(R.id.rewardTV);

        // setup
        setupListView(getApplicationContext());
        disableButtonIfNotAllowed();
        setupRewardTV();

    }

    private void addUser() {

        App.log(this,"Saving user .. ");

        UserDO user = new UserDO();
        user.setUserId(App.USER_ID);
        user.setFirstName("Omar");

        DynamoDBManager db = new DynamoDBManager(this);
        db.saveUserEncrypted(user);
    }

    private void setupRewardTV() {

        ds = new EntriesDataSource(this);
        Double totalReward = RewardManager.calculateReward(ds.getNumOfItems());
        rewardTV.setText("$"+totalReward);

    }

    private void disableButtonIfNotAllowed() {
        // disable button if not allowed
        entMgr = EntriesManager.getInstance(this);
        // check entry button
        PassingString reason = new PassingString("");
        if(!entMgr.canAddEntry(reason)){
            addEntryButton.setText(reason.getPassingString());
            addEntryButton.setEnabled(false);
        }else{
            addEntryButton.setEnabled(true);
        }
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_item) {

            Intent intent = new Intent(this,PreferencesActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    private void putFakeData() {

        App.entriesList = new ArrayList<>();

        Entry entry = new Entry();
        ArrayList<String> emotions = new ArrayList<>();
        emotions.add(getResources().getStringArray(R.array.emotions)[0]);
        emotions.add(getResources().getStringArray(R.array.emotions)[3]);
        entry.setEmotions(emotions);
        App.entriesList.add(entry);

        entry.setOdor("0");
        entry.setNoise("1");
        entry.setTransportation("4");
        entry.setActive("2");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        entry.setCreationDate(timestamp.toString());

        ArrayList<String> activities = new ArrayList<>();
        activities.add(getResources().getStringArray(R.array.activities)[0]);
        activities.add(getResources().getStringArray(R.array.activities)[1]);
        entry.setActivities(activities);

        entry.setPlace(getResources().getStringArray(R.array.locations)[0]);

        App.NUM_OF_ENTRIES = App.entriesList.size();
        listView = findViewById(R.id.listView);
        entriesAdapter = new EntriesAdapter(getApplicationContext(), App.entriesList);
        listView.setAdapter(entriesAdapter);

    }

    private void oneTimeCode() {

        // run code only one time
        App.mainActivityViewd = true;

        // set main activity referece
        App.mainActivity = this;

        // set the user id and num of entries of this user
        if (App.USER_ID == null){

            IdentityManager mgr = IdentityManager.getDefaultIdentityManager();

            if(mgr != null){
                App.USER_ID = mgr.getCachedUserID();
            }

        }

        // if the user has no user id go to the authentication screen
        if(App.USER_ID == "" || App.USER_ID == null){
            IdentityManager.getDefaultIdentityManager().signOut();
        }

        // check location
        if(!LocationManager.isLocationEnabled(this)){
            goToPermissionsScreen();
        }

        // Create connection with maps
        new MapsConnectionManager(this);


        //check if the user has filled the first time form
        new DynamoDBManager(this).getUserFirstForm();

        // notifications
        setupNotifications();

        // set broadcast reciver for the pluged in to power
        registerPlugedInReciver();

        //setup location reciver
        setupLocationReciver();


    }


    ////////////////// EVENTS /////////////////////////


    public void btnClicked(View view) {
        Intent intent = new Intent(this, PeriodicalFormActivity_1.class);
        startActivity(intent);

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
        setupListView(getApplicationContext());

    }

    private void setupListView(final Context context) {

        ds = new EntriesDataSource(context);

        if(App.entriesList == null){
            App.entriesList = new ArrayList<>();
        }

        // setup list view
        final List<Entry> entriesList = ds.getAllItemsOrderedByDate(true);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                listView = findViewById(R.id.listView);
                entriesAdapter = new EntriesAdapter(context, entriesList);
                listView.setAdapter(entriesAdapter);
            }
        });


        // show/hide first label

        View view = findViewById(R.id.emptyScreenView);

        if(entriesList.size() == 0){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.INVISIBLE);
        }


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
        }

        int eveningTime = 18;
        if(prefMgr.getEveningNotificationTime() != null){
            eveningTime = Integer.parseInt(prefMgr.getMorningNotificationTime());
        }

        // 1
        setNotificationAlarm(morningTime,0,App.NOTIFY_ID);

        // 2
        setNotificationAlarm(eveningTime,0,App.NOTIFY_ID_2);

    }

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
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,callendar.getTime().getTime(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    private void registerPlugedInReciver() {

        BroadcastReceiver receiver = new PowerReciver(this);
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);

    }

    private void setupLocationReciver() {

        Log.d("Tracking","Helooooooo");

        Intent intent = new Intent(getApplicationContext(),LocationReciver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),App.NOTIFY_ID_3,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,Calendar.getInstance().getTimeInMillis(),1000*5,pendingIntent);


    }

}
