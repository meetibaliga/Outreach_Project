package com.example.omar.outreach.Models;

import android.content.ContentValues;
import android.util.Log;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.DataItem;
import com.example.omar.outreach.Interfaces.UUIDUser;
import com.example.omar.outreach.Managers.SharedPreferencesManager;
import com.example.omar.outreach.Managers.UUIDManager;
import com.example.omar.outreach.Provider.EntryContentContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Entry extends Model implements DataItem {

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

    // Local not exposed to server
    private int _id = -1;
    private boolean _isDirty;


    public Entry(){

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
        this._isDirty = true;
        this._entryId = getUUID();

    }

    public Entry(EntryDO entryDO){

         this._userId = entryDO.getUserId();
         this._entryId = entryDO.getEntryId();
         this._active = entryDO.getActive();
         this._activities = entryDO.getActivities();
         this._asthmaAttack = entryDO.getAsthmaAttack();
         this._asthmaMedication = entryDO.getAsthmaMedication();
         this._cough = entryDO.getCough();
         this._creationDate = entryDO.getCreationDate();
         this._emotions = entryDO.getEmotions();
         this._limitedActivities = entryDO.getLimitedActivities();
         this._location = entryDO.getLocation();
         this._noise = entryDO.getNoise();
         this._odor = entryDO.getOdor();
         this._place = entryDO.getPlace();
         this._transportation = entryDO.getTransportation();
         this._isDeleted = entryDO.isDeleted();
         _isDirty = false;

    }

    public static List<Entry> getEntries(List<EntryDO> entriesDO){

        List<Entry> entries = new ArrayList<>();

        for(EntryDO entryDO : entriesDO){

            entries.add(new Entry(entryDO));

        }

        return entries;

    }

    public Entry(String _active, Boolean _asthmaAttack, Boolean _asthmaMedication, Boolean _cough, Boolean _limitedActivities, String _noise, String _odor, String _place, String _transportation) {

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


    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

    public String getEntryId() {
        return _entryId;
    }

    public void setEntryId(final String _entryId) {
        this._entryId = _entryId;
    }

    public String getActive() {
        return _active;
    }

    public void setActive(final String _active) {
        this._active = _active;
    }

    public List<String> getActivities() {
        return _activities;
    }

    public void setActivities(final List<String> _activity) {
        this._activities = _activity;
    }
    public Boolean getAsthmaAttack() {
        return _asthmaAttack;
    }

    public void setAsthmaAttack(final Boolean _asthmaAttack) {
        this._asthmaAttack = _asthmaAttack;
    }
    public Boolean getAsthmaMedication() {
        return _asthmaMedication;
    }

    public void setAsthmaMedication(final Boolean _asthmaMedication) {
        this._asthmaMedication = _asthmaMedication;
    }
    public Boolean getCough() {
        return _cough;
    }

    public void setCough(final Boolean _cough) {
        this._cough = _cough;
    }

    public String getCreationDate() {
        return _creationDate;
    }

    public void setCreationDate(final String _creationDate) {
        this._creationDate = _creationDate;
    }
    public List<String> getEmotions() {
        return _emotions;
    }

    public void setEmotions(final List<String> _emotions) {
        this._emotions = _emotions;
    }
    public Boolean getLimitedActivities() {
        return _limitedActivities;
    }

    public void setLimitedActivities(final Boolean _limitedActivities) {
        this._limitedActivities = _limitedActivities;
    }
    public Map<String, String> getLocation() {
        return _location;
    }

    public void setLocation(final Map<String, String> _location) {
        this._location = _location;
    }

    public String getNoise() {
        return _noise;
    }

    public void setNoise(final String _noise) {
        this._noise = _noise;
    }
    public String getOdor() {
        return _odor;
    }

    public void setOdor(final String _odor) {
        this._odor = _odor;
    }
    public String getPlace() {
        return _place;
    }

    public void setPlace(final String _place) {
        this._place = _place;
    }

    public String getTransportation() {
        return _transportation;
    }
    public void setTransportation(final String _transportation) {
        this._transportation = _transportation;
    }

    public Boolean isDeleted() {
        return _isDeleted;
    }
    public void setDeleted(final Boolean _isDeleted) {
        this._isDeleted = _isDeleted;
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

    public void setDirty(Boolean _isDirty) {
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
        values.put(EntryContentContract.EntriesTable.EMOTIONS,App.arrayListToJSON(_emotions,"emotions"));
        values.put(EntryContentContract.EntriesTable.ACTIVITIES,App.arrayListToJSON(_activities,"activities"));
        values.put(EntryContentContract.EntriesTable.LOCATION,App.mapToJSON(_location));
        values.put(EntryContentContract.EntriesTable.ISDIRTY,App.boolToInt(_isDirty));

        return values;
    }

    @Override
    public String getItemId() {
        return _entryId;
    }

    // get random entry

    public static Entry getRandromEntry(){

        Entry entry = new Entry("0",false,true,false,true,"1","3","Home","4");
        entry.addEmotion("Happy");
        entry.addActivity("Studying");
        entry.setLatLng("0","0");

        Log.d("TestActivity","random entry "+entry.toString());

        return entry;

    }


    // to string


    @Override
    public String toString() {
        return "Entry{" +
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
                ", _id=" + _id +
                ", _isDirty=" + _isDirty +
                '}';
    }

    @Override
    public Initials getUUIDInitials() {
        return new Initials("Entry");
    }
}

