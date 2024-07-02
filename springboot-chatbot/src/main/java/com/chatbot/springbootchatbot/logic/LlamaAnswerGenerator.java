package com.chatbot.springbootchatbot.logic;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * LlamaAnswerGenerator ist verantwortlich für den Aufruf der Llama API.
 * Diese Klasse ist ausschließlich für den Ausblick relevant
 */
public class LlamaAnswerGenerator {
    private static final String API_URL = "https://api.llama-api.com/chat/completions";
    private static final String BEARER_TOKEN = "LL-dRur7QTWyKhXtQXECcMvQ9WX8393jJhEQvKMmLXrrDSM603B9P7gEJVcOeHgM4S3"; //TODO delete this later

    /*
     * Hilfsmethode zum Finden eines Eintrags in einer Liste von Einträgen anhand des Werts.
     *
     * @param userInput Chatnachricht, die
     * @return Der gefundene Eintrag oder null, falls kein Eintrag mit dem Wert gefunden wurde.
     */
    public static String getMessageFromLlama(String userInput) {
        StringBuilder responseBuilder = new StringBuilder();
        try {
            // JSON-Daten
            String jsonData = "{\"model\":\"llama-13b-chat\",\"messages\":[{\"role\":\"user\",\"content\":\"" + userInput + "\"}]}";

            // Verbindung herstellen
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
            connection.setDoOutput(true);

            // Daten senden
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Antwortcode erhalten
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Antwort lesen
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseBuilder.append(responseLine.trim());
            }

            // Verbindung schließen
            connection.disconnect();

            // Inhalt aus der Antwort extrahieren
            String response = responseBuilder.toString();
            return extractContentFromResponse(response);

        } catch (Exception e) {
            System.err.println("Bei dem API Aufruf ist ein Fehler aufgetreten. "+e.getMessage());
        }
        return responseBuilder.toString();
    }

    private static String extractContentFromResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode choicesNode = rootNode.get("choices");
            if (choicesNode != null && choicesNode.isArray() && !choicesNode.isEmpty()) {
                JsonNode messageNode = choicesNode.get(0).get("message");
                if (messageNode != null) {
                    JsonNode contentNode = messageNode.get("content");
                    if (contentNode != null) {
                        return contentNode.asText();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Extrahieren des Inhalts aus der API-Antwort: " + e.getMessage());
        }
        return null;
    }
}
