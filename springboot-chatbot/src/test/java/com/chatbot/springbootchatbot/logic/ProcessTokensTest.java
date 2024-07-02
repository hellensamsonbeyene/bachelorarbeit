package com.chatbot.springbootchatbot.logic;

import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessTokensTest {

    @Test
    void processTokens_NoCustomEntities_ReturnsOriginalTokens() {
        String[] tokens = {"word1", "word2", "word3"};
        ProcessTokens.customEntities = List.of();
        String[] expected = {"word1", "word2", "word3"};
        assertArrayEquals(expected, ProcessTokens.spellCheckTokens(tokens));
    }

    @Test
    void processTokens_WithCustomEntities_ReplacesTokensWithClosestEntity() {
        String[] tokens = {"cat", "dog", "rabit"};
        ProcessTokens.customEntities = List.of(
                new AbstractMap.SimpleEntry<>("dog", "animal"),
                new AbstractMap.SimpleEntry<>("rabbit", "animal")
        );
        String[] expected = {"cat", "dog", "rabbit"};
        assertArrayEquals(expected, ProcessTokens.spellCheckTokens(tokens));
    }

    @Test
    void calculateDistance_ReturnsCorrectDistance() {
        int distance1 = ProcessTokens.calculateDistance("kitten", "sitting");
        int distance2 = ProcessTokens.calculateDistance("kitten", "kitchen");
        int distance3 = ProcessTokens.calculateDistance("word", "sword");
        assertEquals(3, distance1);
        assertEquals(2, distance2);
        assertEquals(1, distance3);
    }
}
