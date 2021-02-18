package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.serialize.SerializeJSON;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

public class Export {

    private static Stage window;
    String path;
    GameState gameState;

    public Export(GameMap map) {
        this.gameState = new GameState(map);
    }

    public void createSaveDialog() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        fc.setInitialFileName("export.txt");
        File f = fc.showSaveDialog(window);
        if (f != null) {
            //saveTextToFile("Ez itt a JSON adatok helye\nEz itt a JSON adatok helye", f);
            System.out.println("Path to save: " + f.getAbsolutePath());
            path = f.getAbsolutePath();
        }

    }

    public void saveTheGame() {
        SerializeJSON.saveSerializedGamestate(gameState, path);
    }


}
