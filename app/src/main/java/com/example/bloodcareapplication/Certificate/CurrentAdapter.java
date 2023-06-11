package com.example.bloodcareapplication.Certificate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodcareapplication.QRGenerator;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;

import java.util.List;

public class CurrentAdapter extends RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder> {

    private Context ctx;
    private List<CurrentClass> currentList;

    QRGenerator qrGenerator;

    public CurrentAdapter(Context ctx, List<CurrentClass> currentList) {
        this.ctx = ctx;
        this.currentList = currentList;
    }

    @NonNull
    @Override
    public CurrentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.current_item, null);
        return new CurrentAdapter.CurrentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CurrentViewHolder holder, int position) {

        CurrentClass upcomingClass = currentList.get(position);

        holder.date.setText(upcomingClass.getDate());
        holder.startTime.setText(upcomingClass.getStartTime());
        holder.endTime.setText(upcomingClass.getEndTime());

        //String username = User.getInstance().getUsername();

        String qrcode = User.getInstance().getUsername();

        qrGenerator = new QRGenerator(qrcode);

        // encrypt the carplate
        String encryptedCarPlate = qrGenerator.thirdScanEncryption();

        Bitmap bitmap = qrGenerator.generateQRCode(encryptedCarPlate);
        holder.qrcode.setImageBitmap(bitmap);

        Log.e("anyText", encryptedCarPlate);

    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    class CurrentViewHolder extends RecyclerView.ViewHolder {

        TextView date, startTime, endTime;
        ImageView qrcode;

        public CurrentViewHolder(View itemView) {
            super(itemView);

            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            date = itemView.findViewById(R.id.date);
            qrcode = itemView.findViewById(R.id.qrcode);



        }
    }
}