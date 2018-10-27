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

    private Context context;

    public PowerReciver(){

    }

    public PowerReciver(Context context){
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("PowerReciver","on recive");
        Toast.makeText(context,"Recive",Toast.LENGTH_SHORT);

        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        if (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB) {

            // if the app is synced dont do anything
            if(App.isSynced){
                App.log(context,"No need to sync .. All Done :)");
                return;
            }

            // on AC power
            App.log(this.context,"AC");
            SyncManager syncManager = new SyncManager(context);
            syncManager.syncAll();

        } else {
            // intent did include extra info
        }

    }
}
