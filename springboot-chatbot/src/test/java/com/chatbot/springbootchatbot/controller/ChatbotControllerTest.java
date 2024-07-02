package com.chatbot.springbootchatbot.controller;

import com.chatbot.springbootchatbot.logic.CustomEntitiesLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Files;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(PowerMockRunner.class) // Make sure you're using the correct import for PowerMockRunner
@PrepareForTest({CustomEntitiesLoader.class})
@ExtendWith(SpringExtension.class)
class ChatbotControllerTest {
    @BeforeAll
    static void init(){
        CustomEntitiesLoader.initialFilePath = "src/test/resources/custom-entities.txt";
        CustomEntitiesLoader.testFilePath = "src/test/resources/test-entities.txt";
        CustomEntitiesLoader.filePath = "src/test/resources/custom-entities.txt";

    }
    @Autowired
    private MockMvc mockMvc;

    @Test
    void analyzeText_ReturnsExpectedResult() throws Exception {
        CustomEntitiesLoader customEntitiesLoader = new CustomEntitiesLoader();
        customEntitiesLoader.init();
        String userInput = "Test User Input";
        mockMvc.perform(MockMvcRequestBuilders.post("/analyze")
                        .contentType("application/json")
                        .content("{\"userInput\": \"" + userInput + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Entschuldigung, das habe ich nicht verstanden."));
    }
    @Test
    void resetChatbot_SuccessfullyResetsChatbot() throws Exception {
        File file = new File("src/main/resources/example-entities.txt");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "file.txt",
                "text/plain",
                Files.readAllBytes(file.toPath())
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadFile")
                        .file(multipartFile))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("[vorlesung=Besuchen Sie interessante Vorlesungen zu verschiedenen Fachgebieten., professoren=Unsere erfahrenen Professoren stehen Ihnen für Fragen und Diskussionen zur Verfügung., professorin=Test1., bibliothek=Nutzen Sie unsere gut ausgestattete Bibliothek für Ihr Studium., studium=Erforschen Sie unsere vielfältigen Studiengänge und finden Sie den passenden für sich., studentenwohnheim=Informationen zu Unterkünften für Studierende., campus=Erkunden Sie unseren schönen Campus und seine Einrichtungen.]", CustomEntitiesLoader.customEntities.toString());
        assertEquals("Entschuldigung, das habe ich nicht verstanden.", CustomEntitiesLoader.standardMessage);

        mockMvc.perform(MockMvcRequestBuilders.post("/resetChatbot")
                        .contentType("application/json")
                        .content(""))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("Entschuldigung, das habe ich nicht verstanden.", CustomEntitiesLoader.standardMessage);
        assertEquals("[vorlesung=Besuchen Sie interessante Vorlesungen zu verschiedenen Fachgebieten., professoren=Unsere erfahrenen Professoren stehen Ihnen für Fragen und Diskussionen zur Verfügung., professorin=Test1., bibliothek=Nutzen Sie unsere gut ausgestattete Bibliothek für Ihr Studium., studium=Erforschen Sie unsere vielfältigen Studiengänge und finden Sie den passenden für sich., studentenwohnheim=Informationen zu Unterkünften für Studierende., campus=Erkunden Sie unseren schönen Campus und seine Einrichtungen.]", CustomEntitiesLoader.customEntities.toString());

        System.out.println(CustomEntitiesLoader.customEntities);
    }
}
