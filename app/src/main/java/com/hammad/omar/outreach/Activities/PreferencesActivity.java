package com.hammad.omar.outreach.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Fragments.PreferencesFragment;
import com.hammad.omar.outreach.Interfaces.CallBackSync;
import com.hammad.omar.outreach.Managers.AuthManager;
import com.hammad.omar.outreach.Managers.SharedPreferencesManager;
import com.hammad.omar.outreach.Managers.SyncManager;
import com.hammad.omar.outreach.Provider.EntriesDataSource;
import com.hammad.omar.outreach.Provider.LocationsDataSource;
import com.hammad.omar.outreach.R;

public class PreferencesActivity extends AppCompatActivity implements CallBackSync {

    private static final String TAG = PreferencesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //title
        setTitle("Settings");

        //init fragment
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pref, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.signout_item) {

            // if synced go to synced

            if (App.isSynced) {
                synced();
                return true;
            }else{
                // sync all
                SyncManager mgr = new SyncManager(PreferencesActivity.this);
                mgr.syncAll(PreferencesActivity.this,0);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void callbackSync(int callbackId) {

        if(callbackId == SyncManager.CALL_BACK_SYNC_FAILED){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Toast.makeText(PreferencesActivity.this,"Logout Failed..",Toast.LENGTH_SHORT).show();
                }
            });
        }else if (callbackId == SyncManager.CALL_BACK_SYNC_SUCESS){
            synced();
        }

    }


    private void synced(){
        signout();
    }

    private void signout(){

        boolean signedOut = App.authManager.signout();

        if (signedOut) {
            // go to auth screen
            deleteAllItemsFromApp();
            Intent intent = new Intent(this, AuthActivity.class);
            intent.putExtra("from","signout");
            startActivity(intent);
        }
    }

    private void deleteAllItemsFromApp() {

        // delete all items
        EntriesDataSource entriesDataSource = new EntriesDataSource(this);
        entriesDataSource.deleteAllItems();

        LocationsDataSource locationsDataSource = new LocationsDataSource(this);
        locationsDataSource.deleteAllItems();

        SharedPreferencesManager prf = SharedPreferencesManager.getInstance(this);
        prf.clearAll();
    }

}
