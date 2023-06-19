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

public class EficateAdapter extends RecyclerView.Adapter<EficateAdapter.EficateViewHolder> {

    private Context ctx;
    private List<EficateClass> eficateList;

    public EficateAdapter(Context ctx, List<EficateClass> eficateList) {
        this.ctx = ctx;
        this.eficateList = eficateList;
    }

    @NonNull
    @Override
    public EficateAdapter.EficateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.item_certificate, null);
        return new EficateAdapter.EficateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EficateAdapter.EficateViewHolder holder, int position) {

        EficateClass eficateClass = eficateList.get(position);

        holder.name.setText(eficateClass.getName());
        holder.ic.setText(eficateClass.getIc());
        holder.bloodType.setText(eficateClass.getBloodType());
        holder.date.setText(eficateClass.getDate());

    }

    @Override
    public int getItemCount() {
        return eficateList.size();
    }

    class EficateViewHolder extends RecyclerView.ViewHolder {

        TextView name, ic, bloodType, date;

        public EficateViewHolder(View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.name);
            this.ic = itemView.findViewById(R.id.ic);
            this.bloodType = itemView.findViewById(R.id.bloodType);
            this.date = itemView.findViewById(R.id.date);



        }
    }
}
