package com.happytumiteam.surveypkbl;

/**
 * Created by Sisillia on 2/9/2017.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDemoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Receiving latitude from MainActivity screen
        double latitude = getIntent().getDoubleExtra("lat", 0);

        // Receiving longitude from MainActivity screen
        double longitude = getIntent().getDoubleExtra("lng", 0);

        LatLng position = new LatLng(latitude, longitude);

        // Instantiating MarkerOptions class
        MarkerOptions options = new MarkerOptions();

        // Setting position for the MarkerOptions
        options.position(position);

        // Setting title for the MarkerOptions
        options.title("Position");

        // Setting snippet for the MarkerOptions
        options.snippet("Latitude:"+latitude+",Longitude:"+longitude);

        // Getting Reference to SupportMapFragment of activity_map.xml
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting reference to google map
        GoogleMap googleMap = fm.getMap();

        // Adding Marker on the Google Map
        googleMap.addMarker(options);

        // Creating CameraUpdate object for position
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);

        // Creating CameraUpdate object for zoom
        CameraUpdate updateZoom = CameraUpdateFactory.zoomBy(4);

        // Updating the camera position to the user input latitude and longitude
        googleMap.moveCamera(updatePosition);

        // Applying zoom to the marker position
        googleMap.animateCamera(updateZoom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}