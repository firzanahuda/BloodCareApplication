package com.example.bloodcareapplication.Booking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodcareapplication.R;
import com.google.android.material.tabs.TabLayout;

public class BookingFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager2 viewPager2;
    BookingAdapter bookingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_booking, container, false);

        tabLayout = v.findViewById(R.id.tab_layout);
        viewPager2 = v.findViewById(R.id.view_pager);
        bookingAdapter = new BookingAdapter(this);
        viewPager2.setAdapter(bookingAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });


        return v;
    }

}