package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Scorpion extends Actor {

    private int health = 13;
    private int speed = 1;
    private int strength = 1;

    public Scorpion(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "scorpion";
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
