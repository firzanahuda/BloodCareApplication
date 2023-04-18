package com.example.bloodcareapplication.Certificate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CertificateFragment extends Fragment {

    private User user;
    String username;

    TextView nameuser, emaill, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_certificate, container, false);

        emaill = v.findViewById(R.id.email);
        nameuser = v.findViewById(R.id.username);
        logout = v.findViewById(R.id.logout);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);

            }
        });

        username = User.getInstance().getUsername();
        nameuser.append("@" + username);

        retrieveData();

        return v;
    }

    public void retrieveData(){

        String url = "http://192.168.8.122/bloodcareapplication/getDataProfile.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    Log.e("anyText", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("profile");

                    if(success.equals("1")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String email = obj.getString("Email");

                            emaill.append(email);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                username = User.getInstance().getUsername();

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnSaveData");
                params.put("Customer_Username", username);

                return params;

            }

        };



        requestQueue.add(request);
    }

}
