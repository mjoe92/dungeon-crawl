package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;



@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Crown.class, name = "crown"),
        @JsonSubTypes.Type(value = FullPotion.class, name = "potionfull"),
        @JsonSubTypes.Type(value = HalfPotion.class, name = "potionhalf"),
        @JsonSubTypes.Type(value = Key.class, name = "key"),
        @JsonSubTypes.Type(value = Sword.class, name = "sword")
})

public abstract class Items implements Drawable, Serializable {
    private Cell cell;

    public Items() {
    }

    public Items(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }


}
