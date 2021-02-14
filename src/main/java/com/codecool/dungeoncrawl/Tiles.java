package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {

    public static int TILE_WIDTH = 32; //in a 32x32 pieces of tileset


    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;

        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {                                        //itt a i-től j képpontig olvas be vagy hogy?
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("key", new Tile(16,23));
        tileMap.put("sword", new Tile(2,28));
        tileMap.put("ghost", new Tile(27,6));
        tileMap.put("behemoth", new Tile(30, 6));
        tileMap.put("scorpion", new Tile(24, 5));
        tileMap.put("stairs", new Tile(2,6));

        tileMap.put("cross", new Tile(1,14));
        tileMap.put("bones", new Tile(0,15));

        tileMap.put("crown", new Tile(12,24));
        tileMap.put("closedDoor", new Tile(6,10));
        tileMap.put("openDoor", new Tile (8, 10));

        tileMap.put("heroes", new Tile(31, 0));
        tileMap.put("potionfull", new Tile(26, 23));
        tileMap.put("potionhalf", new Tile(25, 23));

    }


    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile;
        if (d == null) {
            tile = tileMap.get(CellType.EMPTY.getTileName()); //a mapen kívüli üres (név nélküli) mezők miatt
        } else {
            tile = tileMap.get(d.getTileName());
        }
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }


}
