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

import java.util.ArrayList;

public class SerializeJSONTest {



    @Test
    void serializeTestStarterMapPlayerModel(){
        String mapName = "map.txt";
        ArrayList<Items> inventory = new ArrayList<>();


        GameMap map = MapLoader.loadMap(mapName);
        GameState gameState = new GameState(map);
        inventory.add(new Key(map.getCell(1,1)));
        inventory.add(new Sword(map.getCell(10,1)));
        map.getPlayer().setHealth(10);
        map.getPlayer().setStrength(5);
        map.getPlayer().setPlayerName("Kiss Gizi");
        map.getPlayer().setCanMove(true);
        map.getPlayer().setSpeed(2);
        map.getPlayer().setInventory(inventory);

        //TODO inventory to be serialized
        SerializeJSON ser = new SerializeJSON(map.getPlayer());

        System.out.println(ser.serializedPlayerModel);

    }

    @Test
    void serializeTestStarterMapGameState(){
        String mapName = "map.txt";
        ArrayList<Items> inventory = new ArrayList<>();


        GameMap map = MapLoader.loadMap(mapName);
        GameState gameState = new GameState(map);
        inventory.add(new Key(map.getCell(1,1)));
        map.getPlayer().setHealth(10);
        map.getPlayer().setStrength(5);
        map.getPlayer().setPlayerName("Kiss Gizi");
        map.getPlayer().setCanMove(true);
        map.getPlayer().setSpeed(2);

        //TODO inventory to be serialized
        SerializeJSON ser = new SerializeJSON(gameState);

        System.out.println(ser.serializedGamestate);


    }

}
