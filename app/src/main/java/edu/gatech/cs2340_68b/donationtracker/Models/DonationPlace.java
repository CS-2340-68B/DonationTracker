package edu.gatech.cs2340_68b.donationtracker.Models;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.DonationPlaceType;

public class DonationPlace {
    private String name;
    private String id;
    private String phoneNumber;
    private String website;
    private Category category;
    private DonationPlaceType donationPlaceType;
    private Location location;

    public DonationPlace() {};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public DonationPlaceType getDonationPlaceType() {
        return donationPlaceType;
    }

    public void setDonationPlaceType(DonationPlaceType donationPlaceType) {
        this.donationPlaceType = donationPlaceType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
