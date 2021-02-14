package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.Main;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class GameOver {

    private static Stage window;

    public void displayWin() {
        window = new Stage();
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Winner!");
        window.centerOnScreen();
        window.setWidth(500);
        window.setHeight(650);

        Label label = new Label();
        label.setText("YOU DID IT!");
        label.setStyle("-fx-font-size: 1em; -fx-text-fill: #ffffff;");
        label.setAlignment(Pos.TOP_CENTER);

        BackgroundImage gif = new BackgroundImage(new Image("/win.gif"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        Button closeGameButton = new Button("Exit Game");
        closeGameButton.setOnAction(e -> onKeyPressed());
        closeGameButton.setAlignment(Pos.BOTTOM_LEFT);
        closeGameButton.setStyle("-fx-font-size: 2em; -fx-background-color: #000000;-fx-text-fill: #ffffff");



        VBox layout = new VBox();
        layout.setBackground(new Background(gif));
        layout.getChildren().addAll(label, closeGameButton);
        layout.setAlignment(Pos.TOP_CENTER);


        FadeTransition ft2 = new FadeTransition(Duration.millis(2500), layout);
        ft2.setFromValue(0.0);
        ft2.setToValue(1.0);
        ft2.play();

        Scene scene = new Scene(layout);
        scene.setOnKeyPressed(key -> onKeyPressed());
        window.setScene(scene);
        window.showAndWait();
    }

    public static void displayLose() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    Stage window = new Stage();
                    //ne lehessen a fő ablakba visszaváltani
                    window.initModality(Modality.APPLICATION_MODAL);
                    window.initStyle(StageStyle.UNDECORATED);
                    window.setResizable(false);
                    window.setTitle("Loser!");
                    window.centerOnScreen();
                    window.setWidth(700);
                    window.setHeight(500);

                    BackgroundImage gif = new BackgroundImage(new Image("/die.gif"),
                            BackgroundRepeat.REPEAT,
                            BackgroundRepeat.REPEAT,
                            BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT);

                    Button closeGameButton = new Button("Exit Game");
                    closeGameButton.setPadding(new Insets(10));
                    closeGameButton.setStyle("-fx-font-size: 2em; -fx-background-color: #800000;-fx-text-fill: #000000");
                    /*
                            Button tryAgainButton = new Button("Try again");
                            tryAgainButton.setPadding(new Insets(5));
                    */

                    closeGameButton.setOnAction(e -> {
                        window.close();
                        Main.exitGame();
                    });
                    /*
                    tryAgainButton.setOnAction(e -> {
                        window.close();
                        Main.main(new String[]{""});
                        try {
                            Runtime.getRuntime().exec("java -jar dungeon-crawl-1.0-SNAPSHOT-jar-with-dependencies.jar");

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                    */
                    closeGameButton.setAlignment(Pos.BOTTOM_LEFT);

                    HBox layout = new HBox();
                    layout.setBackground(new Background(gif));
                    layout.getChildren().addAll(closeGameButton);
                    //layout.getChildren().addAll(closeGameButton, tryAgainButton);
                    layout.setPadding(new Insets(50));
                    layout.setAlignment(Pos.BOTTOM_CENTER);

                    FadeTransition ft2 = new FadeTransition(Duration.millis(2500), layout);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();

                    Scene scene = new Scene(layout);
                    window.setScene(scene);
                    window.showAndWait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public void onKeyPressed() {
        window.close();
        Main.exitGame();
    }

}