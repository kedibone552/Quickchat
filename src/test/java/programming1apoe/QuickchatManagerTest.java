/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package programming1apoe;

/**
 *
 * @author Matha
 */

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class QuickchatManagerTest {

    private QuickchatManager manager;
    private MessageStorage storage;
    private Path testFile;

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @Before
    public void setUp() throws Exception {
        testFile = Files.createTempFile("quickchat-test", ".json");
        storage = new MessageStorage(testFile);
        manager = new QuickchatManager(storage);

        message1 = new Message(
                "1111111111", 0, "+27834557896",
                "Did you get the cake?",
                "Developer", "SENT", null
        );

        message2 = new Message(
                "2222222222", 1, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "Developer", "STORED", null
        );

        message3 = new Message(
                "3333333333", 2, "+27834484567",
                "Yohoooo, I am at your gate.",
                "Developer", "DISREGARDED", null
        );

        message4 = new Message(
                "0838884567", 3, "+27838884567",
                "It is dinner time !",
                "Developer", "SENT", null
        );

        message5 = new Message(
                "5555555555", 4, "+27838884567",
                "Ok, I am leaving without you.",
                "Developer", "STORED", null
        );

        manager.addMessage(message1);
        manager.addMessage(message2);
        manager.addMessage(message3);
        manager.addMessage(message4);
        manager.addMessage(message5);
    }

    @Test
    public void sentMessagesArrayIsCorrectlyPopulated() {
        assertEquals(2, manager.getSentMessages().length);

        assertEquals(
                "Did you get the cake?",
                manager.getSentMessages()[0].getMessageText()
        );

        assertEquals(
                "It is dinner time !",
                manager.getSentMessages()[1].getMessageText()
        );
    }

    @Test
    public void disregardedMessagesArrayIsCorrectlyPopulated() {
        assertEquals(1, manager.getDisregardedMessages().length);

        assertEquals(
                "Yohoooo, I am at your gate.",
                manager.getDisregardedMessages()[0].getMessageText()
        );
    }

    @Test
    public void storedMessagesArrayIsCorrectlyPopulated() {
        assertEquals(2, manager.getStoredMessages().length);
    }

    @Test
    public void messageHashAndIdArraysArePopulated() {
        assertEquals(5, manager.getMessageHashes().length);
        assertEquals(5, manager.getMessageIDs().length);
    }

    @Test
    public void longestStoredMessageIsDisplayed() {
        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                manager.displayLongestStoredMessage()
        );
    }

    @Test
    public void messageCanBeFoundUsingItsId() {
        String result = manager.searchMessageByID("0838884567");

        assertTrue(result.contains("It is dinner time !"));
        assertTrue(result.contains("+27838884567"));
    }

    @Test
    public void messagesCanBeSearchedByRecipient() {
        String result = manager.searchMessagesByRecipient("+27838884567");

        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."
        ));

        assertTrue(result.contains("It is dinner time !"));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void messageCanBeDeletedUsingItsHash() throws Exception {
        String hash = message2.getMessageHash();

        String result = manager.deleteStoredMessage(hash);

        assertTrue(result.contains("successfully deleted"));
        assertEquals(1, manager.getStoredMessages().length);
        assertFalse(Files.readString(testFile).contains(
                "Where are you? You are late!"
        ));
    }

    @Test
    public void storedMessagesCanBeReadFromJson() throws Exception {
        QuickchatManager newManager = new QuickchatManager(storage);

        newManager.loadStoredMessages();

        assertEquals(2, newManager.getStoredMessages().length);
        assertEquals(
                message2.getMessageID(),
                newManager.getStoredMessages()[0].getMessageID()
        );
    }

    @Test
    public void reportDisplaysStoredMessageDetails() {
        String report = manager.displayMessageReport();

        assertTrue(report.contains("Message ID:"));
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Where are you?"));
        assertTrue(report.contains("Ok, I am leaving without you."));
    }

    @Test
    public void searchingForUnknownIdReturnsNotFound() {
        assertEquals(
                "Message ID not found.",
                manager.searchMessageByID("9999999999")
        );
    }

    @Test
    public void searchingForUnknownRecipientReturnsNotFound() {
        assertEquals(
                "No messages found for this recipient.",
                manager.searchMessagesByRecipient("+27830000000")
        );
    }

    @Test
    public void deletingUnknownHashReturnsNotFound() throws Exception {
        assertEquals(
                "Message hash not found.",
                manager.deleteStoredMessage("UNKNOWN-HASH")
        );
    }
}
