package com.example.edocontrol;
/**
 * Kivun määritykseen tarkoitettu luokka.
 *
 * @author      Jenni Tynkkynen
 */
public class Pain extends InfoActivity {
    private int painType;

    /**
     * Luokan konstruktori
     * @param painType, int, asettaa kivun tyypin
     */

    public Pain(int painType) {
        this.painType = painType;
    }

    /**
     *
     * @return palauttaa kivun tyypin
     */
    public int getPainLevel() {
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