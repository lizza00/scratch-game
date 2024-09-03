package com.scratchgame.service;

import com.scratchgame.model.Game;
import com.scratchgame.model.Symbol;
import com.scratchgame.model.WinningCombination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardCalculationServiceTest {

    @Mock
    private Game config;
    @InjectMocks
    private RewardCalculationService rewardCalculationService;

    @Test
    void calculateReward_ShouldReturnZero_WhenNoWinningCombinations() {
        BigDecimal betAmount = new BigDecimal("100");
        Map<String, List<String>> winningCombinations = null;

        BigDecimal reward = rewardCalculationService.calculateReward(betAmount, winningCombinations, new String[3][3], null);

        assertEquals(BigDecimal.ZERO, reward);
    }

    @Test
    void calculateReward_ShouldCalculateCorrectReward_WithSameSymbolCombination() {
        BigDecimal betAmount = new BigDecimal("100");
        Map<String, List<String>> winningCombinations = new HashMap<>();
        winningCombinations.put("A", Collections.singletonList("same_symbol_3_times"));

        when(config.getSymbols()).thenReturn(createSymbols());
        when(config.getWinCombinations()).thenReturn(createWinningCombinations());

        BigDecimal reward = rewardCalculationService.calculateReward(betAmount, winningCombinations, new String[3][3], null);

        assertEquals(500, reward.doubleValue());
    }

    @Test
    void calculateReward_ShouldApplyBonusMultiplier_WhenBonusSymbolIsPresent() {
        BigDecimal betAmount = new BigDecimal("100");
        Map<String, List<String>> winningCombinations = new HashMap<>();
        winningCombinations.put("A", Collections.singletonList("same_symbol_3_times"));

        when(config.getSymbols()).thenReturn(createSymbolsWithBonus());
        when(config.getWinCombinations()).thenReturn(createWinningCombinations());

        BigDecimal reward = rewardCalculationService.calculateReward(betAmount, winningCombinations, new String[3][3], "5x");

        assertEquals(2500, reward.doubleValue());
    }

    @Test
    void calculateReward_ShouldApplyExtraBonus_WhenBonusSymbolIsPresent() {
        BigDecimal betAmount = new BigDecimal("100");
        Map<String, List<String>> winningCombinations = new HashMap<>();
        winningCombinations.put("A", Collections.singletonList("same_symbol_3_times"));

        when(config.getSymbols()).thenReturn(createSymbolsWithExtraBonus());
        when(config.getWinCombinations()).thenReturn(createWinningCombinations());

        BigDecimal reward = rewardCalculationService.calculateReward(betAmount, winningCombinations, new String[3][3], "+1000");

        assertEquals(1500, reward.doubleValue());
    }

    @Test
    void getBonusSymbol_ShouldReturnNull_WhenMissBonusIsFound() {
        String[][] matrix = {{"A", "B", "MISS"}, {"C", "D", "E"}, {"F", "G", "H"}};
        Map<String, List<String>> winningCombinations = Collections.singletonMap("A", Collections.singletonList("same_symbol_3_times"));

        String bonusSymbol = rewardCalculationService.getBonusSymbol(matrix, winningCombinations);

        assertNull(bonusSymbol);
    }

    @Test
    void getBonusSymbol_ShouldReturnNull_WhenNoWinningCombinations() {
        String[][] matrix = {{"A", "B", "5x"}, {"C", "D", "E"}, {"F", "G", "H"}};
        Map<String, List<String>> winningCombinations = null;

        String bonusSymbol = rewardCalculationService.getBonusSymbol(matrix, winningCombinations);

        assertNull(bonusSymbol);
    }

    private Map<String, Symbol> createSymbols() {
        Map<String, Symbol> symbols = new HashMap<>();

        Symbol symbolA = new Symbol();
        symbolA.setRewardMultiplier(5);
        symbols.put("A", symbolA);

        return symbols;
    }

    private Map<String, Symbol> createSymbolsWithBonus() {
        Map<String, Symbol> symbols = createSymbols();

        Symbol symbol5x = new Symbol();
        symbol5x.setType("bonus");
        symbol5x.setImpact("multiply_reward");
        symbol5x.setRewardMultiplier(5);
        symbols.put("5x", symbol5x);

        Symbol symbolMiss = new Symbol();
        symbolMiss.setType("bonus");
        symbolMiss.setImpact("none");
        symbols.put("MISS", symbolMiss);

        return symbols;
    }

    private Map<String, Symbol> createSymbolsWithExtraBonus() {
        Map<String, Symbol> symbols = createSymbols();

        Symbol symbol1000 = new Symbol();
        symbol1000.setType("bonus");
        symbol1000.setImpact("extra_bonus");
        symbol1000.setExtra(1000);
        symbols.put("+1000", symbol1000);

        return symbols;
    }

    private Map<String, WinningCombination> createWinningCombinations() {
        Map<String, WinningCombination> winCombinations = new HashMap<>();

        WinningCombination combination = new WinningCombination();
        combination.setWhen("same_symbols");
        combination.setCount(3);
        combination.setRewardMultiplier(1);

        winCombinations.put("same_symbol_3_times", combination);

        return winCombinations;
    }
}
