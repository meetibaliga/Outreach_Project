package com.hammad.omar.outreach.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.hammad.omar.outreach.Activities.AboutActivity;
import com.hammad.omar.outreach.Activities.CommunityActivity;
import com.hammad.omar.outreach.Activities.MainActivity;
import com.hammad.omar.outreach.R;

public class HowTo extends YouTubeBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    YouTubePlayerView youtubePlayer;
    YouTubePlayer.OnInitializedListener mOninitializedLister;
    private String YT_API_KEY = "AIzaSyAuznTjuv35yWT7z3qB7b-IPJBYuV3AVbI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        setupNav();
        setupYoutubePlayer();

    }

    private void setupYoutubePlayer() {

        youtubePlayer = findViewById(R.id.youtubePlayerView);

        mOninitializedLister = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo("XnrnwwCYcus");

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        // init
        youtubePlayer.initialize(YT_API_KEY,mOninitializedLister);

    }

    private void setupNav(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        setTitle("How To Use The App");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_how_to);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_how_to);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.how_to, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent nextIntent;

        switch (id){
            case R.id.HomeScreen:
                nextIntent = new Intent(this,MainActivity.class);;
                break;
            case R.id.CommunityScreen:
                nextIntent = new Intent(this,CommunityActivity.class);
                break;
            case R.id.AboutScreen:
                nextIntent = new Intent(this,AboutActivity.class);
                break;
            default:
                nextIntent = null;
        }

        if(nextIntent != null)
            startActivity(nextIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_how_to);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
