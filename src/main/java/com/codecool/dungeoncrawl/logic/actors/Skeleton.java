package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {

    private int sHealth = 5;
    private int sSpeed = 1;
    private int sStrength = 1;

    public Skeleton(Cell cell) {
        super(cell);
    }

    public Skeleton() {
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
    
    @Override
    public int getHealth() {
        return sHealth;
    }

    @Override
    public int getSpeed() {
        return sSpeed;
    }

    @Override
    public int getStrength() {
        return sStrength;
    }

    @Override
    public void setHealth(int health) {
        this.sHealth = health;
    }

    @Override
    public void setSpeed(int speed) {
        this.sSpeed = speed;
    }

    public void setStrength(int strength) {
        this.sStrength = strength;
    }
}
