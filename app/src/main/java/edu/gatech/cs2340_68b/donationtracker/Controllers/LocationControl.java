package edu.gatech.cs2340_68b.donationtracker.Controllers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.Models.LocationList;
import edu.gatech.cs2340_68b.donationtracker.R;

public class LocationControl {
    private Context context;

    public LocationControl(Context context) {
        this.context = context;
    }

    public void readCSVFile() {
        try {
            //Open a stream on the raw file
//            InputStream is = new FileInputStream(new File( "./raw/locationdata.csv"));
            InputStream is =  context.getResources().openRawResource(R.raw.locationdata);
//             InputStream is =  getResources().openRawResource(R.raw.LocationData);

            //From here we probably should call a model method and pass the InputStream
            //Wrap it in a BufferedReader so that we get the readLine() method
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//            String line;
//            int length = 0;
//            br1.readLine(); //get rid of header line
//            // Get number of locations
//            while ((line = br1.readLine()) != null) {
//                length++;
//            }
//            br1.close();

            // Array of locations with appropriate size
            LocationList.locdata = new ArrayList<>();

            // Initialize each location element in the locdata array
            // with parsed tokens from the csv file
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String readline;
            int idx = 0;
            br2.readLine(); //get rid of header line
            while ((readline = br2.readLine()) != null) {
                String[] token = readline.split(",");
                LocationList.locdata.add(new Location(token[0], token[1], token[2], token[3], token[4], token[5],
                        token[6], token[7], token[8], token[9], token[10]));
                idx++;
            }
//            Log.e("abc", LocationList.locdata.toString());
            br2.close();

        } catch (IOException e) {
            Log.e("Location Control", "error reading assets", e);
        }
    }
}
