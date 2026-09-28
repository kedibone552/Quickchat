/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package programming1apoe;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the registration and login features.
 */
public class LoginTest {

    @Test
    public void testValidUsername() {

        Login user = new Login(
                "Kedi",
                "Mathaba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testInvalidUsername() {

        Login user = new Login(
                "Kedi",
                "Mathaba",
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.checkUserName());

        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                user.registerUser()
        );
    }

    @Test
    public void testValidPassword() {

        Login user = new Login(
                "Kedi",
                "Mathaba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testInvalidPassword() {

        Login user = new Login(
                "Kedi",
                "Mathaba",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(user.checkPasswordComplexity());

        assertEquals(
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                user.registerUser()
        );
    }

    @Test
    public void testValidCellPhoneNumber() {

        Login user = new Login(
                "Kedi",
                "Mathaba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidCellPhoneNumber() {

        Login user = new Login(
                "Kedi",
                "Mathaba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(user.checkCellPhoneNumber());

        assertEquals(
                "Cell phone number incorrectly formatted or does not contain international code.",
                user.registerUser()
        );
    }

    @Test
    public void testSuccessfulLogin() {

        Login user = new Login(
                "Kedi",
                "Mathaba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        user.registerUser();

        user.setLoginDetails(
                "kyl_1",
                "Ch&&sec@ke99!"
        );

        assertTrue(user.loginUser());

        assertEquals(
                "Welcome Kedi Mathaba it is great to see you again.",
                user.returnLoginStatus()
        );
    }

    @Test
    public void testFailedLogin() {

        Login user = new Login(
                "Kedi",
                "Mathaba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        user.registerUser();

        user.setLoginDetails(
                "wrong",
                "password"
        );

        assertFalse(user.loginUser());

        assertEquals(
                "Username or password incorrect, please try again.",
                user.returnLoginStatus()
        );
    }
}
