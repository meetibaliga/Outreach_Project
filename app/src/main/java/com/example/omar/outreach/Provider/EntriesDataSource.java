package com.example.omar.outreach.Provider;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.Nullable;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Managers.EntriesManager;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.EntryDO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class EntriesDataSource {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    EntriesManager entMgr;

    public EntriesDataSource(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        entMgr = EntriesManager.getInstance(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Entry insertItem(Entry entry){

        ContentValues content = entry.toValues();
        db.insert(EntryContentContract.EntriesTable.TABLE_NAME,null,content);

        // for the limit
        entMgr.addDailyEntry();

        if(entry.isDirt()){
            App.isSynced = false;
        }

        return entry;

    }

    public Entry deleteItem(Entry entry){

        String entryID = entry.getEntryId();
        deleteItem(entryID);

        return entry;

    }

    public int deleteItem(String entryID){

        String where = EntryContentContract.EntriesTable.ENTRYID + " = ? ";
        String[] args = {entryID};

        return db.delete(EntryContentContract.EntriesTable.TABLE_NAME,where,args);

    }

    public Entry updateItem(Entry entry){

        deleteItem(entry);
        return insertItem(entry);

    }

    public boolean hasDirtyData(){

        if( getDirtyEntries().size() > 0 ){
            return true;
        }

        return false;

    }


    public List<Entry> getDirtyEntries(){

        String selection = EntryContentContract.EntriesTable.ISDIRTY + " = ?";
        String[] args = {"1"};

        return getResults(query(selection,args));

    }


    public List<Entry> getEntryWithId(String id){

        String selection = EntryContentContract.EntriesTable.ENTRYID + " = ?";
        String[] args = {id};

        return getResults(query(selection,args));

    }

    public List<Entry> getAllEntriesOrderedByDate(boolean DESC){
        String order = DESC ? "DESC" : "ASC";
        return getResults(query(null,null,EntryContentContract.EntriesTable.CREATIONDATE+" "+order));
    }

    public List<Entry> getAllEntries(){
        return getResults(query());
    }

    public int getNumOfEntries(){
        return getAllEntries().size();
    }

    public List<Entry> getAllEntries(String orderBy){
        return getResults(query(null,null,orderBy));
    }


    public List<Entry> getAllEntriesSortedByDate(){

        if( Build.VERSION.SDK_INT < 24){
            return getAllEntries();
        }

        List entries = getAllEntries();

        entries.sort(new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {

                Entry entry1 = (Entry) o1;
                Entry entry2 = (Entry) o2;

                try {

                    Date date1 = new SimpleDateFormat(App.sourceDateFormat).parse(entry1.getCreationDate());
                    Date date2 = new SimpleDateFormat(App.sourceDateFormat).parse(entry2.getCreationDate());

                    return date1.compareTo(date2);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return 0;

            }

        });

        return entries;

    }

















    ///////////////////////// HELPING METHODS ////////////////////////

    private Cursor query(){

        return query(null,null);
    }

    private Cursor query(String selection, String[] args){

        return query(selection,args,null);
    }

    private Cursor query(String selection, String[] args, @Nullable String orderBy){

        Cursor cursor = db.query(EntryContentContract.EntriesTable.TABLE_NAME,EntryContentContract.EntriesTable.PROJECTION_ALL,selection,args,null,null,orderBy,null);

        return cursor;
    }

    private List<Entry> getResults(Cursor cursor){

        List<Entry> entries = new ArrayList<>();

        while (cursor.moveToNext()){
            entries.add(convertRowToEntry(cursor));
        }

        cursor.close();
        return entries;
    }


    private Entry convertRowToEntry(Cursor cursor) {

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
