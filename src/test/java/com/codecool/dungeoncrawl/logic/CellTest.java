package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.door.Door;
import com.codecool.dungeoncrawl.logic.door.OpenDoor;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map = new GameMap(30, 30, CellType.FLOOR);

    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbor(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        assertEquals(null, cell.getNeighbor(0, -1));

        cell = map.getCell(1, 2);
        assertEquals(null, cell.getNeighbor(0, 1));
    }

    @Test
    void getCellType(){
        map.getCell(2, 1).setType(CellType.WALL);
        assertEquals(CellType.WALL, map.getCell(2,1).getType());
    }

    @Test
    void setCellType(){
        assertEquals(CellType.FLOOR, map.getCell(2,1).getType());
        map.getCell(2, 1).setType(CellType.WALL);
        assertEquals(CellType.WALL, map.getCell(2,1).getType());
    }

    @Test
    void getActor(){
        Skeleton skeleton = new Skeleton(map.getCell(1,1));
        assertEquals(Skeleton.class.getSimpleName(), map.getCell(1,1).getActor().getClass().getSimpleName());
    }

    @Test
    void setActor(){
        Skeleton skeleton = new Skeleton(map.getCell(1,1));
        map.getCell(5,5).setActor(skeleton);
        assertEquals(Skeleton.class.getSimpleName(), map.getCell(5,5).getActor().getClass().getSimpleName());
    }

    @Test
    void getItem(){
        Key key = new Key(map.getCell(1,1));
        assertTrue(map.getCell(1,1).getItem().equals(key));
    }

    @Test
    void setItem(){
        Key key = new Key(map.getCell(1,1));
        map.getCell(5,5).setItem(key);
        assertTrue(map.getCell(5,5).getItem().equals(key));
    }

    @Test
    void cannotsetItemIfAlreadyHasItem(){
        Key key = new Key(map.getCell(1,1));
        Sword sword = new Sword(map.getCell(5,5));
        assertThrows(IllegalArgumentException.class, () -> map.getCell(5,5).setItem(key));
    }

    @Test
    void isActorCanStand(){
        assertTrue(map.getCell(1,1).isActorCanStand());
    }

    @Test
    void setActorCanStand(){
        map.getCell(1,1).setActorCanStand(false);
        assertFalse(map.getCell(1,1).isActorCanStand());
    }

    @Test
    void getDoorIfNull(){
        assertEquals(null, map.getCell(1,1).getDoor());
    }

    @Test
    void getDoorIfExists(){
        Door door = new OpenDoor(map.getCell(1,1));
        assertEquals(door, map.getCell(1,1).getDoor());
    }

    @Test
    void setDoor(){
        Door door = new OpenDoor(map.getCell(1,1));
        map.getCell(5,5).setDoor(door);
        assertEquals(door, map.getCell(5,5).getDoor());
    }

    @Test
    void getTileName(){
        assertEquals(CellType.FLOOR.getTileName(), map.getCell(1,1).getTileName());
    }


}