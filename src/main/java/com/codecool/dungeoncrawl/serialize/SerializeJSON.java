package com.codecool.dungeoncrawl.serialize;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.google.gson.Gson;


public class SerializeJSON {

    /**stringekbe menti az állapotokat json formátumba, ha akarjuk ebből csinálhatunk txt-t vagy menthetjük databasebe
     **/

    String serializedGamestate;
    String serializedPlayerModel;

    GameState gameState;
    PlayerModel playerModel;

    public SerializeJSON(GameState gameState) {
        this.gameState = gameState;            //TODO change parameter to gamemap and generate gamestate from that?
        setSerializedGamestate(); //TODO after test make it accessible

    }

    public SerializeJSON(Player player) {

        this.playerModel = new PlayerModel(player);
        setSerializedPlayerModel();
    }

    public SerializeJSON(GameState gameState, Player player) {
        this.gameState = gameState;                         //TODO change parameter to gamemap and generate gamestate from that
        this.playerModel = new PlayerModel(player);
        setSerializedGamestate(); //TODO after test make it accessible
        setSerializedPlayerModel();
    }

    public String getSerializedGamestate() {
        return serializedGamestate;
    }

    public void setSerializedGamestate() {
        this.serializedGamestate = new Gson().toJson(gameState);
    }

    public String getSerializedPlayerModel() {
        return serializedPlayerModel;
    }

    public void setSerializedPlayerModel() {
        this.serializedPlayerModel = new Gson().toJson(playerModel);
    }



}
