package com.scratchgame.service;

import java.util.Random;

public class RandomProvider {
    private final Random random = new Random();

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}