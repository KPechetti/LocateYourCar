package com.example.anil6066.locateyourcar;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GlobalClass {

    public static GoogleMap mMap;
    public static double lat, longi;
    public static Button btn;
    public static double carLat, carLong;
    public static int check = 0;
    public static double currLat, currLong, wayLat, wayLong;
    public static String parkStr;
    public static  String uri ="http://maps.google.com/maps?daddr=";
    public static String place = "You're Here";

    public static void setLatLng() {

        wayLat = carLat;
        wayLong = carLong;
        LatLng park = new LatLng(wayLat, wayLong);
        mMap.addMarker(new MarkerOptions().position(park).title(parkStr).icon(BitmapDescriptorFactory.fromResource(R.mipmap.steeringicon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(park, 18.2f));
        mMap.addCircle(new CircleOptions().center(park).radius(50).strokeColor(Color.RED));
        btn.setText("Navigate");
    }
    public  static void setLocations(double lat, double longit)
    {
        carLat = currLat = lat;
        carLong = currLong = longit;
    }

}
