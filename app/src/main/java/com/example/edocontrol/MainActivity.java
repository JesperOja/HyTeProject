package com.example.edocontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;



public class MainActivity extends AppCompatActivity {


        // Define the variable of CalendarView type
        // and TextView type;
        CalendarView calendar;
        TextView date_view;
        private ConstraintLayout mBottomSheetLayout;
        private BottomSheetBehavior sheetBehavior;
        private TextView bottomSheetDate;
        private ImageView bottomSheetImg;
        private Button addButton;
        Date date = new Date();
        SimpleDateFormat thisday = new SimpleDateFormat("EEEE");
        String calendarDay;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            bottomSheetDate = findViewById(R.id.BottomSheetDate);
            mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
            sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
            bottomSheetImg = findViewById(R.id.bottomSheetImg);
            addButton = findViewById(R.id.addButton);
            sheetBehavior.setExpandedOffset(80);
            calendar = findViewById(R.id.calendar);
            date_view = findViewById(R.id.date_view);

            date_view.setText(thisday.format(date) + " " +date.getDate() + "." +date.getMonth() + "." + date.getYear());

            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(
                        @NonNull CalendarView view,
                        int year,
                        int month,
                        int dayOfMonth) {


                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year,month,dayOfMonth);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    //Log.d("Date", thisday.format(dayofweek));
                    switch (dayOfWeek){
                        case 1: calendarDay = "Sunday";break;
                        case 2: calendarDay = "Monday";break;
                        case 3: calendarDay = "Tuesday";break;
                        case 4: calendarDay = "Wednesday";break;
                        case 5: calendarDay = "Thursday";break;
                        case 6: calendarDay = "Friday";break;
                        case 7: calendarDay = "Saturday";break;
                    }
                    String Date
                            =  calendarDay + " " +dayOfMonth + "."
                            + (month + 1) + "." + year;
                    if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                        sheetBehavior.setHalfExpandedRatio((float) 0.35);
                        sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                        bottomSheetDate.setText(Date);
                    }
                    // set this date in TextView for Display
                    date_view.setText(Date);
                }
            });
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

}
