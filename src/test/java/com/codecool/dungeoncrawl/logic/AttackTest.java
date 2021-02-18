package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackTest {

    GameMap map = new GameMap(30,30, CellType.FLOOR);

    @Test
    void loseHealthBothPlayerAndMonster(){
        Player player = new Player(map.getCell(1,1));
        player.setHealth(10);
        player.setStrength(2);
        Skeleton skeleton = new Skeleton(map.getCell(2,1));
        skeleton.setHealth(10);
        skeleton.setStrength(3);
        player.move(1,0);
        assertEquals(8, skeleton.getHealth());
        assertEquals(7, player.getHealth());
    }

    @Test
    void monsterDiesHealthZero(){
        Player player = new Player(map.getCell(1,1));
        player.setHealth(10);
        player.setStrength(20);
        Skeleton skeleton = new Skeleton(map.getCell(2,1));
        skeleton.setHealth(10);
        skeleton.setStrength(3);
        player.move(1,0);
        assertEquals(0, skeleton.getHealth());
        assertEquals(null, map.getCell(2,1).getActor());
        assertEquals(0, skeleton.getSpeed());
        assertEquals(CellType.BONES, map.getCell(2,1).getType());
    }

    @Test
    void playerDiesHealthZero(){
        Player player = new Player(map.getCell(1,1));
        player.setHealth(10);
        player.setStrength(2);
        Skeleton skeleton = new Skeleton(map.getCell(2,1));
        skeleton.setHealth(10);
        skeleton.setStrength(10);
        player.move(1,0);
        assertEquals(0, player.getHealth());
        assertEquals(null, map.getCell(1,1).getActor());
        assertEquals(CellType.CROSS, map.getCell(1,1).getType());
    }
}
