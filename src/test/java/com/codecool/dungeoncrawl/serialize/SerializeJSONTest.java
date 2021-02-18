package com.codecool.dungeoncrawl.serialize;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.model.GameState;
import org.junit.jupiter.api.Test;

public class SerializeJSONTest {



    @Test
    void serializeTestStarterMap(){
        String mapName = "map.txt";

        GameMap map = MapLoader.loadMap(mapName);
        GameState gameState = new GameState(mapName,map);

        SerializeJSON ser = new SerializeJSON(gameState, map.getPlayer());

        System.out.println(ser.serializedGamestate);

        System.out.println("-----------------");
        System.out.println();

        System.out.println(ser.serializedPlayerModel);

    }

}
