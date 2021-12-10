package com.example.edocontrol;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Creates Calendar view and it's onClick method
 *
 * @author Jesper Oja
 */
public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ArrayList<LocalDate> daysOfMonth;
    public final View parentView;
    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;

    /**
     * Constructor for calendar view
     *
     * @param itemView       View
     * @param onItemListener Which itemListener we will be using
     * @param daysOfMonth    ArrayList of month
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
     * onClick method for every day in calendar
     *
     * @param v View - Which day was clicked
     */
    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), daysOfMonth.get(getAdapterPosition()));
    }
}
