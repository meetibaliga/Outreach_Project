package com.example.omar.outreach.Managers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackAuth;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.LocationDO;
import com.example.omar.outreach.Models.UserLocation;
import com.example.omar.outreach.Provider.EntriesDataSource;
import com.example.omar.outreach.Provider.LocationsDataSource;

import java.util.ArrayList;
import java.util.List;

public class SyncManager implements CallBackDB, CallBackAuth{

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

                sync();

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
                    locationsDataSource.updateItem(location);
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
