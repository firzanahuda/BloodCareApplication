package com.example.bloodcareapplication.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.bloodcareapplication.EndDate;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.StartDate;
import com.example.bloodcareapplication.User;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class BookingDetails extends AppCompatActivity {

    TextInputEditText textInputStart, textInputEnd;
    TextView textStartTime, textEndTime;
    Button buttonViewBooking, buttonConfirm;
    int startHour, startMinute, endHour, endMinute;
    String username;

    private BookingClass bookings;
    private Vector<BookingClass> booking;
    private BookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        textInputStart = findViewById(R.id.start);
        textInputEnd = findViewById(R.id.end);
        textStartTime = findViewById(R.id.startTime);
        textEndTime = findViewById(R.id.endTime);

        buttonViewBooking = findViewById(R.id.buttonViewBooking);
        buttonConfirm = findViewById(R.id.buttonConfirm);



        buttonViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fnRecyclerView();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });



        textInputStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    Intent intent = new Intent(BookingDetails.this, StartDate.class);
                    startActivityForResult(intent, 1);
                }

                if (!hasFocus) {
                    fnFormValidation();
                }
            }
        });

        textInputStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingDetails.this, StartDate.class);
                startActivityForResult(intent, 1);
            }
        });

        textInputEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    Intent intent = new Intent(BookingDetails.this, EndDate.class);
                    startActivityForResult(intent, 2);
                }

                if (!hasFocus) {
                    fnFormValidation();
                }
            }
        });

        textInputEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingDetails.this, EndDate.class);
                startActivityForResult(intent, 2);
            }
        });






        textStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnInvokeTimePicker();
            }
        });

        textEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnInvokeTimePickerEnd();
            }
        });


    }

    private void fnFormValidation() {
    }


    private void fnInvokeTimePickerEnd() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                BookingDetails.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endHour = hourOfDay;
                        endMinute = minute;
                        String time = endHour + ":" + endMinute;
                        SimpleDateFormat f24Hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date dateEnd = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm:aa"
                            );
                            textEndTime.setText(f12Hours.format(dateEnd));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );

        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();


    }

    private void fnInvokeTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                BookingDetails.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startHour = hourOfDay;
                        startMinute = minute;
                        String time = startHour + ":" + startMinute;
                        SimpleDateFormat f24Hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date dateStart = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm:aa"
                            );
                            textStartTime.setText(f12Hours.format(dateStart));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );

        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }









    private void fnRecyclerView() throws ParseException {

        //textInputCarPlate = findViewById(R.id.carPlate);
        //textInputVehicle = findViewById(R.id.vehicle);
        textInputStart = findViewById(R.id.start);
        textInputEnd = findViewById(R.id.end);
        textStartTime = findViewById(R.id.startTime);
        textEndTime = findViewById(R.id.endTime);

        username = User.getInstance().getUsername();

        String start, end, startTime, endTime;

        start = String.valueOf(textInputStart.getText().toString());
        end = String.valueOf(textInputEnd.getText().toString());
        startTime = String.valueOf(textStartTime.getText().toString());
        endTime = String.valueOf(textEndTime.getText().toString());


        // Creating a SimpleDateFormat object
        // to parse time in the format HH:MM:SS


        bookings = new BookingClass(start, end, startTime, endTime);

        booking.add(bookings);
        adapter.notifyItemInserted(booking.size());

    }


}