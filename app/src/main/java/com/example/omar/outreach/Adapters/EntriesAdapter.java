package com.example.omar.outreach.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.R;
import com.example.omar.outreach.UIComponents.ListCell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntriesAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<EntryDO> entries;

    public EntriesAdapter(Context context, ArrayList<EntryDO> entries) {
        this.context = context;
        this.entries = entries;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(entries.get(position).getEntryId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // resuse functionality
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_entry_2, null);
        }

        //init elements
        TextView timeEmojie = convertView.findViewById(R.id.timeEmojie);
        TextView timeText = convertView.findViewById(R.id.timeText);
        TextView emotionEmojie_1 = convertView.findViewById(R.id.emotionEmojie_1);
        TextView emotionEmojie_2 = convertView.findViewById(R.id.emotionEmojie_2);
        TextView emotionText_1 = convertView.findViewById(R.id.emotionText_1);
        TextView emotionText_2 = convertView.findViewById(R.id.emotionText_2);
        TextView activityPlaceText = convertView.findViewById(R.id.activityPlaceText);
        TextView airEmojie = convertView.findViewById(R.id.airEmojie);
        ImageView airPercentageText = convertView.findViewById(R.id.airPercentageText);
        TextView noiseEmojie = convertView.findViewById(R.id.noiseEmojie);
        ImageView noisePercentageText = convertView.findViewById(R.id.noisePercentageText);
        TextView transEmojie = convertView.findViewById(R.id.transEmojie);
        ImageView transPercentageText = convertView.findViewById(R.id.transPercentageText);
        TextView activeEmojie = convertView.findViewById(R.id.activeEmojie);
        ImageView activePercentageText = convertView.findViewById(R.id.activePercentageText);
        TextView healthText = convertView.findViewById(R.id.healthText);


        // set values to elements
        EntryDO entry = entries.get(position);
        timeEmojie.setText(getTimeEmojie(entry.getCreationDate()));
        timeText.setText(getTimeFormatted(entry.getCreationDate()));
        setEmotionEmojies(entry.getEmotions(),emotionEmojie_1,emotionEmojie_2);
        setEmotionTexts(entry.getEmotions(),emotionText_1,emotionText_2);
        activityPlaceText.setText(getActivityPlaceText(entry.getActivities(),entry.getPlace()));
        setEnvEmojies(airEmojie,noiseEmojie,transEmojie,activeEmojie);
        airPercentageText.setImageResource(getPercentage(entry.getOdor()));
        noisePercentageText.setImageResource(getPercentage(entry.getNoise()));
        transPercentageText.setImageResource(getPercentage(entry.getTransportation()));
        activePercentageText.setImageResource(getPercentage(entry.getActive()));
        healthText.setText(getHealthText(entry.getCough(),entry.getLimitedActivities(),entry.getAsthmaAttack(),entry.getAsthmaMedication()));


        // setup background cell
        ListCell.copyAttributes(convertView);

        return convertView;

    }

    private String getHealthText(Boolean cough, Boolean limitedActivities, Boolean asthmaAttack, Boolean asthmaMedication) {

        String healthText = "";

        if(cough) healthText += context.getResources().getString(R.string.cough_phrase);
        if(limitedActivities) healthText += "   " + context.getResources().getString(R.string.cough_phrase);
        if(asthmaAttack) healthText += "   " + context.getResources().getString(R.string.cough_phrase);
        if(asthmaMedication) healthText += "   " + context.getResources().getString(R.string.cough_phrase);

        return healthText;


    }

    private int getPercentage(String value) {

        Double doubleValue = Double.parseDouble(value);
        Double percentage = doubleValue / 5 * 100;
        int intPercent = percentage.intValue();

        switch (intPercent){
            case 0:
                return R.drawable.bar_0;
            case 20:
                return R.drawable.bar_20;
            case 40:
                return R.drawable.bar_40;
            case 60:
                return R.drawable.bar_60;
            case 80:
                return R.drawable.bar_80;
            case 100:
                return R.drawable.bar_100;
        }

        return 0;

    }

    private void setEnvEmojies(TextView airEmojie, TextView noiseEmojie, TextView transEmojie, TextView activeEmojie) {

        airEmojie.setText(App.emojies.get("air"));
        noiseEmojie.setText(App.emojies.get("noise"));
        transEmojie.setText(App.emojies.get("trans"));
        activeEmojie.setText(App.emojies.get("active"));

    }

    private String getActivityPlaceText(List<String> activities, String place) {

        String activityPlaceText = activities.get(0);

        if(activities.size() > 1){
            activityPlaceText += " and " + activities.get(1);
        }

        activityPlaceText += " at " + place;

        return activityPlaceText;
    }

    private void setEmotionTexts(List<String> emotions, TextView emotionText_1, TextView emotionText_2) {

        emotionText_1.setText(emotions.get(0));

        if (emotions.size() > 1){
            emotionText_2.setText(emotions.get(1));
        }else{
            emotionText_2.setVisibility(View.INVISIBLE);
        }


    }

    private void setEmotionEmojies(List<String> emotions, TextView emotionEmojie_1, TextView emotionEmojie_2) {

        emotionEmojie_1.setText(App.emojies.get(emotions.get(0)));

        if (emotions.size() > 1){
            emotionEmojie_2.setText(App.emojies.get(emotions.get(1)));
        }else{
            emotionEmojie_2.setVisibility(View.INVISIBLE);
        }


    }

    private String getTimeFormatted(String creationDate) {

        String sourceFormat = "yyyy-MM-dd HH:mm:ss.SSS";

        SimpleDateFormat dateFormat = new SimpleDateFormat(sourceFormat);
        Date date = null;

        try {
            date = dateFormat.parse(creationDate);
        } catch (ParseException e) {
            Log.d("Main","Paring error");
            e.printStackTrace();
        }

        int hour = date.getHours();
        int min = date.getMinutes();
        String amOrPm = "";

        if(hour >= 12){
            amOrPm = "PM";
            hour -= 12;
        }else{
            amOrPm = "AM";
        }

        String time = hour +":"+min+"\n"+amOrPm;

        return time;
    }

    private String getTimeEmojie(String creationDate) {

        String nightEmojie = "🌙";
        String morningEmojie = "☀️";

        String sourceFormat = "yyyy-MM-dd HH:mm:ss.SSS";

        SimpleDateFormat dateFormat = new SimpleDateFormat(sourceFormat);
        Date date = null;

        try {
            date = dateFormat.parse(creationDate);
        } catch (ParseException e) {
            Log.d("Main","Paring error");
            e.printStackTrace();
        }

        int hour = date.getHours();

        if(hour >= 12){
            return nightEmojie;
        }else{
            return morningEmojie;
        }

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }


}
