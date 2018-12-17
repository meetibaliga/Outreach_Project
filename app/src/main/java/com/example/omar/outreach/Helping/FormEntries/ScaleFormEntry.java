package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
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

import info.hoang8f.android.segmented.SegmentedGroup;

public class ScaleFormEntry extends RadioFormEntry {

    private TextView minValue;
    private TextView maxValue;
    private String lowLabel;
    private String highLabel;

    //ui
    protected ConstraintLayout minMaxLabelsContainer;

    // ids

    private int MIN_VIEW_ID = 0;
    private int MAX_VIEW_ID = 1;
    private int CONTAINER_VIEW_ID = 2;


    public ScaleFormEntry(String title, Context context, int scaleSize, String lowLabel, String highLabel){
        super(title,context);
        this.lowLabel = lowLabel;
        this.highLabel = highLabel;
        this.minValue = new TextView(getContext());
        this.maxValue = new TextView(getContext());
        this.minMaxLabelsContainer = new ConstraintLayout(getContext());


        ArrayList<String> choices = new ArrayList<String>();

        for (int i = 0 ; i < scaleSize ; i++){

            if(i == 0){
                choices.add(i+1+"");
            }else if (i == scaleSize-1){
                choices.add(i+1+"");
            }else{
                choices.add(i+1+"");
            }

        }

        // add low and high label
        choices.get(0).concat(" "+lowLabel);
        choices.get(choices.size()-1).concat(" "+highLabel);

        setChoises(choices);

        // layout params

    }

    @Override
    public View getView() {

        // make choices spread along the screen
        LinearLayout superLayout = (LinearLayout) super.getView();

        // labels container
        minMaxLabelsContainer.setId(CONTAINER_VIEW_ID);
        minMaxLabelsContainer.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // min value
        minValue.setId(MIN_VIEW_ID);
        minValue.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        minValue.setText(lowLabel);
        minValue.setTextSize(12);
        minMaxLabelsContainer.addView(minValue);

        // max
        maxValue.setId(MAX_VIEW_ID);
        maxValue.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        maxValue.setText(highLabel);
        maxValue.setTextSize(12);
        minMaxLabelsContainer.addView(maxValue);

        // constraints
        ConstraintSet set = new ConstraintSet();
        set.clone(minMaxLabelsContainer);
        set.connect(minValue.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0);
        set.connect(minValue.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
        set.connect(maxValue.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0);
        set.connect(maxValue.getId(),ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END,0);
        set.applyTo(minMaxLabelsContainer);

        superLayout.addView(minMaxLabelsContainer);

        return superLayout;

    }

    @Override
    public RadioButton getRadioButtonType() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (RadioButton) inflater.inflate(R.layout.radio_button_item,null);

    }

    @Override
    public RadioGroup getRadioGroupType() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SegmentedGroup group = (SegmentedGroup) inflater.inflate(R.layout.segmented_group,null);
        group.setTintColor(getResources().getColor(R.color.colorPrimary));

        return group;
    }

    @Override
    public void setRadioButtonParams(RadioButton radioButton) {
        // do nothing
    }

    @Override
    public void updateBackground(RadioGroup group) {

        SegmentedGroup seg = (SegmentedGroup) group;
        seg.updateBackground();

    }

    @Override
    public void setRadioButtonSize(RadioButton radio) {

//        int numChoices = getChoises().size();
//        int parentWidth = super.getWidth();
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        radio.setLayoutParams(params);


    }
}
