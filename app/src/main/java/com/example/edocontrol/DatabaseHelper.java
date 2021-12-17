package com.example.edocontrol;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Class to define and create the basis of the SQLite database used to store user input from InfoActivity.
 * Extends to SQLite Open Helper
 *
 * @author Jenni Tynkkynen
 * @author Jesper Oja
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ENDO_TABLE = "ENDO_TABLE";
    public static final String COLUMN_ACTIVE_PERIOD = "ACTIVE_PERIOD";
    public static final String COLUMN_PERIOD_INTENSITY = "ENDO_INTENSITY";
    public static final String COLUMN_ENDO_MEDS = "ENDO_MEDS";
    public static final String COLUMN_ENDO_APPOINTMENT = "ENDO_APPOINTMENT";
    public static final String COLUMN_PAIN = "ENDO_PAIN";
    public static final String COLUMN_NOTES = "COLUMN_NOTES";
    public static final String USED_ID = "USER_ID";
    public static final String COLUMN_ID = "ID";

    /**
     * Constructor of the class
     *
     * @param context
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, "endoDB", null, 1);
    }

    /**
     * Called when the database is created
     *
     * @param db SQLiteDatabase, the created database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ENDO_TABLE + "(" + COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_ENDO_MEDS + " TEXT, " + COLUMN_PERIOD_INTENSITY + " INTEGER, " + COLUMN_ACTIVE_PERIOD + " INTEGER, " +
                COLUMN_ENDO_APPOINTMENT + " INTEGER, " + COLUMN_PAIN + " TEXT, " + COLUMN_NOTES + " TEXT, " + USED_ID + " TEXT)";

        db.execSQL(createTableStatement);

    }

    /**
     * Called in case the version number of the database changes
     *
     * @param db         SQLiteDatabase, the used database
     * @param oldVersion int, old version number
     * @param newVersion int, new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * Method to add details from the selected date to the database
     *
     * @param period      Period, adds the period status and intensity to its rightful column
     * @param pain        Pain, adds pain types to their rightful column
     * @param appointment int, adds appointment status to its rightful column
     * @param meds        String, adds medication types to their rightful column
     * @param date        String, adds the selected date to its rightful column
     * @param note        String, adds user notes to their rightful column
     * @param userID      String, adds user ID to its rightful column
     */
    public void addEverything(Period period, String pain, int appointment, String meds, String date, String note, String userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USED_ID, userID);
        cv.put(COLUMN_PAIN, pain);
        cv.put(COLUMN_ACTIVE_PERIOD, period.isPeriodActive()); //kuukautiset
        cv.put(COLUMN_PERIOD_INTENSITY, period.getPeriodIntensity()); //intensiteetti
        cv.put(COLUMN_ENDO_APPOINTMENT, appointment); //lääkärikäynti
        cv.put(COLUMN_ENDO_MEDS, meds); // Medication
        cv.put(COLUMN_ID, date);
        cv.put(COLUMN_NOTES, note);

        db.insert(ENDO_TABLE, null, cv);
    }

    /**
     * Updates data for the selected date to the database
     *
     * @param period      adds the period status and intensity to its rightful column
     * @param pain        adds pain types to their rightful column
     * @param appointment adds appointment status to its rightful column
     * @param meds        adds medication types to their rightful column
     * @param date        adds the selected date to its rightful column
     * @param note        adds user notes to their rightful column
     * @param userID      adds user ID to its rightful column
     */
    public void updateData(Period period, String pain, int appointment, String meds, String date, String note, String userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USED_ID, userID);
        cv.put(COLUMN_PAIN, pain);
        cv.put(COLUMN_ACTIVE_PERIOD, period.isPeriodActive()); //kuukautiset
        cv.put(COLUMN_PERIOD_INTENSITY, period.getPeriodIntensity()); //intensiteetti
        cv.put(COLUMN_ENDO_APPOINTMENT, appointment); //lääkärikäynti
        cv.put(COLUMN_ENDO_MEDS, meds); // Medication
        cv.put(COLUMN_ID, date);
        cv.put(COLUMN_NOTES, note);

        db.replace(ENDO_TABLE, null, cv);

    }
}
