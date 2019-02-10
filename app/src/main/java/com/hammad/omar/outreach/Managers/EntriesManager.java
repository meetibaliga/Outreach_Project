package com.hammad.omar.outreach.Managers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Helping.FormEntries.PassingString;

public class EntriesManager {

    private static final String TAG = EntriesManager.class.getSimpleName();


    //vars
    private static EntriesManager instance;
    private Context context;
    SharedPreferencesManager prefMgr;

    // constants
    public static final int MAX_NUM_OF_DAILY_ENTRIES = 2;
    public static final int MINIMUM_HOURS_BETWEEN_ENTRIES = 3;
    public int numOfDailyUserInteries = 0;
    public int lastEntryDateAdded;
    public int lastEntryHourAdded;

    // get instance
    public static EntriesManager getInstance(Context context) {
        if(instance == null){
            instance = new EntriesManager(context);
        }
        return instance;
    }

    // public constructor

    public EntriesManager(Context context){

        prefMgr = SharedPreferencesManager.getInstance(context);
        this.context = context;
        this.numOfDailyUserInteries = prefMgr.getDailyEntries();
        this.lastEntryDateAdded = prefMgr.getLastEntryDate();
        this.lastEntryHourAdded = prefMgr.getLastEntryHour();

    }

    // Entries limit

    public boolean canAddEntry(@Nullable PassingString reason){

        int todayDate = App.getTodayDayOfMonth();

        if( todayDate != lastEntryDateAdded ){
            resetNumOfDailyEntries();
        }

        boolean can = true;

        if(isEntriesLimitReached()){
            can = false;
            reason.setPassingString("You can add an entry tomorrow");
        }else if(!isMinimumHoursReached()){
            can = false;
            reason.setPassingString("You can add an entry after " + getRemainingHours()+ " Hours");
        }

        return can;
    }

    public boolean canAddEntry(){

        return canAddEntry(new PassingString(""));
    }

    public void addDailyEntry(){
        numOfDailyUserInteries++;
        lastEntryDateAdded = App.getTodayDayOfMonth();
        lastEntryHourAdded = App.getNowHourOfDay();
        updateDataSource();
    }

    public void resetNumOfDailyEntries(){
        numOfDailyUserInteries = 0;
        updateDataSource();
    }

    //////////////// PRIVATE HELPING METHODS ///////////////

    private void updateDataSource(){
        prefMgr.setDailyEntries(numOfDailyUserInteries);
        prefMgr.setLastEntryDate(lastEntryDateAdded);
        prefMgr.setLastEntryHour(lastEntryHourAdded);
    }

    private boolean isMinimumHoursReached() {

        if (lastEntryDateAdded != App.getTodayDayOfMonth()){
            return true;
        }

        return lastEntryHourAdded + MINIMUM_HOURS_BETWEEN_ENTRIES <= App.getNowHourOfDay();

    }

    private boolean isEntriesLimitReached() {
        return numOfDailyUserInteries == MAX_NUM_OF_DAILY_ENTRIES;
    }

    private int getRemainingHours(){

        Log.d(TAG,"last hour : " + lastEntryHourAdded);
        Log.d(TAG,"min hours : " + MINIMUM_HOURS_BETWEEN_ENTRIES);
        Log.d(TAG,"NOW : " + App.getNowHourOfDay());

        return lastEntryHourAdded + MINIMUM_HOURS_BETWEEN_ENTRIES - App.getNowHourOfDay() ;
    }
}
