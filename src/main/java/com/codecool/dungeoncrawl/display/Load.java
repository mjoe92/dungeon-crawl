package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.dao.PlayerDao;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.codecool.dungeoncrawl.display.Settings;

public class Load {
    private static Stage window;
    private List<String> savedGameList = new ArrayList<>();

    GameDatabaseManager dbManager;
    Player player;
    GameMap map;

    public Load(GameMap map) {
        this.player = map.getPlayer();
        this.map = map;
    }

    public void generateSavedGameList() {
        setupDbManager();
        List<PlayerModel> list = dbManager.getAll();
        for (PlayerModel savedPM : list) {
            savedGameList.add(savedPM.getSavedName());
        }
    }

    public void displayLoadWindow() {

        window = new Stage();
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);

        BackgroundImage backgroundImage = new BackgroundImage(new Image("/menubackground.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        BackgroundImage buttonBackgroundImage = new BackgroundImage(
                new Image(getClass()
                        .getResource("/menubuttonbackground.jpg")
                        .toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(buttonBackgroundImage);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Load");
        window.centerOnScreen();
        window.setWidth(852);
        window.setHeight(413);

        //mjoe: combobox added
        ComboBox<String> saves = savedGamesListBox();
        //saves.getItems().add("sample save 1");
        generateSavedGameList();
        saves.getItems().addAll(savedGameList);
        saves.setPrefSize(500, 50);

        saves.setStyle("-fx-font-size: 2em;-fx-border-color: #ffdb00;" +
                "    -fx-border-radius: 5;" +
                "    -fx-padding: 6 6 6 6; -fx-text-fill: #ffdb00");

        saves.setBackground(null);

        Button loadButton = new Button("LOAD");
        loadButton.setOnAction(e -> onKeyPressed(saves));
        loadButton.setAlignment(Pos.BOTTOM_CENTER);
        loadButton.setBackground(background);
        loadButton.setStyle("-fx-font-size: 2em;-fx-border-color: #ffdb00;" +
                "    -fx-border-radius: 5;" +
                "    -fx-padding: 3 6 6 6; -fx-text-fill: #ffdb00");
        loadButton.setPrefSize(200, 50);
        //  saveButton.setMaxHeight(50);

        Button cancelButton = new Button("CANCEL");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> window.close());
        cancelButton.setAlignment(Pos.BOTTOM_CENTER);
        cancelButton.setBackground(background);
        cancelButton.setStyle("-fx-font-size: 2em;-fx-border-color: #ffdb00;" +
                "    -fx-border-radius: 5;" +
                "    -fx-padding: 3 6 6 6; -fx-text-fill: #ffdb00");
        //cancelButton.setMaxWidth(100);
        cancelButton.setPrefSize(200, 50);

        VBox input = new VBox();
        input.setMaxWidth(300);
        input.setSpacing(20);
        input.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(loadButton, cancelButton);
        hbox.setSpacing(10);
        // hbox.setPadding(new Insets(15,12,15,12));
        input.getChildren().addAll(saves, hbox);

        VBox layout = new VBox();
        layout.setBackground(new Background(backgroundImage));
        layout.getChildren().add(input);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        layout.setPadding(new Insets(0, 0, 100, 0));

        FadeTransition ft2 = new FadeTransition(Duration.millis(1000), layout);
        ft2.setFromValue(0.0);
        ft2.setToValue(1.0);
        ft2.play();

        Scene scene = new Scene(layout);
        scene.setOnKeyPressed(key -> onKeyPressed(saves));

        window.setScene(scene);
        window.showAndWait();
    }

    private void onKeyPressed(ComboBox<String> box) {
        /**There is a Load menu which brings up a modal window, showing the previously saved states with their names as a
         * selectable list. Choosing an element loads the selected game state with the proper map, position and inventory*/

        setupDbManager();
        long id = savedGameList.indexOf(box.getSelectionModel().getSelectedItem()) + 1;
        PlayerModel player = dbManager.getPM((int) id);
        GameState state = dbManager.getGS((int) id);
    }


    private void setupDbManager() {

        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup(map);
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private ComboBox<String> savedGamesListBox() {
        ComboBox<String> saves = new ComboBox<>();
        saves.setEditable(false);
        saves.setBackground(null);
        saves.setStyle("-fx-font-size: 2em;-fx-border-color: #ffdb00;" +
                "    -fx-border-radius: 5;" +
                "    -fx-padding: 6 6 6 6; -fx-text-fill: #ffdb00");
        return saves;
    }


}
