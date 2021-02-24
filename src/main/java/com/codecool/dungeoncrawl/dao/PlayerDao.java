package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Items;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    void createPlayerTable();
    void createInventoryTable();
    void add(PlayerModel player);
    void update(PlayerModel player);
    PlayerModel get(int id);
    List<PlayerModel> getAll();
    List<Items> getAllItemsForPlayer(PlayerModel pm);

    void addItem(PlayerModel playerModel);
}
