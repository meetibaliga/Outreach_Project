package com.example.omar.outreach;

import android.app.Application;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.Models.UserDO;
import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;

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
