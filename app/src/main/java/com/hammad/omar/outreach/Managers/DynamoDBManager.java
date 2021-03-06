package com.hammad.omar.outreach.Managers;

import android.location.Location;
import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Interfaces.CallBackDB;
import com.hammad.omar.outreach.Models.Entry;
import com.hammad.omar.outreach.Models.EntryDO;
import com.hammad.omar.outreach.Models.LocationDO;
import com.hammad.omar.outreach.Models.RewardDO;
import com.hammad.omar.outreach.Models.UpdateDO;
import com.hammad.omar.outreach.Models.UserDO;
import com.hammad.omar.outreach.Models.UserLocation;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DynamoDBManager {

    private static final String TAG = DynamoDBManager.class.getSimpleName();

    private DynamoDBMapper dynamoDBMapper;
    private CallBackDB callback;

    // callback IDs
    public static int CALL_BACK_ID_GET_ENTRIES = 1;
    public static int CALL_BACK_ID_GET_USER= 2;
    public static int CALL_BACK_ID_ENTRY_SAVED = 3;
    public static int CALL_BACK_ID_LOCATION_SAVED = 4;
    public static int CALL_BACK_ID_USER_SAVED = 5;
    public static int CALL_BACK_ID_REWARD_UPDATED = 6;
    public static final int CALL_BACK_ID_LOCATIONS_BATCH_SAVED = 7;
    public static final int CALL_BACK_ID_ENTRIES_BATCH_SAVED = 8;
    public static final int CALL_BACK_ID_GET_UPDATE = 9;



    //consts
    public static long SAVE_ITEM_TO_DB_RATE = 1 * 1000;


    public DynamoDBManager(CallBackDB callBack){
        initializeDB();
        this.callback = callBack;
    }

    public void getEntries(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                // get num of items
                EntryDO entryDO = new EntryDO();
                entryDO.setUserId(App.USER_ID);
                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withHashKeyValues(entryDO)
                        .withConsistentRead(false)
                        .withLimit(10);
                PaginatedList<EntryDO> result = dynamoDBMapper.query(EntryDO.class, queryExpression);

                callback.callbackDB(result,CALL_BACK_ID_GET_ENTRIES);

            }
        }).start();
    }

    public void getUserFirstForm(){

        if(App.USER_ID == null || App.USER_ID.isEmpty()){
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                // save
                UserDO userDo = new UserDO();
                userDo.setUserId(App.USER_ID);
                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withHashKeyValues(userDo)
                        .withConsistentRead(false);
                PaginatedList<UserDO> result = dynamoDBMapper.query(UserDO.class, queryExpression);
                callback.callbackDB(result,CALL_BACK_ID_GET_USER);

            }
        }).start();
    }

    public void getUpdates(){
        new Thread(new Runnable() {

            @Override
            public void run() {

                // get num of items
                UpdateDO updateDO = new UpdateDO("1");
                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withHashKeyValues(updateDO)
                        .withConsistentRead(false);
                PaginatedList<UpdateDO> result = dynamoDBMapper.query(UpdateDO.class, queryExpression);

                callback.callbackDB(result,CALL_BACK_ID_GET_UPDATE);

            }
        }).start();
    }

    public void saveEntry() {
        saveEntry(App.inputEntry);
    }

    public void saveEntry(final Entry entry){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // save
                dynamoDBMapper.save(new EntryDO(entry));
                Log.d("MainActivity","Entry Saved with ID : " + entry.getEntryId());
                callback.callbackDB(entry,CALL_BACK_ID_ENTRY_SAVED);
            }
        }).start();

    }

    public void saveEntries(final List<Entry> entries,final long rate){

        if(rate == 0){

            new Thread(new Runnable() {
                @Override
                public void run() {

                    // save
                    List<EntryDO> entryDOS = EntryDO.fromEntriesList(entries);
                    dynamoDBMapper.batchSave(entryDOS);
                    Log.d("MainActivity","Entries Saved");
                    callback.callbackDB(entries,CALL_BACK_ID_ENTRIES_BATCH_SAVED);

                }
            }).start();

        }else {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    final Iterator<Entry> i = entries.iterator();
                    final Timer timer = new Timer();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            if (!i.hasNext()) {
                                timer.cancel();
                                return;
                            }
                            Entry entry = i.next();

                            // save
                            dynamoDBMapper.save(new EntryDO(entry));
                            Log.d("MainActivity", "Entry Saved with ID : " + entry.getEntryId());
                            callback.callbackDB(entry, CALL_BACK_ID_ENTRY_SAVED);


                        }
                    }, 0, rate);


                }
            }).start();
        }

    }

    public void saveEntries(final List<Entry> entries){
        saveEntries( entries,SAVE_ITEM_TO_DB_RATE);
    }


    public void saveUserForm(){

        Log.d("DB","HERE ATE SAVE USER");

        new Thread(new Runnable() {

            @Override
            public void run() {
                // save
                dynamoDBMapper.save(App.user);
                Log.d("DB","User Saved");
                callback.callbackDB(null,CALL_BACK_ID_USER_SAVED);
            }

        }).start();

    }

    public void updateUserForm(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                // save
                Log.d("DB","User Saved");
                callback.callbackDB(null,CALL_BACK_ID_USER_SAVED);
            }

        }).start();
    }

    public void saveLocation(final UserLocation location){

        final LocationDO locationDO = new LocationDO(location);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // save
                dynamoDBMapper.save(locationDO);
                Log.d("MainActivity","Location Saved with ID : " + location.get_locationId());
                callback.callbackDB(location,CALL_BACK_ID_LOCATION_SAVED);
            }
        }).start();

    }

    public void saveLocations(final List<UserLocation> locations, final long rate){


        if(rate == 0){

            new Thread(new Runnable() {
                @Override
                public void run() {

                    // save
                    List<LocationDO> locationDOS = LocationDO.fromLocationsList(locations);
                    dynamoDBMapper.batchSave(locationDOS);
                    Log.d("MainActivity","Locations Saved with");
                    callback.callbackDB(locations,CALL_BACK_ID_LOCATIONS_BATCH_SAVED);

                }
            }).start();

        }else{

            new Thread(new Runnable() {

                @Override
                public void run() {

                    final Iterator<UserLocation> i = locations.iterator();
                    final Timer timer = new Timer();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            if(!i.hasNext()){timer.cancel();return;}
                            UserLocation location = i.next();

                            LocationDO locationDO = new LocationDO(location);

                            // save
                            dynamoDBMapper.save(locationDO);
                            Log.d("MainActivity","Location Saved with ID : " + location.get_locationId());
                            callback.callbackDB(location,CALL_BACK_ID_LOCATION_SAVED);

                        }
                    },0,rate);

                }
            }).start();

        }



    }

    public void saveLocations(final List<UserLocation> locations){
        saveLocations(locations,SAVE_ITEM_TO_DB_RATE);
    }

    public void saveUserEncrypted(final UserDO user){

        new Thread(new Runnable() {

            @Override
            public void run() {
                // save
                dynamoDBMapper.save(user);
                Log.d("MainActivity","User Saved with ID : " + user.getUserId());
                callback.callbackDB(user,CALL_BACK_ID_USER_SAVED);
            }
        }).start();

    }

    public void updateReward(double value){

        final RewardDO rewardDO = new RewardDO(App.USER_ID,value);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // save
                dynamoDBMapper.save(rewardDO);
                Log.d(TAG,"Reward updated with ID : " + rewardDO.getUsrId());
                callback.callbackDB(rewardDO,CALL_BACK_ID_USER_SAVED);
            }
        }).start();

    }




    // PRIVATE METHODS

    private void initializeDB() {

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(App.authManager.getCredentialsProvider());

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .build();
    }
}
