package com.codecool.dungeoncrawl.logic;

// Overwrites default rules

import com.codecool.dungeoncrawl.logic.actors.Actor;

public class SpecialRules {

    public enum changeActorCanStand {
        GHOST_ON_WALL("ghost", CellType.WALL,true),
        GHOST_ON_EMPTY("ghost", CellType.FLOOR,true);

        private String actorName;
        private CellType type;
        private boolean canPass;

        changeActorCanStand(String actorName, CellType type, boolean canPass) {
            this.actorName = actorName;
            this.type = type;
            this.canPass = canPass;
        }

        public String getActorName() {
            return actorName;
        }

        public CellType getType() {
            return type;
        }

        public void makeTilesPassForActor(Actor actor) {

        }
    }

}
