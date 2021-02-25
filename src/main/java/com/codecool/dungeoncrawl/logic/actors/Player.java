package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.display.Settings;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Items;

import java.util.ArrayList;


public class Player extends Actor {

    private static boolean canPassWall = false;
    private String playerName = "player";
    private int strength = 5;
    private int health;
    private int speed;
    private String savedName;
    private String currentMap;

    private String labelName;

    public Player(Cell cell) {
        super(cell);
        this.health = 10;
        this.speed = 1;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Player() {
    }

    public String getTileName() {       //csempe neve
        return playerName;
    }

    public void setTileName(String playerName) {
        this.playerName = playerName;
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

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }
}
