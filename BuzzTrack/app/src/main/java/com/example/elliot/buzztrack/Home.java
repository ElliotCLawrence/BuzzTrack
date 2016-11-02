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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drinkList = new ArrayList<Drink>(); //initalize the drinkList



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

        /*Timer set up*/
        /**************/
        updateBACScheduler = new Timer(); //Set up the timer
        updateBACScheduler.scheduleAtFixedRate(new TimerTask() { //define what the timer will run
            @Override

            //This method will execute every 1000 miliseconds to update the BAC

            public void run() {
                updateBAC();
            }
        }, 0, 5000);


        currentDrink = new Drink("beer",5.0,12.0,System.currentTimeMillis());
        TextView dName = (TextView) findViewById(curDrink);
        dName.setText("Currently drinking: Beer");
        /*****************/
        /*End timer setup*/
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


        if (currentSettings.isValid && drinkList.size() > 0)
        {
            double gramsOfAlcohol = drinksToGrams();
            double weightAmmount = currentSettings.weight * 453.592; //453.592 is a conversion from lbs to grams
            double bAC; //raw BAC, time is not taken into account.
            double adjustedBAC; //This is the BAC after time is added to calculation
            double r;  //coefficient for sex

            if (currentSettings.isMale) //if user is male
                r = 0.68;
            else                        //user is female
                r = 0.55;

            bAC = (gramsOfAlcohol/(weightAmmount * r)) * 100; //figure out the base bAC
            long msPassed = System.currentTimeMillis(); //figure out the time passed
            msPassed = msPassed - drinkList.get(0).timeDrinken;
            float hoursPassed = (float) (msPassed/(1000.0*60.0*60.0));
            adjustedBAC = bAC - (hoursPassed * 0.015);

            if (adjustedBAC < 0.0)
            {
                adjustedBAC = 0.0;
                //remove all drinks
            }


            final String bacString = "BAC: "+ String.format("%.8f",adjustedBAC); //create the final string

            assert abv != null;
            {
                runOnUiThread(new Runnable() {
                    public void run()
                    {
                        abv.setText(bacString);
                    }
                });
            }
        } //end update
        else
        {
            runOnUiThread(new Runnable() {
                public void run()
                {
                    abv.setText("BAC: 0.0");
                }
            });
        }

    }

    public void addDrink(View view)
    {
        if (!currentSettings.isValid) //check to make sure user as added settings first.
        {
            Toast.makeText(getApplicationContext(),"Please update settings first.",Toast.LENGTH_LONG).show();
            return;
        }
        if (currentDrink.valid) //check to make sure there's a valid drink.
        {
            Drink newDrink = new Drink(currentDrink.name, currentDrink.bAC, currentDrink.volume,  System.currentTimeMillis());
            drinkList.add(newDrink);
            updateBAC();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please add a drink",Toast.LENGTH_LONG).show();
        }
    }

    public void removeDrink(View view)
    {
        if (drinkList.size()> 0)
        {
            drinkList.remove(drinkList.size()-1);
            updateBAC();
        }
    }

    private double drinksToGrams()
    {
        double totalGrams = 0.0;
        double grams;

        for (Drink drink : drinkList)
        {
            grams = drink.bAC * 0.01;
            grams = grams * (drink.volume * 29.574);
            //29.574 is a conversion constant from fluid oz to ml

            grams = grams * 0.789; //Did the math on iOS design, not sure where this constant comes from, but it works!
            totalGrams += grams; //add this drinks alcohol gram count to the total count.
        }
        return totalGrams;
    }
}
