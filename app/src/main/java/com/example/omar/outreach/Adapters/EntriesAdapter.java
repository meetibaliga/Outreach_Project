package com.example.omar.outreach.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
            convertView = layoutInflater.inflate(R.layout.item_entry_3, null);
        }

        //init elements
        ImageView timeEmojie = convertView.findViewById(R.id.timeEmojie);
        TextView timeText = convertView.findViewById(R.id.timeText);
        ImageView emotionEmojie_1 = convertView.findViewById(R.id.emotionEmojie_1);
        ImageView emotionEmojie_2 = convertView.findViewById(R.id.emotionEmojie_2);
        TextView emotionText_1 = convertView.findViewById(R.id.emotionText_1);
        TextView emotionText_2 = convertView.findViewById(R.id.emotionText_2);
        TextView activityPlaceText = convertView.findViewById(R.id.activityPlaceText);
        ImageView airEmojie = convertView.findViewById(R.id.airEmojie);
        ImageView airPercentageText = convertView.findViewById(R.id.airPercentageText);
        ImageView noiseEmojie = convertView.findViewById(R.id.noiseEmojie);
        ImageView noisePercentageText = convertView.findViewById(R.id.noisePercentageText);
        ImageView transEmojie = convertView.findViewById(R.id.transEmojie);
        ImageView transPercentageText = convertView.findViewById(R.id.transPercentageText);
        ImageView activeEmojie = convertView.findViewById(R.id.activeEmojie);
        ImageView activePercentageText = convertView.findViewById(R.id.activePercentageText);
        TextView healthText = convertView.findViewById(R.id.healthText);


        // set values to elements
        EntryDO entry = entries.get(position);
        timeEmojie.setImageResource(getTimeEmojie(entry.getCreationDate()));
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

        if(cough == null || limitedActivities == null || asthmaAttack == null || asthmaMedication == null ){
            return "null";
        }

        String healthText = "";

        if(cough) healthText += context.getResources().getString(R.string.cough_phrase);
        if(limitedActivities) healthText += "   " + context.getResources().getString(R.string.cough_phrase);
        if(asthmaAttack) healthText += "   " + context.getResources().getString(R.string.cough_phrase);
        if(asthmaMedication) healthText += "   " + context.getResources().getString(R.string.cough_phrase);

        return healthText;


    }

    private int getPercentage(String value) {

        if(value == null){
            return R.drawable.not_found_icn;
        }

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

    private void setEnvEmojies(ImageView airEmojie, ImageView noiseEmojie, ImageView transEmojie, ImageView activeEmojie) {

        airEmojie.setImageResource(getImageResourceMappedWith("air"));
        noiseEmojie.setImageResource(getImageResourceMappedWith("noise"));
        transEmojie.setImageResource(getImageResourceMappedWith("traffic"));
        activeEmojie.setImageResource(getImageResourceMappedWith("active"));

    }

    private String getActivityPlaceText(List<String> activities, String place) {

        if(activities == null || activities.size() == 0 || place == null){
            return "Null";
        }

        String activityPlaceText = activities.get(0);

        if(activities.size() > 1){
            activityPlaceText += " and " + activities.get(1);
        }

        activityPlaceText += " at " + place;

        return activityPlaceText;
    }

    private void setEmotionTexts(List<String> emotions, TextView emotionText_1, TextView emotionText_2) {

        if(emotions == null || emotions.size() == 0 ){
            return;
        }


        emotionText_1.setText(emotions.get(0));

        if (emotions.size() > 1){
            emotionText_2.setText(emotions.get(1));
        }else{
            emotionText_2.setVisibility(View.INVISIBLE);
        }


    }

    private void setEmotionEmojies(List<String> emotions, ImageView emotionEmojie_1, ImageView emotionEmojie_2) {

        if(emotions == null || emotions.size() == 0 || emotionEmojie_1 == null || emotionEmojie_2 == null){
            return;
        }

        // setting first emojie face
        String emotion_1 = emotions.get(0);
        int resID_1 = getImageResourceMappedWith(emotion_1);
        emotionEmojie_1.setImageResource(resID_1);

        // set other emojie face if found

        if (emotions.size() > 1){
            String emotion_2 = emotions.get(1);
            int resID_2 = getImageResourceMappedWith(emotion_2);
            emotionEmojie_2.setImageResource(resID_2);
        }else{
            emotionEmojie_2.setVisibility(View.INVISIBLE);
        }
    }

    private int getImageResourceMappedWith(String name){

        if(!App.imagesNames.containsKey(name)){
            return R.drawable.not_found_icn;
        }

        String mDrawableName = App.imagesNames.get(name);
        mDrawableName += "_icn";
        mDrawableName = mDrawableName.toLowerCase();

        return getImageResourceNamed(mDrawableName);

    }

    private int getImageResourceNamed(String name){

        if (name == null || name == ""){
            return -1;
        }

        int resID = context.getResources().getIdentifier(name,"drawable", context.getPackageName());

        return resID;

    }

    private String getTimeFormatted(String creationDate) {

        if(creationDate == null){
            return "null";
        }

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

    private int getTimeEmojie(String creationDate) {

        if(creationDate == null){
            return R.drawable.time_evening_icn;
        }

        int nightEmojie = R.drawable.time_evening_icn;
        int morningEmojie = R.drawable.time_morning_icn;

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
