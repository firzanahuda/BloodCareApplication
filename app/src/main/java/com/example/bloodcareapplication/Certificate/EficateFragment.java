package com.example.bloodcareapplication.Certificate;

import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.STORAGE_SERVICE;

import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
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
import com.example.bloodcareapplication.R;
import com.example.bloodcareapplication.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EficateFragment extends Fragment {

    String username;
    View v;
    List<EficateClass> eficateList;
    RecyclerView recyclerView;
    ImageView imageView;
    TextView textView;
    Button pdfButton;
    File filePDFOutput;
    //filtered list
    List<EficateClass> filteredList= new ArrayList<EficateClass>();
    List<String> filters = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        eficateList = new ArrayList<>();

        v = inflater.inflate(R.layout.fragment_eficate, container, false);

        imageView = v.findViewById(R.id.retrieve);
        pdfButton = v.findViewById(R.id.pdfButton);
        textView = v.findViewById(R.id.texthistory);
        recyclerView = v.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        StorageManager storageManager = (StorageManager) getActivity().getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal memory/ storage

        retrieveData();

        pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{READ_MEDIA_IMAGES, WRITE_EXTERNAL_STORAGE},
                        PackageManager.PERMISSION_GRANTED);



                try {

                    filePDFOutput = new File(storageVolume.getDirectory().getPath() + "/Download/Output.pdf");
                    buttonPrint();
                    Toast.makeText(getContext(), "The PDF saved in Download file!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {


                    e.printStackTrace();
                }


            }
        });

        return v;
    }

    public void buttonPrint() throws IOException {

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(recyclerView.getWidth(),
                recyclerView.getHeight(),
                1).create();

        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        recyclerView.draw(page.getCanvas());
        pdfDocument.finishPage(page);
        pdfDocument.writeTo(new FileOutputStream(filePDFOutput));
        pdfDocument.close();

    }

    public void retrieveData() {

        String url = "http://192.168.8.122/bloodcareapplication/getEficateData.php";
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
                        JSONObject history = array.getJSONObject(i);

                        //adding the product to product list
                        eficateList.add(new EficateClass(

                                history.getString("Date"),
                                history.getString("BloodType"),
                                history.getString("Address"),
                                history.getString("Name"),
                                history.getString("IC"),
                                history.getString("Status")
                        ));

                    }

                    filters.add("Done");

                    //now filter the original list

                    for(int i = 0 ; i<eficateList.size() ; i++){

                        EficateClass item = eficateList.get(i);

                        if(filters.contains(item.getStatus())){

                            filteredList.add(item);

                        }
                    }

                    if(filteredList.size() == 0){

                        pdfButton.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);

                    }else
                    {
                        pdfButton.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                    }


                    //creating adapter object and setting it to recyclerview
                    EficateAdapter adapter = new EficateAdapter(getContext(), filteredList);
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
}