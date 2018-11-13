package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.StatesList;

/**
 * Test valueOfName method in the StatesList class
 * @author Nhat Lan Le Tu
 */
public class StateUnitTest {
    @Test
    public void validState() {
        String state = "GEORGIA";
        StatesList Gstate = StatesList.GEORGIA;
        assertEquals(Gstate, StatesList.valueOfName(state));
    }

    @Test
    public void validStateLowerCase() {
        String state = "georgia";
        StatesList Gstate = StatesList.GEORGIA;
        assertEquals(Gstate, StatesList.valueOfName(state));
    }

    @Test
    public void validStateTwoWords() {
        String state = "SOUTH DAKOTA";
        StatesList SDstate = StatesList.SOUTH_DAKOTA;
        assertEquals(SDstate, StatesList.valueOfName(state));
    }

    @Test
    public void validStateTwoWordsLower() {
        String state = "south dakota";
        StatesList SDstate = StatesList.SOUTH_DAKOTA;
        assertEquals(SDstate, StatesList.valueOfName(state));
    }


    @Test
    public void invalidState() {
        String state = "JAPAN";
        assertEquals(StatesList.UNKNOWN, StatesList.valueOfName(state));
    }

    @Test(expected = NullPointerException.class)
    public void invalidInputFormat() {
        StatesList.valueOfName(null);
    }

    @Test
    public void failedComparison() {
        String state = "Florida";
        assertNotEquals(StatesList.UNKNOWN, StatesList.valueOfName(state));
    }

    @Test
    public void checkAbbreviationInput() {
        String state = "FL";
        assertNotEquals(StatesList.FLORIDA, StatesList.valueOfName(state));
    }

}
