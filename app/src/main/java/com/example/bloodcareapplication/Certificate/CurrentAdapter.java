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

import com.example.bloodcareapplication.Login;
import com.example.bloodcareapplication.QRGenerator;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.List;

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
        insertData();

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

    public void insertData(){

        String status = "Booking";
        String username = User.getInstance().getUsername();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[3];
                field[0] = "QRCode";
                field[1] = "Status";
                field[2] = "username";
                //Creating array for data
                String[] data = new String[3];
                data[0] = encryptedUsername;
                data[1] = status;
                data[2] = username;

                PutData putData = new PutData("http://192.168.8.122/bloodcareapplication/scanQRCode.php", "POST", field, data);

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.e("anyText", result);
                        if(result.equals("Sign Up Success")){

                        }
                        else{
                            //Toast.makeText(getApplicationContext(),"This Username exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //End Write and Read data with URL
            }
        });
    }

}
