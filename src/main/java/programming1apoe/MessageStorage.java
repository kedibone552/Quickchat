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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MessageStorage {

    private final Path filePath;

    public MessageStorage() {
        this(Path.of("messages.json"));
    }

    public MessageStorage(Path filePath) {
        this.filePath = filePath;
    }

    // Saves stored messages in JSON format.
    // JSON format reference: https://www.json.org/json-en.html
    public void storeMessage(List<Message> messages) throws IOException {
        StringBuilder json = new StringBuilder("[\n");
        boolean first = true;

        for (Message message : messages) {
            if (!"STORED".equals(message.getAction())) {
                continue;
            }

            if (!first) {
                json.append(",\n");
            }

            json.append("  {\n")
                .append("    \"messageID\": \"")
                .append(escape(message.getMessageID())).append("\",\n")
                .append("    \"messageNumber\": ")
                .append(message.getMessageNumber()).append(",\n")
                .append("    \"messageHash\": \"")
                .append(escape(message.getMessageHash())).append("\",\n")
                .append("    \"recipient\": \"")
                .append(escape(message.getRecipientCell())).append("\",\n")
                .append("    \"message\": \"")
                .append(escape(message.getMessageText())).append("\",\n")
                .append("    \"action\": \"STORED\"\n")
                .append("  }");

            first = false;
        }

        json.append("\n]\n");

        Files.writeString(
            filePath,
            json.toString(),
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE
        );
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
