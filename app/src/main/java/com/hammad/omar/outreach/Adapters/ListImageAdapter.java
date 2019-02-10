package com.hammad.omar.outreach.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.R;
import com.hammad.omar.outreach.UIComponents.ListCell;

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

        // resuse functionality
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_item_with_image, null);
        }

        //init elements

        ImageView tv_img = (ImageView) convertView.findViewById(R.id.textView_img);
        TextView tv = (TextView) convertView.findViewById(R.id.textView_text);

        // set values to elements

        Log.d("Img",text);

        tv_img.setImageResource(getImageResourceMappedWith(text));
        tv.setText(text);

        // setup background cell
        ListCell.copyAttributes(convertView);

        return convertView;
    }

    private int getImageResourceMappedWith(String name){

        if(!App.imagesNames.containsKey(name)){
            return R.drawable.not_found_icn;
        }

        String mDrawableName = App.imagesNames.get(name).trim().toLowerCase();
        mDrawableName += "_icn";

        Log.d("Img",mDrawableName);

        return getImageResourceNamed(mDrawableName);

    }
    private int getImageResourceNamed(String name){

        if (name == null || name == ""){
            return -1;
        }

        int resID = context.getResources().getIdentifier(name,"drawable", context.getPackageName());

        return resID;

    }
}
