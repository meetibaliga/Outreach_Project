package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
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

        LinearLayout dropDownLayout = new LinearLayout(getContext());
        dropDownLayout.setLayoutParams(getFormEntryParams());
        dropDownLayout.setOrientation(LinearLayout.VERTICAL);

        // text

        TextView dropDownTv = new TextView(getContext());
        dropDownTv.setLayoutParams(getNestedFormEntryParams());
        dropDownTv.setText(getTitle());
        dropDownTv.setTextSize(getLabelSize());
        dropDownLayout.addView(dropDownTv);

        // spinners container

        LinearLayout spinnerContainer = new LinearLayout(getContext());
        spinnerContainer.setLayoutParams(getNestedFormEntryParams());
        spinnerContainer.setOrientation(LinearLayout.HORIZONTAL);
        dropDownLayout.addView(spinnerContainer);

        // add spinners to container

        for(int i = 0 ; i < this.choices.size() ; i++){
            Spinner spinner = new Spinner(getContext());
            spinner.setId(i);
            spinner.setLayoutParams(getNestedHorizontalFormEntryParams());
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,this.choices.get(i));
            spinner.setAdapter(spinnerAdapter);
            spinnerContainer.addView(spinner);
            spinners.add(spinner);
        }


        return dropDownLayout;
    }

    @Override
    public List<String> getValues() {
        ArrayList<String>list = new ArrayList<String>();

        for (Spinner s : spinners){
            list.add(s.getSelectedItem().toString());
        }

        return list;
    }

}
