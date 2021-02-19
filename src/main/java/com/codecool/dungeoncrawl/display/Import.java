package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.serialize.DeserializeJSON;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.codecool.dungeoncrawl.logic.CellType.FLOOR;

public class Import {

    private static Stage window;
    String path;
    GameState gameState;
    GameMap map;

    public Import(GameMap map) {
        this.map = map;
    }

    public void createLoadDialog() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        fc.setInitialFileName("export.txt");
        File f = fc.showOpenDialog(window);

        if (f != null) {
            System.out.println("Path to load: " + f.getAbsolutePath());

            path = f.getAbsolutePath();
        }

        loadGame();
    }

    private void loadGame() {

        GameState gameStateToLoad = DeserializeJSON.importGameState(path);
        PlayerModel playerModelToLoad = gameStateToLoad.getPlayer();

        String currentmap = gameStateToLoad.getCurrentMap();
        map = MapLoader.loadMap(currentmap);

        Player player = map.getPlayer();

        map.setMonsters(new ArrayList<>(gameStateToLoad.getMonsters()));

        player.setPlayerName(playerModelToLoad.getPlayerName());
        player.setCurrentMap(currentmap);
        player.setInventory(new ArrayList<>(playerModelToLoad.getInventory()));
        player.setCell(map.getCell(playerModelToLoad.getX(), playerModelToLoad.getY())); //ezzel teszem be helyére
        player.setHealth(playerModelToLoad.getHealth());
        player.setStrength(playerModelToLoad.getStrength());
        player.setSpeed(playerModelToLoad.getSpeed());
        player.setCanPassWall(playerModelToLoad.isCanPassWall());





    }

}
