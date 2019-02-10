package com.hammad.omar.outreach.Models;

import android.util.Log;

import com.hammad.omar.outreach.Interfaces.UUIDUser;
import com.hammad.omar.outreach.Managers.UUIDManager;

import java.sql.Timestamp;

public abstract class Model implements UUIDUser {

    @Override
    public Initials getUUIDInitials() {
        return new Initials(getClass().getSimpleName());
    }

    String getUUID(){
        String uuid = new UUIDManager(this).generateUUID();
        Log.d("uuid",uuid);
        return uuid;
    }
}
