package com.hammad.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hammad.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DropDownFormEntry extends FormEntry{

    private List<List<String>> choices;
    private List<Spinner> spinners;

    public DropDownFormEntry(String title, Context context,List<String> ...choices){
        super(title,context);
        this.choices = new ArrayList<>();
        this.spinners = new ArrayList<>();
        for(List<String> c : choices){
            this.choices.add(c);
        }
    }

    @Override
    public View getView() {

        // set padding and background color for view
        containerLayout.setBackgroundResource(R.drawable.view_corner_radius);
        GradientDrawable dr = (GradientDrawable)containerLayout.getBackground();
        dr.setColor(getResources().getColor(R.color.colorLightGrey));

        // text
        TextView dropDownTv = new TextView(getContext());
        dropDownTv.setLayoutParams(getNestedFormEntryParams());
        dropDownTv.setText(getTitle());
        dropDownTv.setTextSize(getLabelSize());
        containerLayout.addView(dropDownTv);

        // spinners container
        LinearLayout spinnerContainer = new LinearLayout(getContext());
        spinnerContainer.setLayoutParams(getNestedFormEntryParams());
        spinnerContainer.setOrientation(LinearLayout.HORIZONTAL);
        containerLayout.addView(spinnerContainer);

        // add spinners to container

        for(int i = 0 ; i < this.choices.size() ; i++){

            Spinner spinner = new Spinner(getContext(),Spinner.MODE_DROPDOWN);
            spinner.setId(i);
            spinner.setLayoutParams(getNestedHorizontalFormEntryParams());
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,this.choices.get(i));
            spinner.setAdapter(spinnerAdapter);
            spinnerContainer.addView(spinner);


            spinners.add(spinner);
        }

        return containerLayout;
    }

    @Override
    public List<String> getValues() {
        ArrayList<String> list = new ArrayList<String>();

        for (Spinner s : spinners){
            list.add(s.getSelectedItem().toString());
        }

        return list;
    }

}
