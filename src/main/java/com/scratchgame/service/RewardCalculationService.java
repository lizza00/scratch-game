package com.scratchgame.service;

import com.scratchgame.model.Game;
import com.scratchgame.model.Symbol;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class RewardCalculationService {
    private static final String MISS_BONUS_SYMBOL = "MISS";
    private static final String BONUS_TYPE = "bonus";
    private static final String MULTIPLY_REWARD = "multiply_reward";
    private static final String EXTRA_BONUS = "extra_bonus";
    private final Game config;

    public BigDecimal calculateReward(BigDecimal betAmount, Map<String, List<String>> winningCombinations, String[][] matrix, String bonusSymbol) {
        BigDecimal totalReward = BigDecimal.ZERO;
        if (winningCombinations == null) {
            return totalReward;
        }
        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            BigDecimal symbolRewardMultiplier = BigDecimal.valueOf(config.getSymbols().get(symbol).getRewardMultiplier());
            BigDecimal symbolReward = betAmount.multiply(symbolRewardMultiplier);

            for (String combination : entry.getValue()) {
                BigDecimal combinationMultiplier = BigDecimal.valueOf(config.getWinCombinations().get(combination).getRewardMultiplier());
                symbolReward = symbolReward.multiply(combinationMultiplier);
            }

            totalReward = totalReward.add(symbolReward);
        }
        totalReward = applyBonusSymbol(totalReward, bonusSymbol);
        return totalReward;
    }

    private BigDecimal applyBonusSymbol(BigDecimal totalReward, String bonusSymbol) {
        if (bonusSymbol != null) {
            Symbol bonusConfig = config.getSymbols().get(bonusSymbol);
            if (MULTIPLY_REWARD.equals(bonusConfig.getImpact())) {
                totalReward = totalReward.multiply(BigDecimal.valueOf(bonusConfig.getRewardMultiplier()));
            } else if (EXTRA_BONUS.equals(bonusConfig.getImpact())) {
                totalReward = totalReward.add(BigDecimal.valueOf(bonusConfig.getExtra()));
            }
        }
        return totalReward;
    }

    public String getBonusSymbol(String[][] matrix, Map<String, List<String>> winningCombinations) {
        if (winningCombinations == null) return null;

        for (int row = 0; row < config.getRows(); row++) {
            for (int col = 0; col < config.getColumns(); col++) {
                String symbol = matrix[row][col];
                Symbol symbolConfig = config.getSymbols().get(symbol);
                if (symbolConfig != null && BONUS_TYPE.equals(symbolConfig.getType())) {
                    return MISS_BONUS_SYMBOL.equals(symbol) ? null : symbol;
                }
            }
        }

        return null;
    }
}
