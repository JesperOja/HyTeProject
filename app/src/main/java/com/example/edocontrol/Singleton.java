package com.example.edocontrol;

public class Singleton {
    private static final Singleton ourInstance = new Singleton();
    private String date;
    private int painLevel;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(int painLevel) {
        this.painLevel = painLevel;
    }

    public Singleton getInstance(){
        return ourInstance;
    }
    private Singleton(){

    }
}
