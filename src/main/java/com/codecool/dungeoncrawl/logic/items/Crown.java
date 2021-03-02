package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Crown extends Items{

    public Crown() {
    }

    public Crown(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "crown";
    }
}