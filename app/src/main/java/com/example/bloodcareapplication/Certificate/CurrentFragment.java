package com.example.bloodcareapplication.Certificate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.bloodcareapplication.QRGenerator;
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentFragment extends Fragment {

    String username;
    View v;
    List<CurrentClass> currentList;
    RecyclerView recyclerView;
    QRGenerator qrGenerator;
    ImageView imageView;
    TextView textView;
    //filtered list
    List<CurrentClass> filteredList = new ArrayList<CurrentClass>();

    //filters
    List<String> filters = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentList = new ArrayList<>();
        retrieveData();

        v = inflater.inflate(R.layout.fragment_current, container, false);

        imageView = v.findViewById(R.id.retrieve);
        textView = v.findViewById(R.id.textretrieve);

        recyclerView = v.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }


    public void retrieveData() {

        String url = "http://192.168.8.122/bloodcareapplication/getDataQRCode.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("anyText", response);
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject current = array.getJSONObject(i);

                        //adding the product to product list
                        currentList.add(new CurrentClass(

                                current.getString("ID"),
                                current.getString("Date"),
                                current.getString("StartTime"),
                                current.getString("EndTime"),
                                current.getString("QRCode")

                        ));

                        String qrcode = current.getString("ID");
                        CurrentClass.getInstance().setID(qrcode);
                    }

                    if(currentList.size() == 0){

                        imageView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);

                    }else
                    {
                        imageView.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                    }

                    CurrentAdapter adapter = new CurrentAdapter(getContext(), currentList);
                    recyclerView.setAdapter(adapter);






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                username = User.getInstance().getUsername();

                Map<String, String> params = new HashMap<>();
                params.put("Customer_Username", username);

                return params;

            }

        };

        requestQueue.add(request);

    }


    public void retrieveQRCode(){

        String url = "http://192.168.8.122/bloodcareapplication/getDataQRCode.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    Log.e("anyText", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("qrcode");

                    if(success.equals("1")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String qrcode = obj.getString("QRCode");
                            String id = obj.getString("ID");


                            Log.e("qr", id);

                            User.getInstance().setSaveQRcode(id);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ctx.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String username = User.getInstance().getUsername();

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnSaveData");
                params.put("Customer_Username", username);

                return params;

            }

        };



        requestQueue.add(request);
    }

}