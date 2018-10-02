package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ShortTextFormEntry extends FormEntry{

    EditText et;

    public ShortTextFormEntry(Context context) {

        super(context);
    }

    public ShortTextFormEntry(String title, Context context) {
        super(title, context);
    }

    @Override
    public View getView() {
        et = new EditText(getContext());
        et.setLayoutParams(getFormEntryParams());
        et.setHint(getTitle());
        return et;
    }

    @Override
    public List<String> getValues() {

        if(et.getText().equals("")){
            return null;
        }else{
            ArrayList<String>list = new ArrayList<>();
            list.add(et.getText().toString());
            return list;
        }
    }

}