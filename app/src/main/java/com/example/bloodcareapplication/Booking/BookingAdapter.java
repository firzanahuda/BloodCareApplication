package com.example.bloodcareapplication.Booking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BookingAdapter extends FragmentStateAdapter {

    public BookingAdapter(@NonNull BookingFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HealthFragment();
            case 1:
                return new BookingDetailsFragment();
            default:
                return new HealthFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
