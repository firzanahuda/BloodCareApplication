package com.example.bloodcareapplication.Certificate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodcareapplication.Login;
import com.example.bloodcareapplication.QRGenerator;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentAdapter extends RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder> {

    private Context ctx;
    private List<CurrentClass> currentList;
    String encryptedUsername;

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
        encryptedUsername = qrGenerator.thirdScanEncryption();

        Bitmap bitmap = qrGenerator.generateQRCode(encryptedUsername);
        holder.qrcode.setImageBitmap(bitmap);
        // insertData();

        Log.e("anyText", encryptedUsername);

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
