package com.example.elliot.buzztrack;

import java.io.Serializable;

/**
 * Created by Elliot on 10/31/2016.
 */

public class SettingsData implements Serializable{
    private int age;
    private double weight;
    private double heightInInches;
    private boolean isMale; //if false, female

    public void SettingsData() //helper class for storing settings.
    {
        age = 0;
        weight = 0.0;
        heightInInches = 0.0;
        isMale = true;
    }

    public void SettingsData(int uAge, double uWeight, double uHeightInInches, boolean uIsMale)
    {
        age = uAge;
        weight = uWeight;
        heightInInches = uHeightInInches;
        isMale = uIsMale;

    }
}
