package com.example.omar.outreach.Activities;

import android.content.Intent;

import com.example.omar.outreach.App;
import com.example.omar.outreach.BaseActivites.RegistrationBaseActivity;
import com.example.omar.outreach.Helping.FormEntries.DropDownFormEntry;
import com.example.omar.outreach.Helping.FormEntries.FormEntry;
import com.example.omar.outreach.Helping.FormEntries.ListPopulatingHelpers;
import com.example.omar.outreach.Helping.FormEntries.ShortTextFormEntry;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OneTimeForm_2 extends RegistrationBaseActivity {


    // ui
    private FormEntry typeOfHome;
    private FormEntry yearBuilt;
    private FormEntry homeArea;
    private FormEntry pets;
    private FormEntry occupants;


    protected void nextButtonClicked() {

        // check if form is complete
        if (!App.checkForm(this.formEntries,this))
            return;

        // do the
        App.user.setHomeType(typeOfHome.getValue());
        App.user.setHomeBuiltYear(yearBuilt.getValue());
        App.user.setHomeArea(homeArea.getValue());
        App.user.setNumPets(pets.getValue());
        App.user.setOccupantsLived(occupants.getValue());

        // navigate to next screen
        Intent intent = new Intent(this,OneTimeForm_3.class);
        startActivity(intent);

    }

    @Override
    protected String getScreenTitle() {
        return getResources().getString(R.string.home_title);
    }

    @Override
    protected String getActionButtonText() {
        return ACTION_BUTTON_TEXT_NEXT;
    }

    @Override
    protected ArrayList<FormEntry> getFormElements() {

        ArrayList<FormEntry> entries = new ArrayList<>();

        // built year

        yearBuilt = new DropDownFormEntry(getResources().getString(R.string.yearBuilt_question),this, ListPopulatingHelpers.getListOfYears());
        entries.add(yearBuilt);

        // shortText

        homeArea = new ShortTextFormEntry(getResources().getString(R.string.squareFeet_question),this);
        ((ShortTextFormEntry) homeArea).setTypeToNumber();
        entries.add(homeArea);

        //pets

        pets = new ShortTextFormEntry(getResources().getString(R.string.pets_question),this);
        ((ShortTextFormEntry) pets).setTypeToNumber();
        entries.add(pets);

        // occupants

        occupants = new DropDownFormEntry(getResources().getString(R.string.occupants_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.how_long))));
        entries.add(occupants);

        // type of home

        typeOfHome = new DropDownFormEntry(getResources().getString(R.string.typeOfHome_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.type_of_homes))));
        entries.add(typeOfHome);

        return entries;
    }
}
