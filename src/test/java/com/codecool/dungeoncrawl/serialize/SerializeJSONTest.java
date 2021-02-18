package com.codecool.dungeoncrawl.serialize;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Items;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.model.GameState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SerializeJSONTest {



    @Test
    void serializeTestStarterMap(){
        String mapName = "map.txt";
        ArrayList<Items> inventory = new ArrayList<>();


        GameMap map = MapLoader.loadMap(mapName);
        GameState gameState = new GameState(mapName,map);
        inventory.add(new Key(new Cell(map,7, 7, CellType.FLOOR)));
        map.getPlayer().setHealth(10);
        map.getPlayer().setStrength(5);
        map.getPlayer().setPlayerName("Kiss Gizi");
        map.getPlayer().setCanMove(true);
        map.getPlayer().setSpeed(2);

        SerializeJSON ser = new SerializeJSON(gameState, map.getPlayer());

        System.out.println(ser.serializedGamestate);

        System.out.println("-----------------");
        System.out.println();

        System.out.println(ser.serializedPlayerModel);

    }

}
