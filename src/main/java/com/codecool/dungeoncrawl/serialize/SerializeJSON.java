package com.codecool.dungeoncrawl.serialize;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import static com.codecool.dungeoncrawl.serialize.DeserializeJSON.createMapper;


public class SerializeJSON {

    /**
     * JSON fájlba  menti az állapotokat json formátumban,
     **/
   // TODO itt leehtne szép saját checked exceptiont dobni

    public static void saveSerializedGamestate (GameState gameState, String path) {

        try {
            ObjectMapper mapper = createMapper();
            mapper.writeValue(new File(path), gameState);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }


}
