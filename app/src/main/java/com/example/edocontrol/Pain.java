package com.example.edocontrol;

public class Pain extends InfoActivity {
    private int painLevel;

    public Pain(int painLevel) {
        this.painLevel = painLevel;
    }

    public Pain (){
        this.painLevel = 0;
    }

    public int getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(int painLevel) {
        this.painLevel = painLevel;
    }
}
