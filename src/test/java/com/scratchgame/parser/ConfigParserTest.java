package com.scratchgame.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchgame.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigParserTest {

    @TempDir
    Path tempDir;

    private final ConfigParser configParser = new ConfigParser();

    @Test
    void parse_ShouldReturnGameObject_WhenValidConfigFileIsProvided() throws IOException {
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        Game expectedGame = new Game();
        File tempFile = createTempConfigFile("valid-config.json", "{}");

        when(objectMapper.readValue(tempFile, Game.class)).thenReturn(expectedGame);

        Game result = configParser.parse(tempFile.getAbsolutePath());

        assertNotNull(result);
        assertEquals(expectedGame, result);
    }

    @Test
    void parse_ShouldThrowRuntimeException_WhenConfigFileCannotBeParsed() throws IOException {
        File tempFile = createTempConfigFile("invalid-config.json", "{invalid-json}");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            configParser.parse(tempFile.getAbsolutePath());
        });

        assertTrue(exception.getMessage().contains("Failed to parse config file."));
    }

    private File createTempConfigFile(String fileName, String content) throws IOException {
        Path tempFilePath = Files.createFile(tempDir.resolve(fileName));
        Files.write(tempFilePath, content.getBytes());
        return tempFilePath.toFile();
    }
}
