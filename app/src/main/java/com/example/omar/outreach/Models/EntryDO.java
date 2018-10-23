package com.example.omar.outreach.Models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Provider.EntryContentContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@DynamoDBTable(tableName = "outreach-mobilehub-787670546-Entry")

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
    private boolean _isDeleted;
    private long _lastUpdated;

    // Local not exposed to server
    private int _id = -1;
    private boolean _isDirty;

    public EntryDO(){
        this._userId = App.USER_ID;
        String randomID = UUID.randomUUID().toString();
        this._entryId = randomID;
        this._emotions = new ArrayList<String>();
        this._location = new HashMap<String,String>();
        this._activities = new ArrayList<String>();
        this._asthmaAttack = false;
        this._asthmaMedication = false;
        this._cough = false;
        this._limitedActivities = false;
        this._creationDate = App.getCurrentDateString();
        this._isDeleted = false;
        this._isDirty = true;
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
    public boolean isDeleted() {
        return _isDeleted;
    }
    public void setDeleted(final boolean _isDeleted) {
        this._isDeleted = _isDeleted;
    }

    @DynamoDBAttribute(attributeName = "lastUpdated")
    public long getLastUpdated() {
        return _lastUpdated;
    }
    public void setLastupdated(final Long _lastUpdated) {
        this._lastUpdated = _lastUpdated;
    }

    // Local Attributes

    public int getId() {
        return _id;
    }

    public boolean isDirt() {
        return _isDirty;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setDirty(boolean _isDirty) {
        this._isDirty = _isDirty;
    }

    // custom methods

    public void setLatLng(String lat, String lng){
        Map<String,String> map = new HashMap<>();
        map.put("latitude",lat);
        map.put("longitude",lng);
        setLocation(map);
    }

    public void addEmotion(String emotion){
        _emotions.add(emotion);
    }

    public void addActivity(String activity){
        _activities.add(activity);
    }

    // to values

    public ContentValues toValues(){

        ContentValues values = new ContentValues();

        values.put(EntryContentContract.EntriesTable.ENTRYID,_entryId);
        values.put(EntryContentContract.EntriesTable.ACTIVE,_active);
        values.put(EntryContentContract.EntriesTable.ASTHMAATTACK,_asthmaAttack);
        values.put(EntryContentContract.EntriesTable.ASTHMAMEDICATION,_asthmaMedication);
        values.put(EntryContentContract.EntriesTable.COUGH,_cough);
        values.put(EntryContentContract.EntriesTable.CREATIONDATE,_creationDate);
        values.put(EntryContentContract.EntriesTable.LIMITEDACTIVITIES,_limitedActivities);
        values.put(EntryContentContract.EntriesTable.NOISE,_noise);
        values.put(EntryContentContract.EntriesTable.ODOR,_odor);
        values.put(EntryContentContract.EntriesTable.PLACE,_place);
        values.put(EntryContentContract.EntriesTable.TRANSPORTATION,_transportation);
        values.put(EntryContentContract.EntriesTable.ISDELETED,_isDeleted);
        values.put(EntryContentContract.EntriesTable.LASTUPDATED,_lastUpdated);
        values.put(EntryContentContract.EntriesTable.EMOTIONS,App.arrayListToJSON(_emotions,"emotions"));
        values.put(EntryContentContract.EntriesTable.ACTIVITIES,App.arrayListToJSON(_activities,"activities"));
        values.put(EntryContentContract.EntriesTable.LOCATION,App.mapToJSON(_location));

        return values;
    }

    // get random entry

    public static EntryDO getRandromEntry(){

        EntryDO entryDO = new EntryDO("0",false,true,false,true,"1","3","Home","4");
        entryDO.addEmotion("Happy");
        entryDO.addActivity("Studying");
        entryDO.setLatLng("0","0");

        Log.d("TestActivity","random entry "+entryDO.toString());

        return entryDO;

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
                ", _id=" + _id +
                ", _isDirty=" + _isDirty +
                '}';
    }
}

