package com.hammad.omar.outreach.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.BaseActivites.PeriodicalBaseFormActivity;
import com.hammad.omar.outreach.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PeriodicalFormActivity_2 extends PeriodicalBaseFormActivity {

    private static final String TAG = PeriodicalFormActivity_2.class.getSimpleName();


    @Override
    protected String getScreenTitle() {
        return getString(R.string.activities);
    }

    @Override
    public String getTitleQuestion() {
        return getString(R.string.whatAreYouDoing);
    }

    @Override
    protected boolean showSubtitle() {
        return true;
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
    protected void addItemToModel(String text) {
        App.inputEntry.getActivities().add(text);
        Log.d(TAG,App.inputEntry.getActivities().toString());
    }

    @Override
    protected void removeItemFromModel() {
        App.inputEntry.getActivities().remove(0);
    }

    @Override
    protected void specificCode() {
        App.inputEntry.getActivities().clear();
    }
}
