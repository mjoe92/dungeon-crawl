package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.io.Serializable;

public abstract class Items implements Drawable, Serializable {
    private Cell cell;

    public Items(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public Items() {
    }

}
