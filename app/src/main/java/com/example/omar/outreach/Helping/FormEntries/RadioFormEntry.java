package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class RadioFormEntry extends FormEntry{

    private List<String> choises;
    private RadioGroup radioGroup;
    private List<RadioButton> radioButtons;

    public RadioFormEntry(String title, List<String> choises, Context context){
        super(title,context);
        this.choises = choises;
    }

    public RadioFormEntry(String title, Context context){
        this(title,new ArrayList<String>(),context);
    }

    @Override
    public View getView() {

        radioButtons = new ArrayList<>();

        // layout
        LinearLayout radioLayout = new LinearLayout(getContext());
        radioLayout.setLayoutParams(getFormEntryParams());
        radioLayout.setOrientation(LinearLayout.VERTICAL);

        // set padding and background color for view
        radioLayout.setBackgroundResource(R.drawable.view_corner_radius);
        GradientDrawable dr = (GradientDrawable)radioLayout.getBackground();
        dr.setColor(getResources().getColor(R.color.colorLightGrey));

        // text view
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(getNestedFormEntryParams());
        tv.setText(getTitle());
        tv.setTextSize(getLabelSize());
        radioLayout.addView(tv);

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
        radioLayout.addView(radioGroup);

        return radioLayout;

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
