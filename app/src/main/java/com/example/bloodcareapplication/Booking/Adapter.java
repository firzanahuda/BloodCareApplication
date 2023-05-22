package com.example.bloodcareapplication.Booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bloodcareapplication.R;

import java.util.Vector;

public class Adapter extends RecyclerView.Adapter<BookingViewHolder>
{

    private final LayoutInflater layoutInflater;
    private final Vector<BookingClass> booking;

    public Adapter(LayoutInflater layoutInflater, Vector<BookingClass> booking){
        this.layoutInflater = layoutInflater;
        this.booking = booking;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingViewHolder(layoutInflater.inflate(R.layout.item_booking, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {

        holder.setBooking(booking.get(position));
    }


    @Override
    public int getItemCount() {
        return booking.size();
    }
}