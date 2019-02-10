package com.hammad.omar.outreach.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "application.db";
    private static final int DBVERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EntryContentContract.EntriesTable.CREATE_SQLITE_TABLE);
        db.execSQL(EntryContentContract.LocationsTable.CREATE_SQLITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EntryContentContract.EntriesTable.DROP_SQLITE_TABLE);
        db.execSQL(EntryContentContract.EntriesTable.CREATE_SQLITE_TABLE);
        db.execSQL(EntryContentContract.LocationsTable.DROP_SQLITE_TABLE);
        db.execSQL(EntryContentContract.LocationsTable.CREATE_SQLITE_TABLE);
    }
}
