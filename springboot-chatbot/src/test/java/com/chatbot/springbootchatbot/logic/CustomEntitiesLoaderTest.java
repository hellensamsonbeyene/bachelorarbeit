package com.chatbot.springbootchatbot.logic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CustomEntitiesLoaderTest {

    @BeforeAll
    static void init(){
        CustomEntitiesLoader.initialFilePath = "src/test/resources/custom-entities.txt";
        CustomEntitiesLoader.testFilePath = "src/test/resources/test-entities.txt";
        CustomEntitiesLoader.filePath = "src/test/resources/custom-entities.txt";

    }
    @Test
    void init_LoadsCustomEntitiesAndStandardMessage_Success() throws IOException {
        ResponseEntity<String> responseEntity = CustomEntitiesLoader.init();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(CustomEntitiesLoader.getCustomEntities());
        assertNotNull(CustomEntitiesLoader.getStandardMessage());
    }

    @Test
    void init_CustomEntitiesFileNotFound_ReturnsInternalServerError() throws IOException {
        CustomEntitiesLoader.initialFilePath = "non_existing_file.txt";

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, CustomEntitiesLoader.init().getStatusCode());
    }

    @Test
    void loadCustomEntities_LoadsCustomEntitiesFromFile_Success() throws IOException {
        List<Map.Entry<String, String>> customEntities = CustomEntitiesLoader.loadCustomEntities(new File(CustomEntitiesLoader.filePath).getAbsolutePath());

        assertNotNull(customEntities);
        assertFalse(customEntities.isEmpty());
    }

    @Test
    void loadStandardMessage_LoadsStandardMessageFromFile_Success() {
        String standardMessage = CustomEntitiesLoader.loadStandardMessage(new File(CustomEntitiesLoader.filePath).getAbsolutePath());

        assertNotNull(standardMessage);
        assertFalse(standardMessage.isEmpty());
    }
    @Test
    void loadCustomEntities_LoadsEntitiesFromFile() throws IOException {
        CustomEntitiesLoader.filePath = "src/test/resources/university-entities.txt";
        List<Map.Entry<String, String>> customEntities = CustomEntitiesLoader.loadCustomEntities(new File(CustomEntitiesLoader.filePath).getAbsolutePath());

        assertNotNull(customEntities);
        assertEquals(7, customEntities.size());

        assertEquals(new AbstractMap.SimpleEntry<>("vorlesung", "Besuchen Sie interessante Vorlesungen zu verschiedenen Fachgebieten."), customEntities.get(0));
        assertEquals(new AbstractMap.SimpleEntry<>("professoren", "Unsere erfahrenen Professoren stehen Ihnen für Fragen und Diskussionen zur Verfügung."), customEntities.get(1));
    }

    @Test
    void uploadFile_WithValidFile_UpdatesCustomEntitiesAndStandardMessage() {
        CustomEntitiesLoader.customEntities = null;
        CustomEntitiesLoader.standardMessage = null;
        MockMultipartFile file = new MockMultipartFile("file", "custom-entities.txt", "text/plain", "#Standardnachricht\n Sentence\n\n#Entitäten und Antworten\n entity:sentence.".getBytes());

        ResponseEntity<String> responseEntity = CustomEntitiesLoader.uploadFile(file);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(CustomEntitiesLoader.getCustomEntities());
        assertNotNull(CustomEntitiesLoader.getStandardMessage());
    }

    @Test
    void uploadFile_WithEmptyFile_ReturnsInternalServerError() {
        MockMultipartFile file = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);

        ResponseEntity<String> responseEntity = CustomEntitiesLoader.uploadFile(file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void uploadFile_WithInvalidFile_ReturnsInternalServerError() {
        MockMultipartFile file = new MockMultipartFile("file", "invalid.txt", "text/plain", "invalid content".getBytes());

        ResponseEntity<String> responseEntity = CustomEntitiesLoader.uploadFile(file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void loadStandardMessage_FileIsEmpty_ReturnsNull() {
        String filePath = "src/test/resources/empty-file.txt"; // Beispiel: Leere Textdatei
        String loadedMessage = CustomEntitiesLoader.loadStandardMessage(new File(filePath).getAbsolutePath());
        assertNull(loadedMessage);
    }

    @Test
    void loadCustomEntities_FileIsEmpty_ReturnsNull() throws IOException {
        String filePath = "src/test/resources/empty-file.txt"; // Beispiel: Leere Textdatei
        assertNull(CustomEntitiesLoader.loadCustomEntities(new File(filePath).getAbsolutePath()));
    }

    @Test
    void uploadFile_IOException_ReturnsInternalServerError() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", new byte[0]);

        ResponseEntity<String> responseEntity = CustomEntitiesLoader.uploadFile(file);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void init_InitializesCustomEntitiesAndOutputs() throws IOException {
        ResponseEntity<String> response = CustomEntitiesLoader.init();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
