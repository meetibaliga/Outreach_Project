package com.example.omar.outreach;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.omar.outreach.Helping.FormEntries.FormEntry;
import com.example.omar.outreach.Managers.AuthManager;
import com.example.omar.outreach.Managers.EntriesManager;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.UserDO;
import com.example.omar.outreach.Provider.EntriesDataSource;
import com.example.omar.outreach.Provider.LocationsDataSource;
import com.google.android.gms.common.util.ArrayUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    // models
    public static Entry inputEntry; // initialized every time a new entry is beign added
    public static UserDO user = new UserDO();
    public static String USER_ID;
    public static int NUM_OF_ENTRIES;
    public static AuthManager authManager;
    public static EntriesManager entriesManager;

    // lists
    public static ArrayList<Entry> entriesList;
    public static HashMap<String,String> imagesNames;

    // constants
    public static int NOTIFY_ID = 1001;
    public static int NOTIFY_ID_2 = 1002;
    public static int NOTIFY_ID_3 = 0;
    public static String sourceDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
    public static String simpleDateFormat = "EEEE, dd MMM";

    // flags
    public static boolean isSynced = false;
    public static boolean mainActivityViewd = false;

    //enums
    public enum types{INT,STRING,BOOL}

    //activites
    public static Activity mainActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        imagesNames = populateEmojiesMapWithDrawables();
        isSynced = checkIfAppIsSynced();
        authManager = AuthManager.getInstance(getApplicationContext());
        entriesManager = EntriesManager.getInstance(getApplicationContext());
    }



    //////////////////// PUBLIC HELPING METHODS /////////////////////////

    public static int getTodayDayOfMonth(){

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return day;
    }

    public static int getNowHourOfDay() {

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        Log.d(TAG,"Hour from App " + hour);

        return hour;

    }


    public static String getCurrentDateString(){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString();

    }

    public static String getDateFromDateString(String dateString){

        String day = dateString.trim().substring(0, dateString.indexOf(" "));
        return day;
    }

    public static String getDateInFormat(String format,String dateString){

        try {
            Date date = new SimpleDateFormat(sourceDateFormat).parse(dateString);
            String newDateString = new SimpleDateFormat(format).format(date);
            return newDateString;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static boolean intToBool(int integer){
        return integer == 1;
    }

    public static int boolToInt(boolean bool) {
        if(bool){
            return 1;
        }else{
            return 0;
        }
    }

    public static String arrayListToJSON(List<String> list, String name){

        JSONObject json = new JSONObject();

        try {
            json.put(name, new JSONArray(list));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    public static List<String> JSONtoArrayList(String jsonString,String name){

        JSONObject json = null;

        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.d("TestActivity","cannot extract json from string");
            e.printStackTrace();

        }

        JSONArray jsonArray = json.optJSONArray(name);
        ArrayList<String> list = new ArrayList<String>();

        if (jsonArray != null) {

            for (int i=0;i<jsonArray.length();i++){

                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    Log.d("TestActivity","cannot get string from json array");
                    e.printStackTrace();
                }

            }
        }

        return list;

    }

    public static Map<String,String> JSONToMap(String json) {

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("App","cannot create json from string");
        }

        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){

            String key = (String)keys.next();
            String value = null;

            try {
                value = jObject.getString(key);
            } catch (JSONException e) {
                Log.d("App","cannot get value from json object");
                e.printStackTrace();
            }

            map.put(key, value);
        }

        return map;

    }

    public static String mapToJSON(Map<String,String> map){
        return new JSONObject(map).toString();
    }


    public static void log(final Context context, final String log) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Log.d(context.getClass().getSimpleName(),log);
                Toast.makeText(context,log,Toast.LENGTH_SHORT).show();
            }
        });



    }

    public static boolean hasActiveInternetConnection(Context context) {

        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                log(context, "Error checking internet connection");
            }
        } else {
            log(context, "No network available!");
        }
        return false;
    }

    public static String intToDayTime(int intTime){

        Log.d("PrefAct",intTime+"");

        if( intTime < 1 || intTime > 24 ){
            return "invalid";
        }else if(intTime < 12){
            return (intTime+":"+"00"+"AM");
        }else if (intTime == 12){
            return intTime+":"+"00"+"PM";
        }else if (intTime > 12 && intTime < 24){
            return intTime-12+":"+"00"+"PM";
        }else{
            return intTime-12+":"+"00"+"AM";
        }

    }

    public static boolean checkForm(List<FormEntry> formEntries,Context context){
        // check form
        for(FormEntry f: formEntries){
            if(f.isEmpty()){
                Toast.makeText(context,context.getString(R.string.fillForm),Toast.LENGTH_LONG);
                return false;
            }
        }
        return true;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }




















    /////////////////////////////// PRIVATE METHODS ////////////////////////////////

    private HashMap<String,String> populateEmojiesMapWithDrawables() {

        // Keys
        String[] emotions = getResources().getStringArray(R.array.emotions);
        String[] activities = getResources().getStringArray(R.array.activities);
        String[] locations = getResources().getStringArray(R.array.locations);
        String[] envs = getResources().getStringArray(R.array.env_Images_names);

        // Values
        String[] emotions_Images_Names =  getResources().getStringArray(R.array.emotions_image_names);
        String[] activities_Images_Names =  getResources().getStringArray(R.array.activities_images_names);
        String[] locations_Images_Names =  getResources().getStringArray(R.array.locations_images_names);
        String[] envs_Images_Names = getResources().getStringArray(R.array.env_Images_names);

        // Concat
        String[] allStrings = ArrayUtils.concat(emotions,activities,locations,envs);
        String[] allFaces = ArrayUtils.concat(emotions_Images_Names,activities_Images_Names,locations_Images_Names,envs_Images_Names);

        // Create a map
        HashMap<String,String> imagesNames = new HashMap<>();
        for(int i = 0 ; i < allStrings.length ; i++){
            imagesNames.put(allStrings[i],allFaces[i]);
        }

        return imagesNames;

    }

    private boolean checkIfAppIsSynced() {

        EntriesDataSource ds = new EntriesDataSource(this);
        LocationsDataSource ld = new LocationsDataSource(this);

        return !ds.hasDirtyData() && !ld.hasDirtyData();

    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
