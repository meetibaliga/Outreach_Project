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

import com.example.omar.outreach.Adapters.AdapterForActivities;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Model.EntryDO;
import com.example.omar.outreach.R;

public class FormActivity_2 extends AppCompatActivity {

    //models
    public EntryDO entryDO;

    // UI
    protected static AdapterForActivities activityAdapter;
    protected static GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_2);

        // setup ui
        setTitle("Enter Your Activity Status");

        //data
        String[] activities = getResources().getStringArray(R.array.activities);
        gridView = (GridView) findViewById(R.id.gridView);
        activityAdapter = new AdapterForActivities(this, activities);
        gridView.setAdapter(activityAdapter);

        //selectiing
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting the item clicked
                CharSequence text = ((TextView) view.findViewById(R.id.textview_activity)).getText();
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
                App.inputEntry.setActivity(text.toString());
                navigateToNextScreen();
            }
        });
    }

    private void navigateToNextScreen() {

        Intent intent = new Intent(this,FormActivity_3.class);
        startActivity(intent);

    }
}
