package com.scratchgame.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BettingAmountServiceTest {

    private final BettingAmountService bettingAmountService = new BettingAmountService();

    @Test
    void getBettingAmount_ShouldReturnCorrectAmount_WhenValidAmountProvided() {
        String[] args = {"--config", "config.json", "--betting-amount", "100"};
        BigDecimal result = bettingAmountService.getBettingAmount(args);
        assertNotNull(result);
        assertEquals(new BigDecimal("100"), result);
    }

    @Test
    void getBettingAmount_ShouldReturnNull_WhenNegativeAmountProvided() {
        String[] args = {"--config", "config.json", "--betting-amount", "-50"};
        BigDecimal result = bettingAmountService.getBettingAmount(args);
        assertNull(result);
    }

    @Test
    void getBettingAmount_ShouldReturnNull_WhenZeroAmountProvided() {
        String[] args = {"--config", "config.json", "--betting-amount", "0"};
        BigDecimal result = bettingAmountService.getBettingAmount(args);
        assertNull(result);
    }

    @Test
    void getBettingAmount_ShouldReturnNull_WhenNonNumericAmountProvided() {
        String[] args = {"--config", "config.json", "--betting-amount", "abc"};
        BigDecimal result = bettingAmountService.getBettingAmount(args);
        assertNull(result);
    }

    @Test
    void getBettingAmount_ShouldThrowException_WhenInsufficientArgsProvided() {
        String[] args = {"--config", "config.json"};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> bettingAmountService.getBettingAmount(args));
    }
}
