package com.example.edocontrol;
/**
 * Class to define bleeding status and intensity.
 *
 * @author      Jenni Tynkkynen
 */
public class Period extends InfoActivity {
    private boolean periodActive;
    private int periodIntensity;

    /**
     * Constructor for the class without parameters
     */
    public Period() {
        periodActive = false;
        periodIntensity = 0;
    }

    /**
     * Constructor for the class with parameters
     * @param periodActive boolean, defines if bleeding is active
     * @param periodIntensity int, defines the bleeding intensity
     */
    public Period(boolean periodActive, int periodIntensity) {
        this.periodActive = periodActive;
        this.periodIntensity = periodIntensity;
    }

    /**
     * Getter that tells if bleeding is active
     * @return returns an integer as a period status
     */
    public int isPeriodActive() {
        if(periodActive)
            return 1;
        else
            return 0;
    }

    /**
     * Getter for bleeding intensity
     * @return returns an integer value for bleeding intensity
     */
    public int getPeriodIntensity() {
        return periodIntensity;
    }


}

