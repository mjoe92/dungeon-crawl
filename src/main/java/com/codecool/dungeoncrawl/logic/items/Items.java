package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.io.Serializable;

public abstract class Items implements Drawable, Serializable {
    private Cell cell;

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Items(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);

        this.x = cell.getX();
        this.y = cell.getY();
    }


}
