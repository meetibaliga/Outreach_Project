package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class FormEntry {

    private String title;
    private Context context;

    // ui
    private static LinearLayout.LayoutParams formEntryParams;
    private static LinearLayout.LayoutParams nestedFormEntryParams;
    private static LinearLayout.LayoutParams nestedHorizontalFormEntryParams;

    //consts
    private static final int formEntryBottomMargin = 24;
    private static final int nestedFormEntryBottomMargin = 16;
    private static final int nestedHorizontalformEntryBottomMargin = 8;
    private static final int nestedHorizontalformEntryRightMargin = 8;
    private static final int labelSize = 16;


    // types

    public FormEntry(Context context){
        this("Untitled",context);
        this.context = context;
    }

    public FormEntry(String title, Context context) {

        this.title = title;
        this.context = context;

        // layout params
        formEntryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        formEntryParams.bottomMargin = formEntryBottomMargin;

        nestedFormEntryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nestedFormEntryParams.bottomMargin = nestedFormEntryBottomMargin;

        nestedHorizontalFormEntryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nestedHorizontalFormEntryParams.bottomMargin = nestedHorizontalformEntryBottomMargin;
        nestedHorizontalFormEntryParams.rightMargin = nestedHorizontalformEntryRightMargin;
    }

    public String getTitle() {
        return title;
    }

    public Context getContext() {
        return context;
    }

    public static LinearLayout.LayoutParams getFormEntryParams() {
        return formEntryParams;
    }

    public static LinearLayout.LayoutParams getNestedFormEntryParams() {
        return nestedFormEntryParams;
    }

    public static LinearLayout.LayoutParams getNestedHorizontalFormEntryParams() {
        return nestedHorizontalFormEntryParams;
    }

    public static int getLabelSize() {
        return labelSize;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract View getView();
    public abstract List<String> getValues();

    public String getValue(){
        if( getValues() != null && getValues().size() > 0 ){
            return getValues().get(0);
        }else{
            return null;
        }
    }

    public boolean isEmpty(){
        return getValues().size() == 0 || getValue().equals("");
    }
}
