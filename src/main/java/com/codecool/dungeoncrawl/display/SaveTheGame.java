package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.function.UnaryOperator;
import com.codecool.dungeoncrawl.display.Settings;

public class SaveTheGame {
    private static Stage window;
    //TODO Settings/getPlayerName-t lehetne megadni alapértelmezettnek de nem látja innen
    final TextField name = new TextField("Playername");
    GameDatabaseManager dbManager;
    Player player;

    public SaveTheGame(Player player) {
        this.player = player;
    }

    public void displaySaveWindow() {

        window = new Stage();
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);

        BackgroundImage backgroundImage = new BackgroundImage(new Image("/menubackground.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Save");
        window.centerOnScreen();
        window.setWidth(852);
        window.setHeight(413);
        name.setPrefWidth(100);

        final int MAX_LENGTH = 20;
        UnaryOperator<TextFormatter.Change> rejectChange = c -> {
            if (c.isContentChange()) {
                if (c.getControlNewText().length() > MAX_LENGTH) {
                    return null;
                }
            }
            return c;
        };
        name.setTextFormatter(new TextFormatter(rejectChange));

        BackgroundImage buttonBackgroundImage = new BackgroundImage(
                new Image( getClass()
                        .getResource("/menubuttonbackground.jpg")
                        .toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(buttonBackgroundImage);


        Button saveButton = new Button("SAVE");
        saveButton.setOnAction(e -> onKeyPressed());
        saveButton.setAlignment(Pos.BOTTOM_CENTER);
        saveButton.setBackground(background);
        saveButton.setStyle("-fx-font-size: 2em;-fx-border-color: #ffdb00;" +
                "    -fx-border-radius: 5;" +
                "    -fx-padding: 3 6 6 6; -fx-text-fill: #ffdb00");

        Button cancelButton = new Button("CANCEL");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> window.close());
        cancelButton.setAlignment(Pos.BOTTOM_CENTER);
        cancelButton.setBackground(background);
        cancelButton.setStyle("-fx-font-size: 2em;-fx-border-color: #ffdb00;" +
                "    -fx-border-radius: 5;" +
                "    -fx-padding: 3 6 6 6; -fx-text-fill: #ffdb00");


        VBox input = new VBox();
        input.setMaxWidth(200);
        input.setSpacing(20);
        input.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(saveButton, cancelButton);
        hbox.setSpacing(10);
       // hbox.setPadding(new Insets(15,12,15,12));
        input.getChildren().addAll(name, hbox);

        VBox layout = new VBox();
        layout.setBackground(new Background(backgroundImage));
        layout.getChildren().add(input);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        layout.setPadding(new Insets(0,0,100,0));

        FadeTransition ft2 = new FadeTransition(Duration.millis(1000), layout);
        ft2.setFromValue(0.0);
        ft2.setToValue(1.0);
        ft2.play();

        Scene scene = new Scene(layout);
        scene.setOnKeyPressed(key -> onKeyPressed());

        window.setScene(scene);
        window.showAndWait();
    }

    private void onKeyPressed() {
        dbManager.savePlayer(player);
    }

    private void setupDbManager() {
//TODO onKeyPressed event and setup db implement correctly
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }


}
