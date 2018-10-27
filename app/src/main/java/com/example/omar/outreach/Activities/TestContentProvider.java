package com.example.omar.outreach.Activities;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.omar.outreach.Adapters.EntriesAdapter;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.Provider.DatabaseHelper;
import com.example.omar.outreach.Provider.EntriesConverter;
import com.example.omar.outreach.Provider.EntriesDataSource;
import com.example.omar.outreach.Provider.EntryContentContract;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.List;

public class TestContentProvider extends AppCompatActivity {

    private EntriesAdapter entriesAdapter;
    private ListView listView;
    private ArrayList<EntryDO> entries;

    private static int ENTRIES_LOADER_ASYNC_TASK = 10001;

    private static final int DELETE_NOTE_ASYNC_TASK = 10002;
    private static final int INSERT_NOTE_ASYNC_TASK = 10003;
    private static final int UPDATE_NOTE_ASYNC_TASK = 10004;

    EntriesDataSource entriesDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_content_provider);

        // init data source

        EntriesDataSource ds = new EntriesDataSource(this);

    }

//    @NonNull
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
//        return new CursorLoader(this,
//                EntryContentContract.EntriesTable.CONTENT_URI,
//                EntryContentContract.EntriesTable.PROJECTION_ALL,
//                null,
//                null,
//                EntryContentContract.EntriesTable.SORT_ORDER_DEFAULT);
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
//        ((EntriesAdapter)listView.getAdapter()).swapCursor(cursor);
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
//        ((EntriesAdapter) listView.getAdapter()).swapCursor(null);
//    }
//
//
//    void insertItemAsync(final EntryDO entry) {
//
//        AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
//            @Override
//            protected void onInsertComplete(int token, Object cookie, Uri uri) {
//                super.onInsertComplete(token, cookie, uri);
//            }
//        };
//
//        final ContentValues values = EntriesConverter.toContentValues(entry);
//        handler.startInsert(INSERT_NOTE_ASYNC_TASK, entry, EntryContentContract.EntriesTable.CONTENT_URI, values);
//    }
//
//    void removeItemAsync(final EntryDO entry, final int position) {
//
//        AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
//            @Override
//            protected void onDeleteComplete(int token, Object cookie, int result) {
//                super.onDeleteComplete(token, cookie, result);
//            }
//        };
//
//        Uri itemUri = EntryContentContract.EntriesTable.uriBuilder(entry);
//        handler.startDelete(DELETE_NOTE_ASYNC_TASK, entry, itemUri, null, null);
//    }
//
//    void updateItemAsync(final EntryDO entry) {
//
//        AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
//            @Override
//            protected void onUpdateComplete(int token, Object cookie, int result) {
//                super.onUpdateComplete(token, cookie, result);
//            }
//        };
//
//        Uri itemUri = EntryContentContract.EntriesTable.uriBuilder(entry);
//        final ContentValues values = EntriesConverter.toContentValues(entry);
//        handler.startUpdate(UPDATE_NOTE_ASYNC_TASK, entry, itemUri, values, null, null);
//    }
}
