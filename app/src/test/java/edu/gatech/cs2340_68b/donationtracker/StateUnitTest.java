package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Test;

/**
 * Test valueOfName method in the StatesList class
 * @author Nhat Lan Le Tu
 */
public class StateUnitTest {

    /**
     * State is valid
     */
    @Test
    public void validState() {
        String state = "GEORGIA";
        StatesList Gstate = StatesList.GEORGIA;
        assertEquals(Gstate, StatesList.valueOfName(state));
    }

    /**
     * State is invalid
     */
    @Test
    public void validStateLowerCase() {
        String state = "georgia";
        StatesList Gstate = StatesList.GEORGIA;
        assertEquals(Gstate, StatesList.valueOfName(state));
    }

    /**
     * Valid state with two words in the name
     */
    @Test
    public void validStateTwoWords() {
        String state = "SOUTH DAKOTA";
        StatesList SDstate = StatesList.SOUTH_DAKOTA;
        assertEquals(SDstate, StatesList.valueOfName(state));
    }

    /**
     * Valid state with two words in the name in lower case
     */
    @Test
    public void validStateTwoWordsLower() {
        String state = "south dakota";
        StatesList SDstate = StatesList.SOUTH_DAKOTA;
        assertEquals(SDstate, StatesList.valueOfName(state));
    }


    /**
     * Invalid state
     */
    @Test
    public void invalidState() {
        String state = "JAPAN";
        assertEquals(StatesList.UNKNOWN, StatesList.valueOfName(state));
    }

    /***
     * Throw exception in null input
     */
    @Test(expected = NullPointerException.class)
    public void invalidInputFormat() {
        StatesList.valueOfName(null);
    }

    /***
     * Fail to compare state
     */
    @Test
    public void failedComparison() {
        String state = "Florida";
        assertNotEquals(StatesList.UNKNOWN, StatesList.valueOfName(state));
    }

    /***
     * Check abbreviation
     */
    @Test
    public void checkAbbreviationInput() {
        String state = "FL";
        assertNotEquals(StatesList.FLORIDA, StatesList.valueOfName(state));
    }

}
