package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;

/**
 * Control location list, get data from database and add data to database
 */

public class LocationControl {
    private final Context context;

    /**
     * A constructor for LocationControl class
     * @param context a context
     */
    public LocationControl(Context context) {
        this.context = context;
    }

    /**
     * Read csv file
     */
    public void readCSVFile() {
        try {
            //Open a stream on the raw file
            InputStream is =  context.getResources().openRawResource(R.raw.locationdata);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String readline;
            br1.readLine(); //get rid of header line
            while ((readline = br1.readLine()) != null) {
                String[] token = readline.split(",");
                Location location = new Location(token[0], token[1], token[2], token[3], token[4], token[5],
                        token[6], token[7], token[8], token[9], token[10]);
                addLocationToDB(location);
            }
            br1.close();

        } catch (IOException e) {
            Log.e("Location Control", "error reading assets", e);
        }
    }

    /**
     * Add a new location to the database
     * @param location a location
     */
    private void addLocationToDB(final Location location) {

        final DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean canAdd = true;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Location currentPlace = snapshot.getValue(Location.class);
                    if (Objects.requireNonNull(currentPlace).getLocationName().equals(location.getLocationName()) && currentPlace.getLatitude().equals(location.getLatitude()) && currentPlace.getLongitude().equals(location.getLongitude())) {
                        canAdd = false;
                    }
                }

                // After the loop then check if able to add
                if (canAdd) {
                    DatabaseReference newRef = locationDB.push();
                    newRef.setValue(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        // OLD code
//        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
//        DatabaseReference ref = firebase.getReference("locations").push();
//        ref.setValue(location);
    }
}
