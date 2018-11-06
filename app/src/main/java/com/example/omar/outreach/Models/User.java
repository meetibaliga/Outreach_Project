package com.example.omar.outreach.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.example.omar.outreach.Interfaces.UUIDUser;

@DynamoDBTable(tableName = "Users")

public class User implements UUIDUser {

    private String _userId;
    private String _firstName;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAttribute(attributeName = "id")
    public String getUserId() {
        return _userId;
    }
    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return _firstName;
    }
    public void setFirstName(final String _firstName) {
        this._firstName = _firstName;
    }


    @Override
    public Initials getUUIDInitials() {
        return new Initials(getClass().getSimpleName());
    }

    @Override
    public String getCreationDate() {
        return null;
    }
}
