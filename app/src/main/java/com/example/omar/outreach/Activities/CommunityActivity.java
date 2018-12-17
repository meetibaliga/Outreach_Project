package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.omar.outreach.Interfaces.CallBackLambda;
import com.example.omar.outreach.Managers.LambdaManager;
import com.example.omar.outreach.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;


public class CommunityActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CallBackLambda {

    private static final String TAG = CommunityActivity.class.getSimpleName();

    //ui
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        //init
        progress = findViewById(R.id.progress);

        //setup
        setupNav();
        setupUI();
        loadData();
    }

    private void setupNav() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_community);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupUI(){

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_community);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.community, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent nextIntent;

        switch (id){
            case R.id.HomeScreen:
                nextIntent = new Intent(this,MainActivity.class);
                break;
            case R.id.CommunityScreen:
                nextIntent = new Intent(this,CommunityActivity.class);
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
                nextIntent = new Intent(this,MainActivity.class);
                break;
            case R.id.CommunityScreen:
                nextIntent = null;
                break;
            default:
                nextIntent = null;
        }

        if(nextIntent != null)
            startActivity(nextIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_community);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadData(){

        // show progress
        showProgress(true);

        // call api
        callGetNumOfEmotionsToday();

    }

    private void showProgress(boolean show){
        progress.setVisibility(show?View.VISIBLE:View.GONE);
    }

    private void callGetNumOfEmotionsToday(){

        LambdaManager lambda = LambdaManager.getInstance(this,this);
        lambda.getNumOfEmotionsToday();

    }

    public void getResults(View view) {
        callGetNumOfEmotionsToday();
    }

    @Override
    public void callbackLambda(Object results, Object error, String callbackId) {

        if(error != null){
            Log.d(TAG,"error"+error);
            return;
        }

        // no error
        if (!(results instanceof Map)){
            Log.d(TAG,"results not a map");
            return;
        }

        // results is a map
        Map<String,Object> resultMap = (Map<String,Object>) results;
        Log.d(TAG,"result map" + resultMap);

        // result map ok
        if (!resultMap.containsKey("body")){
            Log.d(TAG,"no body");
            return;
        }

        // we have a body
        String body = (String)resultMap.get("body");
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Gson gson = new Gson();
        Map<String,String> bodyMap = gson.fromJson(body,type);

        if(bodyMap == null){
            Log.d(TAG,"body map is null");
            return;
        }

        // we have a body map not null
        for ( Map.Entry<String,String> entry : bodyMap.entrySet() ){
            Log.d(TAG,entry.getKey() + " " + entry.getValue());
            // you have the results .. show me what will you do :)

        }

    }
}
