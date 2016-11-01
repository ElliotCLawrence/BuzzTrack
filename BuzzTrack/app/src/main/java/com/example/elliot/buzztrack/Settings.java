package com.example.elliot.buzztrack;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

/**
 * Created by Elliot on 10/31/2016.
 */

public class Settings extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void SaveSettings(View view) //if user clicks save settings.
    {
        Intent resultIntent = new Intent();
        //resultIntent.putExtra("settings",settingsData);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
