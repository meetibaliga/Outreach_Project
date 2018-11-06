package com.example.omar.outreach.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackDB;
import com.example.omar.outreach.Managers.DynamoDBManager;
import com.example.omar.outreach.Models.User;
import com.example.omar.outreach.R;

import info.hoang8f.android.segmented.SegmentedGroup;

public class TestContentProvider extends AppCompatActivity implements CallBackDB {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_content_provider);


    }

    @Override
    public void callbackDB(Object object, int callbackId) {

        if (callbackId == DynamoDBManager.CALL_BACK_ID_USER_SAVED){
            App.log(this,"User Saved .. I think");
        }

    }
}
