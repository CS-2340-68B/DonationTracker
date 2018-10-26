package edu.gatech.cs2340_68b.donationtracker.Models;

public class DonationDetail {

    private String time;
    private String location;
    private String fullDescription;
    private String shortDescription;
    private String value;
    private String category;
    private String comment;
    private String name;

    public DonationDetail() {};

    public DonationDetail(String time, String location, String fullDescription, String shortDescription, String value, String category, String comment, String name) {
        this.time = time;
        this.location = location;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.value = value;
        this.category = category;
        this.comment = comment;
        this.name = name;
    };

    public String getTime() {
        return time;
    }

    public String getName() { return name; }

    public void setName(String donation) { this.name = name; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
