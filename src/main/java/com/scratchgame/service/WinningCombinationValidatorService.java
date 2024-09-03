package com.scratchgame.service;

import com.scratchgame.model.Game;
import com.scratchgame.model.WinningCombination;

import java.util.*;

public class WinningCombinationValidatorService {
    private static final String COLON = ":";
    private static final String LINEAR_SYMBOLS = "linear_symbols";
    private static final String SAME_SYMBOLS = "same_symbols";
    private final Game config;

    public WinningCombinationValidatorService(Game config) {
        this.config = config;
    }

    public Map<String, List<String>> checkWinningCombinations(String[][] matrix) {
        Map<String, List<String>> winningSymbols = new HashMap<>();

        for (String symbol : config.getSymbols().keySet()) {
            int count = countSymbolOccurrences(matrix, symbol);
            addSameSymbolCombinations(winningSymbols, symbol, count);
        }

        addLinearCombinations(winningSymbols, matrix);

        return winningSymbols.isEmpty() ? Collections.emptyMap() : winningSymbols;
    }

    private int countSymbolOccurrences(String[][] matrix, String symbol) {
        int count = 0;
        for (int row = 0; row < config.getRows(); row++) {
            for (int col = 0; col < config.getColumns(); col++) {
                if (matrix[row][col].equals(symbol)) {
                    count++;
                }
            }
        }
        return count;
    }

    private void addSameSymbolCombinations(Map<String, List<String>> winningSymbols, String symbol, int count) {
        for (Map.Entry<String, WinningCombination> entry : config.getWinCombinations().entrySet()) {
            WinningCombination combo = entry.getValue();
            if (SAME_SYMBOLS.equals(combo.getWhen()) && count >= combo.getCount()) {
                winningSymbols.computeIfAbsent(symbol, k -> new ArrayList<>()).add(entry.getKey());
            }
        }
    }

    private void addLinearCombinations(Map<String, List<String>> winningSymbols, String[][] matrix) {
        for (Map.Entry<String, WinningCombination> entry : config.getWinCombinations().entrySet()) {
            WinningCombination combo = entry.getValue();
            if (LINEAR_SYMBOLS.equals(combo.getWhen())) {
                for (List<String> area : combo.getCoveredAreas()) {
                    if (isMatchingArea(matrix, area)) {
                        String symbol = matrix[Integer.parseInt(area.get(0).split(COLON)[0])]
                                [Integer.parseInt(area.get(0).split(COLON)[1])];
                        winningSymbols.computeIfAbsent(symbol, k -> new ArrayList<>()).add(entry.getKey());
                    }
                }
            }
        }
    }

    private boolean isMatchingArea(String[][] matrix, List<String> area) {
        String firstSymbol = null;
        for (String pos : area) {
            int row = Integer.parseInt(pos.split(COLON)[0]);
            int col = Integer.parseInt(pos.split(COLON)[1]);
            String symbol = matrix[row][col];

            if (firstSymbol == null) {
                firstSymbol = symbol;
            } else if (!firstSymbol.equals(symbol)) {
                return false;
            }
        }
        return true;
    }
}
