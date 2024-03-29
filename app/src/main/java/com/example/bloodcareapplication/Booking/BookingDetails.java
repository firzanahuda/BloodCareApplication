package com.example.bloodcareapplication.Booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodcareapplication.Certificate.HistoryAdapter;
import com.example.bloodcareapplication.Certificate.HistoryClass;
import com.example.bloodcareapplication.EndDate;
import com.example.bloodcareapplication.Login;
import com.example.bloodcareapplication.MainActivity;
import com.example.bloodcareapplication.QRGenerator;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.StartDate;
import com.example.bloodcareapplication.User;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BookingDetails extends AppCompatActivity {

    TextInputEditText textInputStart, textInputBloodType;
    TextView textStartTime, textEndTime;
    Button buttonViewBooking, buttonConfirm;
    int startHour, startMinute, endHour, endMinute;
    String username;
    RecyclerView rcvStud;


    private BookingClass bookings;
    private Vector<BookingClass> booking;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        booking = new Vector<>();
        adapter = new Adapter(getLayoutInflater(), booking);

        textInputStart = findViewById(R.id.start);
        textStartTime = findViewById(R.id.startTime);
        textInputBloodType = findViewById(R.id.bloodType);
        textEndTime = findViewById(R.id.endTime);
        rcvStud = findViewById(R.id.rcvStud);

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



        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    fnAdd();
                    //insertData();

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




        rcvStud.setAdapter(adapter);
        rcvStud.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rcvStud);




    }

    private void fnAdd() throws ParseException {

        String username, date, startTime, endTime, bloodType;
        date = String.valueOf(textInputStart.getText());
        startTime = String.valueOf(textStartTime.getText());
        endTime = String.valueOf(textEndTime.getText());
        bloodType = String.valueOf(textInputBloodType.getText());
        username = User.getInstance().getUsername();
        String address = BookingClass.getInstance().getStation();

        if(!date.equals("") && !startTime.equals("") && !bloodType.equals("") && !endTime.equals("")) {

            ArrayList<Integer> bookIDIntList = new ArrayList<>();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://192.168.8.122/bloodcareapplication/getNewBookID.php";
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (response.equals("No booking yet")) {
                            bookIDIntList.add(0);
                        } else {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject bookObj = jsonArray.getJSONObject(i);
                                //JSONObject scanObj = response.getJSONObject(i);
                                String bookID = bookObj.getString("ID");
                                // get substr and convert to int
                                bookID = bookID.substring(1);
                                bookID = removeZero(bookID);
                                int number = Integer.parseInt(bookID);

                                bookIDIntList.add(number);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // get the largest number and create the new ID
                    int max = Collections.max(bookIDIntList) + 1;
                    String bookingID;
                    if (max >= 10000 && max <= 99999) {
                        bookingID = "B" + Integer.toString(max);
                    } else if (max >= 1000 && max < 10000) {
                        bookingID = "B0" + Integer.toString(max);
                    } else if (max >= 100 && max < 1000) {
                        bookingID = "B00" + Integer.toString(max);
                    } else if (max >= 10 && max < 100) {
                        bookingID = "B000" + Integer.toString(max);
                    } else {
                        bookingID = "B0000" + Integer.toString(max);
                    }

                    Log.e("bookid", bookingID);

                    User.getInstance().setBookingID(bookingID);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[7];
                            field[0] = "date";
                            field[1] = "bloodType";
                            field[2] = "startTime";
                            field[3] = "endTime";
                            field[4] = "username";
                            field[5] = "ID";
                            field[6] = "Address";
                            //Creating array for data
                            String[] data = new String[7];
                            data[0] = date;
                            data[1] = bloodType;
                            data[2] = startTime;
                            data[3] = endTime;
                            data[4] = username;
                            data[5] = bookingID;
                            data[6] = address;

                            PutData putData = new PutData("http://192.168.8.122/bloodcareapplication/booking.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.e("anytext", result);
                                    if (result.equals("Booking Success !")) {
                                        insertData();
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), BookingDisplay.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "All Fields Required !", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonObjectRequest);

        }
    }

    // remove the leading zero of string during retrieve of scan ID
    public static String removeZero(String str) {

        // Count leading zeros

        // Initially setting loop counter to 0
        int i = 0;
        while (i < str.length() && str.charAt(i) == '0')
            i++;

        // Converting string into StringBuffer object
        // as strings are immutable
        StringBuffer sb = new StringBuffer(str);

        // The StringBuffer replace function removes
        // i characters from given index (0 here)
        sb.replace(0, i, "");

        // Returning string after removing zeros
        return sb.toString();
    }



    public void insertData(){

        String qrcode = User.getInstance().getBookingID();

        Log.e("BookingID", qrcode);

        QRGenerator qrGenerator = new QRGenerator(qrcode);

        // encrypt the bookingID
        String encryptedUsername = qrGenerator.thirdScanEncryption();

        User.getInstance().setQrcode(encryptedUsername);

        String status = "Booking";
        String username = User.getInstance().getUsername();
        String bookingID = User.getInstance().getBookingID();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[4];
                field[0] = "QRCode";
                field[1] = "Status";
                field[2] = "username";
                field[3] = "BookingID";
                //Creating array for data
                String[] data = new String[4];
                data[0] = encryptedUsername;
                data[1] = status;
                data[2] = username;
                data[3] = bookingID;

                PutData putData = new PutData("http://192.168.8.122/bloodcareapplication/scanQRCode.php", "POST", field, data);

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.e("anyText", result);
                        if(result.equals("Insert Scan row success")){

                        }
                        else{
                            //Toast.makeText(getApplicationContext(),"This Username exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //End Write and Read data with URL
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                textInputStart.setText(result);
            }
        }
    }


    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            booking.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };

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
        textInputBloodType = findViewById(R.id.bloodType);
        textStartTime = findViewById(R.id.startTime);
        textEndTime = findViewById(R.id.endTime);

        username = User.getInstance().getUsername();

        String start, bloodtype, startTime, endTime;

        start = String.valueOf(textInputStart.getText().toString());
        bloodtype = String.valueOf(textInputBloodType.getText().toString());
        startTime = String.valueOf(textStartTime.getText().toString());
        endTime = String.valueOf(textEndTime.getText().toString());


        // Creating a SimpleDateFormat object
        // to parse time in the format HH:MM:SS


        bookings = new BookingClass(start, bloodtype, startTime, endTime);

        booking.add(bookings);
        adapter.notifyItemInserted(booking.size());

    }


}