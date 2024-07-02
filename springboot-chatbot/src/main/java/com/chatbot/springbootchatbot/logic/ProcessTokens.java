package com.chatbot.springbootchatbot.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Die Klasse ProcessTokens verarbeitet die Tokens und ersetzt sie ggf. durch die nächstgelegene Entität, falls die Levenshtein-Distanz ≤ 3 ist.
 */
public class ProcessTokens {

    /**
     * Verarbeitet die Tokens und ersetzt sie durch die nächstgelegene Entität, falls die Levenshtein-Distanz kleiner oder gleich 3 ist.
     *
     * @param tokens  Ein Array von Tokens, das verarbeitet werden soll.
     * @return Ein Array von verarbeiteten Tokens.
     */
    public static String[] spellCheckTokens(String[] tokens) {
        List<Map.Entry<String, String>> customEntities = CustomEntitiesLoader.getCustomEntities();
        List<String> processedTokens = new ArrayList<>();
        for (String token : tokens) {
            String closestEntity = null;
            int minDistance = 3;

            for (Map.Entry<String, String> entry : customEntities) {
                String entity = entry.getKey();
                int distance = calculateDistance(token, entity);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestEntity = entity;
                }
            }

            // Überprüfen, ob eine nächstgelegene Entität gefunden wurde und die Distanz kleiner oder gleich 3 ist
            if (minDistance >= 3) {
                System.out.println("Token: " + token + " | Keine Änderung erforderlich");
                processedTokens.add(token);
            } else {
                System.out.println("Token: " + token + " | Nächste Entität: " + closestEntity);
                processedTokens.add(closestEntity);
            }
        }
        //Filtern der Stopwörter aus den Tokens
        return StopwordsLoader.filterStopwords(processedTokens.toArray(new String[0]));
    }

    /**
     * Berechnet die Distanz zwischen zwei Wörtern mithilfe des Levenshtein-Algorithmus.
     *
     * @param word1 Das erste Wort.
     * @param word2 Das zweite Wort.
     * @return Die berechnete Distanz zwischen den beiden Wörtern.
     */
    static int calculateDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
}
