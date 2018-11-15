package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Test;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;

import static org.junit.Assert.assertEquals;

public class PasswordEncryptionEncodeTest {

    @Test
    public void EncodeTest1(){
        String pass = "Trinh";
        assertEquals("㬿䙃䋵䓋䊗", PasswordEncryption.encode(pass));
    }

    @Test
    public void EncodeTest2(){
        String pass2 = "hellooooooo";
        assertEquals("䊗䅽䐏䐏䔩䔩䔩䔩䔩䔩䔩", PasswordEncryption.encode(pass2));
    }

    @Test
    public void EncodeTest3(){
        String pass3 = "BangPham";
        assertEquals("㒣䀅䓋䈹㧇䊗䀅䑭", PasswordEncryption.encode(pass3));
    }

    @Test
    public void EncodeTest4(){
        String pass4 = "Tuancho.com";
        assertEquals("㬿䝝䀅䓋䃁䊗䔩ⵋ䃁䔩䑭", PasswordEncryption.encode(pass4));
    }

    @Test
    public void EncodeTestEmpty(){
        assertEquals("", PasswordEncryption.encode(""));
    }





}
