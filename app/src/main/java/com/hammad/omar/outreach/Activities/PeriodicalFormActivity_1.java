package com.hammad.omar.outreach.Activities;

import android.content.Intent;
import android.util.Log;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.BaseActivites.PeriodicalBaseFormActivity;
import com.hammad.omar.outreach.Models.Entry;
import com.hammad.omar.outreach.Models.EntryDO;
import com.hammad.omar.outreach.R;

public class PeriodicalFormActivity_1 extends PeriodicalBaseFormActivity {

    private static final String TAG = PeriodicalFormActivity_1.class.getSimpleName();


    @Override
    protected String getScreenTitle() {
        return getString(R.string.feelings);
    }

    @Override
    public String getTitleQuestion() {
        return getString(R.string.areYouFeelingGood);
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
        Log.d(TAG,App.inputEntry.getEmotions().toString());
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