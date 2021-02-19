package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.door.ClosedDoor;
import com.codecool.dungeoncrawl.logic.door.Door;
import com.codecool.dungeoncrawl.logic.door.OpenDoor;
import com.codecool.dungeoncrawl.logic.items.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(30, 30, CellType.FLOOR);

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(2, player.getCell().getX());
        assertEquals(1, player.getCell().getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(1, player.getCell().getX());
        assertEquals(1, player.getCell().getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(2, player.getCell().getX());
        assertEquals(1, player.getCell().getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(1, player.getCell().getX());
        assertEquals(1, player.getCell().getY());
        assertEquals(2, skeleton.getCell().getX());
        assertEquals(1, skeleton.getCell().getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }
    @Test
    void behemothCannotMove(){
        Behemoth behemoth = new Behemoth(gameMap.getCell(1,1));
        behemoth.move(1,0);
        assertEquals(1, behemoth.getCell().getX());
        assertEquals(1, behemoth.getCell().getY());
    }

    @Test
    void ghostCanMoveToWall(){
        gameMap.getCell(2, 1).setType(CellType.WALL);
        gameMap.getCell(3, 1).setType(CellType.WALL);
        Ghost ghost = new Ghost(gameMap.getCell(1,1));
        ghost.move(1,0);
        assertTrue(ghost.getCell().getType().getTileName().equals("wall"));
    }

    @Test
    void scorpionApproachingThePlayer(){
        Scorpion scorpion = new Scorpion(gameMap.getCell(4,4));
        Player player = new Player(gameMap.getCell(7,7));
        player.move(1,1);

        assertTrue(scorpion.getCell().getX() > 4 || scorpion.getCell().getY() > 4);
    }

    @Test
    void isDoorOpenWhenDoorIsOpen(){
        Door door = new OpenDoor(gameMap.getCell(2,2));
        Player player = new Player(gameMap.getCell(1,2));
        assertTrue(player.isDoorOpen(gameMap.getCell(2,2)));
    }

    @Test
    void isDoorOpenWhenDoorIsClose(){
        Door door = new ClosedDoor(gameMap.getCell(2,2));
        Player player = new Player(gameMap.getCell(1,2));
        assertFalse(player.isDoorOpen(gameMap.getCell(2,2)));
    }

    @Test
    void isDoorOpenWhenThereIsNoDoor(){
        Player player = new Player(gameMap.getCell(1,2));
        assertFalse(player.isDoorOpen(gameMap.getCell(2,2)));
    }

    @Test
    void canDoorBeOpenedWhenPlayerHasKey(){
        Door door = new ClosedDoor(gameMap.getCell(2,2));
        Player player = new Player(gameMap.getCell(1,2));
        Key key = new Key(gameMap.getCell(5,5));
        player.addInventory(key);
        assertTrue(player.canDoorBeOpened(gameMap.getCell(2,2)));
    }

    @Test
    void canDoorBeOpenedWhenPlayerHasNoKey(){
        Door door = new ClosedDoor(gameMap.getCell(2,2));
        Player player = new Player(gameMap.getCell(1,2));
        assertFalse(player.canDoorBeOpened(gameMap.getCell(2,2)));
    }

    @Test
    void hasKeyWhenReallyHas(){
        Door door = new ClosedDoor(gameMap.getCell(2,2));
        Player player = new Player(gameMap.getCell(1,2));
        Key key = new Key(gameMap.getCell(5,5));
        player.addInventory(key);
        assertTrue(player.hasKey());
    }

    @Test
    void hasKeyWhenHasNot(){
        Door door = new ClosedDoor(gameMap.getCell(2,2));
        Player player = new Player(gameMap.getCell(1,2));
        assertFalse(player.hasKey());
    }

    @Test
    void removeKeyWhenHasKey(){
        Player player = new Player(gameMap.getCell(1,2));
        Key key = new Key(gameMap.getCell(5,5));
        player.addInventory(key);
        player.removeKey();
        assertEquals(0, player.getInventory().size());
        assertFalse(player.hasKey());
    }

    @Test
    void removeKeyWhenHasNot(){
        Player player = new Player(gameMap.getCell(1,2));
        assertThrows(IllegalStateException.class, () -> player.removeKey());
    }


}