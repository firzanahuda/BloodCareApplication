package com.example.bloodcareapplication.Certificate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodcareapplication.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private Context ctx;
    private List<HistoryClass> historyList;

    public HistoryAdapter(Context ctx, List<HistoryClass> historyList) {
        this.ctx = ctx;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.item_history, null);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {

        HistoryClass historyClass = historyList.get(position);
        String place = historyClass.getAddress();

        holder.date.setText(historyClass.getDate());
        holder.startTime.setText(historyClass.getStartTime());
        holder.endTime.setText(historyClass.getEndTime());
        holder.bloodType.setText(historyClass.getBloodType());

        if (place.equals("Hospital Melaka")){

            holder.address.append(ctx.getString(R.string.hospital));
        }else if (place.equals("MITC Melaka Convention Centre")){

            holder.address.append(ctx.getString(R.string.mitc));
        }else
        {
            holder.address.append(ctx.getString(R.string.dataranpahlawan));
        }

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView startTime, endTime, date, bloodType, address;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            this.date = itemView.findViewById(R.id.date);
            this.startTime = itemView.findViewById(R.id.startTime);
            this.endTime = itemView.findViewById(R.id.endTime);
            this.bloodType = itemView.findViewById(R.id.bloodType);
            this.address = itemView.findViewById(R.id.address);



        }
    }

}