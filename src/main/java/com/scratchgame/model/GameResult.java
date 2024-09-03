package com.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scratchgame.serializer.PlainBigDecimalSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResult {
    private final String[][] matrix;

    @JsonSerialize(using = PlainBigDecimalSerializer.class)
    private final BigDecimal reward;

    @JsonProperty("applied_winning_combinations")
    private final Map<String, List<String>> appliedWinningCombinations;

    @JsonProperty("applied_bonus_symbol")
    private final String appliedBonusSymbol;
}
