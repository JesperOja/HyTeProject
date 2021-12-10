package com.example.edocontrol;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Every calendar focused method that is used in various places
 *
 * @author Jesper Oja
 */
public class CalendarUtils {

    /**
     *
     */
    public static LocalDate selectedDate;

    /**
     * ArrayList to hold every day in a month.
     *
     * @param date Gives date
     * @return Full ArrayList that has dates of specific month
     */
    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i<= 42; i++){
            if(i <= dayOfWeek || i > daysInMonth +dayOfWeek){
                daysInMonthArray.add(null);
            }else{
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    /**
     * Tells which month and year it iis
     *
     * @param date Gets specific date
     * @return String - formatted from date
     */
    public static String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
}
