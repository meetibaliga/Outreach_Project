package com.example.omar.outreach.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.omar.outreach.Helping.FormEntries.RadioFormEntry;
import com.example.omar.outreach.Helping.FormEntries.ShortTextFormEntry;
import com.example.omar.outreach.Helping.FormEntries.YesNoFormEntry;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Registration_1 extends AppCompatActivity {

    private static LinearLayout formLayout;
    private ArrayList<FormEntry> formEntries;

    // ui

    private FormEntry firstName;
    private FormEntry lastName;
    private FormEntry relationship;
    private FormEntry sex;
    private FormEntry dateOfBirth;
    private FormEntry martialStatus;
    private FormEntry highest;
    private FormEntry employed;
    private FormEntry race;
    private FormEntry income;

    private void nextButtonClicked() {

        // check form

        for(FormEntry f: formEntries){
            if(f.isEmpty()){
                Log.d("Form","Empty");
                Toast.makeText(this,getString(R.string.fillForm),Toast.LENGTH_LONG);
                return;
            }
        }

        // if all filled do the following

        App.userDo.setUserId(App.USER_ID);
        App.userDo.setFirstName(firstName.getValue());
        App.userDo.setLastName(lastName.getValue());
        App.userDo.setRelationToHousehold(relationship.getValue());
        App.userDo.setSex(sex.getValue());
        App.userDo.setMonthBirth(dateOfBirth.getValues().get(0));
        App.userDo.setYearBirth(dateOfBirth.getValues().get(1));
        App.userDo.setMartialStatus(martialStatus.getValue());
        App.userDo.setHighestDegree(highest.getValue());
        App.userDo.setEmployed(employed.getValue());
        App.userDo.setRace(race.getValue());
        App.userDo.setAverageIncome(income.getValue());

        // navigate to next screen

        Intent intent = new Intent(this,Registration_2.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_1);

        // init ui

        formLayout = findViewById(R.id.formLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 24;

        //TITLE

        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText("Household and Personal Information");

        // Form model

        formEntries = new ArrayList<>();

        // first name
        firstName = new ShortTextFormEntry("First Name",this);
        formEntries.add(firstName);

        // last name
        lastName = new ShortTextFormEntry("Last Name",this);
        formEntries.add(lastName);

        // Relationship
        relationship = new DropDownFormEntry("Relationship with household head",this, ListPopulatingHelpers.getListOf("Self","Son","Spouse","Father"));
        formEntries.add(relationship);

        // sex
        sex = new RadioFormEntry("Sex",ListPopulatingHelpers.getListOf("Male","Female","Don't want to say"),this);
        formEntries.add(sex);

        // dob
        dateOfBirth = new DropDownFormEntry("Date of Birth",this,getListOfMonthes(),ListPopulatingHelpers.getListOfYears());
        formEntries.add(dateOfBirth);

        // Martial Status
        martialStatus = new RadioFormEntry("Martial Status",ListPopulatingHelpers.getListOf("Single","Maried"),this);
        formEntries.add(martialStatus);

        // Highest Degree
        highest = new DropDownFormEntry("Highest Degree",this,ListPopulatingHelpers.getListOf("Less than High School","High School", "College"));
        formEntries.add(highest);

        // employed ?
        employed = new YesNoFormEntry("Are you currently employed?",this);
        formEntries.add(employed);

        // race
        race = new DropDownFormEntry("What race do you consider yourself ? ",this,ListPopulatingHelpers.getListOf("American, Indian or Alaska Native","Hispanic"));
        formEntries.add(race);

        // income
        income = new ShortTextFormEntry("What is your yearly average income?",this);
        formEntries.add(income);


        // ADD entries
        addEntries(formEntries);


        // Add Next Button

        Button nextButton = new Button(this);
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

    private List<String> getListOfMonthes(){
       return new ArrayList(Arrays.asList(getResources().getStringArray(R.array.months)));
    }
}
