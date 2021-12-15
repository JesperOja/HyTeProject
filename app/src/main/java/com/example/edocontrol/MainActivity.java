package com.example.edocontrol;

import static com.example.edocontrol.CalendarUtils.daysInMonthArray;
import static com.example.edocontrol.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;

import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.time.LocalDate;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {


    private RecyclerView calendarRecyclerView;
    private TextView monthYear;
    private ConstraintLayout mBottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;
    private TextView bottomSheetDate;
    private TextView appointment;
    private TextView period;
    private TextView intensity;
    private TextView pain;
    private TextView meds;
    private TextView notes;
    private ImageView bottomSheetImg;
    private Button addButton;
    private Singleton user;
    private String addNotesToDate;
    public static final String EXTRA_DATE = "Extra date";
    private DatabaseHelper helper;
    public static SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        helper = new DatabaseHelper(MainActivity.this);
        db = helper.getReadableDatabase();
        user = Singleton.getInstance();
        initWidget();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        bottomSheetDate.setText(CalendarUtils.selectedDate.getDayOfWeek() + " " + CalendarUtils.selectedDate.getDayOfMonth() + " of " + monthYearFromDate(CalendarUtils.selectedDate));

    }

    @Override
    public void onResume() {
        super.onResume();
        setMonthView();
    }

    private void initWidget() {
        bottomSheetDate = findViewById(R.id.BottomSheetDate);
        mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        bottomSheetImg = findViewById(R.id.bottomSheetImg);
        addButton = findViewById(R.id.addButton);
        sheetBehavior.setExpandedOffset(80);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYear = findViewById(R.id.monthYearTV);
        notes = findViewById(R.id.notes);
        pain = findViewById(R.id.pain);
        appointment = findViewById(R.id.appointment);
        period = findViewById(R.id.period);
        intensity = findViewById(R.id.intensity);
        meds = findViewById(R.id.meds);

        addNotesToDate = LocalDate.now().toString();

        meds.setVisibility(View.INVISIBLE);
        pain.setVisibility(View.INVISIBLE);
        intensity.setVisibility(View.INVISIBLE);
        appointment.setVisibility(View.INVISIBLE);
        period.setVisibility(View.INVISIBLE);
        notes.setVisibility(View.INVISIBLE);

        addButton.setText("ADD INFO");
        String queryString = "SELECT * FROM " + DatabaseHelper.ENDO_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {

                String day = cursor.getString(0);

                if (day == null) {

                } else if (day.equalsIgnoreCase(addNotesToDate)) {
                    String painLvl = cursor.getString(5);
                    String pills = cursor.getString(1);
                    int intensityLvl = cursor.getInt(2);
                    int periodYes = cursor.getInt(3);
                    int appointmentYes = cursor.getInt(4);
                    String notesWritten = cursor.getString(6);

                    if (pills != null) {
                        meds.setVisibility(View.VISIBLE);
                        String[] medsUsed = pills.split(",");
                        pills = "";
                        for (int i = 0; i < medsUsed.length; i++) {
                            if (medsUsed[i].equals("1")) {
                                pills += "Hormonal contraception\n";
                            }
                            if (medsUsed[i].equals("2")) {
                                pills += "Pain medication\n";
                            }
                            if (medsUsed[i].equals("3")) {
                                pills += "Herbal remedies";
                            }
                        }
                        meds.setText("Using medication: \n" + pills);
                    }
                    if (painLvl != null) {
                        pain.setVisibility(View.VISIBLE);
                        String[] whereItHurts = painLvl.split(",");
                        painLvl = "";
                        for (int i = 0; i < whereItHurts.length; i++) {
                            if (whereItHurts[i].equals("1")) {
                                painLvl += "Lower abdomen pain\n";
                            }
                            if (whereItHurts[i].equals("2")) {
                                painLvl += "Back pain\n";
                            }
                            if (whereItHurts[i].equals("3")) {
                                painLvl += "Shoulder pain\n";
                            }
                            if (whereItHurts[i].equals("4")) {
                                painLvl += "Chest pain\n";
                            }
                            if (whereItHurts[i].equals("5")) {
                                painLvl += "Headache\n";
                            }
                            if (whereItHurts[i].equals("6")) {
                                painLvl += "Pain when urinating\n";
                            }
                            if (whereItHurts[i].equals("7")) {
                                painLvl += "Pain during bowel movement\n";
                            }
                            if (whereItHurts[i].equals("8")) {
                                painLvl += "Pain during intercourse";
                            }
                        }
                        pain.setText("What kind of pains I'm feeling: \n" + painLvl);
                    }
                    if (periodYes == 1) {
                        intensity.setVisibility(View.VISIBLE);
                        period.setVisibility(View.VISIBLE);
                        period.setText("Yes, I have period");

                        if (intensityLvl == 1) {
                            intensity.setText("Period intensity is mild");
                        } else if (intensityLvl == 2) {
                            intensity.setText("Period intensity is medium");
                        } else if (intensityLvl == 3) {
                            intensity.setText("Period intensity is heavy");
                        } else {
                            intensity.setText("Period intensity is spotting");
                        }

                    }

                    if (appointmentYes == 1) {
                        appointment.setVisibility(View.VISIBLE);
                        appointment.setText("I have appointment!");
                    }
                    if (notesWritten != null) {
                        notes.setVisibility(View.VISIBLE);
                        notes.setText(notesWritten);
                    }
                    addButton.setVisibility(View.INVISIBLE);

                }
            } while (cursor.moveToNext());
        }
    }

    private void setMonthView() {
        monthYear.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view) {

        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {

        addButton.setText("ADD INFO");
        addNotesToDate = date.toString();
        CalendarUtils.selectedDate = date;
        meds.setVisibility(View.INVISIBLE);
        pain.setVisibility(View.INVISIBLE);
        intensity.setVisibility(View.INVISIBLE);
        appointment.setVisibility(View.INVISIBLE);
        period.setVisibility(View.INVISIBLE);
        notes.setVisibility(View.INVISIBLE);
        String queryString = "SELECT * FROM " + DatabaseHelper.ENDO_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if (date.toString().isEmpty()) {
            // Do not do anything if pushed calendar where there isn't day
        } else {
            setMonthView();
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setHalfExpandedRatio((float) 0.15);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                bottomSheetDate.setText(date.getDayOfMonth() + " of " + monthYearFromDate(CalendarUtils.selectedDate));
            }

            if (cursor.moveToFirst()) {
                do {

                    String day = cursor.getString(0);

                    if (day == null) {

                    } else if (day.equalsIgnoreCase(CalendarUtils.selectedDate.toString())) {
                        String painLvl = cursor.getString(5);
                        String pills = cursor.getString(1);
                        int intensityLvl = cursor.getInt(2);
                        int periodYes = cursor.getInt(3);
                        int appointmentYes = cursor.getInt(4);
                        String notesWritten = cursor.getString(6);

                        if(pills != null) {
                            meds.setVisibility(View.VISIBLE);
                            String[] medsUsed = pills.split(",");
                            pills = "";
                            for(int i = 0; i<medsUsed.length; i++){
                                if(medsUsed[i].equals("1")){
                                    pills += "Hormonal contraception\n";
                                }
                                if(medsUsed[i].equals("2")){
                                    pills += "Pain medication\n";
                                }
                                if(medsUsed[i].equals("3")){
                                    pills += "Herbal remedies";
                                }
                            }
                            meds.setText("Using medication: \n"+ pills);
                        }
                        if(painLvl != null) {
                            pain.setVisibility(View.VISIBLE);
                            String[] whereItHurts = painLvl.split(",");
                            painLvl = "";
                            for(int i = 0; i < whereItHurts.length; i++){
                                if(whereItHurts[i].equals("1")){
                                    painLvl += "Lower abdomen pain\n";
                                }
                                if(whereItHurts[i].equals("2")){
                                    painLvl += "Back pain\n";
                                }
                                if(whereItHurts[i].equals("3")){
                                    painLvl += "Shoulder pain\n";
                                }
                                if(whereItHurts[i].equals("4")){
                                    painLvl += "Chest pain\n";
                                }
                                if(whereItHurts[i].equals("5")){
                                    painLvl += "Headache\n";
                                }
                                if(whereItHurts[i].equals("6")){
                                    painLvl += "Pain when urinating\n";
                                }
                                if(whereItHurts[i].equals("7")){
                                    painLvl += "Pain during bowel movement\n";
                                }
                                if(whereItHurts[i].equals("8")){
                                    painLvl += "Pain during intercourse";
                                }
                            }
                            pain.setText("What kind of pains I'm feeling: \n" +painLvl);
                        }
                        if(periodYes == 1){
                            intensity.setVisibility(View.VISIBLE);
                            period.setVisibility(View.VISIBLE);
                            period.setText("Yes, I have period");

                            if(intensityLvl == 1){
                                intensity.setText("Period intensity is mild");
                            }else if(intensityLvl == 2){
                                intensity.setText("Period intensity is medium");
                            }else if(intensityLvl == 3){
                                intensity.setText("Period intensity is heavy");
                            }else{
                                intensity.setText("Period intensity is spotting");
                            }

                        }

                        if(appointmentYes == 1) {
                            appointment.setVisibility(View.VISIBLE);
                            appointment.setText("I have appointment!");
                        }
                        if(notesWritten != null) {
                            notes.setVisibility(View.VISIBLE);
                            notes.setText(notesWritten);
                        }
                        addButton.setText("EDIT");

                    }
                } while (cursor.moveToNext());

            }

            sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    bottomSheetImg.setAlpha(255);
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    bottomSheetImg.setAlpha(100);

                }
            });
        }
        cursor.close();
    }


    public void AddNotes(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(EXTRA_DATE, addNotesToDate);

        startActivity(intent);
    }
}
