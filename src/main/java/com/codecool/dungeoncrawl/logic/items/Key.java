package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Key extends Items{

    private String TileName = "key";

    public Key(Cell cell) {
        super(cell);
    }

    public Key() {
        super();
    }

    @Override
    public String getTileName() {
        return TileName;
    }
}
