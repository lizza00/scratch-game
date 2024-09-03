package com.scratchgame.service;

import com.scratchgame.model.Game;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MatrixGenerationService {
    private final Game config;
    private final RandomProvider randomProvider;

    public String[][] generateMatrix() {
        String[][] matrix = new String[config.getRows()][config.getColumns()];

        for (int row = 0; row < config.getRows(); row++) {
            for (int col = 0; col < config.getColumns(); col++) {
                matrix[row][col] = generateSymbol(row, col);
            }
        }
        addBonusSymbols(matrix);

        return matrix;
    }

    public String generateSymbol(int row, int col) {
        Map<String, Integer> symbolProbabilities = config.getProbabilities().getStandardSymbols().get(row * config.getColumns() + col).getSymbols();

        int totalProbability = symbolProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        int rand = randomProvider.nextInt(totalProbability);

        for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
            rand -= entry.getValue();
            if (rand < 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void addBonusSymbols(String[][] matrix) {
        Map<String, Integer> bonusProbabilities = config.getProbabilities().getBonusSymbols().getSymbols();

        int totalProbability = bonusProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        int rand = randomProvider.nextInt(totalProbability);

        for (Map.Entry<String, Integer> entry : bonusProbabilities.entrySet()) {
            rand -= entry.getValue();
            if (rand < 0) {
                int row = randomProvider.nextInt(config.getRows());
                int col = randomProvider.nextInt(config.getColumns());
                matrix[row][col] = entry.getKey();
                break;
            }
        }
    }
}
