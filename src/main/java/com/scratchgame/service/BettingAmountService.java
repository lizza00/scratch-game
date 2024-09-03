package com.scratchgame.service;

import java.math.BigDecimal;

public class BettingAmountService {
    public BigDecimal getBettingAmount(String[] args) {
        BigDecimal betAmount;
        try {
            betAmount = new BigDecimal(args[3]);
            if (betAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Betting amount must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid betting amount. Please provide a valid number.");
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return betAmount;
    }
}