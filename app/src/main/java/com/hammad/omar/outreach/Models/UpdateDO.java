package com.hammad.omar.outreach.Models;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "Updates")

public class UpdateDO {

    private String updateId;
    private String versionCode;
    private Boolean required;

    public UpdateDO(){
        this("1","",true);
    }

    public UpdateDO(String id){
        this(id,"",true);
    }

    public UpdateDO(String updateId, String versionCode, Boolean required) {
        this.updateId = updateId;
        this.versionCode = versionCode;
        this.required = required;
    }

    @DynamoDBHashKey(attributeName = "updateId")
    @DynamoDBAttribute(attributeName = "updateId")

    public String getId() {
        return updateId;
    }

    public void setId(String id) {
        this.updateId = id;
    }

    @DynamoDBAttribute(attributeName = "version")

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    @DynamoDBAttribute(attributeName = "required")

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "UpdateDO{" +
                "id='" + updateId + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", required=" + required +
                '}';
    }
}


