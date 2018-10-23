package com.example.omar.outreach.Provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Models.EntryDO;

import java.util.ArrayList;
import java.util.List;

public class EntriesDataSource {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    public EntriesDataSource(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public EntryDO insertItem(EntryDO entry){
        ContentValues content = entry.toValues();
        db.insert(EntryContentContract.EntriesTable.TABLE_NAME,null,content);
        return entry;
    }

    public EntryDO deleteItem(EntryDO entry){

        String entryID = entry.getEntryId();
        deleteItem(entryID);

        return entry;

    }

    public int deleteItem(String entryID){

        String where = EntryContentContract.EntriesTable.ENTRYID + " = ? ";
        String[] args = {entryID};

        return db.delete(EntryContentContract.EntriesTable.TABLE_NAME,where,args);

    }

    public EntryDO updateItem(EntryDO entryDO){

        deleteItem(entryDO);
        return insertItem(entryDO);

    }


    public List<EntryDO> getDirtyEntries(){

        String selection = EntryContentContract.EntriesTable.ISDIRTY + " = ?";
        String[] args = {"0"};

        return getResults(query(selection,args));

    }


    public List<EntryDO> getEntryWithId(String id){

        String selection = EntryContentContract.EntriesTable.ENTRYID + " = ?";
        String[] args = {id};

        return getResults(query(selection,args));

    }

    public List<EntryDO> getAllEntries(){
        return getResults(query(null,null));
    }
















    ///////////////////////// HELPING METHODS ////////////////////////

    private Cursor query(String selection, String[] args){

        Cursor cursor = db.query(EntryContentContract.EntriesTable.TABLE_NAME,EntryContentContract.EntriesTable.PROJECTION_ALL,selection,args,null,null,null,null);

        return cursor;
    }

    private List<EntryDO> getResults(Cursor cursor){

        List<EntryDO> entries = new ArrayList<>();

        while (cursor.moveToNext()){
            entries.add(convertRowToEntry(cursor));
        }

        cursor.close();
        return entries;
    }


    private EntryDO convertRowToEntry(Cursor cursor) {

        EntryDO entryDO = new EntryDO();

        entryDO.setId(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ID)));
        entryDO.setEntryId(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.ENTRYID)));
        entryDO.setActive(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.ACTIVE)));
        entryDO.setAsthmaAttack(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ASTHMAATTACK))));
        entryDO.setAsthmaMedication(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ASTHMAMEDICATION))));
        entryDO.setCough(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.COUGH))));
        entryDO.setCreationDate(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.CREATIONDATE)));
        entryDO.setLimitedActivities(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.LIMITEDACTIVITIES))));
        entryDO.setNoise(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.NOISE)));
        entryDO.setOdor(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.ODOR)));
        entryDO.setPlace(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.PLACE)));
        entryDO.setTransportation(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.TRANSPORTATION)));
        entryDO.setDeleted(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ISDELETED))));
        entryDO.setLastupdated(cursor.getLong(cursor.getColumnIndex(EntryContentContract.EntriesTable.LASTUPDATED)));
        entryDO.setDirty(App.intToBool(cursor.getInt(cursor.getColumnIndex(EntryContentContract.EntriesTable.ISDIRTY))));
        entryDO.setEmotions(App.JSONtoArrayList(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.EMOTIONS)),"emotions"));
        entryDO.setActivities(App.JSONtoArrayList(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.ACTIVITIES)),"activities"));
        entryDO.setLocation(App.JSONToMap(cursor.getString(cursor.getColumnIndex(EntryContentContract.EntriesTable.LOCATION))));

        return entryDO;

    }


}
