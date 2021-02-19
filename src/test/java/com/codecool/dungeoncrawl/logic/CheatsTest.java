package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheatsTest {

    GameMap gameMap = new GameMap(30, 30, CellType.FLOOR);

    @Test
    void activateCheatWhenHero(){
        Player player = new Player(gameMap.getCell(1,1));
        Cheats cheats = new Cheats();
        cheats.activateCheat("SÜSÜ", player);
        assertTrue(player.isCanPassWall());
        assertEquals(100, player.getHealth());
    }

    @Test
    void activateCheatWhenFalse(){
        Player player = new Player(gameMap.getCell(1,1));
        Cheats cheats = new Cheats();
        cheats.activateCheat("Sárkányfűárus", player);
        assertFalse(player.isCanPassWall());
        assertEquals(10, player.getHealth());
    }

}
