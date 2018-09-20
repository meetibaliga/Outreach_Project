package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.omar.outreach.Adapters.AdapterForEmotions;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Model.EntryDO;
import com.example.omar.outreach.R;

import java.util.ArrayList;

public class FormActivity_1 extends AppCompatActivity {

    //models
    public EntryDO entryDO = new EntryDO();

    // UI
    protected static AdapterForEmotions ea;
    protected static GridView gv;
    private static Button nextBtn;

    //flags
    private int numOfSelected = 0;
    private ArrayList<Integer> selectedItems = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_1);

        // setup ui

        setTitle("Enter Emotional Status");
        nextBtn = findViewById(R.id.button_emotions);

        // setup user id and entry id

        App.inputEntry.setUserId(App.USER_ID);
        App.inputEntry.setEntryId(""+(App.NUM_OF_ENTRIES+1));

        //data

        String[] emotions = getResources().getStringArray(R.array.emotions);
        String[] images = copyEmotionNamesToImages(emotions);
        gv = (GridView) findViewById(R.id.gridView);
        ea = new AdapterForEmotions(this,emotions,images);
        gv.setAdapter(ea);

        //selectiing

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // getting the item clicked
                setItemSelected(view,position);
                if(numOfSelected == 2){
                    navigateToNextScreen();
                }

            }
        });


    }

    private String[] copyEmotionNamesToImages(String[] emotions) {

        String[] images = new String[emotions.length];

        for (int i = 0 ; i < images.length ; i++){
            String emotion = emotions[i];
            emotion = emotion.toLowerCase();
            emotion = emotion + "_icn";
            images[i] = emotion;
            Log.d("Form",images[i]);
        }

        return images;

    }

    private void setItemSelected(View view, int position) {

        // get item text
        CharSequence text = ((TextView) view.findViewById(R.id.textview_emotion)).getText();


        if(isSelected(position)){
            Log.d("Form","Hi");
            unselect(position);
            view.setBackgroundResource(R.drawable.cell_background);
            App.inputEntry.getEmotions().remove(0);
            nextBtn.setEnabled(false);
        }else{
            Log.d("Form","Bye");
            select(position);
            view.setSelected(true);
            view.setBackgroundResource(R.drawable.cell_background_selected);
            App.inputEntry.getEmotions().add(text.toString());
            nextBtn.setEnabled(true);
        }

    }

    private boolean isSelected(int position){
        return selectedItems.contains(position);
    }

    private void select(int position){
        selectedItems.add(position);
        numOfSelected++;
    }

    private void unselect(int position){
        selectedItems.remove(0);
        numOfSelected--;
    }

    private void navigateToNextScreen() {

        Intent intent = new Intent(this,FormActivity_2.class);
        intent.putExtra("Entry",entryDO);
        startActivity(intent);

    }

    public void nextClicked(View view) {
        navigateToNextScreen();
    }
}
