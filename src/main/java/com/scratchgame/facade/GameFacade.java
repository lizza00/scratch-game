package com.scratchgame.facade;

import com.scratchgame.model.GameResult;
import com.scratchgame.service.MatrixGenerationService;
import com.scratchgame.service.RewardCalculationService;
import com.scratchgame.service.ServiceFactory;
import com.scratchgame.service.WinningCombinationValidatorService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class GameFacade {
    private final MatrixGenerationService matrixGenerationService;
    private final WinningCombinationValidatorService winningCombinationValidatorService;
    private final RewardCalculationService rewardCalculationService;

    public GameFacade(ServiceFactory serviceFactory) {
        this.matrixGenerationService = serviceFactory.createMatrixGenerationService();
        this.winningCombinationValidatorService = serviceFactory.createWinningCombinationValidatorService();
        this.rewardCalculationService = serviceFactory.createRewardCalculationService();
    }

    public GameResult playGame(BigDecimal betAmount) {
        String[][] matrix = matrixGenerationService.generateMatrix();
        Map<String, List<String>> winningCombinations = winningCombinationValidatorService.checkWinningCombinations(matrix);
        String appliedBonusSymbol = rewardCalculationService.getBonusSymbol(matrix, winningCombinations);
        BigDecimal reward = rewardCalculationService.calculateReward(betAmount, winningCombinations, matrix, appliedBonusSymbol);

        return new GameResult(matrix, reward, winningCombinations, appliedBonusSymbol);
    }
}
