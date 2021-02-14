package com.codecool.dungeoncrawl.logic.door;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Door implements Drawable {
    private Cell cell;
    private boolean isOpen;

    public Door(Cell cell) {
        this.cell = cell;
        this.cell.setDoor(this);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean b) {
        this.isOpen = b;
    }
}

