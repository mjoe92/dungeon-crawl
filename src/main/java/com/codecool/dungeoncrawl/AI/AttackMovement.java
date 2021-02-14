package com.codecool.dungeoncrawl.AI;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class AttackMovement {
        private int dx;
        private int dy;

        public AttackMovement(GameMap mapp, Actor monster) {

            if (monster.getCell().getX() - mapp.getPlayer().getCell().getX() > 0) {
                dx = -1;
            } else if (monster.getCell().getX() - mapp.getPlayer().getCell().getX() < 0) {
                dx = 1;
            }
            if (monster.getCell().getY() - mapp.getPlayer().getCell().getY() > 0) {
                dy = -1;
            } else if (monster.getCell().getY() - mapp.getPlayer().getCell().getY() < 0) {
                dy = 1;
            }
        }

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }

    }


