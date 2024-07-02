package com.chatbot.springbootchatbot.logic;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AnalyzeTextFromChatbotTest {

    @BeforeAll
    static void init(){
        CustomEntitiesLoader.initialFilePath = "src/test/resources/university-entities.txt";

    }

   @Test
    void analyzeUserInput_ReturnsStandardMessageWhenNoEntitiesFound() {
        String userInput = "Test input";
        String result = AnalyzeTextFromChatbot.analyzeUserInput(userInput);

        assertEquals(CustomEntitiesLoader.getStandardMessage(), result);
    }

    @Test
    void analyzeUserInput_ReturnsHighestPriorityEntity() {
        String userInput = "Vorlesung";
        String sentence = "Besuchen Sie interessante Vorlesungen zu verschiedenen Fachgebieten.";

        String result = AnalyzeTextFromChatbot.analyzeUserInput(userInput);

        assertEquals(sentence, result);
    }
    @Test
    void getEntryByValue_ReturnsNullWhenListIsEmpty() {
        List<Map.Entry<String, String>> entries = List.of();

        Map.Entry<String, String> result = AnalyzeTextFromChatbot.getEntryByValue(entries, "value");

        Assertions.assertNull(result);
    }

    @Test
    void getEntryByValue_ReturnsNullWhenValueNotFound() {
        List<Map.Entry<String, String>> entries = List.of(
                new AbstractMap.SimpleEntry<>("key1", "value1"),
                new AbstractMap.SimpleEntry<>("key2", "value2")
        );

        Map.Entry<String, String> result = AnalyzeTextFromChatbot.getEntryByValue(entries, "value3");

        Assertions.assertNull(result);
    }

    @Test
    void getEntryByValue_ReturnsEntryWhenValueFound() {
        List<Map.Entry<String, String>> entries = List.of(
                new AbstractMap.SimpleEntry<>("key1", "value1"),
                new AbstractMap.SimpleEntry<>("key2", "value2")
        );

        Map.Entry<String, String> result = AnalyzeTextFromChatbot.getEntryByValue(entries, "value1");

        assert result != null;
        assertEquals("key1", result.getKey());
        assertEquals("value1", result.getValue());
    }
}
