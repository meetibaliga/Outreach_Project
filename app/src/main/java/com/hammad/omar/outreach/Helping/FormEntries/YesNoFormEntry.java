package com.hammad.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.view.View;

import com.hammad.omar.outreach.R;

import java.util.ArrayList;

public class YesNoFormEntry extends RadioFormEntry {

    private static String TRUE_STRING = "Yes";
    private static String FALSE_STRING = "No";

    public YesNoFormEntry(String title, Context context){
        super(title,context);
        TRUE_STRING = context.getString(R.string.yes);
        FALSE_STRING = context.getString(R.string.no);
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
