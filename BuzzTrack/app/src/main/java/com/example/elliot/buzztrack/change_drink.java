package com.example.elliot.buzztrack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.elliot.buzztrack.R.id.ABV;
import static com.example.elliot.buzztrack.R.id.FluidOZ;
import static com.example.elliot.buzztrack.R.id.DrinkName;

/**
 * Created by Elliot on 10/30/2016.
 */

public class change_drink extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_drink);
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
    public void liqour(View view)
    {
        EditText abv = (EditText) findViewById(ABV);
        EditText volume = (EditText) findViewById(FluidOZ);
        EditText name = (EditText) findViewById(DrinkName);

        abv.setText("40.0");
        volume.setText("1.5");
        name.setText("Liqour");
    }
}
