package edu.gatech.cs2340_68b.donationtracker.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Donation detail class
 */
@SuppressWarnings("ConstructorWithTooManyParameters")
public class DonationDetail implements Serializable {

    private String time;
    private String location;
    private String fullDescription;
    private String shortDescription;
    private String value;
    private String category;
    private String comment;
    private String name;
    private String donationKey;

    /**
     * Hold donation information
     */
    public DonationDetail() {}

    /**
     * Constructor for donation detail
     * @param location a location of donation
     */
    public DonationDetail(String location) {
        this.location = location;
    }

    /**
     * A constructor for donation detail class
     * @param time a time of donation
     * @param location a location of donation
     * @param fullDescription a full description
     * @param shortDescription a short description
     * @param value a value
     * @param category a category
     * @param comment a comment to donation
     * @param name a name of donation
     */
    public DonationDetail(String time,
                          String location,
                          String fullDescription,
                          String shortDescription,
                          String value, String category,
                          String comment, String name) {
        this.time = time;
        this.location = location;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.value = value;
        this.category = category;
        this.comment = comment;
        this.name = name;
    }

    /**
     * Get time of donation
     * @return a time
     */
    public String getTime() {
        return time;
    }

    /**
     * Get name of donation
     * @return a name
     */
    public String getName() { return name; }

    /**
     * Set a new name
     * @param name a new name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Set a new time
     * @param time a new time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Get a location of the donation
     * @return a location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set a location detail
     * @param location a new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get a full description of the donation
     * @return a full description
     */
    public String getFullDescription() {
        return fullDescription;
    }

    /**
     * Set a full description
     * @param fullDescription a new full description
     */
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    /**
     * Get a short description of donation
     * @return a short description of donation
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Set a short description of donation
     * @param shortDescription new short description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Get a value of the donation
     * @return a value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set a new value
     * @param value a new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get a category of the donation
     * @return a donation category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set a new category
     * @param category a new category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get a comment
     * @return a comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Get a comment
     * @param comment a new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get donation's key
     * @return donation's key
     */
    public String getDonationKey() {
        return donationKey;
    }

    /**
     * Set the key for donation
     * @param donationKey donation's key
     */
    public void setDonationKey(String donationKey) {
        this.donationKey = donationKey;
    }


    /**
     * Set values
     * @param newDonationDetails donation details
     */
    public void setValues(DonationDetail newDonationDetails) {
        time = newDonationDetails.time;
        name = newDonationDetails.name;
        fullDescription = newDonationDetails.fullDescription;
        shortDescription = newDonationDetails.shortDescription;
        location = newDonationDetails.location;
        value = newDonationDetails.value;
        comment = newDonationDetails.comment;
        category = newDonationDetails.category;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " - " + location + " - " + shortDescription;
    }

}
