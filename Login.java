/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package programming1apoe;

/**
 *
 * @author ST10522809
 */

import java.util.regex.Pattern;

/**
 * Handles registration and login for the user.
 */
public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    private String loginUsername;
    private String loginPassword;

    // Stores the user's details.
    public Login(String firstName, String lastName, String username,
            String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Checks the username format.
    public boolean checkUserName() {

        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    // Checks all the password requirements.
    public boolean checkPasswordComplexity() {

        return password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    /*
     * Checks that the number starts with South Africa's international
     * code (+27) and is followed by nine digits.
     *
     * Regex reference:
     * Oracle (2026), Pattern (Java SE 17).
     * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html
     */
    public boolean checkCellPhoneNumber() {

        String cellPhoneRegex = "^\\+27[0-9]{9}$";

        return cellPhoneNumber != null
                && Pattern.matches(cellPhoneRegex, cellPhoneNumber);
    }

    // Checks the registration details and returns a message.
    public String registerUser() {

        if (!checkUserName()) {

            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

        } else if (!checkPasswordComplexity()) {

            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        } else if (!checkCellPhoneNumber()) {

            return "Cell phone number incorrectly formatted or does not contain international code.";

        } else {

            return "User registered successfully.";
        }
    }

    // Saves the details entered when the user tries to log in.
    public void setLoginDetails(String loginUsername, String loginPassword) {

        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }

    // Compares the login details with the registered details.
    public boolean loginUser() {

        return username.equals(loginUsername)
                && password.equals(loginPassword);
    }

    // Gives the correct message after login.
    public String returnLoginStatus() {

        if (loginUser()) {

            return "Welcome " + firstName + " " + lastName
                    + " it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }
}
