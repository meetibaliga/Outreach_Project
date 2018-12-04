package com.example.omar.outreach.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Managers.SharedPreferencesManager;
import com.example.omar.outreach.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class PreferencesFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferencesManager prefMgr;

    public PreferencesFragment() {

        prefMgr = SharedPreferencesManager.getInstance(getActivity());

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.preferences,rootKey);
        setupPreferences();

        // register listener
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return true;
    }

    private void setupPreferences() {

        // morning
        setSummaryText(R.string.morning_notification_key);
        setSummaryText(R.string.eveninig_notification_key);
//        setSummaryText(R.string.first_name_key);
//        setSummaryText(R.string.last_name_key);
//        setSummaryText(R.string.city_key);
//        setSummaryText(R.string.address_1_key);
//        setSummaryText(R.string.address_2_key);
//        setSummaryText(R.string.zip_key);
//        setSummaryText(R.string.email_key);
//        setSummaryText(R.string.mobile_key);
    }

    private void setSummaryText(int key, @Nullable App.types type) {

        Object value = prefMgr.getValue(key,type);

        if( value != null ){

            String stringValue = (String)value;

            if ( isTimeString(key) ){
                stringValue = App.intToDayTime(Integer.parseInt(stringValue));
            }

            Preference pref = findPreference(getResources().getString(key));
            pref.setSummary(stringValue);

        }


    }

    private boolean isTimeString(int key) {
        return key == R.string.morning_notification_key || key == R.string.eveninig_notification_key;
    }

    private void setSummaryText(int key){
        setSummaryText(key,App.types.STRING);
    }

    private PreferenceGroup getParent(Preference preference)
    {
        return getParent(getPreferenceScreen(), preference);
    }

    private PreferenceGroup getParent(PreferenceGroup root, Preference preference)
    {
        for (int i = 0; i < root.getPreferenceCount(); i++)
        {
            Preference p = root.getPreference(i);
            if (p == preference)
                return root;
            if (PreferenceGroup.class.isInstance(p))
            {
                PreferenceGroup parent = getParent((PreferenceGroup)p, preference);
                if (parent != null)
                    return parent;
            }
        }
        return null;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);

        if(preference instanceof ListPreference){ // morning/evening
            int intTime = Integer.parseInt(prefMgr.getValue(key));
            String stringTime = App.intToDayTime(intTime);
            preference.setSummary(stringTime);
        }else if (preference instanceof EditTextPreference){ //name..address.. etc
            preference.setSummary(sharedPreferences.getString(key,null));
        }else if (preference instanceof SwitchPreferenceCompat){ // notif enabled
            // nothing till now
        }else{
            // i dont know

        }

    }
}
