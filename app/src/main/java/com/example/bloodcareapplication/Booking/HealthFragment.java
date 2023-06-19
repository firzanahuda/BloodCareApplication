package com.example.bloodcareapplication.Booking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HealthFragment extends Fragment {

    Button health;
    TextView textHeight, textWeight, textBmi, textBloodPressure, textBloodType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health,
                container, false);

        health = (Button) view.findViewById(R.id.health);
        textHeight = view.findViewById(R.id.height);
        textWeight = view.findViewById(R.id.weight);
        textBmi = view.findViewById(R.id.bmi);
        textBloodPressure = view.findViewById(R.id.bloodpressure);
        textBloodType = view.findViewById(R.id.bloodtype);


        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateHealth.class);
                startActivity(intent);

            }
        });

        retrieveData();

        return view;
    }

    public void retrieveData(){

        String url = "http://192.168.8.122/bloodcareapplication/getDataHealth.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    Log.e("anyText", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("health");

                    if(success.equals("1")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String height = obj.getString("height");
                            String weight = obj.getString("weight");
                            String bmi = obj.getString("bmi");
                            String bloodpressure = obj.getString("bloodpressure");
                            String bloodtype = obj.getString("bloodtype");

                            textHeight.append(height);
                            textWeight.append(weight);
                            textBmi.append(bmi);
                            textBloodPressure.append(bloodpressure);
                            textBloodType.append(bloodtype);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String username = User.getInstance().getUsername();

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnSaveData");
                params.put("Customer_Username", username);

                return params;

            }

        };



        requestQueue.add(request);
    }

}