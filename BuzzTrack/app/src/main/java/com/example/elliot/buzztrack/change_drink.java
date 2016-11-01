package com.example.elliot.buzztrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import static com.example.elliot.buzztrack.R.id.ABV;
import static com.example.elliot.buzztrack.R.id.FluidOZ;
import static com.example.elliot.buzztrack.R.id.DrinkName;

/**
 * Created by Elliot on 10/30/2016.
 */

public class change_drink extends Activity {
    private Drink currDrink;

    public change_drink() {
        this.currDrink = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_drink);


        currDrink = (Drink) getIntent().getSerializableExtra("drink");

        if (currDrink != null && currDrink.valid) //make sure drink is valid
        {
            EditText abv = (EditText) findViewById(ABV);
            EditText volume = (EditText) findViewById(FluidOZ);
            EditText name = (EditText) findViewById(DrinkName);

            abv.setText(String.valueOf(currDrink.bAC));
            volume.setText(String.valueOf(currDrink.volume));
            name.setText(currDrink.name);
        }
        currDrink = new Drink();
    }

    public void beer(View view) //gets called when you click on the beer button
    {
        EditText abv = (EditText) findViewById(ABV);
        EditText volume = (EditText) findViewById(FluidOZ);
        EditText name = (EditText) findViewById(DrinkName);

        abv.setText("5.0");
        volume.setText("12.0");
        name.setText("Beer");
    }
    public void wine(View view)
    {
        EditText abv = (EditText) findViewById(ABV);
        EditText volume = (EditText) findViewById(FluidOZ);
        EditText name = (EditText) findViewById(DrinkName);

        abv.setText("13.0");
        volume.setText("5.0");
        name.setText("Wine");
    }
    public void liquor(View view)
    {
        EditText abv = (EditText) findViewById(ABV);
        EditText volume = (EditText) findViewById(FluidOZ);
        EditText name = (EditText) findViewById(DrinkName);

        abv.setText("40.0");
        volume.setText("1.5");
        name.setText("liquor");
    }

    public void saveDrink(View view)
    {
        //check if it's valid, if not valid contents, don't leave, and toast the user that they need to change one of the boxes

        EditText abv = (EditText) findViewById(ABV);
        EditText volume = (EditText) findViewById(FluidOZ);
        EditText name = (EditText) findViewById(DrinkName);

        String abvStr = abv.getText().toString();
        String volumeStr =  volume.getText().toString();
        String nameStr = name.getText().toString();

        if (Objects.equals(abvStr, "")|| Objects.equals(volumeStr, "") || Objects.equals(nameStr, ""))
        {
            Toast.makeText(getApplicationContext(),"Make sure to fill out all options",Toast.LENGTH_SHORT).show();
            return;
        }

        double numABV = Double.parseDouble(abvStr);
        double numVolume = Double.parseDouble(volumeStr);


        if (!(numABV >= 0.0 && numABV <= 100.0)) //if percentage is not between 0 and 100
        {
            Toast.makeText(getApplicationContext(),"ABV Percent must be between 0.0 and 100.0",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(numVolume > 0.0 && numVolume <= 130.0)) //if volume is not between 0 and 80
        {
            Toast.makeText(getApplicationContext(),"Liquid Ammount must be between 0.0 and 130.0",Toast.LENGTH_SHORT).show();
            return;
        }

        currDrink.name = nameStr;
        currDrink.volume = numVolume;
        currDrink.bAC = numABV;
        currDrink.valid = true;

        Intent resultIntent = new Intent();
        resultIntent.putExtra("drink",currDrink);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
