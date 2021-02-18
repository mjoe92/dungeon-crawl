package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class GameState extends BaseModel implements Serializable {
    private Date savedAt;
    private String currentMap;
    transient private List<String> discoveredMaps = new ArrayList<>(); //nincs szükségem erre gsonhoz
    transient private PlayerModel player;
    private List<Actor> monsters;

    public GameState(String currentMap, Date savedAt, PlayerModel player) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;

    }

    public GameState() {
    }

    public GameState(GameMap map, PlayerModel playerModel) {        //ezt kell használnunk hogy megkapja az aktuális id-t
        this.currentMap = map.getPlayer().getCurrentMap();
        this.player = playerModel;
        this.monsters = new ArrayList<>(map.getMonsters()) ;
        this.savedAt = new Date(System.currentTimeMillis());
    }

    public GameState(GameMap map) {
        this.currentMap = map.getPlayer().getCurrentMap();
        this.player = new PlayerModel(map.getPlayer());
        this.monsters = new ArrayList<>(map.getMonsters()) ;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public List<Actor> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Actor> monsters) {
        this.monsters = monsters;
    }
}
