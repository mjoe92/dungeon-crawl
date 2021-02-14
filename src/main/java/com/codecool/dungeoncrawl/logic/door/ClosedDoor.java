package com.codecool.dungeoncrawl.logic.door;

import com.codecool.dungeoncrawl.logic.Cell;

public class ClosedDoor extends Door {

    public ClosedDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "closedDoor";
    }
}

