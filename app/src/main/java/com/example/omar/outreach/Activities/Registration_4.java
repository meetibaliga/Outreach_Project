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
import com.example.omar.outreach.Helping.FormEntries.YesNoFormEntry;
import com.example.omar.outreach.Managers.DBManager;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registration_4 extends AppCompatActivity {

    private static LinearLayout formLayout;
    private static Activity activity;
    private ArrayList<FormEntry> formEntries;


    // ui

    private FormEntry fresh_air;
    private FormEntry life_satisfied;
    private FormEntry all_things;
    private FormEntry standard;
    private FormEntry concerns;
    private FormEntry connected;
    private FormEntry asthma;
    private FormEntry helpless;

    public void nextButtonClicked(){

        //check form

        for(FormEntry f: formEntries){
            if(f.isEmpty()){
                Toast.makeText(this,getString(R.string.fillForm),Toast.LENGTH_LONG).show();
                return;
            }
        }

        // do these

        App.userDo.setAirFresh(fresh_air.getValue());
        App.userDo.setLifeSatisfaction(life_satisfied.getValue());
        App.userDo.setHowHappy(all_things.getValue());
        App.userDo.setStandardSatisfaction(standard.getValue());
        App.userDo.setPollutionImpact(concerns.getValue());
        App.userDo.setConnectedToCommunity(connected.getValue());
        App.userDo.setAsthmaDiagnosed(asthma.getValue());
        App.userDo.setHelpless(helpless.getValue());

        Log.d("DB",App.userDo.toString());

        // navigate to next screen

        Intent intent = new Intent(this,RegistrationCompletedActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_4);

        // init

        activity = this;

        // init ui

        formLayout = findViewById(R.id.formLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 24;

        //TITLE

        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText("Well-being and climate change questions");
        tvTitle.setLayoutParams(params);

        // Form model

        formEntries = new ArrayList<>();


        // Build form


        // fresh air

        fresh_air = new YesNoFormEntry("Do you think that the air is fresh in your home\u2028\n",this);
        formEntries.add(fresh_air);

        // life satisfied

        life_satisfied = new DropDownFormEntry("How satisfied are you with how your life has turned out so far?",this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        formEntries.add(life_satisfied);

        // all things satisfied

        all_things = new DropDownFormEntry("Taking all things together, how happy would you say you are?",this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        formEntries.add(all_things);

        // present standard

        standard = new DropDownFormEntry("How satisfied are you with your present standard of living ",this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        formEntries.add(standard);

        // concerns

        concerns = new DropDownFormEntry("I have concerns about the potential impacts due to the pollution or adverse effects caused by the construction outside my home"
                ,this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.agreement_scale))));
        formEntries.add(concerns);

        // connected

        connected = new DropDownFormEntry("How much do you feel connected with you community",this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        formEntries.add(connected);

        // asthma

        asthma = new YesNoFormEntry("Have you been diagnosed with asthma",this);
        formEntries.add(asthma);

        // helpless

        helpless = new YesNoFormEntry("Helpless quesstion ... change later",this);
        formEntries.add(helpless);


        // ADD entries
        addEntries(formEntries);


        // Add Next Button

        Button nextButton = new Button(this);
        nextButton.setText("Done");
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
