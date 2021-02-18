package com.codecool.dungeoncrawl.serialize;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Items;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.model.GameState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class SerializeJSONTest {


    String mapName = "map.txt";
    GameMap map = MapLoader.loadMap(mapName);
    GameState gameState = new GameState(map);


    @Test
    void exportGame(){

        ArrayList<Items> inventory = new ArrayList<>();


        inventory.add(new Key(map.getCell(1,1)));
        map.getPlayer().setHealth(10);
        map.getPlayer().setStrength(5);
        map.getPlayer().setPlayerName("Kiss Gizi");
        map.getPlayer().setCanMove(true);
        map.getPlayer().setSpeed(2);

        //TODO inventory to be serialized
        SerializeJSON.saveSerializedGamestate(gameState, "TestOutput/saved");




    }

    @Test
    void importTest(){

        exportGame();
        GameState gameState2 = DeserializeJSON.importGameState("TestOutput/saved");

        assertEquals(gameState, gameState2);

    }

}
