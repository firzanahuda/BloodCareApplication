package com.example.bloodcareapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.bloodcareapplication.databinding.ActivityStartDateBinding;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class StartDate extends AppCompatActivity {

    ActivityStartDateBinding binding;
    CalendarView calendarView;
    TextView myDate;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendarView = (CalendarView) findViewById(R.id.calendar);
        myDate = (TextView) findViewById(R.id.datestart);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        String current = User.getInstance().getDate();

        if (current == null){

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    String date = dayOfMonth + "/" + (month+1) + "/" + year;
                    myDate.setText(date);


                }
            });
        }else
        {
            LocalDateTime ldt = LocalDateTime.now().plusDays(30);

            ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
            long date = zdt.toInstant().toEpochMilli();

            calendarView.setMinDate(date);

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    String date = dayOfMonth + "/" + (month+1) + "/" + year;
                    myDate.setText(date);


                }
            });
        }


        save = (Button) findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result  = myDate.getText().toString();
                User.getInstance().setDate(result);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", result);

                setResult(RESULT_OK,resultIntent);
                finish();

            }
        });


    }

}