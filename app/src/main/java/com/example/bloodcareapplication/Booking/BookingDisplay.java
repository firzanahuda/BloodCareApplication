package com.example.bloodcareapplication.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodcareapplication.Certificate.CertificateFragment;
import com.example.bloodcareapplication.Home.HomeFragment;
import com.example.bloodcareapplication.MainActivity;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookingDisplay extends AppCompatActivity {

    Button back;
    TextView textDate, textStartTime, textEndTime, station, address, room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_display);

        textDate = findViewById(R.id.date);
        textStartTime = findViewById(R.id.startTime);
        textEndTime = findViewById(R.id.endTime);
        station = findViewById(R.id.station);
        back = findViewById(R.id.back);
        address = findViewById(R.id.address);
        room = findViewById(R.id.room);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intValue = 4;
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                //myIntent.putExtra("intVariableName", intValue);
                startActivity(myIntent);
                //finish();

            }
        });

        retrieveData();



    }

    public void retrieveData(){

        String url = "http://192.168.8.122/bloodcareapplication/getBookingDisplay.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    Log.e("anyText", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("bookingDisplay");

                    if(success.equals("1")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String date = obj.getString("Date");
                            String startTime = obj.getString("StartTime");
                            String endTime = obj.getString("EndTime");

                            String place = BookingClass.getInstance().getStation();

                            if (place == "Hospital Melaka"){

                                address.append(getString(R.string.hospital));
                                room.append("5");
                            }else if (place == "MITC Melaka Convention Centre"){

                                address.append(getString(R.string.mitc));
                                room.append("Level 2");
                            }else
                            {
                                address.append(getString(R.string.dataranpahlawan));
                                room.append("Level 3 (Blood Donation Room)");
                            }

                            textDate.append(date);
                            textStartTime.append(startTime);
                            textEndTime.append(endTime);
                            station.append(place);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String username = User.getInstance().getBookingID();
                String station = BookingClass.getInstance().getStation();

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnSaveData");
                params.put("Customer_Username", username);
                //params.put("Station", station);

                return params;

            }

        };



        requestQueue.add(request);
    }
}