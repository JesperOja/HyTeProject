package com.example.edocontrol;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Every calendar utilities that are needed more than ones
 *
 * @author Jepser Oja
 * @version 1.0
 */
public class CalendarUtils {
    public static LocalDate selectedDate;

    /**
     * Method to create ArrayList of dates
     *
     * @param date LocalDate
     * @return ArrayList - for dates of the month
     */
    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null);
            } else {
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    /**
     * Formatting Date info
     *
     * @param date LocalDate
     * @return LocalDate - Formatted date
     */
    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
}
