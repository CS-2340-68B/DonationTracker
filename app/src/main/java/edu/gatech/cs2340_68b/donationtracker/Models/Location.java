package edu.gatech.cs2340_68b.donationtracker.Models;

import java.io.Serializable;

public class Location implements Serializable {
    private String key;
    private String locationName;
    private String longitude;
    private String latitude;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String locationType;
    private String phone;
    private String website;

    // For the purpose of create instance from Firebase
    public Location() {}

    public Location(String key, String locationName, String latitude, String longitude,
                    String streetAddress, String city, String state, String zip,
                    String locationType, String phone, String website) {
        this.key = key;
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.locationType = locationType;
        this.phone = phone;
        this.website = website;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Key: " + this.key + "\n";
        s += "Location Name: " + this.locationName + "\n";
        s += "Longitude: " + this.longitude + "\n";
        s += "Latitude: " + this.latitude + "\n";
        s += "Street Address: " + this.streetAddress + "\n";
        s += "City: " + this.city + "\n";
        s += "State: " + this.state + "\n";
        s += "Zip: " + this.zip + "\n\n\n\n";
        return s;
    }

    public String getAddress() {
        return streetAddress + ", " + city + ", " + state + ", " + zip;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Location)) {
            return false;
        }
        Location l = (Location) o;
        boolean sameName = locationName.equals(l.locationName)
                || locationName.isEmpty() || l.locationName.isEmpty();
        boolean sameAddress = streetAddress.equals(l.streetAddress)
                || streetAddress.isEmpty() || l.streetAddress.isEmpty();
        boolean sameCity = city.equals(l.city)
                || city.isEmpty() || l.city.isEmpty();
        boolean sameState = state.equals(l.state)
                || state.isEmpty() || l.state.isEmpty();
        boolean sameZip = zip.equals(l.zip)
                || zip.isEmpty() || l.zip.isEmpty();
        return sameName && sameAddress && sameCity && sameState && sameZip;
    }
}
