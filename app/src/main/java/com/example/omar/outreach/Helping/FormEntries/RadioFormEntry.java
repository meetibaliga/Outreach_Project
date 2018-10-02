package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RadioFormEntry extends FormEntry{

    private List<String> choises;
    private RadioGroup radioGroup;
    private List<RadioButton> radioButtons;

    public RadioFormEntry(String title, List<String> choises, Context context){
        super(title,context);
        this.choises = choises;
    }

    public RadioFormEntry(List<String> choises, Context context){
        super(context);
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

        // text view
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(getNestedFormEntryParams());
        tv.setText(getTitle());
        tv.setTextSize(getLabelSize());
        radioLayout.addView(tv);

        // Radios
        radioGroup = new RadioGroup(getContext());
        radioGroup.setLayoutParams(getNestedFormEntryParams());
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0 ; i < choises.size() ; i++){
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(i);
            radioButton.setText(choises.get(i));
            radioButton.setLayoutParams(getNestedHorizontalFormEntryParams());
            radioGroup.addView(radioButton);
            radioButtons.add(radioButton);
        }

        // add radio group to layout
        radioLayout.addView(radioGroup);

        return radioLayout;

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


}
