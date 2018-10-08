package com.example.omar.outreach.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "outreach-mobilehub-787670546-Entry")

public class EntryDO {
    private String _userId;
    private String _entryId;
    private String _active;
    private String _activity;
    private Boolean _asthmaAttack;
    private Boolean _asthmaMedication;
    private Boolean _cough;
    private String _creationDate;
    private List<String> _emotions;
    private Boolean _limitedActivities;
    private Map<String, String> _location;
    private String _noise;
    private String _odor;
    private String _place;
    private String _transportation;

    public EntryDO(){
        this._emotions = new ArrayList<String>();
        this._location = new HashMap<String,String>();
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
    @DynamoDBAttribute(attributeName = "asthma_attack")
    public Boolean getAsthmaAttack() {
        return _asthmaAttack;
    }

    public void setAsthmaAttack(final Boolean _asthmaAttack) {
        this._asthmaAttack = _asthmaAttack;
    }
    @DynamoDBAttribute(attributeName = "asthma_medication")
    public Boolean getAsthmaMedication() {
        return _asthmaMedication;
    }

    public void setAsthmaMedication(final Boolean _asthmaMedication) {
        this._asthmaMedication = _asthmaMedication;
    }
    @DynamoDBAttribute(attributeName = "cough")
    public Boolean getCough() {
        return _cough;
    }

    public void setCough(final Boolean _cough) {
        this._cough = _cough;
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
    @DynamoDBAttribute(attributeName = "limited_activities")
    public Boolean getLimitedActivities() {
        return _limitedActivities;
    }

    public void setLimitedActivities(final Boolean _limitedActivities) {
        this._limitedActivities = _limitedActivities;
    }
    @DynamoDBAttribute(attributeName = "location")
    public Map<String, String> getLocation() {
        return _location;
    }

    public void setLocation(final Map<String, String> _location) {
        this._location = _location;
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

    // custom methods

    public void setLatLng(String lat, String lng){
        Map<String,String> map = new HashMap<>();
        map.put("latitude",lat);
        map.put("longitude",lng);
        setLocation(map);
    }
}

