package com.example.edocontrol;

/**
 * Class to define remedies used.
 *
 * @author      Jenni Tynkkynen
 */
public class Meds extends InfoActivity{
    private int medType;

    /**
     * Class constructor with parameters
     * @param medType int, defines a remedy used
     */
    public Meds(int medType) {
        this.medType = medType;
    }

    /**
     * Class constructor without parameters
     */
    public Meds (){
        this.medType = 0;
    }

    /**
     * Getter for the type of remedy used
     * @return returns int as a remedy type
     */
    public int getMedType() {
        return medType;
    }

}
// VALUES AND THEIR MEANINGS
// 1 = Hormonal contraception
// 2 = Pain medication
// 3 = Herbal remedies