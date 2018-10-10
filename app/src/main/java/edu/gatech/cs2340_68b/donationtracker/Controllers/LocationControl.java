package edu.gatech.cs2340_68b.donationtracker.Controllers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.Models.LocationList;
import edu.gatech.cs2340_68b.donationtracker.R;

public class LocationControl {
    public static void readCSVFile() {
//        try {
//            //Open a stream on the raw file
//            InputStream is = new FileInputStream(new File("./raw/locationdata.csv"));
//            // InputStream is = new getResources().openRawResource(R.raw.LocationData);
//
//            //From here we probably should call a model method and pass the InputStream
//            //Wrap it in a BufferedReader so that we get the readLine() method
//            BufferedReader br1 = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//            String line;
//            int length = 0;
//            br1.readLine(); //get rid of header line
//            // Get number of locations
//            while ((line = br1.readLine()) != null) {
//                length++;
//            }
//            br1.close();
//
//            // Array of locations with appropriate size
//            LocationList.locdata = new ArrayList<>();
//
//            // Initialize each location element in the locdata array
//            // with parsed tokens from the csv file
//            BufferedReader br2 = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//            String readline;
//            int idx = 0;
//            br2.readLine(); //get rid of header line
//            while ((readline = br2.readLine()) != null) {
//                String[] token = line.split(",");
//                LocationList.locdata.add(new Location(token[0], token[1], token[2], token[3], token[4], token[5],
//                        token[6], token[7], token[8], token[9], token[10]));
//                idx++;
//            }
//            br2.close();
//
//        } catch (IOException e) {
//            Log.e("Location Control", "error reading assets", e);
//        }
    }
}
