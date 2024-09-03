package com.scratchgame.service;

import com.scratchgame.model.Game;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class ServiceFactory {

    private final Game config;

    public MatrixGenerationService createMatrixGenerationService() {
        return new MatrixGenerationService(config, new RandomProvider());
    }

    public WinningCombinationValidatorService createWinningCombinationValidatorService() {
        return new WinningCombinationValidatorService(config);
    }

    public RewardCalculationService createRewardCalculationService() {
        return new RewardCalculationService(config);
    }
}
