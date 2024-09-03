package com.scratchgame.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchgame.facade.GameFacade;
import com.scratchgame.model.Game;
import com.scratchgame.model.GameResult;
import com.scratchgame.parser.ConfigParser;
import com.scratchgame.service.BettingAmountService;
import com.scratchgame.service.ServiceFactory;

import java.math.BigDecimal;

public class GameRunner {

    public void run(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java -jar <jar-file> --gameConfig <gameConfig-file> --betting-amount <amount>");
            return;
        }

        String configFilePath = args[1];
        BigDecimal betAmount = new BettingAmountService().getBettingAmount(args);
        if (betAmount == null) return;

        Game gameConfig = new ConfigParser().parse(configFilePath);

        GameFacade gameFacade = new GameFacade(new ServiceFactory(gameConfig));
        GameResult result = gameFacade.playGame(betAmount);
        try {
            String jsonOutput = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result);
            System.out.println(jsonOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}