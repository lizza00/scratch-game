package com.scratchgame.facade;

import com.scratchgame.model.GameResult;
import com.scratchgame.service.MatrixGenerationService;
import com.scratchgame.service.RewardCalculationService;
import com.scratchgame.service.ServiceFactory;
import com.scratchgame.service.WinningCombinationValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameFacadeTest {

    @Mock
    private MatrixGenerationService matrixGenerationService;
    @Mock
    private WinningCombinationValidatorService winningCombinationValidatorService;
    @Mock
    private RewardCalculationService rewardCalculationService;
    @Mock
    private ServiceFactory serviceFactory;
    private GameFacade gameFacade;

    @BeforeEach
    void setUp() {
        when(serviceFactory.createMatrixGenerationService()).thenReturn(matrixGenerationService);
        when(serviceFactory.createWinningCombinationValidatorService()).thenReturn(winningCombinationValidatorService);
        when(serviceFactory.createRewardCalculationService()).thenReturn(rewardCalculationService);
        gameFacade = new GameFacade(serviceFactory);
    }

    @Test
    void playGame_ShouldReturnGameResult_WhenWinningCombinationsAreFound() {
        String[][] matrix = {{"A", "B", "C"}, {"A", "B", "C"}, {"A", "B", "C"}};
        BigDecimal betAmount = new BigDecimal("100");
        Map<String, List<String>> winningCombinations = Collections.singletonMap("A", Collections.singletonList("same_symbol_3_times"));
        String bonusSymbol = "5x";
        BigDecimal reward = new BigDecimal("500");

        when(matrixGenerationService.generateMatrix()).thenReturn(matrix);
        when(winningCombinationValidatorService.checkWinningCombinations(matrix)).thenReturn(winningCombinations);
        when(rewardCalculationService.getBonusSymbol(matrix, winningCombinations)).thenReturn(bonusSymbol);
        when(rewardCalculationService.calculateReward(betAmount, winningCombinations, matrix, bonusSymbol)).thenReturn(reward);

        GameResult result = gameFacade.playGame(betAmount);

        assertNotNull(result);
        assertEquals(matrix, result.getMatrix());
        assertEquals(reward, result.getReward());
        assertEquals(winningCombinations, result.getAppliedWinningCombinations());
        assertEquals(bonusSymbol, result.getAppliedBonusSymbol());
    }

    @Test
    void playGame_ShouldReturnGameResult_WhenNoWinningCombinationsAreFound() {
        String[][] matrix = {{"A", "B", "C"}, {"D", "E", "F"}, {"G", "H", "I"}};
        BigDecimal betAmount = new BigDecimal("100");
        Map<String, List<String>> winningCombinations = Collections.emptyMap();
        String bonusSymbol = null;
        BigDecimal reward = BigDecimal.ZERO;

        when(matrixGenerationService.generateMatrix()).thenReturn(matrix);
        when(winningCombinationValidatorService.checkWinningCombinations(matrix)).thenReturn(winningCombinations);
        when(rewardCalculationService.getBonusSymbol(matrix, winningCombinations)).thenReturn(bonusSymbol);
        when(rewardCalculationService.calculateReward(betAmount, winningCombinations, matrix, bonusSymbol)).thenReturn(reward);

        GameResult result = gameFacade.playGame(betAmount);

        assertNotNull(result);
        assertEquals(matrix, result.getMatrix());
        assertEquals(reward, result.getReward());
        assertEquals(winningCombinations, result.getAppliedWinningCombinations());
        assertNull(result.getAppliedBonusSymbol());
    }
}
