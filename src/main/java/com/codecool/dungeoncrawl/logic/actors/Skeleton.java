package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {

    private int health = 5;
    private int speed = 1;
    private int strength = 1;

    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
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
