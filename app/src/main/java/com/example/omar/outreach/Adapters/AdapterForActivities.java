package com.example.omar.outreach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.omar.outreach.UIComponents.ListCell;
import com.example.omar.outreach.R;

public class AdapterForActivities extends BaseAdapter {

    private final Context context;
    private final String[] activities;

    public AdapterForActivities(Context context , String[] emotions){
        this.context = context;
        this.activities = emotions;
    }

    @Override
    public int getCount() {
        return activities.length;
    }

    @Override
    public Object getItem(int position) {
        return activities[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override

    // define the cell look

    public View getView(int position, View convertView, ViewGroup parent) {

        final String emotion = activities[position];

        // resuse functionality

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_activity, null);
        }

        //init elements

        TextView tv = (TextView) convertView.findViewById(R.id.textview_activity);

        // set values to elements

        tv.setText(emotion);

        // setup background cell

        ListCell.copyAttributes(convertView);



        return convertView;
    }
}
