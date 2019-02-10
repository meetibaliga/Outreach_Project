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
import com.hammad.omar.outreach.Models.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public abstract class GenericDataSource<E extends DataItem> {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    // abstract methods
    protected abstract String getTableName();
    protected abstract String getItemId();
    protected abstract String getDirtyColumnName();
    protected abstract String getCreationDateColumnName();
    protected abstract String[] getProjection();
    protected abstract E convertRowToEntry(Cursor cursor);

    public GenericDataSource(Context context){
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

    public E insertItem(E item){

        String table = getTableName();
        ContentValues content = item.toValues();
        db.insert(table,null,content);

        return item;

    }


    public E deleteItem(E item){

        String itemId = item.getItemId();
        deleteItem(itemId);

        return item;

    }

    public int deleteItem(String itemId){

        String where = getItemId() + " = ? ";
        String[] args = {itemId};

        return db.delete(getTableName(),where,args);

    }


    public E updateItem(E item){

        deleteItem(item);
        return insertItem(item);

    }

    public boolean hasDirtyData(){

        if( getDirtyItems().size() > 0 ){
            return true;
        }

        return false;

    }


    public List<E> getDirtyItems(){

        String selection = getDirtyColumnName() + " = ?";
        String[] args = {"1"};

        return getResults(query(selection,args));

    }



    public List<E> getItemWithId(String id){

        String selection = getItemId() + " = ?";
        String[] args = {id};

        return getResults(query(selection,args));

    }

    public List<E> getAllItemsOrderedByDate(boolean DESC){

        String order = DESC ? "DESC" : "ASC";

        return getResults(query(null,null,getCreationDateColumnName()+" "+order));
    }

    public List<E> getAllItems(){
        return getResults(query());
    }

    public int getNumOfItems(){
        return getAllItems().size();
    }

    public List<E> getAllItems(String orderBy){
        return getResults(query(null,null,orderBy));
    }


    public List<E> getAllItemsSortedByDate(){

        if( Build.VERSION.SDK_INT < 24){
            return getAllItems();
        }

        List items = getAllItems();

        items.sort(new Comparator() {

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

        return items;

    }

















    ///////////////////////// HELPING METHODS ////////////////////////

    private Cursor query(){

        return query(null,null);
    }

    private Cursor query(String selection, String[] args){

        return query(selection,args,null);
    }

    private Cursor query(String selection, String[] args, @Nullable String orderBy){

        Cursor cursor = db.query(getTableName(),getProjection(),selection,args,null,null,orderBy,null);

        return cursor;
    }

    private List<E> getResults(Cursor cursor){

        List<E> dataItems = new ArrayList<>();

        while (cursor.moveToNext()){
            dataItems.add(convertRowToEntry(cursor));
        }

        cursor.close();
        return dataItems;
    }

}
