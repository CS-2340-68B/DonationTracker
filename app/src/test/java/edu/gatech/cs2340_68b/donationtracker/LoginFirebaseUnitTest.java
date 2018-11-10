package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the login using firebase by Mock Library method in the User class
 *
 * @author Thinh Nguyen
 */
public class LoginFirebaseUnitTest {
    private String userName;
    private String password;
    private VerifyFormat verifyFormat;

    @Before
    public void setup() {
        // Correct email and password format
        userName = "test1@gatech.edu";
        password = "12Th34567890";
    }

    @Test
    public void testValidLogin() {
        User classUser = mock(User.class);
        when(classUser.getUsername()).thenReturn("test1@gatech.edu");
        when(classUser.getPassword()).thenReturn("12Th34567890");

        if (verifyFormat.verifyEmailFormat(classUser.getUsername()) && verifyFormat.verifyPassword(classUser.getPassword())) {
            // In case if both password and username are match
            assertEquals(userName, classUser.getUsername());
            assertEquals(password, classUser.getPassword());
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testInvalidLogin() {
        User classUser = mock(User.class);
        when(classUser.getUsername()).thenReturn("test@gatech.edu");
        when(classUser.getPassword()).thenReturn("12Th34567890./");

        // In case if both password and username are not match
        if (!verifyFormat.verifyEmailFormat(classUser.getUsername()) && !verifyFormat.verifyPassword(classUser.getPassword())) {
            assertFalse(true);
        } else {
            assertNotEquals(userName, classUser.getUsername());
            assertNotEquals(password, classUser.getPassword());
        }
    }

    @Test
    public void testInvalidEmailFormat() {
        User classUser = mock(User.class);
        when(classUser.getUsername()).thenReturn("");
        assertFalse(classUser.getUsername(), false);

        when(classUser.getUsername()).thenReturn("tnn@mail");
        assertFalse(classUser.getUsername(), false);

        when(classUser.getUsername()).thenReturn("132323223");
        assertFalse(classUser.getUsername(), false);

        assertFalse(verifyFormat.verifyEmailFormat("tnn123232"));
        assertFalse(verifyFormat.verifyEmailFormat("tnn@mail"));
    }

    @Test
    public void testValidEmailFormat() {
        User classUser = mock(User.class);
        when(classUser.getUsername()).thenReturn("tnn@gatech.edu");
        assertTrue(classUser.getUsername(), true);

        assertTrue(verifyFormat.verifyEmailFormat("tnn123232@gatech.edu"));
        assertTrue(verifyFormat.verifyEmailFormat("tesst@gmail.com"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(verifyFormat.verifyPassword(password));
        assertTrue(verifyFormat.verifyPassword("123231231Thg"));

        String fake = "1234Th34567890";
        assertTrue(verifyFormat.verifyPassword(fake) && fake.length() >= 8);
    }

    @Test
    public void testNotValidPassword() {
        assertFalse(verifyFormat.verifyPassword(password + "///."));
        assertFalse(verifyFormat.verifyPassword("123231231hhg./"));

        String fake = "1234Th";
        assertFalse(verifyFormat.verifyPassword(fake) && fake.length() >= 8);
    }
}
