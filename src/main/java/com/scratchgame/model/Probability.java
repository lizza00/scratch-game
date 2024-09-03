package com.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Probability {
    @JsonProperty("standard_symbols")
    private List<StandardSymbolConfig> standardSymbols;
    @JsonProperty("bonus_symbols")
    private SymbolsConfig bonusSymbols;

    @Data
    public static class SymbolsConfig {
        protected Map<String, Integer> symbols;
    }

    @Data
    public static class StandardSymbolConfig extends SymbolsConfig {
        private int column;
        private int row;
    }
}