package com.example.omar.outreach.Models;

import android.util.Log;

import com.example.omar.outreach.Interfaces.UUIDUser;
import com.example.omar.outreach.Managers.UUIDManager;

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
