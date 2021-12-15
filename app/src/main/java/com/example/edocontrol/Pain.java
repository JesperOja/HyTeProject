package com.example.edocontrol;
/**
 * Class to define the type of pain.
 *
 * @author      Jenni Tynkkynen
 */
public class Pain extends InfoActivity {
    private int painType;

    /**
     * Class constructor for the pain type
     * @param painType, int, defines a pain type
     */

    public Pain(int painType) {
        this.painType = painType;
    }

    /**
     * Getter for pain type
     * @return returns a pain type as an integer
     */
    public int getPainType() {
        return painType;
    }

}
// VALUES AND THEIR MEANINGS
// 1 = Lower abdomen pain
// 2 = Back pain
// 3 = Shoulder pain
// 4 = Chest pain
// 5 = Headache
// 6 = Pain when urinating
// 7 = Pain during bowel movement
// 8 = Pain during intercourse