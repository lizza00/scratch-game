package com.scratchgame.service;

import com.scratchgame.model.Game;
import com.scratchgame.model.Symbol;
import com.scratchgame.model.WinningCombination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WinningCombinationValidatorServiceTest {

    @Mock
    private Game config;
    @InjectMocks
    private WinningCombinationValidatorService validatorService;

    @Test
    void checkWinningCombinations_ShouldReturnEmptyMap_WhenNoWinningCombinations() {
        String[][] matrix = {{"A", "B", "C"}, {"D", "E", "F"}, {"G", "H", "I"}};

        when(config.getSymbols()).thenReturn(createSymbols());
        when(config.getWinCombinations()).thenReturn(createWinningCombinations());

        Map<String, List<String>> result = validatorService.checkWinningCombinations(matrix);

        assertTrue(result.isEmpty());
    }

    @Test
    void checkWinningCombinations_ShouldReturnWinningCombinations_ForLinearSymbols() {
        String[][] matrix = {{"A", "B", "C"}, {"A", "B", "C"}, {"A", "B", "C"}};

        when(config.getSymbols()).thenReturn(createSymbols());
        when(config.getWinCombinations()).thenReturn(createLinearWinningCombinations());

        Map<String, List<String>> result = validatorService.checkWinningCombinations(matrix);

        assertEquals(1, result.size());
        assertTrue(result.containsKey("A"));
        assertEquals(1, result.get("A").size());
        assertEquals("vertical_3_A", result.get("A").get(0));
    }

    private Map<String, WinningCombination> createWinningCombinations() {
        Map<String, WinningCombination> winCombinations = new HashMap<>();

        WinningCombination combination = new WinningCombination();
        combination.setWhen("same_symbols");
        combination.setCount(3);

        winCombinations.put("same_symbol_3_times", combination);

        return winCombinations;
    }

    private Map<String, WinningCombination> createLinearWinningCombinations() {
        Map<String, WinningCombination> winCombinations = new HashMap<>();

        WinningCombination combination = new WinningCombination();
        combination.setWhen("linear_symbols");
        combination.setCoveredAreas(Collections.singletonList(Arrays.asList("0:0", "1:0", "2:0")));

        winCombinations.put("vertical_3_A", combination);

        return winCombinations;
    }

    private Map<String, Symbol> createSymbols() {
        Map<String, Symbol> symbols = new HashMap<>();
        symbols.put("A", new Symbol());
        symbols.put("B", new Symbol());
        symbols.put("C", new Symbol());
        return symbols;
    }
}
