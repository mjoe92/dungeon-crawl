package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword extends Items{


    public Sword(Cell cell) {
        super(cell);
    }

    public Sword() {
        super();
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
