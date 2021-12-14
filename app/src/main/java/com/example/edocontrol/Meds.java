package com.example.edocontrol;

public class Meds extends InfoActivity{
    private int medType;

    public Meds(int medType) {
        this.medType = medType;
    }

    public Meds (){
        this.medType = 0;
    }

    public int getMedType() {
        return medType;
    }

    public void setMedType(int medType) {
        this.medType = medType;
    }

    // Medication type 1 = Pain medication
    // Medication type 2 = Herbal remedies
    // Medication type 3 = Hormonal contraceptive
}

