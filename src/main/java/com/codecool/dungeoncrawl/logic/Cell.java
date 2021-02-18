package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.door.Door;
import com.codecool.dungeoncrawl.logic.items.Items;


public class Cell implements Drawable {

    private CellType type;
    private Actor actor;
    private Items item;
    private GameMap gameMap;
    private int x, y;
    private boolean actorCanStand = true;
    private Door door;

    public Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor() {
        return door;
    }

    public Actor getActor() {
        return actor;
    }


    public Items getItem() {
        return item;
    }
    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }



    @Override
    public String getTileName() {
        return type.getTileName();      //visszaadja a mező nevét (CellType enumok)
    }

    public void setTileName() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActorCanStand() {
        return actorCanStand;
    }

    public void setActorCanStand(boolean actorCanStand) {
        this.actorCanStand = actorCanStand;
    }
}
