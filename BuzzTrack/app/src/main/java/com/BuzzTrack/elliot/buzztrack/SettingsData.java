package com.BuzzTrack.elliot.buzztrack;

import java.io.Serializable;

/**
 * Created by Elliot on 10/31/2016.
 */

public class SettingsData implements Serializable{
    public double weight;
    public double heightInInches;
    public boolean isMale; //if false, female
    public boolean isValid;


    public SettingsData() //helper class for storing settings.
    {

        weight = 0.0;
        heightInInches = 0.0;
        isMale = true;
        isValid = false;
    }

    public SettingsData(double uWeight, double uHeightInInches, boolean uIsMale)
    {

        weight = uWeight;
        heightInInches = uHeightInInches;
        isMale = uIsMale;
        isValid = true;

    }
}
