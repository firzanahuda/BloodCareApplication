package com.example.bloodcareapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bloodcareapplication.Booking.BookingDetails;

public class MitcMelaka extends Fragment {


    Button buttonBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mitc_melaka,
                container, false);
        buttonBook = (Button) view.findViewById(R.id.btn_book);
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookingDetails.class);
                startActivity(intent);

            }
        });

        return view;
    }
}