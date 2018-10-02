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
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registration_3 extends AppCompatActivity {

    private static LinearLayout formLayout;
    private static Activity activity;
    private ArrayList<FormEntry> formEntries;

    // ui

    private FormEntry ventilationType;
    private FormEntry filterationSystem;
    private FormEntry furnace;
    private FormEntry practice_summer;
    private FormEntry practice_winter;
    private FormEntry window_open;
    private FormEntry ventilation_window;
    private FormEntry exhaust_fan;
    private FormEntry kerosen;
    private FormEntry radon;

    public void nextButtonClicked(){


        // check form

        for(FormEntry f: formEntries){
            if(f.isEmpty()){
                Toast.makeText(this,getString(R.string.fillForm),Toast.LENGTH_LONG);
                return;
            }
        }

        // do these next

        App.userDo.setVentilationType(ventilationType.getValue());
        App.userDo.setFilterationSystem(filterationSystem.getValue());
        App.userDo.setFurnaceSystem(furnace.getValue());
        App.userDo.setVentilationPracticeSummer(practice_summer.getValue());
        App.userDo.setVentilationPracticeWinter(practice_winter.getValue());
        App.userDo.setWindowsOpen(window_open.getValue());
        App.userDo.setVentilationWindow(ventilation_window.getValue());
        App.userDo.setExhaustFans(exhaust_fan.getValue());
        App.userDo.setKerosenUse(kerosen.getValue());
        App.userDo.setRadonMitigation(radon.getValue());

        // navigate to next screen

        Intent intent = new Intent(this,Registration_4.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_3);

        // init

        activity = this;

        // init ui

        formLayout = findViewById(R.id.formLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 24;

        //TITLE

        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText("Information on home ventilation and Air-Conditioning");
        tvTitle.setLayoutParams(params);


        // Form model

        formEntries = new ArrayList<FormEntry>();

        // Build form entries

        // ventilation type

        ventilationType = new DropDownFormEntry("Ventilation type",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.ventilation_type))));
        formEntries.add(ventilationType);

        // filteration system

        filterationSystem = new DropDownFormEntry("Air handling unit (AHU) filtration system, if AHU is present",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.filteration_sysytem))));
        formEntries.add(filterationSystem);

        // furnace

        furnace = new DropDownFormEntry("Furnace / Heating System",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.furnace_system))));
        formEntries.add(furnace);

        // practice summer

        practice_summer = new DropDownFormEntry("Most common ventilation practice during summer",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.ventilation_practice_summer))));
        formEntries.add(practice_summer);

        // practice winter

        practice_winter = new DropDownFormEntry("Most common ventilation practice during winter",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.ventilation_practice_winter))));
        formEntries.add(practice_winter);

        // window open

        window_open = new DropDownFormEntry("How often are the windows open in any given day?",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.windows_open))));
        formEntries.add(window_open);

        // window open

        ventilation_window = new DropDownFormEntry("Window Ventilation type",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.ventilation_type_window))));
        formEntries.add(ventilation_window);

        // exhaust fan

        exhaust_fan = new DropDownFormEntry("How often are the kitchen/bathroom exhaust fans used?",this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.exhaust_fan_use))));
        formEntries.add(exhaust_fan);

        // kerosen

        kerosen = new YesNoFormEntry("Do the residents use kerosene heaters/ovens to heat the home?",this);
        formEntries.add(kerosen);

        // window open

        radon = new YesNoFormEntry("Do the residents have a radon mitigation system in place?",this);
        formEntries.add(radon);

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

}