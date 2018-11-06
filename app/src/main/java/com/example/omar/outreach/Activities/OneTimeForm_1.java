package com.example.omar.outreach.Activities;

import android.content.Intent;

import com.example.omar.outreach.App;
import com.example.omar.outreach.BaseActivites.RegistrationBaseActivity;
import com.example.omar.outreach.Helping.FormEntries.DropDownFormEntry;
import com.example.omar.outreach.Helping.FormEntries.FormEntry;
import com.example.omar.outreach.Helping.FormEntries.ListPopulatingHelpers;
import com.example.omar.outreach.Helping.FormEntries.RadioFormEntry;
import com.example.omar.outreach.Helping.FormEntries.ShortTextFormEntry;
import com.example.omar.outreach.Helping.FormEntries.YesNoFormEntry;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OneTimeForm_1 extends RegistrationBaseActivity {

    // ui
    private FormEntry firstName;
    private FormEntry lastName;
    private FormEntry address_1;
    private FormEntry address_2;
    private FormEntry city;
    private FormEntry zip_code;
    private FormEntry relationship;
    private FormEntry sex;
    private FormEntry dateOfBirth;
    private FormEntry martialStatus;
    private FormEntry highest;
    private FormEntry employed;
    private FormEntry race;
    private FormEntry income;

    @Override
    protected ArrayList<FormEntry> getFormElements() {

        ArrayList<FormEntry> entries = new ArrayList<>();

        // first name
        firstName = new ShortTextFormEntry(getResources().getString(R.string.firstName_question),this);
        entries.add(firstName);

        // last name
        lastName = new ShortTextFormEntry(getResources().getString(R.string.lastName_question),this);
        entries.add(lastName);

        //address 1
        address_1 = new ShortTextFormEntry(getResources().getString(R.string.address1_question),this);
        entries.add(address_1);

        //address 2
        address_2 = new ShortTextFormEntry(getResources().getString(R.string.address2_question),this);
        entries.add(address_2);

        //address 2
        city = new ShortTextFormEntry(getResources().getString(R.string.city_question),this);
        entries.add(city);

        //address 2
        zip_code = new ShortTextFormEntry(getResources().getString(R.string.zip_code),this);
        entries.add(zip_code);

        // income
        income = new ShortTextFormEntry(getResources().getString(R.string.income_question),this);
        entries.add(income);

        // Relationship
        relationship = new DropDownFormEntry(getResources().getString(R.string.relationship_question),this, ListPopulatingHelpers.getListOf("Self","Son","Spouse","Father"));
        entries.add(relationship);

        // sex
        sex = new RadioFormEntry(getResources().getString(R.string.sex_question),ListPopulatingHelpers.getListOf("Male","Female","Don't want to say"),this);
        entries.add(sex);

        // dob
        dateOfBirth = new DropDownFormEntry(getResources().getString(R.string.dateOfBirth_question),this,getListOfMonthes(),ListPopulatingHelpers.getListOfYears());
        entries.add(dateOfBirth);

        // Martial Status
        martialStatus = new RadioFormEntry(getResources().getString(R.string.martialStatus_question),ListPopulatingHelpers.getListOf("Single","Maried"),this);
        entries.add(martialStatus);

        // Highest Degree
        highest = new DropDownFormEntry(getResources().getString(R.string.highest_question),this,ListPopulatingHelpers.getListOf("Less than High School","High School", "College"));
        entries.add(highest);

        // employed ?
        employed = new YesNoFormEntry(getResources().getString(R.string.employed_question),this);
        entries.add(employed);

        // race
        race = new DropDownFormEntry(getResources().getString(R.string.race_question),this,ListPopulatingHelpers.getListOf("American, Indian or Alaska Native","Hispanic", "Prefer not to disclose"));
        entries.add(race);

        return entries;
    }

    protected void nextButtonClicked() {

        if (!App.checkForm(this.formEntries,this))
            return;

        // if all filled do the following

        App.user.setUserId(App.USER_ID);
        App.user.setFirstName(firstName.getValue());
        App.user.setLastName(lastName.getValue());
        App.user.set_address1(address_1.getValue());
        App.user.set_address2(address_2.getValue());
        App.user.set_city(city.getValue());
        App.user.set_zip(zip_code.getValue());
        App.user.setRelationToHousehold(relationship.getValue());
        App.user.setSex(sex.getValue());
        App.user.setMonthBirth(dateOfBirth.getValues().get(0));
        App.user.setYearBirth(dateOfBirth.getValues().get(1));
        App.user.setMartialStatus(martialStatus.getValue());
        App.user.setHighestDegree(highest.getValue());
        App.user.setEmployed(employed.getValue());
        App.user.setRace(race.getValue());
        App.user.setAverageIncome(income.getValue());

        // navigate to next screen

        Intent intent;

        if(relationship.getValue().equalsIgnoreCase("self")){
             intent = new Intent(this,OneTimeForm_2.class);
        }else{
            intent = new Intent(this,OneTimeForm_4.class);
        }

        startActivity(intent);


    }

    @Override
    protected String getScreenTitle() {
        return getResources().getString(R.string.personal_title);
    }

    @Override
    protected String getActionButtonText() {
        return ACTION_BUTTON_TEXT_NEXT;
    }


    private List<String> getListOfMonthes(){
       return new ArrayList(Arrays.asList(getResources().getStringArray(R.array.months)));
    }
}
