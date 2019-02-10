package com.hammad.omar.outreach.Activities;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.BaseActivites.RegistrationBaseActivity;
import com.hammad.omar.outreach.Helping.FormEntries.DropDownFormEntry;
import com.hammad.omar.outreach.Helping.FormEntries.FormEntry;
import com.hammad.omar.outreach.Helping.FormEntries.YesNoFormEntry;
import com.hammad.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OneTimeForm_3 extends RegistrationBaseActivity {

    // ui

    private FormEntry heatingSystem;
    private FormEntry coolingSystem;
    private FormEntry filterationSystem;
    private FormEntry furnace;
    private FormEntry practice_summer;
    private FormEntry practice_winter;
    private FormEntry window_open;
    private FormEntry exhaust_fan;
    private YesNoFormEntry kerosen;
    private YesNoFormEntry radon;

    public void nextButtonClicked(){

        // check form
        if (!App.checkForm(formEntries,this))
            return;

        // do these next

        App.user.set_heatingSystem(heatingSystem.getValue());
        App.user.set_coolingSystem(coolingSystem.getValue());
        App.user.setFilterationSystem(filterationSystem.getValue());
        App.user.setFurnaceSystem(furnace.getValue());
        App.user.setVentilationPracticeSummer(practice_summer.getValue());
        App.user.setVentilationPracticeWinter(practice_winter.getValue());
        App.user.setWindowsOpen(window_open.getValue());
        App.user.setExhaustFans(exhaust_fan.getValue());
        App.user.setKerosenUse(kerosen.getTrueOrFalse());
        App.user.setRadonMitigation(radon.getTrueOrFalse());

        // navigate to next screen

        Intent intent = new Intent(this,OneTimeForm_4.class);
        startActivity(intent);
    }

    @Override
    protected String getScreenTitle() {
        return getResources().getString(R.string.ventilation_title);
    }

    @Override
    protected String getActionButtonText() {
        return ACTION_BUTTON_TEXT_NEXT;
    }

    @Override
    protected ArrayList<FormEntry> getFormElements() {

        ArrayList<FormEntry> entries = new ArrayList<>();

        // heating system

        heatingSystem = new DropDownFormEntry(getResources().getString(R.string.heatingSystem_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.heating_system))));
        entries.add(heatingSystem);

        // cooling system

        coolingSystem = new DropDownFormEntry(getResources().getString(R.string.coolingSystem_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.cooling_system))));
        entries.add(coolingSystem);



        // filteration system

        filterationSystem = new DropDownFormEntry(getResources().getString(R.string.filterationSystem_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.filteration_sysytem))));
        entries.add(filterationSystem);

        // furnace

        furnace = new DropDownFormEntry(getResources().getString(R.string.furnace_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.furnace_system))));
        entries.add(furnace);

        // practice summer

        practice_summer = new DropDownFormEntry(getResources().getString(R.string.practice_summer_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.ventilation_practice_summer))));
        entries.add(practice_summer);

        // practice winter

        practice_winter = new DropDownFormEntry(getResources().getString(R.string.practice_winter_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.ventilation_practice_winter))));
        entries.add(practice_winter);

        // window open

        window_open = new DropDownFormEntry(getResources().getString(R.string.window_open_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.windows_open))));
        entries.add(window_open);

        // exhaust fan

        exhaust_fan = new DropDownFormEntry(getResources().getString(R.string.exhaust_fan_question),this, new ArrayList(Arrays.asList(getResources().getStringArray(R.array.exhaust_fan_use))));
        entries.add(exhaust_fan);

        // kerosen

        kerosen = new YesNoFormEntry(getResources().getString(R.string.kerosen_question),this);
        entries.add(kerosen);

        // window open

        radon = new YesNoFormEntry(getResources().getString(R.string.radon_question),this);
        entries.add(radon);

        return entries;
    }

}