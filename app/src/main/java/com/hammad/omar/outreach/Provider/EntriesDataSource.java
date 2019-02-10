package com.hammad.omar.outreach.Provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.Nullable;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Interfaces.DataItem;
import com.hammad.omar.outreach.Managers.EntriesManager;
import com.hammad.omar.outreach.Models.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class EntriesDataSource<E extends Entry> extends GenericDataSource {

    public EntriesDataSource(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return EntryContentContract.EntriesTable.TABLE_NAME;
    }

    @Override
    protected String getItemId() {
        return EntryContentContract.EntriesTable.ENTRYID;
    }

    @Override
    protected String getDirtyColumnName() {
        return EntryContentContract.EntriesTable.ISDIRTY;
    }

    @Override
    protected String getCreationDateColumnName() {
        return EntryContentContract.EntriesTable.CREATIONDATE;
    }

    @Override
    protected String[] getProjection() {
        return EntryContentContract.EntriesTable.PROJECTION_ALL;
    }

    protected DataItem convertRowToEntry(Cursor cursor) {

        Entry entry = new Entry();

        entry.setId(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ID)));
        entry.setEntryId(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.ENTRYID)));
        entry.setActive(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.ACTIVE)));
        entry.setAsthmaAttack(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ASTHMAATTACK))));
        entry.setAsthmaMedication(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ASTHMAMEDICATION))));
        entry.setCough(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.COUGH))));
        entry.setCreationDate(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.CREATIONDATE)));
        entry.setLimitedActivities(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.LIMITEDACTIVITIES))));
        entry.setNoise(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.NOISE)));
        entry.setOdor(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.ODOR)));
        entry.setPlace(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.PLACE)));
        entry.setTransportation(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.TRANSPORTATION)));
        entry.setDeleted(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ISDELETED))));
        entry.setDirty(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ISDIRTY))));
        entry.setEmotions(App.JSONtoArrayList(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.EMOTIONS)),"emotions"));
        entry.setActivities(App.JSONtoArrayList(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.ACTIVITIES)),"activities"));
        entry.setLocation(App.JSONToMap(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.LOCATION))));

        return entry;

    }


}
