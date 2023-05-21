package com.example.bloodcareapplication.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class HealthInformation extends AppCompatActivity {

    TextInputEditText textInputWeight, textInputHeight, textInputBloodPressure, textInputBloodType;
    Button buttonHealth;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_information);

        textInputWeight = findViewById(R.id.textInputWeight);
        textInputHeight = findViewById(R.id.textInputHeight);
        textInputBloodPressure = findViewById(R.id.textInputBloodPressure);
        textInputBloodType = findViewById(R.id.textInputBloodType);
        buttonHealth = findViewById(R.id.buttonHealth);

        textInputHeight.addTextChangedListener(personalTextWatcher);
        textInputWeight.addTextChangedListener(personalTextWatcher);
        textInputBloodPressure.addTextChangedListener(personalTextWatcher);
        textInputBloodType.addTextChangedListener(personalTextWatcher);

        buttonHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String weight = String.valueOf(textInputWeight.getText().toString());
                String height = String.valueOf(textInputHeight.getText().toString());
                String bloodPressure = String.valueOf(textInputBloodPressure.toString());
                String bloodType = String.valueOf(textInputBloodType.toString());

                username = User.getInstance().getUsername();


                if (!weight.equals("") && !height.equals("") && !bloodPressure.equals("") && !bloodType.equals("")) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "weight";
                            field[1] = "height";
                            field[2] = "bloodPressure";
                            field[3] = "bloodType";
                            field[4] = "username";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = weight;
                            data[1] = height;
                            data[2] = bloodPressure;
                            data[3] = bloodType;
                            data[4] = username;


                            PutData putData = new PutData("http://192.168.8.122/bloodcareapplication/addPersonal.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Personal Information Updated !")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();*/
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

            String weightInput = textInputWeight.getText().toString().trim();
            String heightInput = textInputHeight.getText().toString().trim();
            String bloodPressureInput = textInputBloodPressure.getText().toString().trim();
            String bloodTypeInput = textInputBloodType.getText().toString().trim();

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