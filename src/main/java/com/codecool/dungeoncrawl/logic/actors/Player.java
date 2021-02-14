package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;


public class Player extends Actor {

    private static boolean canPassWall = false;
    private String playerName = "player";
    private int strength = 5;
    private int health;
    private int speed;


    public Player(Cell cell) {
        super(cell);
        this.health = 10;
        this.speed = 1;
    }

    public String getTileName() {       //csempe neve
        return playerName;
    }

    @Override
    public boolean isCanPassWall() {
        return canPassWall;
    }

    public void setCanPassWall(boolean canPassWall) {
        Player.canPassWall = canPassWall;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getName() {
        return playerName;
    }


}
