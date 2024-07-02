package com.chatbot.springbootchatbot.logic;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * AnalyzeTextFromChatbot ist verantwortlich für die Analyse von Benutzereingaben mithilfe von OpenNLP
 */
public class AnalyzeTextFromChatbot {

    static Tokenizer tokenizer;
    static String tokenizerPath = "/opennlp-de-ud-gsd-tokens-1.0-1.9.3.bin";

    static {
        init();
    }


    private static void init() {
        try (InputStream modelIn = AnalyzeTextFromChatbot.class.getResourceAsStream(tokenizerPath)) {
            if (modelIn == null) {
                throw new FileNotFoundException("Tokenizer Datei nicht gefunden " + tokenizerPath);
            }
            TokenizerModel model = new TokenizerModel(modelIn);
            tokenizer = new TokenizerME(model);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Initialisieren des Tokenizers", e);
        }
    }

    /**
     * Analysiert die Benutzereingabe und gibt den Satz der höchst priorisierten erkannten Entität zurück.
     *
     * @param userInput Die Eingabe des Benutzers, die analysiert werden soll.
     * @return Das Ergebnis von checkForEntities()
     */
    public static String analyzeUserInput(String userInput) {

        String[] tokens = tokenizer.tokenize(userInput);
        String[] processedTokens = ProcessTokens.spellCheckTokens(tokens);
        System.out.println("Processed Tokens: " + Arrays.toString(processedTokens));
        return checkForEntities(processedTokens, userInput);
    }

    /**
     * Analysiert die Tokens und gibt den Satz der höchst priorisierten erkannten Entität zurück.
     * @param processedTokens Die gefilterten und korrigierten Tokens des UserInputs
     * @param userInput User Input für die Erweiterung für den Ausblick, um der API den userInput zu schicken
     * @return Der Satz der höchst priorisierten erkannten Entität oder die Standardnachricht, falls keine Entität erkannt wurde.
     */
    public static String checkForEntities(String[] processedTokens, String userInput) {
        List<Map.Entry<String, String>> customEntities = CustomEntitiesLoader.getCustomEntities();

        String highestPrioritySentence = null;
        // Durchlaufen der Tokens und benutzerdefinierten Entitäten
        for (String token : processedTokens) {
            for (Map.Entry<String, String> entry : customEntities) {
                String entity = entry.getKey();
                if (entity.startsWith(token)) {
                    System.out.println("Erkannte Entität: " + entity + " und Token: " + token);
                    String sentence = entry.getValue();
                    // Überprüfen und aktualisieren der höchst priorisierten Entität
                    if (highestPrioritySentence == null || customEntities.indexOf(entry) < customEntities.indexOf(getEntryByValue(customEntities, highestPrioritySentence))) {
                        highestPrioritySentence = sentence;
                    }
                    break;
                }
            }
        }

        if (highestPrioritySentence != null) {
            return highestPrioritySentence;
        } else {
            //return LlamaAnswerGenerator.getMessageFromLlama(userInput); //Ausblick: Bei keiner erkannten Entität API Aufruf
            return CustomEntitiesLoader.getStandardMessage();
        }
    }

    /**
     * Hilfsmethode zum Finden eines Eintrags in einer Liste von Einträgen anhand des Werts.
     *
     * @param entries Die Liste von Einträgen, in der nach dem Wert gesucht wird.
     * @param value   Der gesuchte Wert.
     * @return Der gefundene Eintrag oder null, falls kein Eintrag mit dem Wert gefunden wurde.
     */
    static <K, V> Map.Entry<K, V> getEntryByValue(List<Map.Entry<K, V>> entries, V value) {
        for (Map.Entry<K, V> entry : entries) {
            if (entry.getValue().equals(value)) {
                return entry;
            }
        }
        return null;
    }
}
