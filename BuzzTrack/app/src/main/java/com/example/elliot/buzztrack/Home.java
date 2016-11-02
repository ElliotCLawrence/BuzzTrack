package com.example.elliot.buzztrack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

import static com.example.elliot.buzztrack.R.id.BAC;
import static com.example.elliot.buzztrack.R.id.curDrink;

public class Home extends AppCompatActivity {

    private ArrayList<Drink> drinkList;
    SettingsData currentSettings;
    private Drink currentDrink;
    Timer updateBACScheduler;

    /*Remove this later*/
    int Testcounter;
    /*^Remove this later^*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Testcounter = 0;

        drinkList = new ArrayList<Drink>(); //initalize the drinkList

        /*Timer set up*/
        /**************/
        updateBACScheduler = new Timer(); //Set up the timer
        updateBACScheduler.scheduleAtFixedRate(new TimerTask() { //define what the timer will run
            @Override

            //This method will execute every 1000 miliseconds to update the BAC

            public void run() {
                updateBAC();
            }
        }, 0, 1000);


        currentDrink = new Drink("beer",5.0,12.0,System.currentTimeMillis());
        TextView dName = (TextView) findViewById(curDrink);
        dName.setText("Currently drinking: Beer");
        /*****************/
        /*End timer setup*/

        //grab data from database for settings (if any)
        //currentSettings = new SettingsData();

        SharedPreferences settings = getSharedPreferences("buzzTrack", 0);
        float weight = settings.getFloat("weight", (float) -0.01);
        float height = settings.getFloat("height", (float) -0.01);
        boolean isMale = settings.getBoolean("isMale", false);

        if (weight == (float) -0.01 || height == (float) -0.01) //default value means not found, settings stored.
        {
            currentSettings = new SettingsData();
            Toast.makeText(getApplicationContext(),"Please fill out settings",Toast.LENGTH_LONG).show();
            changeSettings(this.findViewById(android.R.id.content));
        }
        else
        {
            currentSettings = new SettingsData((double) weight, (double) height, isMale);
        }


    }

    @Override
    protected void onStop(){
        super.onStop();

        if (currentSettings.isValid == true)
        {
            SharedPreferences settings = getSharedPreferences("buzzTrack",0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isMale", currentSettings.isMale);
            editor.putFloat("weight", (float) currentSettings.weight);
            editor.putFloat("height", (float) currentSettings.heightInInches);
            editor.commit();
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode)
        {
            case (1) : //if coming back from change_drink
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    //code for new drink...
                    Drink temp = (Drink) data.getSerializableExtra("drink");
                    if (temp != null && temp.valid)
                    {
                        currentDrink = temp;
                        TextView dName = (TextView) findViewById(curDrink);
                        dName.setText("Currently drinking: " + currentDrink.name);
                    }
                }
            }

            case (2):
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    //if back from settings
                    SettingsData newSettings = (SettingsData) data.getSerializableExtra("settings");

                    if (newSettings != null)
                    {
                        currentSettings = newSettings;
                    }
                }
            }
        }
    }

    public void changeDrink(View view) //move to change drink activity
    {
        Intent intent = new Intent(this, change_drink.class);
        intent.putExtra("drink", currentDrink);
        startActivityForResult(intent, 1); //1 is for the changeDrink
    }

    public void changeSettings(View view)
    {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("settings", currentSettings);
        startActivityForResult(intent, 2); //1 is for the changeDrink
    }

    public void updateBAC()
    {
        final TextView abv = (TextView) findViewById(BAC);
        final String temp = String.valueOf(Testcounter);
        assert abv != null;
        {
            runOnUiThread(new Runnable() {
                public void run()
                {
                    abv.setText(temp);
                }
            });

        }

        Testcounter++;
    }
}
