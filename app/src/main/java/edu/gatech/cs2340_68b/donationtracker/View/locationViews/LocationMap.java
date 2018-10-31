package edu.gatech.cs2340_68b.donationtracker.View.locationViews;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.Register;
import edu.gatech.cs2340_68b.donationtracker.View.Welcome;

public class LocationMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button detailButton;
    private Location clickedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        detailButton = findViewById(R.id.DetailButton);

        detailButton.setVisibility(View.INVISIBLE);
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
        LatLngBounds.Builder latBuilder = new LatLngBounds.Builder();

        final ArrayList<Location> locations = (ArrayList<Location>) getIntent().getSerializableExtra("LocationList");
        for (Location l: locations) {
            LatLng location = new LatLng(Double.parseDouble(l.getLatitude()), Double.parseDouble(l.getLongitude()));
            Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(l.getLocationName()));
            latBuilder.include(location);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latBuilder.build(), 100));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker m) {
                m.showInfoWindow();
                detailButton.setVisibility(View.VISIBLE);
                String title = m.getTitle();
                for (Location l: locations) {
                    if (l.getLocationName().equals(title)) {
                        clickedLocation = l;
                        break;
                    }
                }
                return true;
            }
        });

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationDetail = new Intent(LocationMap.this, LocationDetail.class);
                locationDetail.putExtra("LOCATION", clickedLocation);
                startActivity(locationDetail);
            }
        });
    }
}