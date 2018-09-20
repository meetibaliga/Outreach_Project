package com.example.omar.outreach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.outreach.UIComponents.ListCell;
import com.example.omar.outreach.R;

public class AdapterForEmotions extends BaseAdapter {

    private final Context context;
    private final String[] emotions;
    private final String[] images;

    public AdapterForEmotions(Context context , String[] emotions, String[] images){
        this.context = context;
        this.emotions = emotions;
        this.images = images;
    }

    @Override
    public int getCount() {
        return emotions.length;
    }

    @Override
    public Object getItem(int position) {
        return emotions[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override

    // define the cell look

    public View getView(int position, View convertView, ViewGroup parent) {

        final String emotion = emotions[position];

        // resuse functionality

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_emotion, null);
        }

        //init elements

        ImageView imgv = (ImageView) convertView.findViewById(R.id.imageview_icon);
        TextView tv = (TextView) convertView.findViewById(R.id.textview_emotion);

        // set values to elements

        int resId = context.getResources().getIdentifier(images[position],"drawable",context.getPackageName());
        imgv.setImageResource(resId);
        tv.setText(emotion);

        // setup background cell

        ListCell.copyAttributes(convertView);

        return convertView;
    }
}
