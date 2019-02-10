package com.hammad.omar.outreach.BaseActivites;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hammad.omar.outreach.Helping.FormEntries.FormEntry;
import com.hammad.omar.outreach.R;

import java.util.ArrayList;

public abstract class RegistrationBaseActivity extends AppCompatActivity {

    protected LinearLayout formLayout;
    protected ArrayList<FormEntry> formEntries;
    protected String ACTION_BUTTON_TEXT_NEXT = "NEXT";
    protected String ACTION_BUTTON_TEXT_DONE= "DONE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // constants
        ACTION_BUTTON_TEXT_NEXT = getString(R.string.next);
        ACTION_BUTTON_TEXT_DONE = getString(R.string.done);

        // init ui
        formLayout = findViewById(R.id.formLayout);

        // title
        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText(getScreenTitle());

        //add form elements
        this.formEntries = getFormElements();
        addEntriesToLayout(this.formEntries);

        //error message
        TextView errorMessage = new TextView(this);
        errorMessage.setText(R.string.pleaseComplete);
        errorMessage.setTextColor(Color.RED);
        errorMessage.setTextSize(12);
        errorMessage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        errorMessage.setVisibility(View.GONE);
        formLayout.addView(errorMessage);

        //button
        Button actionButton = new Button(this);
        actionButton.setText(getActionButtonText());
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButtonClicked();
            }
        });
        formLayout.addView(actionButton);

    }

    private void addEntriesToLayout(ArrayList<FormEntry> formEntries) {
        for (int i = 0 ; i < formEntries.size() ; i++){
            View entry = formEntries.get(i).getView();
            formLayout.addView(entry);
        }
    }

    protected boolean checkForm(){
        // check form
        for(FormEntry f: this.formEntries){
            if(f.isEmpty()){
                Toast.makeText(this,getString(R.string.fillForm),Toast.LENGTH_LONG);
                return false;
            }
        }
        return true;
    }

    // abstract

    protected abstract void nextButtonClicked();
    protected abstract String getScreenTitle();
    protected abstract String getActionButtonText();
    protected abstract ArrayList<FormEntry> getFormElements();



}
