package edu.gatech.cs2340_68b.donationtracker.Models;
/**
 * This class hold user information
 */
public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emergencyContact;
    private Location location;

    /**
     * Get a first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set a first name
     * @param firstName a new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get a last name
     * @return a last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set a new last name
     * @param lastName a new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get a phone number
     * @return a phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set a new phone number
     * @param phoneNumber a new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get an emergency contact
     * @return an emergency contact
     */
    public String getEmergencyContact() {
        return emergencyContact;
    }

    /**
     * Set an emergency contact
     * @param emergencyContact an emergency contact
     */
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    /**
     * Get a location of the user
     * @return a location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set a location of the user
     * @param location a new a location of the user
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
