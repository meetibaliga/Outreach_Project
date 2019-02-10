package com.hammad.omar.outreach.Helping.FormEntries;

public class PassingString {

    String passingString;

    public PassingString(String string){
        this.passingString = string;
    }

    public String getPassingString() {
        return passingString;
    }

    public void setPassingString(String passingString) {
        this.passingString = passingString;
    }

    public void concat(String conc){
        this.passingString += conc;
    }
}
