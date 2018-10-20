package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.util.Log;

import com.example.omar.outreach.App;
import com.example.omar.outreach.BaseActivites.RegistrationBaseActivity;
import com.example.omar.outreach.Helping.FormEntries.DropDownFormEntry;
import com.example.omar.outreach.Helping.FormEntries.FormEntry;
import com.example.omar.outreach.Helping.FormEntries.YesNoFormEntry;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OneTimeForm_4 extends RegistrationBaseActivity {


    // ui
    private YesNoFormEntry fresh_air;
    private FormEntry life_satisfied;
    private FormEntry all_things;
    private FormEntry standard;
    private FormEntry concerns;
    private FormEntry connected;
    private YesNoFormEntry asthma;
    private FormEntry helpless;

    public void nextButtonClicked(){

        //check form
        if (!checkForm())
            return;

        // do these
        App.user.setAirFresh(fresh_air.getTrueOrFalse());
        App.user.setLifeSatisfaction(life_satisfied.getValue());
        App.user.setHowHappy(all_things.getValue());
        App.user.setStandardSatisfaction(standard.getValue());
        App.user.setPollutionImpact(concerns.getValue());
        App.user.setConnectedToCommunity(connected.getValue());
        App.user.setAsthmaDiagnosed(asthma.getTrueOrFalse());
        App.user.setHelpless(helpless.getValue());

        Log.d("DB",App.user.toString());

        // navigate to next screen

        Intent intent = new Intent(this,OneTimeFormCompletedActivity.class);
        startActivity(intent);

    }

    @Override
    protected String getScreenTitle() {
        return getResources().getString(R.string.well_title);
    }

    @Override
    protected String getActionButtonText() {
        return ACTION_BUTTON_TEXT_DONE;
    }

    @Override
    protected ArrayList<FormEntry> getFormElements() {


        ArrayList<FormEntry> entries = new ArrayList<>();

        // fresh air

        fresh_air = new YesNoFormEntry(getResources().getString(R.string.fresh_air_question),this);
        entries.add(fresh_air);

        // life satisfied

        life_satisfied = new DropDownFormEntry(getResources().getString(R.string.life_satisfied_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        entries.add(life_satisfied);

        // all things satisfied

        all_things = new DropDownFormEntry(getResources().getString(R.string.all_things_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        entries.add(all_things);

        // present standard

        standard = new DropDownFormEntry(getResources().getString(R.string.standard_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        entries.add(standard);

        // concerns

        concerns = new DropDownFormEntry(getResources().getString(R.string.concerns_question)
                ,this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.agreement_scale))));
        entries.add(concerns);

        // connected

        connected = new DropDownFormEntry(getResources().getString(R.string.connected_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        entries.add(connected);

        // asthma

        asthma = new YesNoFormEntry(getResources().getString(R.string.asthma_question),this);
        entries.add(asthma);

        // helpless

        helpless = new YesNoFormEntry(getResources().getString(R.string.helpless_question),this);
        entries.add(helpless);

        return entries;
    }

}
