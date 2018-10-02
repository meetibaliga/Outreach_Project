package com.example.omar.outreach.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Helping.FormEntries.DropDownFormEntry;
import com.example.omar.outreach.Helping.FormEntries.FormEntry;
import com.example.omar.outreach.Helping.FormEntries.ListPopulatingHelpers;
import com.example.omar.outreach.Helping.FormEntries.ShortTextFormEntry;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registration_2 extends AppCompatActivity {

    private static LinearLayout formLayout;
    private static Activity activity;
    private ArrayList<FormEntry> formEntries;


    // ui

    private FormEntry typeOfHome;
    private FormEntry yearBuilt;
    private FormEntry stories;
    private FormEntry pets;
    private FormEntry occupants;


    private void nextButtonClicked() {

        // check if form is complete

        for(FormEntry f: formEntries){
            if(f.isEmpty()){
                Toast.makeText(this,getString(R.string.fillForm),Toast.LENGTH_LONG);
                return;
            }
        }

        // do th

        App.userDo.setHomeType(typeOfHome.getValue());
        App.userDo.setHomeBuiltYear(yearBuilt.getValue());
        App.userDo.setStories(stories.getValue());
        App.userDo.setNumPets(pets.getValue());
        App.userDo.setOccupantsLived(occupants.getValue());

        // navigate to next screen

        Intent intent = new Intent(this,Registration_3.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_2);

        // init

        activity = this;

        // init ui

        formLayout = findViewById(R.id.formLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 24;

        //TITLE

        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText("Home Information");
        tvTitle.setLayoutParams(params);

        // Form model

        formEntries = new ArrayList<FormEntry>();

        // built year

        yearBuilt = new DropDownFormEntry("Year home built",this, ListPopulatingHelpers.getListOfYears());
        formEntries.add(yearBuilt);

        // shortText

        stories = new ShortTextFormEntry("How many stories is the house",this);
        formEntries.add(stories);

        //pets

        pets = new ShortTextFormEntry("How many pets do you have ?",this);
        formEntries.add(pets);

        // occupants

        occupants = new DropDownFormEntry("How long have the occupants lived there ?",this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.how_long))));
        formEntries.add(occupants);

        // type of home

        typeOfHome = new DropDownFormEntry("What type of home are you living in?",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.type_of_homes))));
        formEntries.add(typeOfHome);

        // ADD entries
        addEntries(formEntries);


        // Add Next Button

        final Button nextButton = new Button(this);
        nextButton.setText("Next");
        nextButton.setTag("button");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButtonClicked();
            }
        });

        formLayout.addView(nextButton);


    }

    private void addEntries(List<FormEntry> formEntries) {
        for (int i = 0 ; i < formEntries.size() ; i++){
            FormEntry entry = formEntries.get(i);
            formLayout.addView(entry.getView());
        }
    }
}
