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

public class UpdatePersonal extends AppCompatActivity {

    TextInputEditText textInputName, textInputIC, textInputAddress, textInputPhone;
    Button buttonPersonal;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_personal);

        textInputName = findViewById(R.id.Name);
        textInputIC = findViewById(R.id.IC);
        textInputAddress = findViewById(R.id.Address);
        textInputPhone = findViewById(R.id.Phone);
        buttonPersonal = findViewById(R.id.buttonPersonal);

        textInputName.addTextChangedListener(personalTextWatcher);
        textInputIC.addTextChangedListener(personalTextWatcher);
        textInputAddress.addTextChangedListener(personalTextWatcher);
        textInputPhone.addTextChangedListener(personalTextWatcher);

        buttonPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = String.valueOf(textInputName.getText().toString().trim());
                String ic = String.valueOf(textInputIC.getText().toString().trim());
                String address = String.valueOf(textInputAddress.getText().toString().trim());
                String phone = String.valueOf(textInputPhone.getText().toString().trim());

                username = User.getInstance().getUsername();


                if (!name.equals("") && !ic.equals("") && !address.equals("") && !phone.equals("")) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "name";
                            field[1] = "ic";
                            field[2] = "address";
                            field[3] = "phone";
                            field[4] = "username";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = name;
                            data[1] = ic;
                            data[2] = address;
                            data[3] = phone;
                            data[4] = username;


                            PutData putData = new PutData("http://192.168.8.122/bloodcareapplication/updatePersonal.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.e("anyText", result);
                                    if (result.equals("Personal Information Updated !")) {

                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        int intValue = 3;
                                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        myIntent.putExtra("intVariableName", intValue);
                                        startActivity(myIntent);
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

            String nameInput = textInputName.getText().toString().trim();
            String icInput = textInputIC.getText().toString().trim();
            String addressInput = textInputAddress.getText().toString().trim();
            String phoneInput = textInputPhone.getText().toString().trim();

            if(!nameInput.isEmpty() && !icInput.isEmpty() && !addressInput.isEmpty() && !phoneInput.isEmpty()){
                buttonPersonal.setEnabled(true);
                buttonPersonal.setBackgroundResource(R.drawable.squaremaroon);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}