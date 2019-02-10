package com.hammad.omar.outreach.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Helping.FormEntries.FormEntry;
import com.hammad.omar.outreach.Helping.FormEntries.YesNoFormEntry;
import com.hammad.omar.outreach.R;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalFormActivity_5 extends AppCompatActivity {

    private static LinearLayout formLayout;
    private ArrayList<FormEntry> formEntries;

    // ui

    private YesNoFormEntry cough;
    private YesNoFormEntry limited_activities;
    private YesNoFormEntry asthma_attack;
    private YesNoFormEntry asthma_medication;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_5);

        // form layout

        formLayout = findViewById(R.id.formLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 24;

        //TITLE

        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText(R.string.answerTheFollowingHealth);
        setTitle(getString(R.string.health));


        // Form model

        formEntries = new ArrayList<>();

        // form entries

        cough = new YesNoFormEntry(getResources().getString(R.string.cough_question),this);
        formEntries.add(cough);

        limited_activities = new YesNoFormEntry(getResources().getString(R.string.limited_activities_question),this);
        formEntries.add(limited_activities);

        if (App.user.hasAsthma()){

            asthma_attack = new YesNoFormEntry(getResources().getString(R.string.asthma_attack_question),this);
            formEntries.add(asthma_attack);

            asthma_medication = new YesNoFormEntry(getResources().getString(R.string.asthma_medication_question),this);
            formEntries.add(asthma_medication);

        }

        // ADD entries
        addEntries(formEntries);

        // Add Next Button

        Button nextButton = new Button(this);
        nextButton.setText(R.string.done);
        nextButton.setTag(getString(R.string.button));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButtonClicked();
            }
        });

        formLayout.addView(nextButton);

    }

    private void nextButtonClicked() {

        if (!App.checkForm(this.formEntries,this))
            return;

        App.inputEntry.setCough(cough.getTrueOrFalse());
        App.inputEntry.setLimitedActivities(limited_activities.getTrueOrFalse());

        if (App.user.hasAsthma()){

            App.inputEntry.setAsthmaAttack(asthma_attack.getTrueOrFalse());
            App.inputEntry.setAsthmaMedication(asthma_medication.getTrueOrFalse());

        }else{

            App.inputEntry.setAsthmaAttack(false);
            App.inputEntry.setAsthmaMedication(false);
        }

        navigateToNextScreen();
    }

    private void navigateToNextScreen() {
        Intent intent = new Intent(this,PeriodicalFormCompletedActivity.class);
        startActivity(intent);
    }

    private void addEntries(List<FormEntry> formEntries) {
        for (int i = 0 ; i < formEntries.size() ; i++){
            FormEntry entry = formEntries.get(i);
            formLayout.addView(entry.getView());
        }
    }
}
