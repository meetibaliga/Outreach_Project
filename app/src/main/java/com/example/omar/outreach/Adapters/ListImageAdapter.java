package com.example.omar.outreach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.omar.outreach.R;
import com.example.omar.outreach.UIComponents.ListCell;

public class ListImageAdapter extends BaseAdapter {

    private final Context context;
    private final String[] texts;
    private final String[] images;

    public ListImageAdapter(Context context , String[] emotions, String[] images){
        this.context = context;
        this.texts = emotions;
        this.images = images;
    }

    @Override
    public int getCount() {
        return texts.length;
    }

    @Override
    public Object getItem(int position) {
        return texts[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    // define the cell look

    public View getView(int position, View convertView, ViewGroup parent) {

        final String text = texts[position];
        final String img = images[position];

        // resuse functionality
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_item_with_image, null);
        }

        //init elements

        TextView tv_img = (TextView) convertView.findViewById(R.id.textView_img);
        TextView tv = (TextView) convertView.findViewById(R.id.textView_text);

        // set values to elements

        tv_img.setText(img);
        tv.setText(text);

        // setup background cell
        ListCell.copyAttributes(convertView);

        return convertView;
    }
}
