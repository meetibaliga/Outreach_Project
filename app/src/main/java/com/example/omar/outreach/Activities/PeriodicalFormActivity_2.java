package com.example.omar.outreach.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.omar.outreach.App;
import com.example.omar.outreach.BaseActivites.PeriodicalBaseFormActivity;
import com.example.omar.outreach.R;

import org.w3c.dom.Text;

public class PeriodicalFormActivity_2 extends PeriodicalBaseFormActivity {

    @Override
    protected String getScreenTitle() {
        return "2/5 Activities";
    }

    @Override
    public String getTitleQuestion() {
        return "What are you doing right now?";
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

    }

    @Override
    protected void removeItemFromModel() {
        App.inputEntry.getEmotions().remove(0);
    }

}
