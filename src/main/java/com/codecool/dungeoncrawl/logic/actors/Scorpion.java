package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Scorpion extends Actor {

    private int shealth = 13;
    private int sspeed = 1;
    private int sstrength = 1;

    public Scorpion(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "scorpion";
    }

    @Override
    public int getHealth() {
        return shealth;
    }


    @Override
    public int getSpeed() {
        return sspeed;
    }

    @Override
    public int getStrength() {
        return sstrength;
    }

    @Override
    public void setHealth(int health) {
        this.shealth = health;
    }

    @Override
    public void setSpeed(int speed) {
        this.sspeed = speed;
    }

    public void setStrength(int strength) {
        this.sstrength = strength;
    }
}
