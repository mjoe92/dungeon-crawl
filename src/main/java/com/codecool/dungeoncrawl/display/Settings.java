package com.codecool.dungeoncrawl.display;

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

import java.util.function.UnaryOperator;

public class Settings {

    private static String playerName;
    private static Stage window;
    final TextField name = new TextField("Enter your name...");

    public void displayWithSettings() {

        window = new Stage();
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);

        BackgroundImage backgroundImage = new BackgroundImage(new Image("/WelcomeScreenEdit.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);



        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Welcome");
        window.centerOnScreen();
        window.setWidth(852);
        window.setHeight(480);

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


        Button closeButton = new Button("START");
        closeButton.setOnAction(e -> onKeyPressed());
        closeButton.setAlignment(Pos.BOTTOM_CENTER);

        VBox input = new VBox();
        input.setMaxWidth(200);
        input.setSpacing(20);
        input.setAlignment(Pos.CENTER);
        input.getChildren().addAll(name, closeButton);

        VBox layout = new VBox();
        layout.setBackground(new Background(backgroundImage));
        layout.getChildren().add(input);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        layout.setPadding(new Insets(0,0,100,0));

        FadeTransition ft2 = new FadeTransition(Duration.millis(2500), layout);
        ft2.setFromValue(0.0);
        ft2.setToValue(1.0);
        ft2.play();


        Scene scene = new Scene(layout);
        scene.setOnKeyPressed(key -> onKeyPressed());

        window.setScene(scene);
        window.showAndWait();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void onKeyPressed() {
        if (name.getText().equals("Enter your name...") || name.getText().equals("")) {
            playerName = "Player 1";
        } else {
            playerName = name.getText();
        }
        window.close();
    }

}
