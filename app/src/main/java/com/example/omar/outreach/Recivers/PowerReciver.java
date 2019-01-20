package com.example.omar.outreach.Recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Managers.DynamoDBManager;
import com.example.omar.outreach.Managers.SyncManager;

public class PowerReciver extends BroadcastReceiver {

    private static final String TAG = PowerReciver.class.getSimpleName();

    private Context context;

    public PowerReciver(){} // to shut up the compiler

    public PowerReciver(Context context){
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // is charging
        Log.d(TAG,"Power Recived");


        // if the app is synced dont do anything
        if(App.isSynced){
            Log.d(TAG,"No need return");
            return;
        }

        // on AC power
        Log.d(TAG,"AC");
        SyncManager syncManager = new SyncManager(context);
        syncManager.syncAll();

    }
}
