package com.example.omar.outreach.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Model.EntryDO;
import com.example.omar.outreach.R;

public class FormActivity_4 extends AppCompatActivity {

    //model
    EntryDO entryDO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_4);
    }

    public void RadioAClicked(View view) {
        Log.d("MainActivity","Hi");
        Toast.makeText(this,"A",Toast.LENGTH_SHORT).show();
    }

    public void RadioBClicked(View view) {
        Toast.makeText(this,"B",Toast.LENGTH_SHORT).show();
    }

    public void RadioCClicked(View view) {
        Toast.makeText(this,"C",Toast.LENGTH_SHORT).show();
    }

    public void RadioDClicked(View view) {
        Toast.makeText(this,"D",Toast.LENGTH_SHORT).show();
    }


    public void DoneClicked(View view) {

        RadioGroup rga = (RadioGroup) findViewById(R.id.radioGroup_a);
        RadioGroup rgb = (RadioGroup) findViewById(R.id.radioGroup_b);
        RadioGroup rgc = (RadioGroup) findViewById(R.id.radioGroup_c);
        RadioGroup rgd = (RadioGroup) findViewById(R.id.radioGroup_d);

        int checked_a_id = rga.getCheckedRadioButtonId();
        int checked_b_id = rgb.getCheckedRadioButtonId();
        int checked_c_id = rgc.getCheckedRadioButtonId();
        int checked_d_id = rgd.getCheckedRadioButtonId();

        if(checked_a_id == -1 || checked_b_id == -1 || checked_c_id == -1 || checked_d_id == -1){
            Toast.makeText(this,"Please select all",Toast.LENGTH_SHORT).show();
            return;
        }

        String a_str = ((RadioButton) findViewById(checked_a_id)).getText().toString();
        String b_str = ((RadioButton) findViewById(checked_b_id)).getText().toString();
        String c_str = ((RadioButton) findViewById(checked_c_id)).getText().toString();
        String d_str = ((RadioButton) findViewById(checked_d_id)).getText().toString();

        int a = getValueFromString(a_str);
        int b = getValueFromString(b_str);
        int c = getValueFromString(c_str);
        int d = getValueFromString(d_str);

        App.inputEntry.setOdor(""+a);
        App.inputEntry.setNoise(""+b);
        App.inputEntry.setTransportation(""+c);
        App.inputEntry.setActive(""+d);

        Log.d("Form",App.inputEntry.toString());
        Toast.makeText(this,""+a+b+c+d , Toast.LENGTH_LONG).show();

        navigateToNextScreen();

    }

    private void navigateToNextScreen() {
        Intent intent = new Intent(this,FormCompletedActivity.class);
        startActivity(intent);
    }

    private int getValueFromString(String str){

        int number = Integer.parseInt(str.substring(0,1));
        return number;

    }

}
