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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("===== REGISTRATION =====");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your cell phone number (+27...): ");
        String cellPhone = input.nextLine();

        Login user = new Login(
            firstName, lastName, username, password, cellPhone
        );

        System.out.println(user.registerUser());

        if (!user.checkUserName()
                || !user.checkPasswordComplexity()
                || !user.checkCellPhoneNumber()) {
            System.out.println("Please correct your registration details.");
            return;
        }

        System.out.println("\n===== LOGIN =====");

        System.out.print("Enter your username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = input.nextLine();

        user.setLoginDetails(loginUsername, loginPassword);
        System.out.println(user.returnLoginStatus());

        if (!user.loginUser()) {
            return;
        }

        runQuickChat(input);
    }

    private static void runQuickChat(Scanner input) {
        List<Message> messages = new ArrayList<>();
        int nextMessageNumber = 0;
        int totalSent = 0;
        boolean running = true;

        System.out.println("Welcome to QuickChat.");

        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    int count = readMessageCount(input);

                    for (int i = 0; i < count; i++) {
                        System.out.println("\nMessage " + (i + 1)
                                + " of " + count);

                        System.out.print("Enter recipient number (+27...): ");
                        String recipient = input.nextLine().trim();

                        System.out.print("Enter your message: ");
                        String text = input.nextLine();

                        Message message = new Message(
                            nextMessageNumber, recipient, text
                        );

                        System.out.println(
                            "Message ID generated: " + message.getMessageID()
                        );

                        String recipientStatus = message.checkRecipientCell();
                        String lengthStatus = message.checkMessageLength();

                        System.out.println(recipientStatus);
                        System.out.println(lengthStatus);

                        if (!recipientStatus.equals(
                                "Cell phone number successfully captured.")
                                || !lengthStatus.equals("Message ready to send.")) {
                            System.out.println(
                                "Please correct the details and enter the message again."
                            );
                            i--;
                            continue;
                        }

                        System.out.println(message.SentMessage(readAction(input)));

                        if ("DISREGARDED".equals(message.getAction())) {
                            System.out.print("Enter 0 to delete this message: ");

                            if ("0".equals(input.nextLine().trim())) {
                                nextMessageNumber++;
                                continue;
                            }
                        }

                        messages.add(message);

                        if ("SENT".equals(message.getAction())) {
                            totalSent++;
                            System.out.println(message.printMessages());
                        }

                        if ("STORED".equals(message.getAction())) {
                            try {
                                message.storeMessage(messages);
                            } catch (IOException e) {
                                System.out.println(
                                    "Could not save messages.json: " + e.getMessage()
                                );
                            }
                        }

                        nextMessageNumber++;
                    }

                    System.out.println("Total messages sent: " + totalSent);
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;

                default:
                    System.out.println("Invalid option. Choose 1, 2 or 3.");
            }
        }
    }

    private static int readMessageCount(Scanner input) {
        while (true) {
            System.out.print("How many messages would you like to enter? ");

            try {
                int count = Integer.parseInt(input.nextLine().trim());

                if (count >= 0) {
                    return count;
                }
            } catch (NumberFormatException e) {
                // Ask again when the input is not a number.
            }

            System.out.println("Please enter zero or a positive whole number.");
        }
    }

    private static String readAction(Scanner input) {
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Choose an action: ");

        return input.nextLine();
    }
}
