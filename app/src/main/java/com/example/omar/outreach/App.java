package com.example.omar.outreach;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.Models.UserDO;
import com.google.android.gms.common.util.ArrayUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class App extends Application {

    public static EntryDO inputEntry = new EntryDO();
    public static UserDO user = new UserDO();
    public static String USER_ID;
    public static int NUM_OF_ENTRIES;
    public static boolean mainActivityViewd = false;
    public static int NOTIFY_ID = 1001;
    public static int NOTIFY_ID_2 = 1002;
    public static HashMap<String,String> imagesNames;

    @Override
    public void onCreate() {

        super.onCreate();
        imagesNames = populateEmojiesMapWithDrawables();
    }

    public static String getCurrentDateString(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString();
    }

    public static boolean intToBool(int integer){
        return integer == 1;
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


    public static void log(Context context, String log) {

        Log.d(context.getClass().getSimpleName(),log);
        Toast.makeText(context,log,Toast.LENGTH_SHORT);

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

    private void populateEmojiesMap() {

        String[] emotions = getResources().getStringArray(R.array.emotions);
        String[] activities = getResources().getStringArray(R.array.activities);
        String[] locations = getResources().getStringArray(R.array.locations);

        String[] emotions_faces =  getResources().getStringArray(R.array.emotions_faces);
        String[] activities_faces =  getResources().getStringArray(R.array.activities_faces);
        String[] locations_faces =  getResources().getStringArray(R.array.locations_faces);

        String[] allStrings = ArrayUtils.concat(emotions,activities,locations);
        String[] allFaces = ArrayUtils.concat(emotions_faces,activities_faces,locations_faces);

        imagesNames = new HashMap<>();
        for(int i = 0 ; i < allStrings.length ; i++){
            imagesNames.put(allStrings[i],allFaces[i]);
        }

        String[] envEmojies = getResources().getStringArray(R.array.env_faces);

        // env part
        imagesNames.put("air",envEmojies[0]);
        imagesNames.put("noise",envEmojies[1]);
        imagesNames.put("trans",envEmojies[2]);
        imagesNames.put("active",envEmojies[3]);

    }
}
