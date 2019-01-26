package com.example.anil6066.locateyourcar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.*;
import android.net.Uri;
import android.support.v4.app.*;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.util.List;

import static com.example.anil6066.locateyourcar.GlobalClass.*;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    LocationManager locationManager;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btn = (Button) findViewById(R.id.carPark);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButton(v);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocation();
    }
    public void navigate() {

        LatLng position = new LatLng(currLat, currLong);
        mMap.addMarker(new MarkerOptions().position(position).title(place));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14.2f));
        mMap.addCircle(new CircleOptions().center(position).radius(1).strokeColor(Color.BLUE).fillColor(Color.BLUE));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri+wayLat+","+wayLong));
        startActivity(intent);
    }


    private void getLocation() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);

        }

        if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    try {
                        mMap.setMyLocationEnabled(true);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        setLocations(latitude,longitude);
                        LatLng latLng = new LatLng(latitude,longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude,1);
                        String str = addressList.get(0).getAddressLine(0);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.2f));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        else if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER))
        {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lat = location.getLatitude();
                    longi = location.getLongitude();

                    LatLng latLng = new LatLng(lat,longi);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(lat, longi,1);
                        parkStr = addressList.get(0).getAddressLine(0);
                        mMap.addMarker(new MarkerOptions().position(latLng).title(parkStr));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }
        else {
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20.2f));
        }

    }


    private void setButton(View v)
    {
        switch (check)
        {
            case 1: navigate(); v.setVisibility(View.GONE); check = 2; break;
            case 0: setLatLng(); check = 1; break;
            default: break;
        }

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
    }
}