package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Behemoth extends Actor {

    private int bhealth = 15;
    private int bspeed = 0;
    private int bstrength = 3;

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

    public Behemoth(Cell cell) {
        super(cell);
        this.x = cell.getX();
        this.y = cell.getY();

    }

    public Behemoth() {
    }

    @Override
    public String getTileName() {
        return "behemoth";
    }

    @Override
    public int getHealth() {
        return bhealth;
    }

    @Override
    public int getSpeed() {
        return bspeed;
    }

    @Override
    public int getStrength() {
        return bstrength;
    }

    @Override
    public void setHealth(int health) {
        this.bhealth = health;
    }

    @Override
    public void setSpeed(int speed) {
        this.bspeed = speed;
    }

    public void setStrength(int strength) {
        this.bstrength = strength;
    }
}
