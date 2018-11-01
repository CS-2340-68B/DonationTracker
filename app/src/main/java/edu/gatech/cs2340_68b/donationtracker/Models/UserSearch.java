package edu.gatech.cs2340_68b.donationtracker.Models;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.SearchOptions;

public class UserSearch {
    private String keyword;
    private SearchOptions searchOption;
    private String locationName;
    private long timeStamp;

    public UserSearch() {}

    public UserSearch(String keyword, SearchOptions searchOption, String locationName, long timeStamp) {
        this.keyword = keyword;
        this.searchOption = searchOption;
        this.locationName = locationName;
        this.timeStamp = timeStamp;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public SearchOptions getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(SearchOptions searchOption) {
        this.searchOption = searchOption;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
