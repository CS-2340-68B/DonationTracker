package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Test;

import static edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption.decode;
import static org.junit.Assert.*;

/***
 * JUnit test for decoding password
 *
 * @author tnguyen467
 */
public class DecodeUnitTest {
    @Test
    public void decodeEmptyString() {
        assertEquals("", decode(""));
    }

    @Test
    public void decodeShortString() {
        assertEquals("23", decode("⻃⼡"));
    }

    @Test
    public void decodeLongString() {
        assertEquals("This1, Is. a2 Te!st?",
                decode("㬿䊗䋵䚡\u2E65ⲏ⠧㜵䚡ⵋ⠧䀅⻃⠧㬿䅽⢅䚡䛿㎉"));
    }

    @Test(expected =  IllegalArgumentException.class)
    public void invalidString() {
        decode(null);
    }
}
