package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.omar.outreach.R;

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

        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(getFormEntryParams());

        // set padding and background color for view
        layout.setBackgroundResource(R.drawable.view_corner_radius);
        GradientDrawable dr = (GradientDrawable)layout.getBackground();
        dr.setColor(getResources().getColor(R.color.colorLightGrey));

        // text view
        et = new EditText(getContext());
        et.setLayoutParams(getNestedFormEntryParams());
        et.setHint(getTitle());

        layout.addView(et);

        return layout;
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