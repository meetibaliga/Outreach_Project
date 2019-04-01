package com.hammad.omar.outreach.Adapters;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Models.Entry;
import com.hammad.omar.outreach.R;
import com.hammad.omar.outreach.UIComponents.ListCell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class EntriesAdapter extends BaseAdapter{

    private static final String TAG = EntriesAdapter.class.getSimpleName();

    private final Context context;
    private final List<Object> entries;
    LayoutInflater layoutInflater;


    public EntriesAdapter(Context context, List<Entry> entries) {

        this.context = context;
        this.entries = new ArrayList<>();
        this.entries.addAll(entries);

        // add the headers to the list
        addHeaderToList(entries);
    }

    private void addHeaderToList(List<Entry> entries) {

        String lastDate = "";
        ListIterator itr = this.entries.listIterator();

        while (itr.hasNext()){

            Object item = itr.next();

            while (item instanceof String){
                item = itr.next();
            }

            Entry entry = (Entry) item;
            String entryDate = App.getDateInFormat(App.simpleDateFormat,entry.getCreationDate());

            if(!entryDate.trim().equals(lastDate)){

                itr.previous();
                itr.add(entryDate);
                lastDate = entryDate;
                itr.next();
            }
        }

        Log.d("Header",this.entries.toString());
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Object item = entries.get(position);
        layoutInflater = LayoutInflater.from(context);

        // header

        if (item instanceof String) {

            int id = R.layout.item_entry_header;
            convertView = layoutInflater.inflate(id, null);

            String stringItem = (String) item;
            TextView tv = convertView.findViewById(R.id.textView);
            tv.setText(stringItem);

            return convertView;

        } else if ( item instanceof Entry ) {

            // Entry
            int id = R.layout.item_entry_3;
            convertView = layoutInflater.inflate(id, null);

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
            Entry entry = (Entry)item;
            timeEmojie.setImageResource(getTimeEmojie(entry.getCreationDate()));
            timeText.setText(getTimeFormatted(entry.getCreationDate()));
            setEmotionEmojies(entry.getEmotions(), emotionEmojie_1, emotionEmojie_2);
            setEmotionTexts(entry.getEmotions(), emotionText_1, emotionText_2);
            activityPlaceText.setText(getActivityPlaceText(entry.getActivities(), getDisplayText("PL",entry.getPlace(),R.array.locations)));
            setEnvEmojies(airEmojie, noiseEmojie, transEmojie, activeEmojie);
            airPercentageText.setImageResource(getPercentage(entry.getOdor()));
            noisePercentageText.setImageResource(getPercentage(entry.getNoise()));
            transPercentageText.setImageResource(getPercentage(entry.getTransportation()));
            activePercentageText.setImageResource(getPercentage(entry.getActive()));
            healthText.setText(getHealthText(entry.getCough(), entry.getLimitedActivities(), entry.getAsthmaAttack(), entry.getAsthmaMedication()));


            // setup background cell
            ListCell.copyAttributes(convertView);

            return convertView;
        }

        return convertView;

    }

    private boolean layoutIsFromResource(View convertView, int id) {

        if(convertView == null){
            return false;
        }

        XmlResourceParser xml = convertView.getResources().getLayout(id);

        if(xml == null){
            Log.d("Header","Null XML");
            Log.d("Header","id:"+id);
        }else{
            Log.d("Header","XML:"+xml.toString());
            Log.d("Header","id:"+id);

        }

        return xml != null;

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

        airEmojie.setImageResource(App.getImageResourceMappedWith("air",context));
        noiseEmojie.setImageResource(App.getImageResourceMappedWith("noise",context));
        transEmojie.setImageResource(App.getImageResourceMappedWith("traffic",context));
        activeEmojie.setImageResource(App.getImageResourceMappedWith("active",context));

    }

    private String getActivityPlaceText(List<String> activities, String place) {

        if(activities == null || activities.size() == 0 || place == null){
            return "Null";
        }

        String activityPlaceText = getDisplayText("AC",activities.get(0),R.array.activities);

        if(activities.size() > 1){

            activityPlaceText += " and " + getDisplayText("AC",activities.get(1),R.array.activities);
        }

        activityPlaceText += "\nat " + place;

        return activityPlaceText;
    }

    private void setEmotionTexts(List<String> emotions, TextView emotionText_1, TextView emotionText_2) {

        if(emotions == null || emotions.size() == 0 ){
            return;
        }


        emotionText_1.setText(getDisplayText("EM",emotions.get(0),R.array.emotions));

        if (emotions.size() > 1){
            emotionText_2.setText(getDisplayText("EM",emotions.get(1),R.array.emotions));
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
        Log.d(TAG,emotion_1);
        int resID_1 = App.getImageResourceMappedWith(emotion_1,context);
        emotionEmojie_1.setImageResource(resID_1);

        // set other emojie face if found

        if (emotions.size() > 1){
            String emotion_2 = emotions.get(1);
            int resID_2 = App.getImageResourceMappedWith(emotion_2,context);
            emotionEmojie_2.setImageResource(resID_2);
        }else{
            emotionEmojie_2.setVisibility(View.INVISIBLE);
        }
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

        String hourString = ""+hour;

        if(hour < 10){
            hourString = "0"+hour;
        }

        String minString = ""+min;

        if(min < 10){
            minString = "0"+min;
        }



        String time = hourString +":"+minString+"\n"+amOrPm;

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

    /**
     * Gets the display text from the DB text ( For localization purpose )*/

    private String getDisplayText(String category,String dbText,int arrayId) {

        String text = dbText.replaceAll("/|-| /","_");
        int id = context.getResources().getIdentifier(category+"_"+text,"string",context.getPackageName());
        if(id == 0){
            return dbText;
        }
        int index = Integer.parseInt(context.getResources().getString(id));
        String v = context.getResources().getStringArray(arrayId)[index];

        return v;

    }

}
