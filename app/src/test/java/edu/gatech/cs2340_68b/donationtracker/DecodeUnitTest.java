package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Test;

import static edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption.decode;
import static org.junit.Assert.*;

/***
 * JUnit test for decoding password
 *
 * @author tnguyen467
 */
@SuppressWarnings("FeatureEnvy")
public class DecodeUnitTest {
    @Test
    public void decodeEmptyString() {
        assertEquals("", decode(""));
    }

    @Test
    public void decodeShortString() {
        assertEquals("abc", decode("䀅䁣䃁"));
        assertEquals("23", decode("⻃⼡"));
        assertEquals("GeorgiaTech", decode("㙹䅽䔩䙃䈹䋵䀅㬿䅽䃁䊗"));
    }

    @Test
    public void decodeLongString() {
        assertEquals("This1, Is. a2 Te!st?",
                decode("㬿䊗䋵䚡\u2E65ⲏ⠧㜵䚡ⵋ⠧䀅⻃⠧㬿䅽⢅䚡䛿㎉"));
        assertEquals("The quick brown fox jumps over the lazy dog",
                decode("㬿䊗䅽⠧䗥䝝䋵䃁䎱⠧䁣䙃䔩䠙䓋⠧" +
                        "䇛䔩䡷⠧䍓䝝䑭䖇䚡⠧䔩䞻䅽䙃⠧䛿䊗䅽⠧䐏䀅䤳䣕⠧䄟䔩䈹"));
        assertEquals("RamblinWreckfromGeorgiaTechandahe" +
                        "llofanengineerAhelluvahelluvahelluvahelluvahell" +
                        "ofanengineer",
                decode("㪃䀅䑭䁣䐏䋵䓋㱙䙃䅽䃁䎱䇛䙃䔩䑭" +
                        "㙹䅽䔩䙃䈹䋵䀅㬿䅽䃁䊗䀅䓋䄟䀅䊗䅽䐏䐏䔩䇛䀅䓋䅽䓋䈹䋵䓋" +
                        "䅽䅽䙃㑅䊗䅽䐏䐏䝝䞻䀅䊗䅽䐏䐏䝝䞻䀅䊗䅽䐏䐏䝝䞻䀅䊗䅽䐏" +
                        "䐏䝝䞻䀅䊗䅽䐏䐏䔩䇛䀅䓋䅽䓋䈹䋵䓋䅽䅽䙃"));
    }

    @Test(expected =  IllegalArgumentException.class)
    public void invalidString() {
        decode(null);
    }
}
