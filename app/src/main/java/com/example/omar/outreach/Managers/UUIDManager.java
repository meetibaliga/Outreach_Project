package com.example.omar.outreach.Managers;

import com.example.omar.outreach.Interfaces.UUIDUser;

import java.util.UUID;

public class UUIDManager {

    UUIDUser user;

    public UUIDManager(UUIDUser user){
        this.user = user;
    }

    public String generateUUID(){
        return user.getUUIDInitials().toString() + "_" + UUID.randomUUID() + "_" + user.getCreationDate() ;
    }

}
