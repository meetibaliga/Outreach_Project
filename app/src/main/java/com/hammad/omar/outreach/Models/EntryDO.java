package com.hammad.omar.outreach.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.hammad.omar.outreach.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@DynamoDBTable(tableName = "outreach-mobilehub-787670546-Entry")
@DynamoDBTable(tableName = "Outreach-Entry")

public class EntryDO {

    private String _userId;
    private String _entryId;
    private String _active;
    private List<String> _activities;
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
    private Boolean _isDeleted;
    private Double _lastUpdated;

    public EntryDO(){
        this._userId = App.USER_ID;
        this._emotions = new ArrayList<String>();
        this._location = new HashMap<String,String>();
        this._activities = new ArrayList<String>();
        this._asthmaAttack = false;
        this._asthmaMedication = false;
        this._cough = false;
        this._limitedActivities = false;
        this._creationDate = App.getCurrentDateString();
        this._isDeleted = false;
        String randomID = UUID.randomUUID().toString();
        this._entryId = randomID+_creationDate;
    }

    public EntryDO(String _active, Boolean _asthmaAttack, Boolean _asthmaMedication, Boolean _cough, Boolean _limitedActivities, String _noise, String _odor, String _place, String _transportation) {

        this();
        this._active = _active;
        this._activities = _activities;
        this._asthmaAttack = _asthmaAttack;
        this._asthmaMedication = _asthmaMedication;
        this._cough = _cough;
        this._emotions = _emotions;
        this._limitedActivities = _limitedActivities;
        this._location = _location;
        this._noise = _noise;
        this._odor = _odor;
        this._place = _place;
        this._transportation = _transportation;

    }

    public EntryDO(Entry entry){

        this._userId = entry.getUserId();
        this._entryId = entry.getEntryId();
        this._active = entry.getActive();
        this._activities = entry.getActivities();
        this._asthmaAttack = entry.getAsthmaAttack();
        this._asthmaMedication = entry.getAsthmaMedication();
        this._cough = entry.getCough();
        this._creationDate = entry.getCreationDate();
        this._emotions = entry.getEmotions();
        this._limitedActivities = entry.getLimitedActivities();
        this._location = entry.getLocation();
        this._noise = entry.getNoise();
        this._odor = entry.getOdor();
        this._place = entry.getPlace();
        this._transportation = entry.getTransportation();
        this._isDeleted = entry.isDeleted();

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
    public List<String> getActivities() {
        return _activities;
    }

    public void setActivities(final List<String> _activity) {
        this._activities = _activity;
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

    @DynamoDBAttribute(attributeName = "isDeleted")
    public Boolean isDeleted() {
        return _isDeleted;
    }
    public void setDeleted(final Boolean _isDeleted) {
        this._isDeleted = _isDeleted;
    }

    @DynamoDBAttribute(attributeName = "lastUpdated")
    public Double getLastUpdated() {
        return _lastUpdated;
    }
    public void setLastupdated(final Double _lastUpdated) {
        this._lastUpdated = _lastUpdated;
    }

    // custom methods

    public void setLatLng(String lat, String lng){
        Map<String,String> map = new HashMap<>();
        map.put("latitude",lat);
        map.put("longitude",lng);
        setLocation(map);
    }

    // to string


    @Override
    public String toString() {
        return "EntryDO{" +
                "_userId='" + _userId + '\'' +
                ", _entryId='" + _entryId + '\'' +
                ", _active='" + _active + '\'' +
                ", _activities=" + _activities +
                ", _asthmaAttack=" + _asthmaAttack +
                ", _asthmaMedication=" + _asthmaMedication +
                ", _cough=" + _cough +
                ", _creationDate='" + _creationDate + '\'' +
                ", _emotions=" + _emotions +
                ", _limitedActivities=" + _limitedActivities +
                ", _location=" + _location +
                ", _noise='" + _noise + '\'' +
                ", _odor='" + _odor + '\'' +
                ", _place='" + _place + '\'' +
                ", _transportation='" + _transportation + '\'' +
                ", _isDeleted=" + _isDeleted +
                ", _lastUpdated=" + _lastUpdated +
                '}';
    }
}

