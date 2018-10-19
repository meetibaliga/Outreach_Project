package com.example.omar.outreach.Managers;

import android.util.Log;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.Models.UserDO;

import java.util.ArrayList;

public class DBManager {

    private DynamoDBMapper dynamoDBMapper;
    private CallBackDB callback;

    // callback IDs

    public static int CALL_BACK_ID_GET_ENTRIES = 1;
    public static int CALL_BACK_ID_GET_USER= 2;


    public DBManager(){
        this(null);
    }

    public DBManager(CallBackDB callBack){
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
                        .withConsistentRead(false);
                PaginatedList<EntryDO> result = dynamoDBMapper.query(EntryDO.class, queryExpression);

                callback.callbackDB(result,CALL_BACK_ID_GET_ENTRIES);

            }
        }).start();
    }

    public void getUserFirstForm(){

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

    public void saveEntry() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // save
                dynamoDBMapper.save(App.inputEntry);
                Log.d("MainActivity","Entry Saved with ID : "+App.inputEntry.getEntryId());
                callback.callbackDB(null,0);
            }
        }).start();

    }

    public void saveUserForm(){

        Log.d("DB","HERE ATE SAVE USER");

        new Thread(new Runnable() {
            @Override
            public void run() {
                // save
                dynamoDBMapper.save(App.user);
                Log.d("DB","User Saved");
                callback.callbackDB(null,0);
            }
        }).start();

    }


    // PRIVATE METHODS

    private void initializeDB() {

        // AWSMobileClient enables AWS user credentials to access your table

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

        // Add code to instantiate a AmazonDynamoDBClient

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();
    }
}
