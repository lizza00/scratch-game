package com.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Game {
    private int rows;
    private int columns;
    private Map<String, Symbol> symbols;
    private Probability probabilities;
    @JsonProperty("win_combinations")
    private Map<String, WinningCombination> winCombinations;
}
