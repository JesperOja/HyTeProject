package com.example.edocontrol;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * CalendarViewHolder class for creating onClick listener for calendar days
 *
 * @author Jesper Oja
 * @version 1.0
 */
public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ArrayList<LocalDate> daysOfMonth;
    public final View parentView;
    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;

    /**
     * CalendarViewHolder constructor
     *
     * @param itemView View
     * @param onItemListener onItemListener - Using CalendarAdapter's itemListener
     * @param daysOfMonth   ArrayList - Dates of the month in single array
     */
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> daysOfMonth) {
        super(itemView);
        this.daysOfMonth = daysOfMonth;
        parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    /**
     * onClick for calendar
     *
     * @param v View - To tell which day was pressed
     */
    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), daysOfMonth.get(getAdapterPosition()));
    }
}
