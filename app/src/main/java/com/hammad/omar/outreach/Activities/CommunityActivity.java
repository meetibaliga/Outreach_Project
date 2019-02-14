package com.hammad.omar.outreach.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Interfaces.CallBackLambda;
import com.hammad.omar.outreach.Managers.LambdaManager;
import com.hammad.omar.outreach.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;


public class CommunityActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CallBackLambda {

    private static final String TAG = CommunityActivity.class.getSimpleName();

    //ui
    ProgressBar progress;
    ImageView emojies[];
    TextView percentages[];

    //data
    static ArrayList<EmotionCount> emotionsCount;

    //flas
    static boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        //init
        progress = findViewById(R.id.progress);
        emotionsCount = new ArrayList();

        //setup
        setupNav();
        setupUI();
        loadData();
    }

    private void setupNav() {

        setTitle(getString(R.string.communityTitle));

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

        emojies = new ImageView[12];
        percentages = new TextView[12];

        for (int i = 1 ; i <= 12 ; i++){
            int img_id = getResources().getIdentifier("img_"+i,"id",getPackageName());
            ImageView img = findViewById(img_id);
            emojies[i-1] = img;
            int txt_id = getResources().getIdentifier("txt_"+i,"id",getPackageName());
            TextView tv = findViewById(txt_id);
            percentages[i-1] = tv;

            //hide them
            img.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
        }


    }

    private void showFaces(){
        for (int i = 1 ; i <= 12 ; i++){
            emojies[i-1].setVisibility(View.VISIBLE);
            percentages[i-1].setVisibility(View.VISIBLE);
        }
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

        finish();
        startActivity(getIntent());
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
            case R.id.AboutScreen:
                nextIntent = new Intent(this,AboutActivity.class);;
                break;
            case R.id.HowToScreen:
                nextIntent = new Intent(this,HowTo.class);
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

        showProgress(false);

        if(error != null){
            Log.d(TAG,"error"+error);
            showErrorLabel(true);
            return;
        }

        // no error
        if (!(results instanceof Map)){
            Log.d(TAG,"results not a map");
            return;
        }

        loaded = true;

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

        // 1. add the map to an array

        ArrayList<String> loadedEmotions = new ArrayList<>();
        int totalCounts = 0;

        for ( Map.Entry<String,String> entry : bodyMap.entrySet() ){
            Log.d(TAG,entry.getKey() + " " + entry.getValue());

            EmotionCount emotionCount = new EmotionCount(entry.getKey(),Integer.parseInt(entry.getValue()));
            emotionsCount.add(emotionCount);
            loadedEmotions.add(entry.getKey());
            totalCounts += Integer.parseInt(entry.getValue());

        }

        if(totalCounts == 0){
            showNoEmotionsLabel(true);
            return;
        }

        Log.d(TAG,"loaded emotions : "+ loadedEmotions);

        // 2. check the missing ones

        String emotionsArray[] = getResources().getStringArray(R.array.emotions);
        ArrayList<String> allEmotionsList = new ArrayList<>(Arrays.asList(emotionsArray));

        Log.d(TAG,"all emotions : " + allEmotionsList);

        for (String item: allEmotionsList){
            if(!loadedEmotions.contains(item)){
                EmotionCount emotionCount = new EmotionCount(item,0);
                emotionsCount.add(emotionCount);
            }
        }

        // 3. sort the emotionsCount array

        Collections.sort(emotionsCount);
        Log.d(TAG,emotionsCount.toString());

        // 4. show them

        for ( int i = 0 ; i < 12 ; i++){
            emojies[i].setVisibility(View.VISIBLE);
            percentages[i].setVisibility(View.VISIBLE);
        }

        // 5. map them to the ui

        for ( int i = 0 ; i < 12 ; i++){

            Log.d(TAG,"i:"+i);
            emojies[i].setImageResource(App.getImageResourceMappedWith(emotionsCount.get(i).emotion,this));

            // calculate percentage
            int per = (int)(emotionsCount.get(i).count / totalCounts * 100);
            per = Math.round(per);
            percentages[i].setText(per+"%"+"\n"+emotionsCount.get(i).emotion);

            // calculate sizes for the second row and go on
            if(i < 1){
                percentages[i].setText(per+"% "+emotionsCount.get(i).emotion);
                continue;
            }

            double relativeRatio = emotionsCount.get(i).count / emotionsCount.get(1).count;
            double refSize = emojies[1].getLayoutParams().height;

            if (relativeRatio == 0){
                relativeRatio = 0.2;
            }

            // set the size
            emojies[i].getLayoutParams().height = (int) (refSize * relativeRatio);
            emojies[i].getLayoutParams().width = (int) (refSize * relativeRatio);
            emojies[i].requestLayout();
        }

    }

    private void showNoEmotionsLabel(boolean show) {
        TextView noEmotions = findViewById(R.id.noEmotionsLabel);
        noEmotions.setVisibility(show?View.VISIBLE:View.GONE);
    }

    private void showErrorLabel(boolean show){
        TextView errorLabel = findViewById(R.id.errorLoadingLabel);
        errorLabel.setVisibility(show?View.VISIBLE:View.GONE);
    }

}

class EmotionCount implements Comparable{

    String emotion;
    double count;

    public EmotionCount(String emotion,int count){
        this.emotion = emotion;
        this.count = count;
    }




    @Override
    public int compareTo(@NonNull Object o) {
        EmotionCount emotionCount = (EmotionCount)o;
        return (int)(emotionCount.count - this.count);
    }

    @Override
    public String toString() {
        return "EmotionCount{" +
                "emotion='" + emotion + '\'' +
                ", count=" + count +
                '}';
    }
}
