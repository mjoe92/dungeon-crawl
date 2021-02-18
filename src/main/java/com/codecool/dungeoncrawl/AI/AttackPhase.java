package com.codecool.dungeoncrawl.AI;

import com.codecool.dungeoncrawl.display.GameOver;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.FullPotion;
import com.codecool.dungeoncrawl.logic.items.HalfPotion;

import java.util.concurrent.TimeUnit;

public class AttackPhase {

    private Actor monster;
    private Actor player;
    private GameOver gameOver = new GameOver();

    private int[][] neighbours = {
            {1, 0},
            {1, 1},
            {0, 1},
            {-1, 1},
            {-1, 0},
            {-1, -1},
            {0, -1},
            {1, -1}
    };

    public AttackPhase(Actor player, Actor monster) {
        this.monster = monster;
        this.player = player;
    }

    public void loseHealth() {
        monster.setUnderAttack(true);

        monster.setHealth(monster.getHealth() - player.getStrength());

        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // AUDIO

        player.setHealth(player.getHealth() - monster.getStrength());
        
        player.setUnderAttack(true);

        if (monster.getHealth() < 1) {
            monsterDies();


        }
        if (player.getHealth() < 1) {
            playerDies();
        }

    }

    public void monsterDies() {

        monster.getCell().setType(CellType.BONES);

        monster.setSpeed(0);

        monster.getCell().setActor(null);

        //after death poition shows up
        if (monster.getTileName().equals("skeleton")){
            monster.getCell().getNeighbor(freePosition()[0],freePosition()[1])
                    .setItem(new HalfPotion(monster.getCell()
                            .getNeighbor(freePosition()[0], freePosition()[1])));
        }

        if (monster.getTileName().equals("scorpion")){
            monster.getCell().getNeighbor(freePosition()[0],freePosition()[1])
                    .setItem(new FullPotion(monster.getCell()
                            .getNeighbor(freePosition()[0], freePosition()[1])));
        }

        if (monster.getTileName().equals("behemoth")){
            monster.getCell().getNeighbor(freePosition()[0],freePosition()[1])
                    .setItem(new FullPotion(monster.getCell()
                            .getNeighbor(freePosition()[0], freePosition()[1])));
            monster.getCell().getNeighbor(0,0).setItem(
                    new FullPotion(monster.getCell()
                    .getNeighbor(freePosition()[0], freePosition()[1])));
        }


        monster.setHealth(0);
    }

    public void playerDies() {

        player.getCell().setType(CellType.CROSS);

        player.getCell().setActor(null);

        player.setHealth(0);

    }

    public int[] freePosition() {
        for (int[] neighbour : neighbours) {
            if (monster.getCell().getNeighbor(neighbour[0], neighbour[1]).getType() != CellType.WALL
                    && monster.getCell().getNeighbor(neighbour[0], neighbour[1]).getType() != CellType.POTION_FULL
                    && monster.getCell().getNeighbor(neighbour[0], neighbour[1]).getType() != CellType.POTION_HALF
                    && monster.getCell().getNeighbor(neighbour[0], neighbour[1]) != player.getCell()
                    && monster.getCell().getNeighbor(neighbour[0], neighbour[1]).getType() != CellType.BONES) {
                return new int[]{neighbour[0], neighbour[1]};
            }
        }
        return new int[]{0, 0};
    }

}
