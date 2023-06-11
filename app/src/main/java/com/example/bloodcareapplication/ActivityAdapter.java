package com.example.bloodcareapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bloodcareapplication.Certificate.CertificateFragment;
import com.example.bloodcareapplication.Certificate.CurrentFragment;
import com.example.bloodcareapplication.Certificate.EficateFragment;
import com.example.bloodcareapplication.Certificate.HistoryFragment;

public class ActivityAdapter extends FragmentStateAdapter {


    public ActivityAdapter(@NonNull CertificateFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CurrentFragment();

            case 1:
                return new HistoryFragment();
            case 2:
                return new EficateFragment();
            default:
                return new CurrentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
