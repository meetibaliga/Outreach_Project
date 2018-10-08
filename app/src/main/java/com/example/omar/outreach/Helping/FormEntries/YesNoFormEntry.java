package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;

public class YesNoFormEntry extends RadioFormEntry {

    private static final String TRUE_STRING = "Yes";
    private static final String FALSE_STRING = "No";

    public YesNoFormEntry(String title, Context context){
        super(title,context);
        ArrayList<String> choices = new ArrayList<String>();
        choices.add(TRUE_STRING);
        choices.add(FALSE_STRING);
        setChoises(choices);
    }

    @Override
    public View getView() {
        return super.getView();
    }

    //
    public boolean getTrueOrFalse(){
        String value = getValue();
        if(value.equals(TRUE_STRING)){
            return true;
        }else{
            return false;
        }
    }
}