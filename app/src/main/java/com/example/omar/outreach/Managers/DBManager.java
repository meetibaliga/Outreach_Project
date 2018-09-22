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
import com.example.omar.outreach.Model.EntryDO;

public class DBManager {

    private DynamoDBMapper dynamoDBMapper;
    private CallBackDB callback;

    public DBManager(){
        this(null);
    }

    public DBManager(CallBackDB callBack){
        initializeDB();
        this.callback = callBack;
    }

    public void setNumberOfEntries(){

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
                App.NUM_OF_ENTRIES = result.size();

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
                callback.callbackDB(null);
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
