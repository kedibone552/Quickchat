/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programming1apoe;
/**
 *
 * @author ST10522809
 */

import java.util.Scanner;

/**
 * Runs the registration and login application.
 */
public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println(" REGISTRATION ");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your South African cell phone number: ");
        String cellPhoneNumber = input.nextLine();

        // Creates the user.
        Login user = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        String registrationMessage = user.registerUser();

        System.out.println("\n" + registrationMessage);

        // Only allows login after successful registration.
        if (user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber()) {

            System.out.println("\n===== LOGIN =====");

            System.out.print("Enter your username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = input.nextLine();

            user.setLoginDetails(loginUsername, loginPassword);

            System.out.println("\n" + user.returnLoginStatus());

        } else {

            System.out.println("Please correct your registration details and try again.");
        }

        input.close();
    }
}

