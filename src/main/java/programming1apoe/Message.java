/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programming1apoe;

/**
 *
 * @author Matha
 */

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.io.IOException;

public class Message {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final String messageID;
    private final int messageNumber;
    private final String recipientCell;
    private final String messageText;
    private final String messageHash;
    private String action = "";

    public Message(int messageNumber, String recipientCell, String messageText) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipientCell = recipientCell == null ? "" : recipientCell.trim();
        this.messageText = messageText == null ? "" : messageText;
        this.messageHash = createMessageHash();
    }

    public Message(String messageID, int messageNumber,
                   String recipientCell, String messageText) {
        this.messageID = messageID == null ? "" : messageID;
        this.messageNumber = messageNumber;
        this.recipientCell = recipientCell == null ? "" : recipientCell.trim();
        this.messageText = messageText == null ? "" : messageText;
        this.messageHash = createMessageHash();
    }

    public static String generateMessageID() {
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            id.append(RANDOM.nextInt(10));
        }

        return id.toString();
    }

    public Boolean checkMessageID() {
        return messageID.matches("[0-9]{1,10}");
    }

    public String checkRecipientCell() {
        if (recipientCell.matches("^\\+27[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }

        return "Message exceeds 250 characters by "
                + (messageText.length() - 250)
                + "; please reduce the size.";
    }

    public String createMessageHash() {
        String firstTwo = messageID.length() >= 2
                ? messageID.substring(0, 2) : messageID;

        String cleaned = messageText
                .replaceAll("[^A-Za-z0-9\\s]", " ")
                .trim();

        String[] words = cleaned.isEmpty()
                ? new String[0] : cleaned.split("\\s+");

        String first = words.length == 0 ? "" : words[0];
        String last = words.length == 0 ? "" : words[words.length - 1];

        return (firstTwo + ":" + messageNumber + ":" + first + last)
                .toUpperCase(Locale.ROOT);
    }

    public String SentMessage() {
        Scanner input = new Scanner(System.in);

        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Choose an action: ");

        return SentMessage(input.nextLine());
    }

    public String SentMessage(String choice) {
        if (choice == null) {
            return "Invalid choice. Please choose 1, 2 or 3.";
        }

        switch (choice.trim()) {
            case "1":
                action = "SENT";
                return "Message successfully sent.";

            case "2":
                action = "DISREGARDED";
                return "Press 0 to delete the message.";

            case "3":
                action = "STORED";
                return "Message successfully stored.";

            default:
                return "Invalid choice. Please choose 1, 2 or 3.";
        }
    }

    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipientCell
                + "\nMessage: " + messageText;
    }

    public int returnTotalMessagess() {
        return "SENT".equals(action) ? 1 : 0;
    }

    public void storeMessage(List<Message> messages) throws IOException {
        action = "STORED";
        new MessageStorage().storeMessage(messages);
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipientCell() {
        return recipientCell;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? "" : action;
    }
}
