package com.example.omar.outreach.Model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "outreach-mobilehub-787670546-Location")

public class LocationDO {
    private String _userId;
    private String _locationId;
    private String _creationDate;
    private String _latitude;
    private String _longitude;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "locationId")
    @DynamoDBAttribute(attributeName = "locationId")
    public String getLocationId() {
        return _locationId;
    }

    public void setLocationId(final String _locationId) {
        this._locationId = _locationId;
    }
    @DynamoDBAttribute(attributeName = "creationDate")
    public String getCreationDate() {
        return _creationDate;
    }

    public void setCreationDate(final String _creationDate) {
        this._creationDate = _creationDate;
    }
    @DynamoDBAttribute(attributeName = "latitude")
    public String getLatitude() {
        return _latitude;
    }

    public void setLatitude(final String _latitude) {
        this._latitude = _latitude;
    }
    @DynamoDBAttribute(attributeName = "longitude")
    public String getLongitude() {
        return _longitude;
    }

    public void setLongitude(final String _longitude) {
        this._longitude = _longitude;
    }

    @Override
    public String toString() {
        return "LocationDO{" +
                "_userId='" + _userId + '\'' +
                ", _locationId='" + _locationId + '\'' +
                ", _creationDate='" + _creationDate + '\'' +
                ", _latitude='" + _latitude + '\'' +
                ", _longitude='" + _longitude + '\'' +
                '}';
    }
}
