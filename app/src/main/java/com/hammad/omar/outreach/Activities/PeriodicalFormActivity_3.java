package com.hammad.omar.outreach.Activities;

import android.content.Intent;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.BaseActivites.PeriodicalBaseFormActivity;
import com.hammad.omar.outreach.R;

public class PeriodicalFormActivity_3 extends PeriodicalBaseFormActivity {

    @Override
    protected String getScreenTitle() {
        return getString(R.string.place);
    }

    @Override
    public String getTitleQuestion() {
        return getString(R.string.whereAreYou);
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
