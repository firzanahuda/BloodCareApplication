package com.example.bloodcareapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bloodcareapplication.Booking.BookingFragment;
import com.example.bloodcareapplication.Certificate.CertificateFragment;
import com.example.bloodcareapplication.Home.HomeFragment;
import com.example.bloodcareapplication.Profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.booking:
                            selectedFragment = new BookingFragment();
                            break;
                        case R.id.certificate:
                            selectedFragment = new CertificateFragment();
                            break;
                        case R.id.profile:
                            selectedFragment = new ProfileFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    public static class User {

        private String firstName, lastName, phoneNum, icNum, username;

        private static final User instance = new User();

        public static User getInstance(){
            return instance;

        }
        public User(String firstName, String lastName, String phoneNum, String icNum, String carNumber,
                    String carPlate, String carPlate2, String carPlate3, String carPlate4, String carPlate5) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNum = phoneNum;
            this.icNum = icNum;

        }

        public User() {
            super();
        }


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getIcNum() {
            return icNum;
        }

        public void setIcNum(String icNum) {
            this.icNum = icNum;
        }



    }
}