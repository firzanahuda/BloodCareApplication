package com.example.bloodcareapplication.Booking;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodcareapplication.R;

public class BookingViewHolder extends RecyclerView.ViewHolder {

    private final TextView lblInputDate, lblInputBloodType, lblInputStart, lblInputEnd;

    public BookingViewHolder(@NonNull View itemView) {
        super(itemView);
        this.lblInputDate = itemView.findViewById(R.id.lblInputDate);
        this.lblInputBloodType = itemView.findViewById(R.id.lblInputBloodType);
        this.lblInputStart = itemView.findViewById(R.id.lblInputStart);
        this.lblInputEnd = itemView.findViewById(R.id.lblInputEnd);
    }


    public void setBooking(BookingClass bookingClass){

        lblInputDate.setText(bookingClass.getStartDate());
        lblInputBloodType.setText(bookingClass.getBloodtype());
        lblInputStart.setText(bookingClass.getStartTime());
        lblInputEnd.setText(bookingClass.getEndTime());

    }

}
