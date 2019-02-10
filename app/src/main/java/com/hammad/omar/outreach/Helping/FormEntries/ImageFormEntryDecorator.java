package com.hammad.omar.outreach.Helping.FormEntries;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ImageFormEntryDecorator extends FormEntryDecorator {

    private static final String TAG = ImageFormEntryDecorator.class.getSimpleName();

    protected ImageView imageView;
    protected int imageResource;

    public ImageFormEntryDecorator(FormEntry formEntry, int imageResource) {
        super(formEntry);
        this.imageView = new ImageView(getContext());
        this.imageResource = imageResource;
        setupImageView();
        addImageView();
    }

    @Override
    public View getView() {
        return super.getView();
    }

    private void setupImageView(){
        this.imageView.setImageResource(imageResource);
    }

    private void addImageView(){
        containerLayout.addView(imageView,0);
    }
}
