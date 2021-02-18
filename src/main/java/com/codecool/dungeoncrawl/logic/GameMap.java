package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Items;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private static Player player;
    private static ArrayList<Actor> monsters = new ArrayList<>();
    private static ArrayList<Items> itemsOnMap = new ArrayList<>();

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

    public void putItemsToList(Items item){
        this.itemsOnMap.add(item);
    }

    public ArrayList<Items> getItemsOnMap() {
        return itemsOnMap;
    }

    public void setItemsOnMap(ArrayList<Items> itemsOnMap) {
        GameMap.itemsOnMap = itemsOnMap;
    }

    public void removeItemFromList(Items item){
        for (int i = 0; i< itemsOnMap.size(); i++) {
            Items it = itemsOnMap.get(i);
            if (it.getX() == item.getX() && it.getY() == item.getY() && it.getTileName().equals(item.getTileName())){
                itemsOnMap.remove(i);
            }
        }


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
