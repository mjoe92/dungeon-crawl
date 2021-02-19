package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Scorpion extends Actor {

    private int shealth = 13;
    private int sspeed = 1;
    private int sstrength = 1;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Scorpion(Cell cell) {
        super(cell);

        this.x = cell.getX();
        this.y = cell.getY();
    }

    public Scorpion() {
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
