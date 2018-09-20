package com.example.omar.outreach;
import android.app.Application;
import com.example.omar.outreach.Model.EntryDO;

public class App extends Application {

    public static EntryDO inputEntry = new EntryDO();
    public static String USER_ID;
    public static int NUM_OF_ENTRIES;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
