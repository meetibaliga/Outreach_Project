package com.hammad.omar.outreach.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Calendar;

@DynamoDBTable(tableName = "Outreach_Reward")

public class RewardDO {

    private String usrId;
    private double reward;
    private long lastUpdated;

    public RewardDO(String usrId, double reward) {
        this.usrId = usrId;
        this.reward = reward;
        this.lastUpdated = System.currentTimeMillis();
    }

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    @DynamoDBAttribute(attributeName = "totalReward")
    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }


    @DynamoDBAttribute(attributeName = "lastUpdated")
    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "RewardDO{" +
                "usrId='" + usrId + '\'' +
                ", reward=" + reward +
                '}';
    }
}
