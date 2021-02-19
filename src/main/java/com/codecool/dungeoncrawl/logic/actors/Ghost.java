package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Actor {

    private int ghealth = 12;
    private int gspeed = 2;
    private int gstrength = 1;

    private static boolean canPassWall = true;
    private static boolean canPassEmpty = true;
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


    public Ghost() {
    }

    public Ghost(Cell cell) {
        super(cell);
        this.x = cell.getX();
        this.y = cell.getY();
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public int getHealth() {
        return ghealth;
    }

    @Override
    public int getSpeed() {
        return gspeed;
    }

    @Override
    public boolean isCanPassWall() {
        return canPassWall;
    }

    @Override
    public boolean isCanPassEmpty() {
        return canPassEmpty;
    }

    @Override
    public int getStrength() {
        return gstrength;
    }

    @Override
    public void setHealth(int health) {
        this.ghealth = health;
    }

    @Override
    public void setSpeed(int speed) {
        this.gspeed = speed;
    }

    public void setStrength(int strength) {
        this.gstrength = strength;
    }
}
