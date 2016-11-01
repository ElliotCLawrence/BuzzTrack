package com.example.elliot.buzztrack;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;


/**
 * Created by Elliot on 10/31/2016.
 */

public class Drink implements Serializable{ //this class will represent a drink
    public String name; //name of the drink
    public double bAC;  //alcohol percentage content
    public double volume; //Volume of the drink (in fluid oz)
    public long timeDrinken; //Time drink was consumed
    public boolean valid;

    public Drink()
    {
        valid = false;
        name = null;
        bAC = -1.1;
        volume = -1.1;
        timeDrinken = System.currentTimeMillis();
    }

    public Drink(String dname, double dBAC, double dVolume, Long dTime)
    {
        name = dname;
        bAC = dBAC;
        volume = dVolume;
        timeDrinken = dTime;

        if (name != null && name != "" && bAC >= 0 && volume > 0)
            valid = true;
        else
            valid = false;
    }
}