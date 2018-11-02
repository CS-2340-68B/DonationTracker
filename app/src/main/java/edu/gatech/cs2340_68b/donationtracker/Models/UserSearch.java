package edu.gatech.cs2340_68b.donationtracker.Models;

import java.io.Serializable;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.SearchOptions;

public class UserSearch implements Serializable {
    private String keyword;
    private SearchOptions searchOption;
    private String locationName;

    public UserSearch() {}

    public UserSearch(String keyword, SearchOptions searchOption, String locationName) {
        this.keyword = keyword;
        this.searchOption = searchOption;
        this.locationName = locationName;
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

    @Override
    public String toString() {
        return keyword + " - " + searchOption + " - " + locationName;
    }
}
