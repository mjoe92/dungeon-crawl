package com.codecool.dungeoncrawl.serialize;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public class DeserializeJSON {

    /**megadott pathból Gamestatet ad
     **/

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator
//                .builder()
//                .allowIfBaseType(Actor.class)
//                .build();
//        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
        return mapper;
    }

    public static GameState importGameState(String path) {
        try {
            ObjectMapper mapper = createMapper();
            return mapper.readValue(new File(path), GameState.class);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }


    }

}
