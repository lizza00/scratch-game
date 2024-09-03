package com.scratchgame.runner;

import com.scratchgame.parser.ConfigParser;
import com.scratchgame.service.BettingAmountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameRunnerTest {

    @Mock
    private BettingAmountService bettingAmountService;

    @Mock
    private ConfigParser configParser;

    @InjectMocks
    private GameRunner runner;

    @Test
    void run_ShouldPrintUsageMessage_WhenArgsLengthIsLessThanFour() {
        String[] args = {"--gameConfig", "config.json"};
        runner.run(args);
        verify(bettingAmountService, never()).getBettingAmount(any());
    }

    @Test
    void run_ShouldReturn_WhenBettingAmountIsNull() {
        String[] args = {"--gameConfig", "config.json", "--betting-amount", "0"};
        runner.run(args);
        verify(configParser, never()).parse(anyString());
    }

    @Test
    void run_ShouldParseConfigAndPlayGame_WhenBettingAmountIsValid() throws Exception {
        String[] args = {"--gameConfig", "config.json", "--betting-amount", "100"};

        runner.run(args);
    }

    @Test
    void run_ShouldHandleJsonProcessingException_WhenObjectMapperFails() throws Exception {
        String[] args = {"--gameConfig", "config.json", "--betting-amount", "100"};

        runner.run(args);
    }
}