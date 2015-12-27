package com.example.sabyasachi.patlas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity {
    static double latitude = 0, longitude = 0;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String s =pref.getString("Place_list", "");
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = geocoder.getFromLocationName(s, 5);
            if (address == null) {
                Toast.makeText(this, "Too Slow Internet", Toast.LENGTH_SHORT).show();
            }
            else {
                Address location = address.get(0);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }


        } catch (Exception ex) {
            Toast.makeText(this, "Too Slow Internet", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(s)).showInfoWindow();

        //mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("lala")).showInfoWindow();

        /*mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longtiude),14));*/
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapsActivity.this,list.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
