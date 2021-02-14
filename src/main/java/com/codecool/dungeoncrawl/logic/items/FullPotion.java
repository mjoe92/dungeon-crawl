package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class FullPotion extends Items{

    public FullPotion(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "potionfull";
    }
}
