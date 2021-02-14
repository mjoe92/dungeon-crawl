package com.codecool.dungeoncrawl.AI;

import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.Random;

public class RandomMovement {

    private int dx;
    private int dy;
    private Random dxRandom = new Random();
    private Random dyRandom = new Random();
    private Random speed = new Random();
    private final int[] options = {-1, 0, 1};
    private GameMap map;

    public RandomMovement() {
        this.dx = options[dxRandom.nextInt(options.length)];
        this.dy = options[dyRandom.nextInt(options.length)];
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public GameMap getMap() {
        return this.map;
    }


    public int randomSpeed(int maxSpeed) {
        if (maxSpeed == 0) return 0;
        return maxSpeed > 1 ? speed.nextInt(maxSpeed - 1) + 1 : 1;
    }
}
