package com.example.omar.outreach.Model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "outreach-mobilehub-787670546-Entry")

public class EntryDO implements Serializable {
    private String _userId;
    private String _entryId;
    private String _active;
    private String _activity;
    private String _creationDate;
    private List<String> _emotions;
    private String _noise;
    private String _odor;
    private String _place;
    private String _transportation;

    public EntryDO(){
        _userId = "";
        _entryId = "0";
        _active = "0";
        _activity = "0";
        _creationDate = "0";
        _emotions = new ArrayList<>();
        _noise = "0";
        _odor = "0";
        _place = "0";
        _transportation = "0";
    }

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "entryId")
    @DynamoDBAttribute(attributeName = "entryId")
    public String getEntryId() {
        return _entryId;
    }

    public void setEntryId(final String _entryId) {
        this._entryId = _entryId;
    }
    @DynamoDBAttribute(attributeName = "active")
    public String getActive() {
        return _active;
    }

    public void setActive(final String _active) {
        this._active = _active;
    }
    @DynamoDBAttribute(attributeName = "activity")
    public String getActivity() {
        return _activity;
    }

    public void setActivity(final String _activity) {
        this._activity = _activity;
    }
    @DynamoDBAttribute(attributeName = "creationDate")
    public String getCreationDate() {
        return _creationDate;
    }

    public void setCreationDate(final String _creationDate) {
        this._creationDate = _creationDate;
    }
    @DynamoDBAttribute(attributeName = "emotions")
    public List<String> getEmotions() {
        return _emotions;
    }

    public void setEmotions(final List<String> _emotions) {
        this._emotions = _emotions;
    }
    @DynamoDBAttribute(attributeName = "noise")
    public String getNoise() {
        return _noise;
    }

    public void setNoise(final String _noise) {
        this._noise = _noise;
    }
    @DynamoDBAttribute(attributeName = "odor")
    public String getOdor() {
        return _odor;
    }

    public void setOdor(final String _odor) {
        this._odor = _odor;
    }
    @DynamoDBAttribute(attributeName = "place")
    public String getPlace() {
        return _place;
    }

    public void setPlace(final String _place) {
        this._place = _place;
    }
    @DynamoDBAttribute(attributeName = "transportation")
    public String getTransportation() {
        return _transportation;
    }

    public void setTransportation(final String _transportation) {
        this._transportation = _transportation;
    }

    @Override
    public String toString() {
        return "EntryDO{" +
                "_userId='" + _userId + '\'' +
                ", _entryId='" + _entryId + '\'' +
                ", _active='" + _active + '\'' +
                ", _activity='" + _activity + '\'' +
                ", _creationDate='" + _creationDate + '\'' +
                ", _emotions=" + _emotions +
                ", _noise='" + _noise + '\'' +
                ", _odor='" + _odor + '\'' +
                ", _place='" + _place + '\'' +
                ", _transportation='" + _transportation + '\'' +
                '}';
    }
}

