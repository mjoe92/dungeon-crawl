package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.Arrays;

public class Cheats {

    private final String[] triggerNames = {
            "mjoe92",
            "Eeve",
            "OldCastle",
            "Csaba",
            "gergelyKerekes",
            "Laci",
            "SÜSÜ"
    };

    public final void activateCheat(String name, Player player) {
        if (Arrays.asList(triggerNames).contains(name)) {
            player.setCanPassWall(true);
            player.setPlayerName("heroes");
            player.setHealth(100);
        }
    }

}
