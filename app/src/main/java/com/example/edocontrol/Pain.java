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
// VALUES AND THEIR MEANINGS
// 1 = Lower abdomen pain
// 2 = Back pain
// 3 = Shoulder pain
// 4 = Chest pain
// 5 = Headache
// 6 = Pain when urinating
// 7 = Pain during bowel movement
// 8 = Pain during intercourse