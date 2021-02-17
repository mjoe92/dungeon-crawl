package com.codecool.dungeoncrawl.serialize;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.google.gson.Gson;

public class DeserializeJSON {

    /**json formátomú stringekből létrehozza az osztályt, nullokkal lehet bajunk ha marad olyan field ami null
     **/

    String serializedGamestate;
    String serializedPlayerModel;

    GameState gameState;
    PlayerModel playerModel;

    public DeserializeJSON(String serializedGamestate, String serializedPlayerModel) {
        this.serializedGamestate = serializedGamestate; //TODO change param if we want to read from file here
        this.serializedPlayerModel = serializedPlayerModel;
        setGameState(serializedGamestate);
        setPlayerModel(serializedPlayerModel);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(String serializedGamestate) {
        this.gameState = new Gson().fromJson(serializedGamestate, GameState.class);
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void setPlayerModel(String serializedPlayerModel) {
        this.playerModel = new Gson().fromJson(serializedPlayerModel, PlayerModel.class);
    }
}
