package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.omar.outreach.App;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.List;

public class ShortTextFormEntry extends FormEntry{

    android.support.design.widget.TextInputLayout et;

    public ShortTextFormEntry(Context context) {
        this(null,context);
    }

    public ShortTextFormEntry(String title, Context context) {
        super(title, context);
        setupViews();
    }

    private void setupViews(){

        // text view
        et = new android.support.design.widget.TextInputLayout(getContext());
        et.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        // edit text
        final android.support.design.widget.TextInputEditText editText = new android.support.design.widget.TextInputEditText(getContext());
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setHint(getTitle());
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        //add views
        et.addView(editText);

    }

    public void setInputType(int type){

        EditText editText = et.getEditText();
        if (editText == null){return;}
        editText.setInputType(type);

    }

    public void setTypeToNumber(){
       setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    public View getView() {

        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(getFormEntryParams());

        // set padding and background color for view
        layout.setBackgroundResource(R.drawable.view_corner_radius);
        GradientDrawable dr = (GradientDrawable)layout.getBackground();
        dr.setColor(getResources().getColor(R.color.colorLightGrey));

        layout.addView(et);

        return layout;
    }

    @Override
    public List<String> getValues() {

        if(et.getEditText().getText().equals("")){
            return null;
        }else{
            ArrayList<String>list = new ArrayList<>();
            list.add(et.getEditText().getText().toString());
            return list;
        }
    }

}