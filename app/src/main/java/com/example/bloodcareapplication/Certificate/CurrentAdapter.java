package com.example.bloodcareapplication.Certificate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.bloodcareapplication.MainActivity;
import com.example.bloodcareapplication.QRGenerator;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentAdapter extends RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder> {

    private Context ctx;
    private List<CurrentClass> currentList;
    String encryptedUsername;
    Dialog dialog;

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

        String done = CurrentClass.getInstance().getStatus();
        if(done == "Done"){
            view.setVisibility(View.GONE);
        }

        return new CurrentAdapter.CurrentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CurrentViewHolder holder, int position) {

        CurrentClass upcomingClass = currentList.get(position);
        String place = upcomingClass.getAddress();

        holder.date.setText(upcomingClass.getDate());
        holder.startTime.setText(upcomingClass.getStartTime());
        holder.endTime.setText(upcomingClass.getEndTime());

        System.out.println(place);

        if (place.equals("Hospital Melaka")){

            holder.address.append(ctx.getString(R.string.hospital));
            holder.room.append("5");
        }else if (place.equals("MITC Melaka Convention Centre")){

            holder.address.append(ctx.getString(R.string.mitc));
            holder.room.append("Level 2");
        }else
        {
            holder.address.append(ctx.getString(R.string.dataranpahlawan));
            holder.room.append("Level 3 (Blood Donation Room)");
        }

        //String username = User.getInstance().getUsername();

        //String code = User.getInstance().getUsername();

        String qrcode = CurrentClass.getInstance().getID();

        System.out.println(qrcode);

        qrGenerator = new QRGenerator(qrcode);

        //encrypt the carplate
        encryptedUsername = qrGenerator.thirdScanEncryption();

        Bitmap bitmap = qrGenerator.generateQRCode(encryptedUsername);
        holder.qrcode.setImageBitmap(bitmap);
        // insertData();

        Log.e("anyText", encryptedUsername);

        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(v.getContext());
                dialog.show();
                dialog.setContentView(R.layout.cancel_dialogue);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(ctx.getDrawable(R.drawable.background));
                }
                dialog.getWindow().setBackgroundDrawable(ctx.getDrawable(R.drawable.background));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                Button okay = dialog.findViewById(R.id.btn_yes);
                Button cancel = dialog.findViewById(R.id.btn_later);

                okay.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View v) {

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             //Starting Write and Read data with URL
                                             //Creating array for parameters
                                             String[] field = new String[1];
                                             field[0] = "ID";
                                             //Creating array for data
                                             String[] data = new String[1];
                                             data[0] = qrcode;

                                             PutData putData = new PutData("http://192.168.8.122/bloodcareapplication/deleteData.php", "POST", field, data);

                                             if (putData.startPut()) {
                                                 if (putData.onComplete()) {
                                                     String result = putData.getResult();
                                                     Log.e("anyText", result);
                                                     if (result.equals("Your Booking canceled")) {
                                                         Toast.makeText(ctx.getApplicationContext(), "Your Booking canceled", Toast.LENGTH_SHORT).show();
                                                         dialog.dismiss();
                                                     } else {
                                                         Toast.makeText(ctx.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
                                             }
                                         }
                                     });


                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return currentList.size();
    }

    class CurrentViewHolder extends RecyclerView.ViewHolder {

        TextView date, startTime, endTime, address, room;
        ImageView qrcode;
        Button cancelButton;

        public CurrentViewHolder(View itemView) {
            super(itemView);

            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            date = itemView.findViewById(R.id.date);
            qrcode = itemView.findViewById(R.id.qrcode);
            address = itemView.findViewById(R.id.address);
            room = itemView.findViewById(R.id.room);
            cancelButton = itemView.findViewById(R.id.cancelButton);



        }
    }




}
