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

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<LocalDate> daysOfMonth;
    private final OnItemListener onItemListener;
    private Singleton user;
    String userID;

    public CalendarAdapter(ArrayList<LocalDate> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.1666666);

        return new CalendarViewHolder(view, onItemListener, daysOfMonth);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String queryString = "SELECT * FROM " + DatabaseHelper.ENDO_TABLE;
        final LocalDate date = daysOfMonth.get(position);
        user = Singleton.getInstance();
        Cursor cursor = MainActivity.db.rawQuery(queryString, null);

        if(date == null)
            holder.dayOfMonth.setText("");
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(cursor.moveToFirst()){
                do{
                    LocalDate addToCalendar = LocalDate.of(CalendarUtils.selectedDate.getYear(),CalendarUtils.selectedDate.getMonth(), date.getDayOfMonth());
                    String day = cursor.getString(0);

                    userID = cursor.getString(7);


                    if(day != null && day.equals(addToCalendar.toString()) && userID.equals(LoginActivity.EMAIL)){
                        holder.dayOfMonth.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.calpointsmall);
                    }

                }while(cursor.moveToNext());
            }

            if(date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY);
        }
        cursor.close();
        }


    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, LocalDate date);
    }
}
