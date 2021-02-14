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
        System.out.println("loseHealth method start");
        monster.setHealth(monster.getHealth() - player.getStrength());
        System.out.println("Monsterhealth: " + monster.getHealth()
                + " |  player strength: " + player.getStrength()
                + " |  monster sethealth param: " + (monster.getHealth() - player.getStrength()));

        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // AUDIO

        player.setHealth(player.getHealth() - monster.getStrength());
        System.out.println("  Player health: " + player.getHealth());
        player.setUnderAttack(true);

        if (monster.getHealth() < 1) {
            monsterDies();
           // player.setUnderAttack(false);
            //monster.setUnderAttack(false);

        }
        if (player.getHealth() < 1) {
            playerDies();
        }

    }

    public void monsterDies() {
        System.out.println("  monsterDies started");
        monster.getCell().setType(CellType.BONES);
        System.out.println("  Monster speed: " + monster.getSpeed() + " Monster health: " + monster.getHealth());
        monster.setSpeed(0);
        System.out.println("     Monster speed after set 0: " + monster.getSpeed());
        System.out.println("     PLayer health: " + player.getHealth());
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


        System.out.println("  monsterdies ended");
        System.out.println("----------------------");

        monster.setHealth(0);
    }

    public void playerDies() {
        System.out.println("  playerDies started");
        player.getCell().setType(CellType.CROSS);
        System.out.println("   PLayer health: " + player.getHealth());
        System.out.println("   Monster speed: " + monster.getSpeed() + " Monster health: " + monster.getHealth());
        player.getCell().setActor(null);
        System.out.println("  player dies ended");
        System.out.println("---------------------");
        player.setHealth(0);
      //  gameOver.displayLose();
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
