package com.example.omar.outreach.Activities;

import android.content.Intent;

import com.example.omar.outreach.App;
import com.example.omar.outreach.BaseActivites.PeriodicalBaseFormActivity;
import com.example.omar.outreach.R;

public class PeriodicalFormActivity_3 extends PeriodicalBaseFormActivity {

    @Override
    protected String getScreenTitle() {
        return "3/5 Place";
    }

    @Override
    public String getTitleQuestion() {
        return "Where are you right now?";
    }

    @Override
    protected String[] getTextsArray() {
        return getResources().getStringArray(R.array.locations);
    }

    @Override
    protected String[] getFacesArray() {
        return getResources().getStringArray(R.array.locations_faces);
    }

    @Override
    protected Intent getNextIntent() {
        return new Intent(this,PeriodicalFormActivity_4.class);
    }

    @Override
    protected void addItemToModel(String text) {
        App.inputEntry.setPlace(text);
    }

    @Override
    protected void removeItemFromModel() {
        App.inputEntry.setPlace("");
    }

    protected int getMaxAllowedSelection(){
        return 1;
    }

}
