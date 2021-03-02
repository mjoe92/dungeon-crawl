package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.AI.AttackPhase;
import com.codecool.dungeoncrawl.AI.RandomMovement;
import com.codecool.dungeoncrawl.display.GameOver;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.door.OpenDoor;
import com.codecool.dungeoncrawl.logic.items.Items;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.swing.*;
import java.util.ArrayList;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Behemoth.class, name = "behemoth"),
        @JsonSubTypes.Type(value = Scorpion.class, name = "scorpion"),
        @JsonSubTypes.Type(value = Player.class, name = "player"),
        @JsonSubTypes.Type(value = Skeleton.class, name = "skeleton"),
        @JsonSubTypes.Type(value = Ghost.class, name = "ghost")
})
public abstract class Actor implements Drawable {           //résztvevők/bábuk: játékos, csontváz stb
   @JsonIgnore
    private Cell cell;
   @JsonIgnore
   private Cell prevCell;
    @JsonIgnore
    private ArrayList<Items> inventory = new ArrayList<>();
    private int health;
    private int speed;
    private int strength;
    private boolean isUnderAttack;

    private boolean canPassWall = false;
    private boolean canPassEmpty = false;
    private static boolean canMove = true;

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



    public Actor() {
    }

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        setUnderAttack(false);

        this.x = cell.getX();
        this.y = cell.getY();
    }

    public void move(int dx, int dy) {

        int randomSpeed = new RandomMovement().randomSpeed(getSpeed());
        dx *= randomSpeed;
        dy *= randomSpeed;
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell != null) {
            if (canDoorBeOpened(nextCell)) {
                new OpenDoor(nextCell);
                nextCell.setType(CellType.OPENEDDOOR);
                nextCell.getDoor().setOpen(true);
                removeKey();
            }
            if (nextCell.getActor() == null && canMove && ((nextCell.getType().canActorStand()
                    || (isCanPassWall() && nextCell.getType() == CellType.WALL)
                    || (isCanPassEmpty() && nextCell.getType() == CellType.EMPTY))
                    || isDoorOpen(nextCell))) {
                cell.setActor(null);
                nextCell.setActor(this);

                this.setX(nextCell.getX());
                this.setY(nextCell.getY());

                prevCell = cell;
                cell = nextCell;

            } else if (nextCell.getActor() != null && cell.getActor() instanceof Player) {
                AttackPhase attack = new AttackPhase(cell.getActor(), nextCell.getActor());
                attack.loseHealth();
                canMove = false;
            } else if (cell.getActor() instanceof Player) {
                canMove = false;
            }
        } else {
            canMove = false;
        }
    }

    public boolean isDoorOpen(Cell nextCell) {
        return nextCell.getDoor() != null && nextCell.getDoor().isOpen();
    }

    public boolean canDoorBeOpened(Cell nextCell) {
        return nextCell.getDoor() != null && nextCell.getDoor().getTileName().equals("closedDoor") && (hasKey());
    }

    public boolean hasKey() {
        for (Items item : this.getInventory()) {
            if (item.getTileName().equals("key")) {
                return true;
            }
        }
        return false;
    }

    public void removeKey() {
        for (Items item : this.getInventory()) {
            if (item.getTileName().equals("key")) {
                this.getInventory().remove(item);
                return;
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
    }

    public void addInventory(Items items) {
        inventory.add(items);
    }

    public void addItemToInventory() {
        //tileName alapján ellenőrzöm, ha felszedhető elemen áll listába beteszi
        try {
            if (cell.getItem().getTileName().equals("crown")) {
                GameOver gameOver = new GameOver();
                gameOver.displayWin();
            } else if (cell.getItem().getTileName().equals("potionhalf")) {
                cell.getActor().setHealth(cell.getActor().getHealth() + 2); //2 élettel növeli
                cell.setType(CellType.FLOOR);
                cell.setItem(null);
            }else if (cell.getItem().getTileName().equals("potionfull")) {
                cell.getActor().setHealth(cell.getActor().getHealth() + 3); //4 élettel növeli
                cell.setType(CellType.FLOOR);
                cell.setItem(null);
            } else if (cell.getItem() != null) {
                if (cell.getItem().getTileName().equals("sword")) {
                    cell.getActor().setStrength((getStrength() + 2)); //kard felvétele 2-vel növeli játékos erősségét
                }

                addInventory(cell.getItem());
                cell.setType(CellType.FLOOR);
                cell.setItem(null);
            }
        } catch (NullPointerException ex) {

        }


    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isCanPassEmpty() {
        return canPassEmpty;
    }

    public boolean isCanPassWall() {
        return canPassWall;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public Cell getPrevCell() {
        return prevCell;
    }

    public int getStrength() {
        return strength;
    }

    public boolean isUnderAttack() {
        return isUnderAttack;
    }

    public void setUnderAttack(boolean underAttack) {
        isUnderAttack = underAttack;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
