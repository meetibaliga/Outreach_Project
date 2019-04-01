package com.hammad.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

interface FormEntryInterface {
    public View getView();
    public Context getContext();
}

public abstract class FormEntry extends View implements FormEntryInterface{

    protected String title;
    protected boolean required = true;

    // ui
    protected LinearLayout containerLayout;
    protected static LinearLayout.LayoutParams formEntryParams;
    protected static LinearLayout.LayoutParams nestedFormEntryParams;
    protected static LinearLayout.LayoutParams nestedHorizontalFormEntryParams;


    //consts
    protected static final int formEntryBottomMargin = 24+16;
    protected static final int nestedFormEntryBottomMargin = 16;
    protected static final int nestedHorizontalformEntryBottomMargin = 8;
    protected static final int nestedHorizontalformEntryRightMargin = 8;
    protected static final int labelSize = 16;
    protected static final int formEntryPaddingTopBottom = 16;


    // constructors
    public FormEntry(Context context){
        this("Untitled",context);
    }

    public FormEntry(String title, Context context) {

        super(context);
        this.title = title;

        // layout params
        formEntryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        formEntryParams.bottomMargin = formEntryBottomMargin;

        // nested layout
        nestedFormEntryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nestedFormEntryParams.bottomMargin = nestedFormEntryBottomMargin;

        // horizontal layout
        nestedHorizontalFormEntryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nestedHorizontalFormEntryParams.bottomMargin = nestedHorizontalformEntryBottomMargin;
        nestedHorizontalFormEntryParams.rightMargin = nestedHorizontalformEntryRightMargin;

        // container layout
        this.containerLayout = new LinearLayout(getContext());
        containerLayout.setLayoutParams(getFormEntryParams());
        containerLayout.setOrientation(LinearLayout.VERTICAL);

    }

    public String getTitle() {
        return title;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public abstract View getView();

    public abstract List<String> getValues();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    public String getValue(){
        if( getValues() != null && getValues().size() > 0 ){
            return getValues().get(0);
        }else{
            return null;
        }
    }

    public boolean isEmpty(){

        return isRequired() && ( getValues().size() == 0 || getValue().equals("") );

    }

}

