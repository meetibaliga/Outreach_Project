package com.hammad.omar.outreach.Managers;

public final class RewardManager {

    public static Double MAX_REWARD = 10.0;
    public static Double REWARD_PER_ENTRY = 0.08;

    public static Double

    calculateReward(int numOfEntries){
        if( numOfEntries * REWARD_PER_ENTRY < MAX_REWARD ){
            return numOfEntries * REWARD_PER_ENTRY;
        }else{
            return MAX_REWARD;
        }
    }



}
