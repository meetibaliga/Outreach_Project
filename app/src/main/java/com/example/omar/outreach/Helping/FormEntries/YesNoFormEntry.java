package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;

public class YesNoFormEntry extends RadioFormEntry {


    public YesNoFormEntry(String title, Context context){
        super(title,context);
        ArrayList<String> choices = new ArrayList<String>();
        choices.add("Yes");
        choices.add("No");
        setChoises(choices);
    }

    @Override
    public View getView() {
        return super.getView();
    }
}