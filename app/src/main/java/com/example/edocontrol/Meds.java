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
}
// VALUES AND THEIR MEANINGS
// 1 = Hormonal contraception
// 2 = Pain medication
// 3 = Herbal remedies