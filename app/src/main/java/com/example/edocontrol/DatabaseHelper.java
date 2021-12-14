package com.example.edocontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.net.PortUnreachableException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ENDO_TABLE = "ENDO_TABLE";
    public static final String TIME_TABLE = "TIME_TABLE";
    public static final String COLUMN_ACTIVE_PERIOD = "ACTIVE_PERIOD";
    public static final String COLUMN_PERIOD_INTENSITY = "ENDO_INTENSITY";
    public static final String COLUMN_ENDO_MEDS = "ENDO_MEDS";
    public static final String COLUMN_ENDO_APPOINTMENT = "ENDO_APPOINTMENT";
    public static final String ADD_DATE = "ADD_DATE";
    public static final String COLUMN_NOTES = "COLUMN_NOTES";
    public static final String DATE = "DATE";
    public static final String COLUMN_PAIN = "ENDO_PAIN";


    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "endoDB", null, 1);
    }

    // kutsutaan, kun databaseen mennään ensimmäisen kerran
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createDateTable = "CREATE TABLE " + TIME_TABLE + "(" + ADD_DATE + " STRING PRIMARY KEY)";
        db.execSQL(createDateTable);

        String createTableStatement = "CREATE TABLE " + ENDO_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ENDO_MEDS + " TEXT, " + COLUMN_PERIOD_INTENSITY + " INT, " + COLUMN_ACTIVE_PERIOD + " BOOL, " +
                COLUMN_ENDO_APPOINTMENT + " INT, " + COLUMN_PAIN + " INT, " + COLUMN_NOTES + " STRING, CONSTRAINT " + DATE + " FOREIGN KEY (" + COLUMN_ID + ") " +
                " REFERENCES " + TIME_TABLE + "(" + ADD_DATE + ") )";

        db.execSQL(createTableStatement);

    }

    //kutsutaan, mikäli databasen versionumero muuttuu
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean addPeriod(Period period) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACTIVE_PERIOD, period.isPeriodActive()); //kuukautiset
        cv.put(COLUMN_PERIOD_INTENSITY, period.getPeriodIntensity()); //intensiteetti

        long insert = db.insert(ENDO_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean addAppointment(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ENDO_APPOINTMENT,i); //lääkärikäynti

        long insert = db.insert(ENDO_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean addDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvdate = new ContentValues();

        cvdate.put(ADD_DATE, date); // päivämäärä

        long insert = db.insert(TIME_TABLE, null, cvdate);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean addEntryDetails(String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTES, notes); // muistiinpanot

        long insert = db.insert(ENDO_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addPain(Pain pain){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PAIN, pain.getPainLevel()); // kipu

        long insert = db.insert(ENDO_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }


}
