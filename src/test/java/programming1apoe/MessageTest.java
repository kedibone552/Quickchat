/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package programming1apoe;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Matha
 */

public class MessageTest {

    @Test
    public void messageIdHasTenDigits() {
        Message message = new Message(
            0, "+27718693002", "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void validRecipientIsAccepted() {
        Message message = new Message(
            0, "+27718693002", "Hello"
        );

        assertEquals(
            "Cell phone number successfully captured.",
            message.checkRecipientCell()
        );
    }

    @Test
    public void localNumberIsRejected() {
        Message message = new Message(
            1, "08575975889", "Hi Keegan, did you receive the payment?"
        );

        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            message.checkRecipientCell()
        );
    }

    @Test
    public void messageOf250CharactersIsAccepted() {
        Message message = new Message(
            0, "+27718693002", "a".repeat(250)
        );

        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    @Test
    public void tooLongMessageIsRejected() {
        Message message = new Message(
            0, "+27718693002", "a".repeat(253)
        );

        assertEquals(
            "Message exceeds 250 characters by 3; please reduce the size.",
            message.checkMessageLength()
        );
    }

    @Test
    public void hashMatchesPoeExample() {
        Message message = new Message(
            "0012345678", 0, "+27718693002",
            "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    public void sendActionWorks() {
        Message message = new Message(0, "+27718693002", "Hi Mike");

        assertEquals(
            "Message successfully sent.",
            message.SentMessage("1")
        );

        assertEquals(1, message.returnTotalMessagess());
    }

    @Test
    public void disregardActionWorks() {
        Message message = new Message(0, "+27718693002", "Hi Mike");

        assertEquals(
            "Press 0 to delete the message.",
            message.SentMessage("2")
        );

        assertEquals(0, message.returnTotalMessagess());
    }

    @Test
    public void storeActionWorks() {
        Message message = new Message(0, "+27718693002", "Hi Mike");

        assertEquals(
            "Message successfully stored.",
            message.SentMessage("3")
        );
    }
}
