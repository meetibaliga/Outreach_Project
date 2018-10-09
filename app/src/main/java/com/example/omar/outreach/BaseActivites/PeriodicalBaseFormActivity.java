package com.example.omar.outreach.BaseActivites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.omar.outreach.Adapters.ListImageAdapter;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Models.Activity;
import com.example.omar.outreach.Models.EntryDO;
import com.example.omar.outreach.R;

import java.util.ArrayList;

public abstract class PeriodicalBaseFormActivity extends AppCompatActivity {


    // UI
    protected static ListImageAdapter ea;
    protected static GridView gv;
    private static Button nextBtn;

    //flags
    private int numOfSelected = 0;
    private ArrayList<Integer> selectedItems = new ArrayList<Integer>();
    private int maxAllowedSelection = 2;


    //abstract methods
    protected abstract String getScreenTitle();
    protected abstract String[] getTextsArray();
    protected abstract String[] getFacesArray();
    protected abstract Intent getNextIntent();
    protected abstract void addItemToModel(CharSequence text);
    protected abstract void removeItemFromModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_1);
        maxAllowedSelection = getMaxAllowedSelection();

        // setup ui

        setTitle(getScreenTitle());
        TextView titleTV = findViewById(R.id.textView);
        titleTV.setText(getScreenTitle());
        nextBtn = findViewById(R.id.button);
        nextBtn.setVisibility(View.INVISIBLE);

        // setup user id and entry id

        App.inputEntry.setUserId(App.USER_ID);
        App.inputEntry.setEntryId(""+(App.NUM_OF_ENTRIES+1));

        //data

        String[] texts = getTextsArray();
        String[] images = getFacesArray();

        gv = (GridView) findViewById(R.id.gridView);
        ea = new ListImageAdapter(this,texts,images);
        gv.setAdapter(ea);

        //selectiing
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // getting the item clicked
                setItemSelected(view,position);
                if(numOfSelected == maxAllowedSelection){
                    navigateToNextScreen();
                }
            }
        });


    }

    protected void setItemSelected(View view, int position) {

        // get item text
        CharSequence text = ((TextView) view.findViewById(R.id.textView_text)).getText();

        if(isSelected(position)){
            Log.d("Form","Hi");
            unselect(position);
            view.setBackgroundResource(R.drawable.cell_background);
            removeItemFromModel();
            showButton();
        }else{
            Log.d("Form","Bye");
            select(position);
            view.setSelected(true);
            view.setBackgroundResource(R.drawable.cell_background_selected);
            addItemToModel(text);
            hideButton();

        }

    }

    protected void hideButton(){
        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setEnabled(true);
    }

    protected void showButton(){

        if(maxAllowedSelection < 2){return;}

        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setEnabled(false);
    }

    protected int getMaxAllowedSelection(){
        return 2;
    }

    protected boolean isSelected(int position){
        return selectedItems.contains(position);
    }

    protected void select(int position){
        selectedItems.add(position);
        numOfSelected++;
    }

    protected void unselect(int position){
        selectedItems.remove(0);
        numOfSelected--;
    }

    protected void navigateToNextScreen() {

        Intent intent = getNextIntent();
        startActivity(intent);

    }

    protected void nextClicked(View view) {
        navigateToNextScreen();
    }

}
