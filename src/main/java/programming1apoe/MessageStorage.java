/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programming1apoe;

import java.util.List;

/**
 *
 * @author Matha
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class MessageStorage {

    private final Path filePath;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public MessageStorage() {
        this(Path.of("messages.json"));
    }

    public MessageStorage(Path filePath) {
        this.filePath = filePath;
    }

    // Saves only messages with the STORED action.
    public void storeMessage(List<Message> messages) throws IOException {
        List<Message> stored = new ArrayList<>();

        for (Message message : messages) {
            if ("STORED".equals(message.getAction())) {
                stored.add(message);
            }
        }

        Files.writeString(
                filePath,
                gson.toJson(stored),
                StandardCharsets.UTF_8
        );
    }

    // Reads the JSON file into an array list.
    public List<Message> readMessages() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        String json = Files.readString(filePath, StandardCharsets.UTF_8);

        if (json.trim().isEmpty()) {
            return new ArrayList<>();
        }

        Type messageListType = new TypeToken<ArrayList<Message>>() {
        }.getType();

        List<Message> messages = gson.fromJson(json, messageListType);

        return messages == null ? new ArrayList<>() : messages;
    }

    // Deletes a message using its message hash.
    public boolean deleteMessage(String messageHash) throws IOException {
        List<Message> messages = readMessages();

        boolean removed = messages.removeIf(message ->
                message.getMessageHash().equals(messageHash));

        if (removed) {
            storeMessage(messages);
        }

        return removed;
    }

    public Path getFilePath() {
        return filePath;
    }
}
