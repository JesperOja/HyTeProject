package com.example.edocontrol;

import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * CalendarAdapter class to for creating calendar and maintaining it
 *
 * @author Jesper Oja
 * @version 1.0
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private ArrayList<LocalDate> daysOfMonth;
    private final OnItemListener onItemListener;
    private String userID;

    /**
     * CalendarAdapter constructor
     *
     * @param daysOfMonth    ArrayList - day numbers for month
     * @param onItemListener onClickListener - which listener will be used for calendar
     */
    public CalendarAdapter(ArrayList<LocalDate> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    /**
     * Creating calendar layout
     *
     * @param parent
     * @param viewType int -
     * @return CalendarViewHolder
     */
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.1666666);

        return new CalendarViewHolder(view, onItemListener, daysOfMonth);
    }

    /**
     * Creating calendar with day numbers and adds point if database has something for that day
     *
     * @param holder   CalendarViewHolder - Which month we are looking at
     * @param position int - Where in our calendar days of month goes to
     */
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String queryString = "SELECT * FROM " + DatabaseHelper.ENDO_TABLE;
        final LocalDate date = daysOfMonth.get(position);
        Cursor cursor = MainActivity.db.rawQuery(queryString, null);

        if (date == null)
            holder.dayOfMonth.setText("");
        else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if (cursor.moveToFirst()) {
                do {
                    LocalDate addToCalendar = LocalDate.of(CalendarUtils.selectedDate.getYear(), CalendarUtils.selectedDate.getMonth(), date.getDayOfMonth());
                    String day = cursor.getString(0);
                    String painLvl = cursor.getString(5);
                    String pills = cursor.getString(1);
                    int intensityLvl = cursor.getInt(2);
                    int periodYes = cursor.getInt(3);
                    int appointmentYes = cursor.getInt(4);
                    String notesWritten = cursor.getString(6);
                    userID = cursor.getString(7);

                    if (intensityLvl == 0 && periodYes == 0 && appointmentYes == 0 && painLvl == null && pills == null && notesWritten.equals("") && day.equals(addToCalendar.toString())) {
                        holder.dayOfMonth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    } else {
                        if (day != null && day.equals(addToCalendar.toString())) {
                            if(userID != null) {
                                if (userID.equals(LoginActivity.EMAIL))
                                    holder.dayOfMonth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.calpointsmall);
                            }
                        }
                    }
                } while (cursor.moveToNext());
            }

            if (date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY);
        }
        cursor.close();
    }

    /**
     * How many days are in month
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    /**
     * OnItemListener Interface for calendar so we can tell which day was pressed
     */
    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }
}
