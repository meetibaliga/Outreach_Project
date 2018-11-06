package com.example.omar.outreach.Provider;

import android.content.Context;
import android.database.Cursor;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.DataItem;
import com.example.omar.outreach.Models.UserLocation;

public class LocationsDataSource<E extends UserLocation> extends GenericDataSource {

    public LocationsDataSource(Context context){
        super(context);
    }

    @Override
    protected String getTableName() {
       return EntryContentContract.LocationsTable.TABLE_NAME;
    }

    @Override
    protected String getItemId() {
        return EntryContentContract.LocationsTable.LOCATIONID;
    }

    @Override
    protected String getDirtyColumnName() {
        return EntryContentContract.LocationsTable.ISDIRTY;
    }

    @Override
    protected String getCreationDateColumnName() {
        return EntryContentContract.LocationsTable.CREATIONDATE;
    }

    @Override
    protected String[] getProjection() {
        return EntryContentContract.LocationsTable.PROJECTION_ALL;
    }

    @Override
    protected DataItem convertRowToEntry(Cursor cursor) {

        UserLocation location = new UserLocation();

        location.set_id(cursor.getInt(cursor.getColumnIndex(EntryContentContract.LocationsTable.ID)));
        location.set_locationId(cursor.getString(cursor.getColumnIndex(EntryContentContract.LocationsTable.LOCATIONID)));
        location.set_creationDate(cursor.getString(cursor.getColumnIndex(EntryContentContract.LocationsTable.CREATIONDATE)));
        location.set_latitude(cursor.getString(cursor.getColumnIndex(EntryContentContract.LocationsTable.LATITUDE)));
        location.set_longitude(cursor.getString(cursor.getColumnIndex(EntryContentContract.LocationsTable.LONGITUDE)));
        location.set_isDeleted(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.LocationsTable.ISDELETED))));
        location.set_isDirty(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.LocationsTable.ISDIRTY))));

        return location;
    }
}
