package com.example.bloodcareapplication.Certificate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodcareapplication.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    String username;
    View v;
    List<HistoryClass> historyList;
    RecyclerView recyclerView;
    ImageView imageView;
    TextView textView;
    //filtered list
    List<HistoryClass> filteredList= new ArrayList<HistoryClass>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}