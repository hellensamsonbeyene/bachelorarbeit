package com.chatbot.springbootchatbot.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StopwordsLoaderTest {

    @Test
    void cleanTokens_RemovesStopwords() {
        String[] tokens = {"Das", "ist", "ein", "Test", "mit", "Stoppwörtern"};
        String[] expected = {"Test", "Stoppwörtern"};
        String[] cleanedTokens = StopwordsLoader.filterStopwords(tokens);
        assertArrayEquals(expected, cleanedTokens);
    }

    @Test
    void checkForStopwords_ThrowsExceptionWhenStopwordsFound() {
        // Arrange
        List<Map.Entry<String, String>> entitiesWithStopwords = List.of(
                Map.entry("ich", "Some value"),
                Map.entry("du", "Some value"),
                Map.entry("es", "Some value")
        );

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> StopwordsLoader.checkForStopwords(entitiesWithStopwords));

        assertTrue(exception.getMessage().contains("bitte ersetze die folgenden Wörter: ich, du, es"));
    }

    @Test
    void checkForStopwords_NoExceptionWhenNoStopwordsFound() {
        // Arrange
        List<Map.Entry<String, String>> entitiesWithoutStopwords = List.of(
                Map.entry("Apfel", "Some value"),
                Map.entry("Banane", "Some value"),
                Map.entry("Orange", "Some value")
        );

        // Act & Assert
        assertDoesNotThrow(() -> StopwordsLoader.checkForStopwords(entitiesWithoutStopwords));
    }
    @Test
    void checkForStopwords_FoundStopwords_ThrowsIOException() {
        List<Map.Entry<String, String>> customEntitiesList = new ArrayList<>();
        customEntitiesList.add(Map.entry("an", "sentence1"));
        customEntitiesList.add(Map.entry("aber", "sentence2"));

        IOException exception = assertThrows(IOException.class, () -> StopwordsLoader.checkForStopwords(customEntitiesList));

        assertTrue(exception.getMessage().contains("bitte ersetze die folgenden Wörter: an, aber"));
    }

    @Test
    void checkForStopwords_NoStopwords_DoesNotThrowException() {
        List<Map.Entry<String, String>> customEntitiesList = new ArrayList<>();
        customEntitiesList.add(Map.entry("entity1", "sentence1"));
        customEntitiesList.add(Map.entry("entity2", "sentence2"));

        assertDoesNotThrow(() -> StopwordsLoader.checkForStopwords(customEntitiesList));
    }



    @Test
    void cleanTokens_NoStopwords_ReturnsOriginalTokens() {
        String[] tokens = {"no", "stopwords", "here"};
        String[] expected = {"no", "stopwords", "here"};
        String[] cleanedTokens = StopwordsLoader.filterStopwords(tokens);
        assertArrayEquals(expected, cleanedTokens);
    }
}
