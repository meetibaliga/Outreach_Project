package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.outreach.Adapters.AdapterForLocations;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Model.EntryDO;
import com.example.omar.outreach.R;

public class FormActivity_3 extends AppCompatActivity {

    //models
    public EntryDO entryDO;

    // UI
    protected static AdapterForLocations locationsAdapter;
    protected static GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_3);

        // setup ui
        setTitle("Choose a place");

        //data
        String[] locations = getResources().getStringArray(R.array.locations);
        gridView = (GridView) findViewById(R.id.gridView);
        locationsAdapter = new AdapterForLocations(this, locations);
        gridView.setAdapter(locationsAdapter);

        //selectiing
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting the item clicked
                CharSequence text = ((TextView) view.findViewById(R.id.textview_location)).getText();
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
                App.inputEntry.setPlace(text.toString());
                Log.d("Form","Click");
                navigateToNextScreen();
            }
        });
    }

    private void navigateToNextScreen() {

        Intent intent = new Intent(this,FormActivity_4.class);
        startActivity(intent);

    }
}
