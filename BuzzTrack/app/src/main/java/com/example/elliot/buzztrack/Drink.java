package com.example.elliot.buzztrack;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Elliot on 10/31/2016.
 */

public class Drink { //this class will represent a drink
    public String name; //name of the drink
    public double bAC;  //alcohol percentage content
    public double volume; //Volume of the drink (in fluid oz)
    public Long timeDrinken; //Time drink was consumed

    public Drink()
    {
        name = null;
        bAC = 0.0;
        volume = 0.0;
        timeDrinken = System.currentTimeMillis();
    }

    public Drink(String dname, double dBAC, double dVolume, Long dTime)
    {
        name = dname;
        bAC = dBAC;
        volume = dVolume;
        timeDrinken = dTime;
    }
}