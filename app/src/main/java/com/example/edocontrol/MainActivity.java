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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.time.LocalDate;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{


        private RecyclerView calendarRecyclerView;
        private TextView monthYear;
        private ConstraintLayout mBottomSheetLayout;
        private BottomSheetBehavior sheetBehavior;
        private TextView bottomSheetDate;
        private ImageView bottomSheetImg;
        private Button addButton;
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

            initWidget();
            CalendarUtils.selectedDate = LocalDate.now();
            setMonthView();
            bottomSheetDate.setText(CalendarUtils.selectedDate.getDayOfWeek()+ " "+ CalendarUtils.selectedDate.getDayOfMonth() + " of " +monthYearFromDate(CalendarUtils.selectedDate));

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
            addNotesToDate = date.toString();
            CalendarUtils.selectedDate = date;

            String queryString = "SELECT * FROM " + DatabaseHelper.ENDO_TABLE;
            Cursor cursor = db.rawQuery(queryString, null);

            if(date.toString().isEmpty()){
                // Do not do anything if pushed calendar where there isn't day
            }else {
                setMonthView();
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setHalfExpandedRatio((float) 0.15);
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    bottomSheetDate.setText(date.getDayOfMonth() + " of " + monthYearFromDate(CalendarUtils.selectedDate));
                }

                if(cursor.moveToFirst()){
                    do{

                        String day = cursor.getString(0);
                        //Log.d("Päivä", day);
                        /*
                        if(day == null){
                            Log.d("Testi", "Ei ole päivämäärä");
                        }
                        else if(day.equalsIgnoreCase(CalendarUtils.selectedDate.toString())){
                            Log.d("PÄIVÄ", "Ollaanko täällä");
                        }*/
                    }while(cursor.moveToNext());


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

    public void AddEntry(View view) {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra(EXTRA_DATE,addNotesToDate);

            startActivity(intent);
    }
}
