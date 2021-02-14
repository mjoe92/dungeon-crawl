package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private static Player player;
    private static ArrayList<Actor> monsters = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCellType) {  //const létrehozza  apályát magassag, hossz, alap mező alapján
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0) return null; // a mapen kívüli megjelenítés miatt (Main.refresh() else ág)
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void putMonster(Actor monster) {
        this.monsters.add(monster);
    }

    public void removeMonster(Actor monster) {
        this.monsters.remove(monster);
    }

    public void removePlayer() {
        this.player = null;
    }

    public ArrayList<Actor> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Actor> monsters) {
        GameMap.monsters = monsters;
    }
}
