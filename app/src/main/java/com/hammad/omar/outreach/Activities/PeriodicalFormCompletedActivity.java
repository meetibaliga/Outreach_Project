package com.hammad.omar.outreach.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Interfaces.CallBackLocation;
import com.hammad.omar.outreach.Managers.LocationManager;
import com.hammad.omar.outreach.Managers.RewardManager;
import com.hammad.omar.outreach.Managers.SyncManager;
import com.hammad.omar.outreach.Provider.EntriesDataSource;
import com.hammad.omar.outreach.R;

public class PeriodicalFormCompletedActivity extends AppCompatActivity implements CallBackLocation{

    private static final String TAG = PeriodicalFormCompletedActivity.class.getSimpleName();


    private Location location;
    private EntriesDataSource entriesDataSource;

    //consts
    private int WAIT_TIME_MILLIS = 2500;

    //ui
    TextView totalTv;
    TextView youEarnedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_completed);

        Log.d(TAG,"ONCREATE PER");

        entriesDataSource = new EntriesDataSource(this);

        //init ui
        totalTv = findViewById(R.id.rewardTextView);
        youEarnedTv = findViewById(R.id.youEarnedTextView);

        // set creation date
        App.inputEntry.setCreationDate(App.getCurrentDateString());

        // set location
        if(LocationManager.isLocationEnabled(this)){

            Log.d(TAG,"YES LOCATION SET");

            new LocationManager(this,this).getCurrentLocation();


        }else{

            Log.d(TAG,"SHOW DIALOG");

            showDialog();


        }

    }

    @Override
    public void onBackPressed() {
        //nothing
    }

    private void setupRewardViews() {

        Double totalReward = RewardManager.calculateReward(entriesDataSource.getNumOfItems());
        Double entryReward = RewardManager.REWARD_PER_ENTRY;

        youEarnedTv.setText(getString(R.string.youErned)+entryReward);
        totalTv.setText("$"+totalReward);

    }

    private void showDialog() {

        // notify user
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.pleaseEnableLocation);

        dialog.setPositiveButton(R.string.enableNow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });

        dialog.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                userDidNotEnableLocation();
            }
        });

        dialog.show();
    }

    private void userDidNotEnableLocation() {

    }

    private void navigateToMain() {

        Log.d(TAG,"Going to main .. ");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void callbackCurrentLocation(Object object) {

        Log.d(TAG,"BACK FROM LOCATION");

        if(object instanceof Location){

            Log.d(TAG,"BACK FROM LOCATION OK");

            location = (Location) object;
            App.inputEntry.setLatLng(location.getLatitude()+"",location.getLongitude()+"");
            backFromLocaion();

        }else{

            Log.d(TAG,"BACK FROM LOCATION NO");

            backFromLocaion();
        }
    }

    private void backFromLocaion(){


        if (App.entriesManager.canAddEntry()) {
            entriesDataSource.insertItem(App.inputEntry);
            App.entriesManager.addDailyEntry();
        }

        // if there is internet it will sync all entries
        SyncManager mgr = new SyncManager(this);
        mgr.syncAll(0);

        // change UI
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                setupRewardViews();
            }
        });

        navigateToNextScreen();


    }

    private void navigateToNextScreen() {

        Log.d(TAG,"navigateToNextScreen");

        // go back after 2 secs from the main thread
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToMain();
            }
        }, WAIT_TIME_MILLIS);

    }
}
