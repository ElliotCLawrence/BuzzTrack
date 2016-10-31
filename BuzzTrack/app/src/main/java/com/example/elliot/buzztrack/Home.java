package com.example.elliot.buzztrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

import static com.example.elliot.buzztrack.R.id.BAC;

public class Home extends AppCompatActivity {

    private ArrayList<Drink> drinkList;
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
        /*****************/
        /*End timer setup*/


    }

    public void changeDrink(View view) //move to change drink activity
    {
        Intent intent = new Intent(this, change_drink.class);
        startActivity(intent);
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
