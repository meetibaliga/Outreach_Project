package com.hammad.omar.outreach.Managers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Interfaces.CallBackAuth;
import com.hammad.omar.outreach.Interfaces.CallBackDB;
import com.hammad.omar.outreach.Models.Entry;
import com.hammad.omar.outreach.Models.LocationDO;
import com.hammad.omar.outreach.Models.UserLocation;
import com.hammad.omar.outreach.Provider.EntriesDataSource;
import com.hammad.omar.outreach.Provider.LocationsDataSource;

import java.util.ArrayList;
import java.util.List;

public class SyncManager implements CallBackDB, CallBackAuth{

    private static final String TAG = SyncManager.class.getSimpleName();

    private DynamoDBManager db;
    EntriesDataSource entriesDataSource;
    LocationsDataSource locationsDataSource;
    private Context context;
    private int numOfDirtyItems = 0;
    private CallBackAuth callBackAuth = this;

    public SyncManager(Context context){

        this.context = context;
        db = new DynamoDBManager(this);
        entriesDataSource = new EntriesDataSource(context);
        locationsDataSource = new LocationsDataSource(context);

    }

    public void syncAll(){

        new Thread(new Runnable() {

            @Override
            public void run() {

                // check battery : if the battery is charging it will sync .. otherwise if it is more than 20% it will sync
                // if it is less than 20% and not charging it will not sync ..

                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = context.registerReceiver(null, ifilter);
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;

                if(!isCharging){
                    int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                    int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                    float batteryPct = level / (float)scale;
                    if(batteryPct < 0.2){
                        App.log(context,"low battery .. not syncing");
                        return;
                    }
                }


                // if there is no internet return

                if(!App.hasActiveInternetConnection(context)){
                    App.log(context,"No internet connection");
                    return;
                }

                // if the user is not signed in

                if(!AuthManager.isSignedIn()) {
                    String uid = SharedPreferencesManager.getInstance(context).getUserId();
                    String pass = SharedPreferencesManager.getInstance(context).getUserPassword();
                    AuthManager.getInstance(context).signinUser(uid, pass, callBackAuth);
                    return;
                }

                // user is signed in and there is connection

                sync();

            }
        }).start();

    }

    public void syncAllEntries(){

        new Thread(new Runnable() {

            @Override
            public void run() {

                // if there is no internet return

                if(!App.hasActiveInternetConnection(context)){
                    App.log(context,"No internet connection");
                    return;
                }

                // if the user is not signed in

                if(!AuthManager.isSignedIn()){
                    String uid = SharedPreferencesManager.getInstance(context).getUserId();
                    String pass = SharedPreferencesManager.getInstance(context).getUserPassword();
                    AuthManager.getInstance(context).signinUser(uid,pass,callBackAuth);
                    return;
                }

                // user is signed in and there is connection
                syncEntries();

            }
        }).start();

    }

    public void sync(){

        syncEntries();
        syncUserInfo();
        syncLocations();

        if(numOfDirtyItems == 0){
            App.log(context,"App Synced .. ");
        }
    }

    public void syncEntries(){

        List<Entry> dirtyEntries = getDirtyEntriesList();
        numOfDirtyItems += dirtyEntries.size();
        db.saveEntries(dirtyEntries);

    }

    public void syncUserInfo(){

    }

    public void syncLocations(){

        List<UserLocation> dirtyLocations = getDirtyLocations();

        Log.d("Sync",dirtyLocations.toString());

        numOfDirtyItems += dirtyLocations.size();

        db.saveLocations(dirtyLocations);

    }

    public void syncReward(){

        EntriesDataSource ds = new EntriesDataSource(context);
        double value = RewardManager.calculateReward(ds.getNumOfItems());
        db.updateReward(value);

    }

    @Override
    public void callbackDB(final Object object, final int callbackId) {

        // change UI
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {

                App.log(context,"In call back db .. ");

                if(callbackId == DynamoDBManager.CALL_BACK_ID_ENTRY_SAVED){

                    App.log(context,"Item Saved .. ");
                    Entry entry = (Entry)object;
                    entry.setDirty(false); // synced
                    entriesDataSource.updateItem(entry);
                    numOfDirtyItems--;


                }else if (callbackId == DynamoDBManager.CALL_BACK_ID_LOCATION_SAVED){

                    App.log(context,"Item Saved .. ");
                    UserLocation location = (UserLocation) object;
                    location.set_isDirty(false); // synced
                    locationsDataSource.deleteItem(location);
                    numOfDirtyItems--;

                }

                if(numOfDirtyItems == 0){
                    App.isSynced = true;
                    App.log(context,"App Synced .. ");
                }

            }
        });



    }

    private List<Entry> getDirtyEntriesList() {

        List<Entry> allEntries = entriesDataSource.getAllItems();
        List<Entry> dirtyEntries = new ArrayList<>();

        for( Entry entry: allEntries ){

            if(entry.isDirt()){
                dirtyEntries.add(entry);
            }

        }

        return dirtyEntries;

    }

    private List<UserLocation> getDirtyLocations(){

        List<UserLocation> dirtyLocations = locationsDataSource.getDirtyItems();
        return dirtyLocations;

    }

    @Override
    public void callbackAuth(Object object, int callbackId) {

        if(object instanceof CognitoUser){
            sync();
        }

    }
}
