package com.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WinningCombination {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    private String when;
    private int count;
    private String group;
    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;
}
