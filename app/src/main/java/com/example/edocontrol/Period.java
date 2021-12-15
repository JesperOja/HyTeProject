package com.example.edocontrol;

public class Period extends InfoActivity {
    private boolean periodActive;
    private int periodIntensity;

    public Period() {
        periodActive = false;
        periodIntensity = 0;
    }

    public Period(boolean periodActive, int periodIntensity) {
        this.periodActive = periodActive;
        this.periodIntensity = periodIntensity;
    }

    public int isPeriodActive() {
        if(periodActive)
            return 1;
        else
            return 0;
    }

    public int getPeriodIntensity() {
        return periodIntensity;
    }


}

