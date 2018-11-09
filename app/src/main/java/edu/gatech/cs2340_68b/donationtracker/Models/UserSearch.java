package edu.gatech.cs2340_68b.donationtracker.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.SearchOptions;

/***
 * Search criteria to perform search to database.
 */
public class UserSearch implements Serializable {
    private String keyword;
    private SearchOptions searchOption;
    private String locationName;

    /***
     * Default constructor
     */
    public UserSearch() {}

    /***
     * Constructor with all required parameters
     * @param keyword keyword for the search
     * @param searchOption search option
     * @param locationName name of the location
     */
    public UserSearch(String keyword, SearchOptions searchOption, String locationName) {
        this.keyword = keyword;
        this.searchOption = searchOption;
        this.locationName = locationName;
    }

    /***
     * Getter for the search keyword
     *
     * @return current keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /***
     * Setter for the search keyword
     *
     * @param keyword new keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /***
     * Getter for the search option
     *
     * @return current search option
     */
    public SearchOptions getSearchOption() {
        return searchOption;
    }

    /***
     * Setter for the search option
     *
     * @param searchOption search option
     */
    public void setSearchOption(SearchOptions searchOption) {
        this.searchOption = searchOption;
    }

    /***
     * Getter for the name of the location
     *
     * @return location's name
     */
    public String getLocationName() {
        return locationName;
    }

    /***
     * Setter for the location's name
     *
     * @param locationName location's name
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @NonNull
    @Override
    public String toString() {
        return keyword + " - " + searchOption + " - " + locationName;
    }
}
