package com.example.omar.outreach.UIComponents;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.omar.outreach.R;

public class ListCell extends View {

    static final int PADDING_LEFT = 20;
    static final int PADDING_RIGHT = 20;
    static final int PADDING_TOP = 50;
    static final int PADDING_BOTTOM = 50;
    static final int BACKGROUND_COLOR = Color.LTGRAY;
    static final int ELEVATION = 4;

    public ListCell(Context context) {
        super(context);
    }

    public static View copyAttributes(View newView){

        newView.setPadding(PADDING_LEFT,PADDING_TOP,PADDING_RIGHT,PADDING_BOTTOM);
        newView.setBackgroundResource(R.drawable.cell_background);
        newView.setElevation(ELEVATION);
        newView.setTranslationZ(ELEVATION);

        return newView;

    }
}
