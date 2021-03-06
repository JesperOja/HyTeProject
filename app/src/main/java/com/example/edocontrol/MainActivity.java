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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * MainActivity which holds calendar and BottomSheet which show information
 *
 * @author Jesper Oja
 * @version 1.0
 */

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
    private TextView notesText;
    private TextView notes;
    private ImageView bottomSheetImg;
    private Button addButton;
    private String addNotesToDate;
    public static final String EXTRA_DATE = "Extra date";
    private DatabaseHelper helper;
    public static SQLiteDatabase db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        setMonthView();
    }

    /**
     * onResume will refresh calendar
     */
    @Override
    public void onResume() {
        super.onResume();
        setMonthView();
    }

    // Initializing all widget we are using in MainActivity
    private void initWidget() {
        bottomSheetDate = findViewById(R.id.BottomSheetDate);
        mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        bottomSheetImg = findViewById(R.id.bottomSheetImg);
        addButton = findViewById(R.id.addButton);
        sheetBehavior.setExpandedOffset(80);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYear = findViewById(R.id.monthYearTV);
        notesText = findViewById(R.id.notesText);
        notes = findViewById(R.id.notes);
        pain = findViewById(R.id.pain);
        appointment = findViewById(R.id.appointment);
        period = findViewById(R.id.period);
        intensity = findViewById(R.id.intensity);
        meds = findViewById(R.id.meds);

        helper = new DatabaseHelper(MainActivity.this);
        db = helper.getReadableDatabase();
        auth = FirebaseAuth.getInstance();

        CalendarUtils.selectedDate = LocalDate.now();
        addNotesToDate = LocalDate.now().toString();

        updateBottomSheet(CalendarUtils.selectedDate);

        meds.setVisibility(View.INVISIBLE);
        pain.setVisibility(View.INVISIBLE);
        intensity.setVisibility(View.INVISIBLE);
        appointment.setVisibility(View.INVISIBLE);
        period.setVisibility(View.INVISIBLE);
        notesText.setVisibility(View.INVISIBLE);
        notes.setVisibility(View.INVISIBLE);
    }

    //Update month view on calendar
    private void setMonthView() {
        monthYear.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    /**
     * onClickAction for calendar previous month
     *
     * @param view View
     */
    public void previousMonthAction(View view) {

        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    /**
     * onClickAction for calendar next month
     *
     * @param view View
     */
    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    /**
     * onItemClick method for calendar days
     *
     * @param position int - Which day was pressed
     * @param date     LocalDate - Date of that pressed day
     */
    @Override
    public void onItemClick(int position, LocalDate date) {

        addButton.setText("ADD INFO");
        meds.setVisibility(View.INVISIBLE);
        pain.setVisibility(View.INVISIBLE);
        intensity.setVisibility(View.INVISIBLE);
        appointment.setVisibility(View.INVISIBLE);
        period.setVisibility(View.INVISIBLE);
        notesText.setVisibility(View.INVISIBLE);
        notes.setVisibility(View.INVISIBLE);
        String queryString = "SELECT * FROM " + DatabaseHelper.ENDO_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if (date==null) {
            // Do not do anything if pushed calendar where there isn't day
        } else {
            CalendarUtils.selectedDate = date;
            addNotesToDate = date.toString();
            setMonthView();
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setHalfExpandedRatio((float) 0.15);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                bottomSheetDate.setText(date.getDayOfMonth() + " of " + monthYearFromDate(CalendarUtils.selectedDate));
            }
            updateBottomSheet(CalendarUtils.selectedDate);

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

    /**
     * onClick method for moving to InfoActivity
     *
     * @param view View
     */
    public void AddNotes(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(EXTRA_DATE, addNotesToDate);

        startActivity(intent);
    }

    /**
     * onClick method to log user out back to login screen
     *
     * @param item MenuItem
     */
    public void Logout(MenuItem item) {
        auth.getInstance().signOut();
        Intent logout = new Intent(this, LoginActivity.class);

        finishAffinity();
        startActivity(logout);
    }

    /**
     * Updating BottomSheets view depending on are there info for that day in database or not
     *
     * @param date LocalDate - Which day is selected
     */
    public void updateBottomSheet(LocalDate date) {
        bottomSheetDate.setText(CalendarUtils.selectedDate.getDayOfWeek() + " " + CalendarUtils.selectedDate.getDayOfMonth() + " of " + monthYearFromDate(CalendarUtils.selectedDate));
        addButton.setText("ADD INFO");
        String queryString = "SELECT * FROM " + DatabaseHelper.ENDO_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String day = cursor.getString(0);

                if (day == null) {

                } else if (day.equalsIgnoreCase(date.toString())) {
                    String painLvl = cursor.getString(5);
                    String pills = cursor.getString(1);
                    int intensityLvl = cursor.getInt(2);
                    int periodYes = cursor.getInt(3);
                    int appointmentYes = cursor.getInt(4);
                    String notesWritten = cursor.getString(6);

                    if (intensityLvl == 0 && periodYes == 0 && appointmentYes == 0 && painLvl == null && pills == null && notesWritten.equals("")) {

                    } else {
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
                            meds.setText("Today's remedies: \n" + pills);
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
                            pain.setText("I am experiencing: \n" + painLvl);
                        }
                        if (periodYes == 1) {
                            intensity.setVisibility(View.VISIBLE);
                            period.setVisibility(View.VISIBLE);
                            period.setText("I am bleeding today");

                            if (intensityLvl == 1) {
                                intensity.setText("(mild bleeding)");
                            } else if (intensityLvl == 2) {
                                intensity.setText("(regular bleeding)");
                            } else if (intensityLvl == 3) {
                                intensity.setText("(heavy bleeding)");
                            } else if (intensityLvl == 4) {
                                intensity.setText("(spotting)");
                            } else {
                                intensity.setVisibility(View.INVISIBLE);
                            }

                        }

                        if (appointmentYes == 1) {
                            appointment.setVisibility(View.VISIBLE);
                            appointment.setText("I'm having an appointment today.");
                        }
                        if (notesWritten != null) {
                            notesText.setVisibility(View.VISIBLE);
                            notes.setVisibility(View.VISIBLE);
                            notes.setText(notesWritten);
                        }
                        addButton.setText("EDIT");

                    }
                }

            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
