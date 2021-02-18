package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.dao.GameStateDao;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Items;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerModel extends BaseModel implements Serializable {

    //private Player player;
    private String playerName;
    private int x;
    private int y;
    private int health;
    private int speed;
    private int strength;
    private String savedName;
    private ArrayList<Items> inventory;
    private boolean canPassWall;

    transient private GameStateDao gameStateDao;        //transient mert nem kell gsonhoz

    public PlayerModel(String playerName, int x, int y) {
        //this.playerName = player.getName();
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        //this.player = player;
        this.playerName = player.getName();
        this.x = player.getCell().getX();
        this.y = player.getCell().getY();
        this.inventory = player.getInventory();
        this.canPassWall = player.isCanPassWall();

    }

    public String getPlayerName() {
        return playerName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
    }

    public boolean isCanPassWall() {
        return canPassWall;
    }

    public void setCanPassWall(boolean canPassWall) {
        this.canPassWall = canPassWall;
    }

    /*
    public Player getPlayer() {
        return player;
    }
*/
}
