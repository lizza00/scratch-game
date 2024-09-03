package com.scratchgame.service;

import com.scratchgame.model.Game;
import com.scratchgame.model.Probability;
import com.scratchgame.model.Probability.StandardSymbolConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatrixGenerationServiceTest {

    @Mock
    private Game config;
    @Mock
    private RandomProvider randomProvider;
    @InjectMocks
    private MatrixGenerationService matrixGenerationService;

    @Test
    void generateMatrix_ShouldReturnNonEmptyMatrix_WhenConfigurationIsValid() {
        when(config.getRows()).thenReturn(3);
        when(config.getColumns()).thenReturn(3);
        when(config.getProbabilities()).thenReturn(createProbabilities());

        String[][] matrix = matrixGenerationService.generateMatrix();

        assertNotNull(matrix);
        assertEquals(3, matrix.length);
        assertEquals(3, matrix[0].length);
        for (String[] row : matrix) {
            for (String symbol : row) {
                assertNotNull(symbol);
            }
        }
    }

    @Test
    void generateMatrix_ShouldPlaceBonusSymbol_WhenBonusSymbolIsGenerated() {
        when(config.getRows()).thenReturn(3);
        when(config.getColumns()).thenReturn(3);
        when(config.getProbabilities()).thenReturn(createProbabilities());
        when(randomProvider.nextInt(anyInt())).thenReturn(0);

        String[][] matrix = matrixGenerationService.generateMatrix();

        boolean bonusSymbolFound = false;
        for (String[] row : matrix) {
            for (String symbol : row) {
                if ("BONUS".equals(symbol)) {
                    bonusSymbolFound = true;
                    break;
                }
            }
        }

        assertTrue(bonusSymbolFound, "Bonus symbol should be placed in the matrix.");
    }

    @Test
    void generateSymbol_ShouldReturnCorrectSymbol_BasedOnProbabilities() {
        when(config.getColumns()).thenReturn(3);
        when(config.getProbabilities()).thenReturn(createProbabilities());
        when(randomProvider.nextInt(anyInt())).thenReturn(5);
        String symbol = matrixGenerationService.generateSymbol(0, 0);

        assertEquals("B", symbol);
    }

    @Test
    void addBonusSymbols_ShouldAddBonusSymbolToMatrix_WhenCalled() {
        when(config.getRows()).thenReturn(3);
        when(config.getColumns()).thenReturn(3);
        when(config.getProbabilities()).thenReturn(createProbabilities());
        when(randomProvider.nextInt(anyInt())).thenReturn(0);

        String[][] matrix = new String[3][3];
        matrixGenerationService.addBonusSymbols(matrix);

        boolean bonusSymbolFound = false;
        for (String[] row : matrix) {
            for (String symbol : row) {
                if ("BONUS".equals(symbol)) {
                    bonusSymbolFound = true;
                    break;
                }
            }
        }

        assertTrue(bonusSymbolFound, "Bonus symbol should be added to the matrix.");
    }

    private Probability createProbabilities() {
        Probability probability = new Probability();

        StandardSymbolConfig standardSymbolConfig = new StandardSymbolConfig();
        Map<String, Integer> symbols = new HashMap<>();
        symbols.put("A", 2);
        symbols.put("B", 6);
        symbols.put("C", 2);
        symbols.put("D", 2);
        standardSymbolConfig.setSymbols(symbols);

        probability.setStandardSymbols(List.of(standardSymbolConfig, standardSymbolConfig, standardSymbolConfig,
                standardSymbolConfig, standardSymbolConfig, standardSymbolConfig,
                standardSymbolConfig, standardSymbolConfig, standardSymbolConfig));
        probability.setBonusSymbols(createBonusSymbols());

        return probability;
    }

    private Probability.SymbolsConfig createBonusSymbols() {
        Probability.SymbolsConfig bonusSymbols = new Probability.SymbolsConfig();
        Map<String, Integer> symbols = new HashMap<>();
        symbols.put("BONUS", 10);
        bonusSymbols.setSymbols(symbols);
        return bonusSymbols;
    }
}
