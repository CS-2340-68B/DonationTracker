package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Test;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;

import static org.junit.Assert.assertEquals;

public class PasswordEncryptionEncodeTest {
    String pass = "Trinh123";
    char nt = (('T' + 77) * 94 + 33);
    char nr = (('r' + 77) * 94 + 33);
    char ni = (('i' + 77) * 94 + 33);
    char nn = (('n' + 77) * 94 + 33);
    char nh = (('h' + 77) * 94 + 33);
    char n1 = (('1' + 77) * 94 + 33);
    char n2 = (('2' + 77) * 94 + 33);
    char n3 = (('3' + 77) * 94 + 33);
   String ex = String.valueOf(nt + nr + ni + nn + nh + n1+ n2+ n3);
    public void EncodeTest(){
        assertEquals(ex, PasswordEncryption.encode(pass));
    }


}
