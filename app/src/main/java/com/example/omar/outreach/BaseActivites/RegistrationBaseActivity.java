package com.example.omar.outreach.BaseActivites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.outreach.Helping.FormEntries.FormEntry;
import com.example.omar.outreach.R;

import java.util.ArrayList;

public abstract class RegistrationBaseActivity extends AppCompatActivity {

    protected LinearLayout formLayout;
    protected ArrayList<FormEntry> formEntries;
    protected final String ACTION_BUTTON_TEXT_NEXT = "Next";
    protected final String ACTION_BUTTON_TEXT_DONE= "Done";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // init ui
        formLayout = findViewById(R.id.formLayout);

        // title
        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText(getScreenTitle());

        //add form elements
        this.formEntries = getFormElements();
        addEntriesToLayout(this.formEntries);

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
