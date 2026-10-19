/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package programming1apoe;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matha
 */

public class MessageStorageTest {

    private Path tempFile;
    private MessageStorage storage;
    private List<Message> messages;

    @Before
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("quickchat-test", ".json");
        storage = new MessageStorage(tempFile);
        messages = new ArrayList<>();
    }

    @Test
    public void testStoreMessageWritesStoredMessagesToJson()
            throws IOException {

        Message message = new Message(
            1,
            "+27718693002",
            "Hello chomiii"
        );

        message.setAction("STORED");
        messages.add(message);

        storage.storeMessage(messages);

        String json = Files.readString(
            tempFile,
            StandardCharsets.UTF_8
        );

        assertTrue(json.contains("\"messageID\""));
        assertTrue(json.contains("\"recipient\""));
        assertTrue(json.contains("\"message\""));
        assertTrue(json.contains("Hello chomiii"));
        assertTrue(json.contains("\"action\": \"STORED\""));
    }

    @Test
    public void testDisregardedMessagesAreNotStored()
            throws IOException {

        Message message = new Message(
            1,
            "+27718693002",
            "This message is disregarded"
        );

        message.setAction("DISREGARDED");
        messages.add(message);

        storage.storeMessage(messages);

        String json = Files.readString(
            tempFile,
            StandardCharsets.UTF_8
        );

        assertFalse(json.contains("This message is disregarded"));
        assertEquals("[\n\n]\n", json);
    }
}
