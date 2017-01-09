package com.BuzzTrack.elliot.buzztrack;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import static com.BuzzTrack.elliot.buzztrack.R.id.heightBox;
import static com.BuzzTrack.elliot.buzztrack.R.id.weightBox;
import static com.BuzzTrack.elliot.buzztrack.R.id.sexRadio;
import static com.BuzzTrack.elliot.buzztrack.R.id.maleRadio;
import static com.BuzzTrack.elliot.buzztrack.R.id.femaleRadio;


import android.view.View;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by Elliot on 10/31/2016.
 */

public class Settings extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingsData currSettings = (SettingsData) getIntent().getSerializableExtra("settings");

        if (currSettings != null && currSettings.isValid)
        {
            EditText heightText = (EditText) findViewById(heightBox);
            EditText weightText = (EditText) findViewById(weightBox);
            RadioButton maleButton =  (RadioButton) findViewById(maleRadio);
            RadioButton femaleButton = (RadioButton) findViewById(femaleRadio);

            heightText.setText(String.valueOf(currSettings.heightInInches));
            weightText.setText(String.valueOf(currSettings.weight));

            if (currSettings.isMale)
            {
                maleButton.setChecked(true);
            }
            else
            {
                femaleButton.setChecked(true);
            }
        }
    }



    public void SaveSettings(View view) //if user clicks save settings.
    {
        EditText heightText = (EditText) findViewById(heightBox);
        EditText weightText = (EditText) findViewById(weightBox);
        RadioGroup sexRadioButtons = (RadioGroup) findViewById(sexRadio);

        String heightStr = heightText.getText().toString();
        String weightStr = weightText.getText().toString();

        if ( Objects.equals(heightStr, "")) //check that this box has something in it.
        {
            Toast.makeText(getApplicationContext(),"Enter a height please",Toast.LENGTH_SHORT).show();
            return;
        }

        if (Objects.equals(weightStr,""))//check that this box has something in it.
        {
            Toast.makeText(getApplicationContext(),"Enter a weight please",Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton  selectedButton = (RadioButton) sexRadioButtons.findViewById(sexRadioButtons.getCheckedRadioButtonId());

        if (selectedButton == null) //make sure a button is selected
        {
            Toast.makeText(getApplicationContext(),"Select a sex please",Toast.LENGTH_SHORT).show();
            return;
        }

        String sexStr = selectedButton.getText().toString();


        //declare the values for the new settings data.
        boolean isMale = true;
        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);

        if (weight < 60 || weight > 600)
        {
            Toast.makeText(getApplicationContext(),"Enter a weight between 60 and 600 pounds",Toast.LENGTH_SHORT).show();
            return;
        }

        if (height < 30 || height > 120)
        {
            Toast.makeText(getApplicationContext(),"Enter a height between 30 and 120 inches",Toast.LENGTH_SHORT).show();
            return;
        }

        if (Objects.equals(sexStr,"Female"))
        {
            isMale = false;
        }

        SettingsData settingsData = new SettingsData(weight,height,isMale); //new settings put in by user.

        Intent resultIntent = new Intent();
        resultIntent.putExtra("settings", settingsData);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
