/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programming1apoe;

/**
 *
 * @author Matha
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuickchatManager {

    private final List<Message> allMessages = new ArrayList<>();
    private final MessageStorage storage;

    // The five arrays required for Part 3.
    private Message[] sentMessages = new Message[0];
    private Message[] disregardedMessages = new Message[0];
    private Message[] storedMessages = new Message[0];
    private String[] messageHashes = new String[0];
    private String[] messageIDs = new String[0];

    public QuickchatManager() {
        this(new MessageStorage());
    }

    public QuickchatManager(MessageStorage storage) {
        this.storage = storage;
    }

    public void addMessage(Message message) throws IOException {
        allMessages.add(message);

        if ("STORED".equals(message.getAction())) {
            storage.storeMessage(allMessages);
        }

        refreshArrays();
    }

    // Loads previously stored messages from the JSON file.
    public void loadStoredMessages() throws IOException {
        List<Message> loadedMessages = storage.readMessages();

        for (Message message : loadedMessages) {
            message.setAction("STORED");

            boolean alreadyExists = false;

            for (Message existing : allMessages) {
                if (existing.getMessageID().equals(message.getMessageID())) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                allMessages.add(message);
            }
        }

        refreshArrays();
    }

    // Updates all five arrays from the message list.
    private void refreshArrays() {
        List<Message> sent = new ArrayList<>();
        List<Message> disregarded = new ArrayList<>();
        List<Message> stored = new ArrayList<>();

        List<String> hashes = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        for (Message message : allMessages) {
            hashes.add(message.getMessageHash());
            ids.add(message.getMessageID());

            switch (message.getAction()) {
                case "SENT":
                    sent.add(message);
                    break;

                case "DISREGARDED":
                    disregarded.add(message);
                    break;

                case "STORED":
                    stored.add(message);
                    break;

                default:
                    break;
            }
        }

        sentMessages = sent.toArray(new Message[0]);
        disregardedMessages = disregarded.toArray(new Message[0]);
        storedMessages = stored.toArray(new Message[0]);
        messageHashes = hashes.toArray(new String[0]);
        messageIDs = ids.toArray(new String[0]);
    }

    // Displays the sender and recipient of all stored messages.
    public String displayStoredSendersAndRecipients() {
        StringBuilder result = new StringBuilder();

        for (Message message : storedMessages) {
            result.append("Sender: ")
                    .append(message.getSender())
                    .append("\nRecipient: ")
                    .append(message.getRecipientCell())
                    .append("\n\n");
        }

        return result.length() == 0
                ? "There are no stored messages."
                : result.toString();
    }

    // Finds the longest stored message.
    public String displayLongestStoredMessage() {
        if (storedMessages.length == 0) {
            return "There are no stored messages.";
        }

        Message longest = storedMessages[0];

        for (Message message : storedMessages) {
            if (message.getMessageText().length()
                    > longest.getMessageText().length()) {
                longest = message;
            }
        }

        return longest.getMessageText();
    }

    // Searches for a message using its ID.
    public String searchMessageByID(String messageID) {
        for (Message message : allMessages) {
            if (message.getMessageID().equals(messageID)) {
                return "Recipient: " + message.getRecipientCell()
                        + "\nMessage: " + message.getMessageText();
            }
        }

        return "Message ID not found.";
    }

    // Finds every message sent to a particular recipient.
    public String searchMessagesByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();

        for (Message message : allMessages) {
            if (message.getRecipientCell().equals(recipient)
                    && ("SENT".equals(message.getAction())
                    || "STORED".equals(message.getAction()))) {

                result.append(message.getMessageText()).append("\n");
            }
        }

        return result.length() == 0
                ? "No messages found for this recipient."
                : result.toString();
    }

    // Deletes a stored message by its hash.
    public String deleteStoredMessage(String messageHash) throws IOException {
        Message messageToDelete = null;

        for (Message message : storedMessages) {
            if (message.getMessageHash().equals(messageHash)) {
                messageToDelete = message;
                break;
            }
        }

        if (messageToDelete == null) {
            return "Message hash not found.";
        }

        allMessages.remove(messageToDelete);
        storage.storeMessage(allMessages);
        refreshArrays();

        return "Message: \"" + messageToDelete.getMessageText()
                + "\" successfully deleted.";
    }

    // Displays a full report of all stored messages.
    public String displayMessageReport() {
        StringBuilder report = new StringBuilder();

        if (storedMessages.length == 0) {
            return "There are no stored messages.";
        }

        for (Message message : storedMessages) {
            report.append("--------------------------------\n")
                    .append("Message ID: ")
                    .append(message.getMessageID()).append("\n")
                    .append("Message Hash: ")
                    .append(message.getMessageHash()).append("\n")
                    .append("Sender: ")
                    .append(message.getSender()).append("\n")
                    .append("Recipient: ")
                    .append(message.getRecipientCell()).append("\n")
                    .append("Message: ")
                    .append(message.getMessageText()).append("\n")
                    .append("Status: ")
                    .append(message.getAction()).append("\n");
        }

        return report.toString();
    }

    public Message[] getSentMessages() {
        return sentMessages.clone();
    }

    public Message[] getDisregardedMessages() {
        return disregardedMessages.clone();
    }

    public Message[] getStoredMessages() {
        return storedMessages.clone();
    }

    public String[] getMessageHashes() {
        return messageHashes.clone();
    }

    public String[] getMessageIDs() {
        return messageIDs.clone();
    }

    public List<Message> getAllMessages() {
        return new ArrayList<>(allMessages);
    }
}
