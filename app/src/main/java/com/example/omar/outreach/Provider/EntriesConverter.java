package com.example.omar.outreach.Provider;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.EntryDO;

/**
 * Conversion routines to convert to/from the Note client-side model
 */
public class EntriesConverter {

    public static Entry fromCursor(Cursor c) {

        Entry entry = new Entry();

        entry.setId(getInt(c, EntryContentContract.EntriesTable.ID, -1));
        entry.setEntryId(getString(c, EntryContentContract.EntriesTable.ENTRYID, ""));
        entry.setActive(getString(c, EntryContentContract.EntriesTable.ACTIVE,"0"));
        entry.setCreationDate(getString(c, EntryContentContract.EntriesTable.CREATIONDATE,"0"));
        entry.setNoise(getString(c, EntryContentContract.EntriesTable.NOISE,"0"));
        entry.setOdor(getString(c, EntryContentContract.EntriesTable.ODOR,"0"));
        entry.setTransportation(getString(c, EntryContentContract.EntriesTable.TRANSPORTATION,"0"));
        entry.setPlace(getString(c, EntryContentContract.EntriesTable.PLACE,""));
        entry.setAsthmaAttack(getBoolean(c, EntryContentContract.EntriesTable.ASTHMAATTACK,false));
        entry.setAsthmaMedication(getBoolean(c, EntryContentContract.EntriesTable.ASTHMAMEDICATION,false));
        entry.setCough(getBoolean(c, EntryContentContract.EntriesTable.COUGH,false));
        entry.setLimitedActivities(getBoolean(c, EntryContentContract.EntriesTable.LIMITEDACTIVITIES,false));
        entry.setDeleted(getBoolean(c, EntryContentContract.EntriesTable.ISDELETED,false));
        entry.setDirty(getBoolean(c, EntryContentContract.EntriesTable.ISDIRTY,false));

        return entry;
    }

    public static ContentValues toContentValues(Entry entry) {

        ContentValues values = new ContentValues();

        if (entry.getId() >= 0) {
            values.put(EntryContentContract.EntriesTable.ID, entry.getId());
        }

        values.put(EntryContentContract.EntriesTable.ID,entry.getId());
        values.put(EntryContentContract.EntriesTable.ENTRYID, entry.getEntryId());
        values.put(EntryContentContract.EntriesTable.ACTIVE, entry.getActive());
        values.put(EntryContentContract.EntriesTable.CREATIONDATE,entry.getCreationDate());
        values.put(EntryContentContract.EntriesTable.NOISE,entry.getNoise());
        values.put(EntryContentContract.EntriesTable.ODOR,entry.getOdor());
        values.put(EntryContentContract.EntriesTable.TRANSPORTATION,entry.getTransportation());
        values.put(EntryContentContract.EntriesTable.PLACE,entry.getPlace());
        values.put(EntryContentContract.EntriesTable.ASTHMAATTACK,entry.getAsthmaAttack());
        values.put(EntryContentContract.EntriesTable.ASTHMAMEDICATION,entry.getAsthmaMedication());
        values.put(EntryContentContract.EntriesTable.COUGH,entry.getCough());
        values.put(EntryContentContract.EntriesTable.LIMITEDACTIVITIES,entry.getLimitedActivities());
        values.put(EntryContentContract.EntriesTable.ISDELETED,entry.isDeleted());
        values.put(EntryContentContract.EntriesTable.ISDIRTY,entry.isDirt());

        return values;
    }

    /**
     * Read a string from a key in the cursor
     *
     * @param c the cursor to read from
     * @param col the column key
     * @param defaultValue the default value if the column key does not exist in the cursor
     * @return the value of the key
     */
    private static String getString(Cursor c, String col, String defaultValue) {
        if (c.getColumnIndex(col) >= 0) {
            return c.getString(c.getColumnIndex(col));
        } else {
            return defaultValue;
        }
    }

    /**
     * Read a long value from a key in the cursor
     *
     * @param c the cursor to read from
     * @param col the column key
     * @param defaultValue the default value if the column key does not exist in the cursor
     * @return the value of the key
     */
    private static long getLong(Cursor c, String col, long defaultValue) {
        if (c.getColumnIndex(col) >= 0) {
            return c.getLong(c.getColumnIndex(col));
        } else {
            return defaultValue;
        }
    }

    /**
     * Read a int value from a key in the cursor
     *
     * @param c the cursor to read from
     * @param col the column key
     * @param defaultValue the default value if the column key does not exist in the cursor
     * @return the value of the key
     */

    private static int getInt(Cursor c, String col, int defaultValue) {
        if (c.getColumnIndex(col) >= 0) {
            return c.getInt(c.getColumnIndex(col));
        } else {
            return defaultValue;
        }
    }

    /**
     * Read a boolean value from a key in the cursor
     *
     * @param c the cursor to read from
     * @param col the column key
     * @param defaultValue the default value if the column key does not exist in the cursor
     * @return the value of the key
     */
    private static boolean getBoolean(Cursor c, String col, boolean defaultValue) {
        if (c.getColumnIndex(col) >= 0) {
            return c.getInt(c.getColumnIndex(col)) != 0;
        } else {
            return defaultValue;
        }
    }
}
