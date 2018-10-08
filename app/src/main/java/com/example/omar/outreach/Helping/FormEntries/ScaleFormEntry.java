package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;

public class ScaleFormEntry extends RadioFormEntry {

    public ScaleFormEntry(String title, Context context, int scaleSize,String lowLabel, String highLabel){

        super(title,context);
        ArrayList<String> choices = new ArrayList<String>();

        for (int i = 0 ; i < scaleSize ; i++){

            if( i == 0 ){
                choices.add(i+1+" (bad)");
            }else if (i == scaleSize-1){
                choices.add(i+1+" (good)");
            }else{
                choices.add(i+1+"");
            }

        }

        // add low and high label
        choices.get(0).concat(" "+lowLabel);
        choices.get(choices.size()-1).concat(" "+highLabel);

        setChoises(choices);
    }

    @Override
    public View getView() {
        return super.getView();
    }
}