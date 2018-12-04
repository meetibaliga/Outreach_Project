package com.example.omar.outreach.Models;

import android.content.ContentValues;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.DataItem;
import com.example.omar.outreach.Managers.UUIDManager;
import com.example.omar.outreach.Provider.EntryContentContract;

import java.util.UUID;

public class UserLocation extends Model implements DataItem {

    private String _userId;
    private String _locationId;
    private String _creationDate;
    private Boolean _isDeleted;
    private Boolean _isDirty;
    private String _latitude;
    private String _longitude;
    private int _id = -1;

    public UserLocation(){
        this(App.USER_ID,App.getCurrentDateString(),false,true,"0","0");
    }

    public UserLocation(String lat, String lng){
        this();
        this._latitude = lat;
        this._longitude = lng;
    }

    public UserLocation(String _userId, String _creationDate, Boolean _isDeleted, Boolean _isDirty, String _latitude, String _longitude) {
        this._creationDate = _creationDate;
        this._userId = _userId;
        this._locationId = getUUID();
        this._isDeleted = _isDeleted;
        this._isDirty = _isDirty;
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    public UserLocation(LocationDO locationDO){
        this._userId = locationDO.getUserId();
        this._locationId = locationDO.getLocationId();
        this._creationDate = locationDO.getCreationDate();
        this._isDirty = false;
        this._latitude = locationDO.getLatitude();
        this._longitude = locationDO.getLongitude();
    }

    public String get_userId() {
        return _userId;
    }

    public String get_locationId() {
        return _locationId;
    }

    public String get_creationDate() {
        return _creationDate;
    }

    public Boolean get_isDeleted() {
        return _isDeleted;
    }

    public Boolean get_isDirty() {
        return _isDirty;
    }

    public String get_latitude() {
        return _latitude;
    }

    public String get_longitude() {
        return _longitude;
    }

    public int get_id() {
        return _id;
    }

    public void set_userId(String _userId) {
        this._userId = _userId;
    }

    public void set_locationId(String _locationId) {
        this._locationId = _locationId;
    }

    public void set_creationDate(String _creationDate) {
        this._creationDate = _creationDate;
    }

    public void set_isDeleted(Boolean _isDeleted) {
        this._isDeleted = _isDeleted;
    }

    public void set_isDirty(Boolean _isDirty) {
        this._isDirty = _isDirty;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ContentValues toValues(){

        ContentValues values = new ContentValues();

        values.put(EntryContentContract.LocationsTable.LOCATIONID,_locationId);
        values.put(EntryContentContract.LocationsTable.ISDELETED,App.boolToInt(_isDeleted));
        values.put(EntryContentContract.LocationsTable.ISDIRTY,App.boolToInt(_isDirty));
        values.put(EntryContentContract.LocationsTable.CREATIONDATE,_creationDate);
        values.put(EntryContentContract.LocationsTable.LATITUDE,_latitude);
        values.put(EntryContentContract.LocationsTable.LONGITUDE,_longitude);

        return values;
    }

    @Override
    public String getItemId() {
        return get_locationId();
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "_userId='" + _userId + '\'' +
                ", _locationId='" + _locationId + '\'' +
                ", _creationDate='" + _creationDate + '\'' +
                ", _isDeleted=" + _isDeleted +
                ", _isDirty=" + _isDirty +
                ", _latitude='" + _latitude + '\'' +
                ", _longitude='" + _longitude + '\'' +
                ", _id=" + _id +
                '}';
    }

    @Override
    public Initials getUUIDInitials() {
        return new Initials("LO");
    }

    @Override
    public String getCreationDate() {
        return get_creationDate();
    }
}
