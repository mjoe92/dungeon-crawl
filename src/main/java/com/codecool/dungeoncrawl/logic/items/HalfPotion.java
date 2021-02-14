package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class HalfPotion extends Items{

    public HalfPotion(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "potionhalf";
    }
}
