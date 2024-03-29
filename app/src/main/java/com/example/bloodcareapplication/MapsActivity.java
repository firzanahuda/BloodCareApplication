package com.example.bloodcareapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bloodcareapplication.Booking.Book;
import com.example.bloodcareapplication.Booking.BookingClass;
import com.example.bloodcareapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    SearchView searchView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchView = findViewById(R.id.searchLocation);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {

                    mMap.clear();

                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                        BookingClass.getInstance().setStation(location);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);

                    if (location.equals("Bukit Beruang") || location.equals("bukit beruang")) {

                        Fragment fragment = new Book();

                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        System.out.println(address.getLatitude());
                        System.out.println(address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location)).showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {

                                String place = "Hospital Melaka";
                                BookingClass.getInstance().setStation(place);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                                return false;
                            }
                        });

                    } else if (location.equals("Mitc Melaka") || location.equals("mitc melaka")) {

                        Fragment fragment = new MitcMelaka();
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        System.out.println(address.getLatitude());
                        System.out.println(address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location)).showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {

                                String place = "MITC Melaka Convention Centre";
                                BookingClass.getInstance().setStation(place);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                                return false;
                            }
                        });

                    } else if (location.equals("Dataran Pahlawan Melaka") || location.equals("dataran pahlawan melaka") ||
                            location.equals("Dataran Pahlawan") || location.equals("dataran pahlawan")) {

                        Fragment fragment = new DataranPahlawan();
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        System.out.println(address.getLatitude());
                        System.out.println(address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location)).showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {

                                String place = "Dataran Pahlawan Melaka";
                                BookingClass.getInstance().setStation(place);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                                return false;
                            }
                        });

                    } else {
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                        Toast.makeText(getApplicationContext(), "There is no Blood Donation Centre in this location", Toast.LENGTH_SHORT).show();
                    }


                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(2.2426418, 102.27482200000001);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Bukit Beruang")).showInfoWindow();


        LatLng mitc = new LatLng(2.189751, 102.252619);
        mMap.addMarker(new MarkerOptions().position(mitc).title("Dataran Pahlawan Melaka"));

        LatLng dp = new LatLng(2.27102, 102.2876399);
        mMap.addMarker(new MarkerOptions().position(dp).title("MITC Melaka")).showInfoWindow();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                if (marker.getTitle().equals("Bukit Beruang")) {

                    Fragment fragment = new Book();
                    String place = "Hospital Melaka";
                    BookingClass.getInstance().setStation(place);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                } else if (marker.getTitle().equals("Dataran Pahlawan Melaka")) {

                    Fragment fragment = new DataranPahlawan();
                    String place = "Dataran Pahlawan Melaka";
                    BookingClass.getInstance().setStation(place);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                } else if (marker.getTitle().equals("MITC Melaka")) {

                    Fragment fragment = new MitcMelaka();
                    String place = "MITC Melaka Convention Centre";
                    BookingClass.getInstance().setStation(place);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                }
            }
        });
        }
    }

