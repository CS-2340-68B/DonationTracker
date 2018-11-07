package edu.gatech.cs2340_68b.donationtracker.Models;

import java.io.Serializable;

/***
 * All information about the location, include external information like
 * phone number and website.
 */
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

    /***
     * Base constructor to create Location instance
     */
    public Location() {}

    /***
     * Create new instance of Location with location name
     *
     * @param locationName name of the location
     */
    public Location(String locationName) {
        this.locationName = locationName;
    }

    /***
     * Create new location with all required information
     *
     * @param key key number of the location
     * @param locationName name of the location
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @param streetAddress street address of the location
     * @param city city of the location
     * @param state state of the location
     * @param zip zip code of the location
     * @param locationType type of the location
     * @param phone phone number of the location
     * @param website website of the location
     */
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

    /***
     * Getter for location's key
     *
     * @return location's key number
     */
    public String getKey() {
        return key;
    }

    /***
     * Setter for location's key
     *
     * @param key location's new key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /***
     * Getter for location's name
     *
     * @return location's name
     */
    public String getLocationName() {
        return locationName;
    }

    /***
     * Setter for location's name
     *
     * @param locationName name of the location
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /***
     * Getter for the longitude of the location
     *
     * @return Location's longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /***
     * Setter for location's longitude
     *
     * @param longitude location's longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /***
     * Getter for location's latitude
     *
     * @return location's latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /***
     * Setter for location's latitude
     *
     * @param latitude location's latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /***
     * Getter for street address
     *
     * @return location's street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /***
     * Setter for street address
     *
     * @param streetAddress location's street address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /***
     * Getter for city
     *
     * @return location's city
     */
    public String getCity() {
        return city;
    }

    /***
     * Setter for city
     *
     * @param city location's city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /***
     * Getter for state
     *
     * @return location's state
     */
    public String getState() {
        return state;
    }

    /***
     * Setter for state
     *
     * @param state location's state
     */
    public void setState(String state) {
        this.state = state;
    }

    /***
     * Getter for zip code
     *
     * @return location's zip code
     */
    public String getZip() {
        return zip;
    }

    /***
     * Setter for zip code
     *
     * @param zip location's zip code
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /***
     * Getter for location's type
     *
     * @return location's type
     */
    public String getLocationType() {
        return locationType;
    }

    /***
     * Setter for location's type
     *
     * @param locationType location's type
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /***
     * Getter for location's phone number
     *
     * @return location's phone number
     */
    public String getPhone() {
        return phone;
    }

    /***
     * Setter for location's phone number
     *
     * @param phone location's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /***
     * Getter for location's website
     *
     * @return location's website
     */
    public String getWebsite() {
        return website;
    }

    /***
     * Setter for location's website
     *
     * @param website location's website
     */
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

    /***
     * Retrieve crucial information about the address of the location
     *
     * @return address with street number, city, state, and zip code
     */
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
