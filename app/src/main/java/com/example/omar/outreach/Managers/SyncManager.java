package com.example.omar.outreach.Managers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.Provider.EntriesDataSource;

import java.util.ArrayList;
import java.util.List;

public class SyncManager implements CallBackDB{

    private DynamoDBManager db;
    EntriesDataSource dataSource;
    private Context context;
    private int numOfDirty = 0;

    public SyncManager(Context context){
        this.context = context;
        db = new DynamoDBManager(this);
        dataSource = new EntriesDataSource(context);
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
                App.log(context,"Syncing..");
                syncEntries();
                syncUserInfo();
                syncLocations();

            }
        }).start();

    }

    public void syncEntries(){

        List<Entry> dirtyEntries = getDirtyEntriesList();
        numOfDirty = dirtyEntries.size();

        if( numOfDirty == 0 ) {
            App.isSynced = true;
        }

        for( Entry entry : dirtyEntries ){
            db.saveEntry(entry);
        }

    }

    public void syncUserInfo(){

    }

    public void syncLocations(){

    }

    @Override
    public void callbackDB(final Object object, final int callbackId) {

        // change UI
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {

                App.log(context,"In call back .. ");

                if(callbackId == DynamoDBManager.CALL_BACK_ID_ENTRY_SAVED){
                    App.log(context,"Item Saved .. ");
                    Entry entry = (Entry)object;
                    entry.setDirty(false); // synced
                    dataSource.updateItem(entry);
                    numOfDirty--;

                    if(numOfDirty == 0){
                        App.isSynced = true;
                        App.log(context,"App Synced .. ");

                    }

                }

            }
        });



    }

    private List<Entry> getDirtyEntriesList() {

        List<Entry> allEntries = dataSource.getAllEntries();
        List<Entry> dirtyEntries = new ArrayList<>();

        for( Entry entry: allEntries){

            App.log(context,entry.isDirt()+"");

            if(entry.isDirt()){
                dirtyEntries.add(entry);
            }

        }

        return dirtyEntries;

    }
}
