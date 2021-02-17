package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.dao.GameStateDao;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.io.Serializable;

public class PlayerModel extends BaseModel implements Serializable {

    private Player player;
    private int x;
    private int y;
    private String playerName;

    private GameStateDao gameStateDao;

    public PlayerModel(String playerName, int x, int y) {
        this.playerName = player.getName();
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.player = player;
        this.playerName = player.getName();
        this.x = player.getCell().getX();
        this.y = player.getCell().getY();
    }


    public Player getPlayer() {
        return player;
    }
}
