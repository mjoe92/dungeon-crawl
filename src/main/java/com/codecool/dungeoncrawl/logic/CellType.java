package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", false),
    FLOOR("floor", true),
    WALL("wall", false),
    STAIRS("stairs", true),
    CROSS("cross", true),
    BONES("bones", true),

    CLOSEDDOOR("closedDoor", false),
    OPENEDDOOR("openDoor", true),
    POTION_FULL("potionfull", true),
    POTION_HALF("potionhalf", true);

    private final String tileName;
    private boolean canActorStand;

    CellType(String tileName, boolean canActorStand) {
        this.tileName = tileName;
        this.canActorStand = canActorStand;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean canActorStand() {
        return canActorStand;
    }

}
