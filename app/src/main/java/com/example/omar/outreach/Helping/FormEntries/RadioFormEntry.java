package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class RadioFormEntry extends FormEntry{

    protected List<String> choises;
    protected List<RadioButton> radioButtons;

    //ui
    protected RadioGroup radioGroup;
    protected TextView titleView;

    public RadioFormEntry(String title, List<String> choises, Context context){
        super(title,context);
        this.choises = choises;
        titleView = new TextView(getContext());
    }

    public RadioFormEntry(String title, Context context){
        this(title,new ArrayList<String>(),context);
    }

    @Override
    public View getView() {

        radioButtons = new ArrayList<>();

        // set padding and background color for view
        containerLayout.setBackgroundResource(R.drawable.view_corner_radius);
        GradientDrawable dr = (GradientDrawable) containerLayout.getBackground();
        dr.setColor(getResources().getColor(R.color.colorLightGrey));

        // text view
        titleView.setLayoutParams(getNestedFormEntryParams());
        titleView.setText(getTitle());
        titleView.setTextSize(getLabelSize());
        containerLayout.addView(titleView);

        // Radios
        radioGroup = getRadioGroupType();
        radioGroup.setLayoutParams(getNestedFormEntryParams());
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0 ; i < choises.size() ; i++){
            RadioButton radioButton = getRadioButtonType();
            setRadioButtonParams(radioButton);
            radioButton.setId(i);
            radioButton.setText(choises.get(i));
            setRadioButtonSize(radioButton);
            radioGroup.addView(radioButton);
            radioButtons.add(radioButton);
        }

        // update background for segmented control
        updateBackground(radioGroup);

        // add radio group to layout
        containerLayout.addView(radioGroup);

        return containerLayout;

    }

    public void setRadioButtonSize(RadioButton radio) {
    }

    @Override
    public List<String> getValues() {

        ArrayList<String> list = new ArrayList<String>();
        
        int checkedID = radioGroup.getCheckedRadioButtonId();
        for(RadioButton r : radioButtons){
            if (r.getId() == checkedID){
                list.add(r.getText().toString());
            }
        }

        return list;
    }


    public List<String> getChoises() {
        return choises;
    }

    public void setChoises(List<String> choises) {
        this.choises = choises;
    }

    // override for the subclass

    public RadioGroup getRadioGroupType() {
        return new RadioGroup(getContext());
    }

    public RadioButton getRadioButtonType() {
        return new RadioButton(getContext());
    }

    public void setRadioButtonParams(RadioButton radioButton){
        radioButton.setLayoutParams(getNestedHorizontalFormEntryParams());
    }

    public void updateBackground(RadioGroup group) {
    }
}

