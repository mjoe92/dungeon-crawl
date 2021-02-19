package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    void createGameStateTable();
    void add(GameState state);
    void update(GameState state);
    GameState get(long id);
    List<GameState> getAll();
}
