package com.example.omar.outreach.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.omar.outreach.App;
import com.example.omar.outreach.R;

public class SharedPreferencesManager {


    private static SharedPreferencesManager instance;
    private static Context _context;

    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    public static SharedPreferencesManager getInstance(Context context){

        if(instance == null){
            instance = new SharedPreferencesManager(context);
        }

        return instance;
    }

    public SharedPreferencesManager(Context context){

        _context = context;
        editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public void setMorningNotificationTime(int time){
        editor.putString(_context.getResources().getString(R.string.morning_notification_key),time+"").apply();
    }

    public void setEveningNotificationTime(int time){
        editor.putString(_context.getResources().getString(R.string.eveninig_notification_key),time+"").apply();
    }

    public void setMorningNotificationTime(String time){
        editor.putString(_context.getResources().getString(R.string.morning_notification_key),time+"").apply();
    }

    public void setEveningNotificationTime(String time){
        editor.putString(_context.getResources().getString(R.string.eveninig_notification_key),time+"").apply();
    }

    public String getMorningNotificationTime(){
        return preferences.getString(_context.getResources().getString(R.string.morning_notification_key),null);
    }

    public String getEveningNotificationTime(){
        return preferences.getString(_context.getResources().getString(R.string.eveninig_notification_key),null);
    }


    public void setDailyEntries(int dailyEntries){
        editor.putInt(getKey(R.string.num_of_daily_entries_KEY),dailyEntries).apply();
    }

    public void setLastEntryDate(int lastDate){
        editor.putInt(getKey(R.string.last_entry_date_KEY),lastDate).apply();
    }

    public int getDailyEntries(){
        return preferences.getInt(getKey(R.string.num_of_daily_entries_KEY),0);
    }

    public int getLastEntryDate(){
        return preferences.getInt(getKey(R.string.last_entry_date_KEY),0);
    }

    public int getLastEntryHour() {
        return preferences.getInt(getKey(R.string.last_entry_hour_KEY),0);
    }

    public void setLastEntryHour(int lastDate){
        editor.putInt(getKey(R.string.last_entry_hour_KEY),lastDate).apply();
    }

    public String getValue(int key){
        return preferences.getString(getKey(key),null);
    }

    public String getValue(String key){
        return preferences.getString(key,null);
    }

    public Object getValue(int key, App.types type){
        return getValue(getKey(key),type);
    }

    public Object getValue(String key, App.types type){

        switch (type){
            case INT:
                return preferences.getInt(key,0);
            case BOOL:
                return preferences.getBoolean(key,false);
            case STRING:
                return preferences.getString(key,null);
            default:
                return null;
        }
    }

    private String getKey(int rId) {
        return _context.getResources().getString(rId);
    }



}