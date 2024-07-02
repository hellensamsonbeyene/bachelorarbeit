package com.chatbot.springbootchatbot.controller;

import com.chatbot.springbootchatbot.logic.AnalyzeTextFromChatbot;
import com.chatbot.springbootchatbot.logic.CustomEntitiesLoader;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.chatbot.springbootchatbot.logic.CustomEntitiesLoader.*;

/**
 * ChatbotController behandelt eingehende HTTP-Anfragen im Zusammenhang mit der Chatbot-Funktionalität.
 */
@RestController
public class ChatbotController {

    /**
     * Analysiert die Benutzereingabe und gibt eine Antwort zurück.
     *
     * @param userInput Die Benutzereingabemeldung, die im HTTP-body empfangen wird.
     * @return Die durch die Analyse der Benutzereingabe generierte Antwort.
     */
    @PostMapping("/analyze")
    public String analyzeText(@RequestBody UserInputMessage userInput) {
        String userInputMessage = userInput.userInput();

        return AnalyzeTextFromChatbot.analyzeUserInput(userInputMessage);
    }

    /**
     * Lädt eine Datei mit benutzerdefinierten Entitäten hoch und aktualisiert die Chatbot-Konfiguration.
     *
     * @param file Die Multipart-Datei mit benutzerdefinierten Entitäten und Standardnachricht.
     * @return ResponseEntity
     */
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return CustomEntitiesLoader.uploadFile(file);
    }

    /**
     * Setzt den aktuellen Chatbot zurück auf den BeispielChatbot
     **/
    @PostMapping("/resetChatbot")
    public void resetChatbot() throws IOException {
         CustomEntitiesLoader.customEntities = loadCustomEntities(CustomEntitiesLoader.initialFilePath);
        CustomEntitiesLoader.standardMessage = loadStandardMessage(CustomEntitiesLoader.initialFilePath);
    }

    public record UserInputMessage(String userInput) {
        @Override
        @JsonProperty("userInput")
        public String userInput() {
            return userInput;
        }
    }
}
