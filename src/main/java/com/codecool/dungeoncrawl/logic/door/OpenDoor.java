package com.codecool.dungeoncrawl.logic.door;

import com.codecool.dungeoncrawl.logic.Cell;

public class OpenDoor extends Door {

    public OpenDoor (Cell cell) {
        super(cell);
        this.setOpen(true);
    }

    @Override
    public String getTileName() {
        return "openDoor";
    }
}
