/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programming1apoe;
/**
 *
 * @author ST10522809
 */


import java.io.IOException;
import java.util.Scanner;

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

        runQuickChat(input, firstName + " " + lastName);
    }

    private static void runQuickChat(Scanner input, String sender) {
        QuickchatManager manager = new QuickchatManager();

        try {
            manager.loadStoredMessages();
        } catch (IOException e) {
            System.out.println("Could not load stored messages: "
                    + e.getMessage());
        }

        int nextMessageNumber = manager.getAllMessages().size();
        boolean running = true;

        System.out.println("\nWelcome to QuickChat.");

        while (running) {
            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
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
                                nextMessageNumber,
                                recipient,
                                text
                        );

                        message.setSender(sender);

                        System.out.println("Message ID generated: "
                                + message.getMessageID());

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

                        String action = readAction(input);
                        String actionResult = message.SentMessage(action);
                        System.out.println(actionResult);

                        if ("2".equals(action.trim())) {
                            System.out.print(
                                    "Enter 0 to confirm that this message is disregarded: "
                            );

                            if (!"0".equals(input.nextLine().trim())) {
                                System.out.println("Message was not disregarded.");
                                i--;
                                continue;
                            }
                        }

                        if ("1".equals(action.trim())
                                || "2".equals(action.trim())
                                || "3".equals(action.trim())) {
                            try {
                                manager.addMessage(message);

                                if ("SENT".equals(message.getAction())) {
                                    System.out.println(message.printMessages());
                                }

                                nextMessageNumber++;
                            } catch (IOException e) {
                                System.out.println(
                                        "Could not update messages.json: "
                                        + e.getMessage()
                                );
                            }
                        } else {
                            System.out.println("Please choose 1, 2 or 3.");
                            i--;
                        }
                    }

                    System.out.println("Total messages sent: "
                            + manager.getSentMessages().length);
                    break;

                case "2":
                    Message[] sentMessages = manager.getSentMessages();

                    if (sentMessages.length == 0) {
                        System.out.println("No sent messages yet.");
                    } else {
                        for (Message message : sentMessages) {
                            System.out.println(message.printMessages());
                            System.out.println();
                        }
                    }
                    break;

                case "3":
                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;

                case "4":
                    storedMessagesMenu(input, manager);
                    break;

                default:
                    System.out.println("Invalid option. Choose 1, 2, 3 or 4.");
            }
        }
    }

    private static void storedMessagesMenu(
            Scanner input, QuickchatManager manager) {

        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n===== STORED MESSAGES =====");
            System.out.println("1) Display sender and recipient");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search messages by recipient");
            System.out.println("5) Delete a message using its hash");
            System.out.println("6) Display message report");
            System.out.println("7) Back to main menu");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println(
                            manager.displayStoredSendersAndRecipients()
                    );
                    break;

                case "2":
                    System.out.println(
                            manager.displayLongestStoredMessage()
                    );
                    break;

                case "3":
                    System.out.print("Enter the message ID: ");
                    String id = input.nextLine().trim();

                    System.out.println(manager.searchMessageByID(id));
                    break;

                case "4":
                    System.out.print("Enter the recipient number: ");
                    String recipient = input.nextLine().trim();

                    System.out.println(
                            manager.searchMessagesByRecipient(recipient)
                    );
                    break;

                case "5":
                    System.out.print("Enter the message hash: ");
                    String hash = input.nextLine().trim();

                    try {
                        System.out.println(
                                manager.deleteStoredMessage(hash)
                        );
                    } catch (IOException e) {
                        System.out.println(
                                "Could not delete message: " + e.getMessage()
                        );
                    }
                    break;

                case "6":
                    System.out.println(manager.displayMessageReport());
                    break;

                case "7":
                    inMenu = false;
                    break;

                default:
                    System.out.println("Invalid option. Choose 1 to 7.");
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
                // The user will be asked to enter a valid number.
            }

            System.out.println(
                    "Please enter zero or a positive whole number."
            );
        }
    }

    private static String readAction(Scanner input) {
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Choose an action: ");

        return input.nextLine().trim();
    }
}
