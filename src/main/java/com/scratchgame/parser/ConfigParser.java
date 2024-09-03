package com.scratchgame.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchgame.model.Game;

import java.io.File;
import java.io.IOException;

public class ConfigParser {

    public Game parse(String configFilePath) {
        try {
            return new ObjectMapper().readValue(new File(configFilePath), Game.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse config file.", e);
        }
    }
}
