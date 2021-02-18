package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMapTest {

    GameMap map = new GameMap(30,30, CellType.FLOOR);

    @Test
    void getPlayer(){
        Player player = new Player(map.getCell(1,1));
        map.setPlayer(player);
        assertEquals(player, map.getPlayer());
    }

    @Test
    void getWidth(){
        assertEquals(30, map.getWidth());
    }

    @Test
    void getHeight(){
        assertEquals(30, map.getHeight());
    }

    @Test
    void putMonster(){
        Skeleton skeleton = new Skeleton(map.getCell(1,1));
        map.putMonster(skeleton);
        assertEquals(1, map.getMonsters().size());
    }

    @Test
    void removeMonster(){
        Skeleton skeleton = new Skeleton(map.getCell(1,1));
        map.putMonster(skeleton);
        map.removeMonster(skeleton);
        assertEquals(0, map.getMonsters().size());
    }

    @Test
    void setMonster(){
        Skeleton skeleton = new Skeleton(map.getCell(1,1));
        ArrayList<Actor> monsters = new ArrayList<>();
        monsters.add(skeleton);
        map.setMonsters(monsters);
        assertEquals(1, map.getMonsters().size());
    }
}
