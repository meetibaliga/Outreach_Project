package com.example.omar.outreach.Activities;

import android.content.Intent;

import com.example.omar.outreach.App;
import com.example.omar.outreach.BaseActivites.PeriodicalBaseFormActivity;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.R;

public class PeriodicalFormActivity_1 extends PeriodicalBaseFormActivity {

    @Override
    protected String getScreenTitle() {
        return "1/5 Feelings";
    }

    @Override
    public String getTitleQuestion() {
        return "How Are you feeling right now ?";
    }

    @Override
    protected boolean showSubtitle() {
        return true;
    }

    @Override
    protected String[] getTextsArray() {
        return getResources().getStringArray(R.array.emotions);
    }

    @Override
    protected String[] getFacesArray() {
        return getResources().getStringArray(R.array.emotions_faces);
    }

    @Override
    protected Intent getNextIntent() {
        return new Intent(this,PeriodicalFormActivity_2.class);
    }

    @Override
    protected void addItemToModel(String text) {
        App.inputEntry.getEmotions().add(text);
    }

    @Override
    protected void removeItemFromModel() {
        App.inputEntry.getEmotions().remove(0);
    }

    @Override
    protected void specificCode() {

        // setup user id and entry id
        App.inputEntry = new Entry();

    }
}
