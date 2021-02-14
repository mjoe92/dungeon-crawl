package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Behemoth extends Actor {

    private int health = 15;
    private int speed = 0;
    private int strength = 3;

    public Behemoth(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "behemoth";
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
