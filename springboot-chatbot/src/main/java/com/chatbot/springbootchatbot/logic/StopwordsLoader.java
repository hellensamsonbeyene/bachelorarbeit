package com.chatbot.springbootchatbot.logic;

import java.io.*;
import java.util.*;

public class StopwordsLoader {

    private static final Set<String> stopwords;

    public static String filePath = "/stopwords.txt";

    static {
        // Stopwörter einmalig beim Starten der Klasse laden
        stopwords = loadStopwords();
    }

    static Set<String> loadStopwords() {
        Set<String> stopwords = new HashSet<>();
        try (InputStream inputStream = StopwordsLoader.class.getResourceAsStream(filePath)) {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    stopwords.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Stoppwörter: " + e.getMessage());
        }
        return stopwords;
    }

    /**
     * Bereinigt die übergebenen Tokens von Stopwörtern und gibt das bereinigte Array zurück.
     *
     * @param tokens Ein Array von Tokens, das bereinigt werden soll.
     * @return Ein bereinigtes Array von Tokens ohne Stopwörter.
     */
    public static String[] filterStopwords(String[] tokens) {
        List<String> cleanedTokens = new ArrayList<>();

        for (String token : tokens) {
            // Füge das Token zur bereinigten Liste hinzu, falls es kein Stopwort ist
            if (!stopwords.contains(token.toLowerCase())) {
                cleanedTokens.add(token);
            }
        }
        return cleanedTokens.toArray(new String[0]);
    }

    /**
     * Überprüft, ob in den benutzerdefinierten Entitäten Stoppwörter als Schlüssel existieren.
     *
     * @param customEntitiesList Die Liste der benutzerdefinierten Entitäten.
     * @throws IOException Falls Stoppwörter gefunden werden.
     */
    public static void checkForStopwords(List<Map.Entry<String, String>> customEntitiesList) throws IOException {
        List<String> foundStopwords = new ArrayList<>();

        // Durchlaufen der benutzerdefinierten Entitäten
        for (Map.Entry<String, String> customEntity : customEntitiesList) {
            String entityKey = customEntity.getKey();
            // Überprüfen, ob die benutzerdefinierte Entität ein Stoppwort ist
            if (stopwords.contains(entityKey.toLowerCase())) {
                foundStopwords.add(entityKey);
            }
        }

        // Wenn Stoppwörter gefunden wurden, eine IOException werfen
        if (!foundStopwords.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Die Textdatei enthält Wörter, die keine Entität sein dürfen, bitte ersetze die folgenden Wörter: ");
            for (String stopword : foundStopwords) {
                errorMessage.append(stopword).append(", ");
            }
            errorMessage.deleteCharAt(errorMessage.length() - 1); // Remove last comma
            errorMessage.deleteCharAt(errorMessage.length() - 1); // Remove space
            CustomEntitiesLoader.deleteFile(new File(CustomEntitiesLoader.testFilePath).getAbsolutePath()); // delete testfile
            throw new IOException(errorMessage.toString());
        }
    }
}
