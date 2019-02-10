package com.hammad.omar.outreach.Provider;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import com.hammad.omar.outreach.Models.Entry;
import com.hammad.omar.outreach.Models.UserLocation;

public class EntryContentContract {

    /**
     * The authority of the notes content provider - this must match the authorities field
     * specified in the AndroidManifest.xml provider section
     */
    public static final String AUTHORITY = "com.example.omar.outreach.Provider";

    /**
     * The content URI for the top-level content provider
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    ////////////////////////////// inner class for Entry ///////////////////////////////

    public static final class EntriesTable implements BaseColumns {

        /**
         * The table name within SQLite
         */
        public static final String TABLE_NAME = "entries";

        /**
         * The fields that make up the SQLite table
         */
        public static final String ID = "id";
        public static final String ENTRYID = "entryId";
        public static final String ACTIVE = "active";
        public static final String ASTHMAATTACK = "asthmaAttack";
        public static final String ASTHMAMEDICATION = "asthmaMedication";
        public static final String COUGH = "cough";
        public static final String CREATIONDATE = "creationDate";
        public static final String LIMITEDACTIVITIES = "limitedActivities";
        public static final String NOISE = "noise";
        public static final String ODOR = "odor";
        public static final String PLACE = "place";
        public static final String TRANSPORTATION = "transportation";
        public static final String ISDELETED = "isDeleted";
        public static final String ISDIRTY = "isDirty";
        public static final String EMOTIONS = "emotions";
        public static final String ACTIVITIES = "activities";
        public static final String LOCATION = "location";

        /**
         * The URI base-path for the table
         */
        public static final String DIR_BASEPATH = "entries";

        /**
         * The URI base-path for a single item.  Note the wild-card * to represent
         * any string (so we can specify a noteId within the content URI)
         */
        public static final String ITEM_BASEPATH = "entries/*";

        /**
         * The content URI for this table
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(EntryContentContract.CONTENT_URI, TABLE_NAME);

        /**
         * The MIME type for the response for all items within a table
         */
        static final String BASE_TYPE = "/vnd.com.example.omar.outreach.models.";
        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + BASE_TYPE + TABLE_NAME;

        /**
         * The MIME type for a single item within the table
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + BASE_TYPE + TABLE_NAME;

        /**
         * Method to build a Uri based on the NoteId
         * @param entryId the Id of the note
         * @return the Uri of the note
         */
        public static Uri uriBuilder(String entryId) {
            return Uri.withAppendedPath(CONTENT_URI, entryId);
        }

        /**
         * Method to build a Uri based on a note
         * @param entry the entrry
         * @return the Uri of the note
         */
        public static Uri uriBuilder(Entry entry) {
            return Uri.withAppendedPath(CONTENT_URI, entry.getEntryId());
        }

        /**
         * A projection of all columns in the table
         */
        public static final String[] PROJECTION_ALL = {
                ID, ENTRYID, ACTIVE, ASTHMAATTACK, ASTHMAMEDICATION, COUGH, CREATIONDATE,LIMITEDACTIVITIES,NOISE,ODOR,PLACE,TRANSPORTATION,ISDELETED,ISDIRTY,EMOTIONS,ACTIVITIES,LOCATION
        };

        /**
         * The SQLite CREATE TABLE statement
         */
        public static final String CREATE_SQLITE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + ID + " INTEGER PRIMARY KEY, "
                        + ENTRYID + " TEXT UNIQUE NOT NULL, "
                        + ACTIVE + " TEXT NOT NULL DEFAULT '0', "
                        + ASTHMAATTACK + " BOOLEAN DEFAULT 0, "
                        + ASTHMAMEDICATION + " BOOLEAN DEFAULT 0, "
                        + COUGH + " BOOLEAN DEFAULT 0, "
                        + CREATIONDATE + " TEXT NOT NULL DEFAULT '',"
                        + LIMITEDACTIVITIES + " BOOLEAN DEFAULT 0,"
                        + NOISE + " TEXT NOT NULL DEFAULT '0', "
                        + ODOR + " TEXT NOT NULL DEFAULT '0', "
                        + PLACE + " TEXT NOT NULL DEFAULT '', "
                        + EMOTIONS + " TEXT NOT NULL DEFAULT '', "
                        + ACTIVITIES + " TEXT NOT NULL DEFAULT '', "
                        + LOCATION + " TEXT NOT NULL DEFAULT '', "
                        + TRANSPORTATION + " TEXT NOT NULL DEFAULT '0', "
                        + ISDELETED + " BOOLEAN DEFAULT 0, "
                        + ISDIRTY + " BOOLEAN DEFAULT 1) ";

        /**
         * The default sort order (in SQLite notation)
         */

        public static final String DROP_SQLITE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static final String SORT_ORDER_DEFAULT = ID + " ASC";
    }

    ////////////////////////////// inner class for Location ///////////////////////////////

    public static final class LocationsTable implements BaseColumns {

        /**
         * The table name within SQLite
         */
        public static final String TABLE_NAME = "locations";

        /**
         * The fields that make up the SQLite table
         */
        public static final String ID = "id";
        public static final String LOCATIONID = "locationId";
        public static final String CREATIONDATE = "creationDate";
        public static final String ISDELETED = "isDeleted";
        public static final String ISDIRTY = "isDirty";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";

        /**
         * The URI base-path for the table
         */
        public static final String DIR_BASEPATH = "locations";

        /**
         * The URI base-path for a single item.  Note the wild-card * to represent
         * any string (so we can specify a noteId within the content URI)
         */
        public static final String ITEM_BASEPATH = "locations/*";

        /**
         * The content URI for this table
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(EntryContentContract.CONTENT_URI, TABLE_NAME);

        /**
         * The MIME type for the response for all items within a table
         */
        static final String BASE_TYPE = "/vnd.com.example.omar.outreach.models.";
        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + BASE_TYPE + TABLE_NAME;

        /**
         * The MIME type for a single item within the table
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + BASE_TYPE + TABLE_NAME;

        /**
         * Method to build a Uri based on the NoteId
         * @param locationId the Id of the note
         * @return the Uri of the note
         */
        public static Uri uriBuilder(String locationId) {
            return Uri.withAppendedPath(CONTENT_URI, locationId);
        }

        /**
         * Method to build a Uri based on a note
         * @param location the entrry
         * @return the Uri of the note
         */
        public static Uri uriBuilder(UserLocation location) {
            return Uri.withAppendedPath(CONTENT_URI, location.get_locationId());
        }

        /**
         * A projection of all columns in the table
         */
        public static final String[] PROJECTION_ALL = {
                ID,LOCATIONID,CREATIONDATE,ISDELETED,ISDIRTY,LATITUDE,LONGITUDE
        };

        /**
         * The SQLite CREATE TABLE statement
         */
        public static final String CREATE_SQLITE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + ID + " INTEGER PRIMARY KEY, "
                        + LOCATIONID + " TEXT UNIQUE NOT NULL, "
                        + CREATIONDATE + " TEXT NOT NULL DEFAULT '', "
                        + ISDELETED + " BOOLEAN DEFAULT 0, "
                        + LATITUDE + " TEXT NOT NULL DEFAULT '', "
                        + LONGITUDE + " TEXT NOT NULL DEFAULT '', "
                        + ISDIRTY + " BOOLEAN DEFAULT 1);";

        /**
         * The default sort order (in SQLite notation)
         */

        public static final String DROP_SQLITE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static final String SORT_ORDER_DEFAULT = ID + " ASC";
    }
}
