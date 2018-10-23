package com.example.omar.outreach.Provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Implementation of the content provider for notes
 */
public class EntriesContentProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ALL_ENTRIES = 1;
    private static final int ONE_ENTRY = 2;

    static {
        sUriMatcher.addURI(
                EntryContentContract.AUTHORITY,
                EntryContentContract.EntriesTable.DIR_BASEPATH,
                ALL_ENTRIES);
        sUriMatcher.addURI(
                EntryContentContract.AUTHORITY,
                EntryContentContract.EntriesTable.ITEM_BASEPATH,
                ONE_ENTRY);
    }

    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override

    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriType) {
            case ALL_ENTRIES:
                queryBuilder.setTables(EntryContentContract.EntriesTable.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = EntryContentContract.EntriesTable.SORT_ORDER_DEFAULT;
                }
                break;
            case ONE_ENTRY:
                String where = String.format("%s = \"%s\"", EntryContentContract.EntriesTable.ENTRYID, uri.getLastPathSegment());
                queryBuilder.setTables(EntryContentContract.EntriesTable.TABLE_NAME);
                queryBuilder.appendWhere(where);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case ALL_ENTRIES:
                return EntryContentContract.EntriesTable.CONTENT_DIR_TYPE;
            case ONE_ENTRY:
                return EntryContentContract.EntriesTable.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case ALL_ENTRIES:
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                long id = db.insert(EntryContentContract.EntriesTable.TABLE_NAME, null, values);
                if (id > 0) {
                    String entryId = values.getAsString(EntryContentContract.EntriesTable.ENTRYID);
                    Uri item = EntryContentContract.EntriesTable.uriBuilder(entryId);
                    notifyAllListeners(item);
                    return item;
                }
                throw new SQLException(String.format(Locale.US, "Error inserting for URI %s - id = %d", uri, id));
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        String where;

        switch (uriType) {
            case ALL_ENTRIES:
                where = selection;
                break;
            case ONE_ENTRY:
                where = String.format("%s = \"%s\"",
                        EntryContentContract.EntriesTable.ENTRYID, uri.getLastPathSegment());
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND (" + selection + ")";
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rows = db.delete(EntryContentContract.EntriesTable.TABLE_NAME, where, selectionArgs);
        if (rows > 0) {
            notifyAllListeners(uri);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);

        String where;
        switch (uriType) {
            case ALL_ENTRIES:
                where = selection;
                break;
            case ONE_ENTRY:
                where = String.format("%s = \"%s\"",
                        EntryContentContract.EntriesTable.ENTRYID, uri.getLastPathSegment());
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND (" + selection + ")";
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rows = db.update(EntryContentContract.EntriesTable.TABLE_NAME, values, where, selectionArgs);
        if (rows > 0) {
            notifyAllListeners(uri);
        }

        return rows;
    }

    private void notifyAllListeners(Uri uri) {
        ContentResolver resolver = getContext().getContentResolver();
        if (resolver != null) {
            resolver.notifyChange(uri, null);
        }
    }
}
