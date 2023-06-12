package com.example.bloodcareapplication.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bloodcareapplication.MainActivity;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class HealthInformation extends AppCompatActivity {

    TextInputEditText textInputEditWeight, textInputEditHeight, textInputEditBloodPressure, textInputEditBloodType;
    Button buttonHealth;
    String username, bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_information);

        textInputEditWeight = findViewById(R.id.weight);
        textInputEditHeight = findViewById(R.id.height);
        textInputEditBloodPressure = findViewById(R.id.bloodPressure);
        textInputEditBloodType = findViewById(R.id.bloodType);
        buttonHealth = findViewById(R.id.buttonHealth);

        textInputEditHeight.addTextChangedListener(personalTextWatcher);
        textInputEditWeight.addTextChangedListener(personalTextWatcher);
        textInputEditBloodPressure.addTextChangedListener(personalTextWatcher);
        textInputEditBloodType.addTextChangedListener(personalTextWatcher);

        buttonHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String weight = String.valueOf(textInputEditWeight.getText().toString());
                String height = String.valueOf(textInputEditHeight.getText().toString());
                String bloodPressure = String.valueOf(textInputEditBloodPressure.getText().toString());
                String bloodType = String.valueOf(textInputEditBloodType.getText().toString());

                username = User.getInstance().getUsername();


                double w = Double.parseDouble(weight);
                double h = Double.parseDouble(height);

                double b = w / (h*h);

                String bmi = Double.toString(b);


                if (!weight.equals("") && !height.equals("") && !bloodPressure.equals("") && !bloodType.equals("")) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "weight";
                            field[1] = "height";
                            field[2] = "bloodPressure";
                            field[3] = "bloodType";
                            field[4] = "bmi";
                            field[5] = "username";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = weight;
                            data[1] = height;
                            data[2] = bloodPressure;
                            data[3] = bloodType;
                            data[4] = bmi;
                            data[5] = username;


                            PutData putData = new PutData("http://192.168.8.122/bloodcareapplication/addHealth.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.e("anyText", result);
                                    if (result.equals("Health Record Updated !")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }
                        //End Write and Read data with URL

                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All Fields Required !", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }

    private TextWatcher personalTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @SuppressLint("ResourceType")
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String weightInput = textInputEditWeight.getText().toString().trim();
            String heightInput = textInputEditHeight.getText().toString().trim();
            String bloodPressureInput = textInputEditBloodPressure.getText().toString().trim();
            String bloodTypeInput = textInputEditBloodType.getText().toString().trim();

            if(!weightInput.isEmpty() && !heightInput.isEmpty() && !bloodPressureInput.isEmpty() && !bloodTypeInput.isEmpty()){
                buttonHealth.setEnabled(true);
                buttonHealth.setBackgroundResource(R.drawable.squaremaroon);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}