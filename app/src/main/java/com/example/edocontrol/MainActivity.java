package com.example.edocontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;



public class MainActivity extends AppCompatActivity {


    // Define the variable of CalendarView type
    // and TextView type;
    CalendarView calendar;
    TextView date_view;

    Date date = new Date();
    SimpleDateFormat thisday = new SimpleDateFormat("EEEE");
    String calendarDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

                // set this date in TextView for Display
                date_view.setText(Date);
            }
        });
    }

}
