package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.door.ClosedDoor;
import com.codecool.dungeoncrawl.logic.items.Crown;
import com.codecool.dungeoncrawl.logic.items.FullPotion;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapName) {
        InputStream is = MapLoader.class.getResourceAsStream("/" + mapName); //ide kellett egy paraméter a különböző mapek meghívása miatt
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);       //létrehoz egy játékteret üres mezőkkel
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);                      //karakterek alapján létrehozza a mező típusokat
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);               //' ' = üres mező
                            break;
                        case '#':
                            cell.setType(CellType.WALL);                // # = fal
                            break;
                        case 'z':
                            cell.setType(CellType.STAIRS);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);               // . = föld
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.putMonster(new Skeleton(cell));
                            break;
                        case 'n':
                            cell.setType(CellType.FLOOR);
                            map.putMonster(new Scorpion(cell));
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.putMonster(new Ghost(cell));
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            map.putMonster(new Behemoth(cell));
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Crown(cell);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new FullPotion(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.CLOSEDDOOR);
                            new ClosedDoor(cell);
                           /* if (cell.getDoor().isOpen()) {
                                cell.setType(CellType.FLOOR);
                                new OpenDoor(cell);
                            }*/
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                    //cell.setActorCanStand(CellType); //set cell if Actor cannot stand on (make default)
                }
            }
        }
        return map;
    }
/*
    public static boolean restriction(Cell cell) {
        switch (cell.getType()) {
            case WALL:
            case EMPTY:
            case STAIRS:
                return false;
        }
        return true;
    }
*/

}
