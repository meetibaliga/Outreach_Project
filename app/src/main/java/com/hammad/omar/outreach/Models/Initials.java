package com.hammad.omar.outreach.Models;

import java.util.Date;

public class Initials {

    String initials;

    public Initials(String initials){

        if(initials.length() >= 2){
            this.initials = initials.substring(0,2).toUpperCase();
        }

    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {

        if(initials.length() >= 2){
            this.initials = initials.substring(0,2).toUpperCase();
        }

    }

    @Override
    public String toString() {
        return initials;
    }
}
