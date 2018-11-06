package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

public class SegmentedFormEntry extends RadioFormEntry {

    public SegmentedFormEntry(String title, List<String> choises, Context context){
        super(title,choises,context);
    }

    public SegmentedFormEntry(String title, Context context){
        super(title,context);
    }

    @Override
    public RadioButton getRadioButtonType() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return (RadioButton) inflater.inflate(R.layout.radio_button_item,null);
    }

    @Override
    public RadioGroup getRadioGroupType() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return (SegmentedGroup) inflater.inflate(R.layout.segmented_group,null);
    }

    @Override
    public void setRadioButtonParams(RadioButton radioButton) {
        // do nothing
    }
}
