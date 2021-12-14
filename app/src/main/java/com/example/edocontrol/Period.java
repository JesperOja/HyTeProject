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

    public boolean isPeriodActive() {
        return periodActive;
    }

    public int getPeriodIntensity() {
        return periodIntensity;
    }

    public void setPeriodActive(boolean periodActive) {
        this.periodActive = periodActive;
    }

    public void setPeriodIntensity(int periodIntensity) {
        this.periodIntensity = periodIntensity;
    }

}

