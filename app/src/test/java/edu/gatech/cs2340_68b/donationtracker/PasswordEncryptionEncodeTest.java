package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Test;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;

import static org.junit.Assert.assertEquals;

public class PasswordEncryptionEncodeTest {
    String pass  = "Trinh";
    String pass2 = "hellooooooo";
    String pass3 = "BangPham";
    String pass4 = "Tuancho.com";

    @Test
    public void EncodeTest1(){
        assertEquals("㬿䙃䋵䓋䊗", PasswordEncryption.encode(pass));
    }

    @Test
    public void EncodeTest2(){
        assertEquals("䊗䅽䐏䐏䔩䔩䔩䔩䔩䔩䔩", PasswordEncryption.encode(pass2));
    }

    @Test
    public void EncodeTest3(){
        assertEquals("㒣䀅䓋䈹㧇䊗䀅䑭", PasswordEncryption.encode(pass3));
    }

    @Test
    public void EncodeTest4(){
        assertEquals("㬿䝝䀅䓋䃁䊗䔩ⵋ䃁䔩䑭", PasswordEncryption.encode(pass4));
    }

    @Test
    public void EncodeTestEmpty(){
        assertEquals("", PasswordEncryption.encode(""));
    }





}
