package com.example.omar.outreach.Activities;

import android.content.Intent;

import com.example.omar.outreach.App;
import com.example.omar.outreach.BaseActivites.PeriodicalBaseFormActivity;
import com.example.omar.outreach.R;

public class PeriodicalFormActivity_2 extends PeriodicalBaseFormActivity {

    @Override
    protected String getScreenTitle() {
        return "What are you doing right now?";
    }

    @Override
    protected String[] getTextsArray() {
        return getResources().getStringArray(R.array.activities);
    }

    @Override
    protected String[] getFacesArray() {
        return getResources().getStringArray(R.array.activities_faces);
    }

    @Override
    protected Intent getNextIntent() {
        return new Intent(this,PeriodicalFormActivity_3.class);
    }

    @Override
    protected void addItemToModel(CharSequence text) {
        App.inputEntry.getActivities().add(text.toString());

    }

    @Override
    protected void removeItemFromModel() {
        App.inputEntry.getEmotions().remove(0);
    }
}
