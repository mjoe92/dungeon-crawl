package com.codecool.dungeoncrawl.display;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.logic.actors.Player;
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
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.codecool.dungeoncrawl.display.Settings;

public class SaveTheGame {
    private static Stage window;
    private static List<String> savedGameList;
    //TODO Settings/getPlayerName-t lehetne megadni alapértelmezettnek de nem látja innen
    final TextField name = new TextField("Name");

    GameDatabaseManager dbManager;
    Player player;

    public SaveTheGame(Player player) {
        this.player = player;
    }

    public void setSavedGameList(List<String> savedGameList) {
        SaveTheGame.savedGameList = savedGameList;
    }

    public void displaySaveWindow() {

        window = new Stage();
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);

        BackgroundImage backgroundImage = new BackgroundImage(new Image("/menubackground.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        BackgroundImage buttonBackgroundImage = new BackgroundImage(
                new Image( getClass()
                        .getResource("/menubuttonbackground.jpg")
                        .toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(buttonBackgroundImage);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Save");
        window.centerOnScreen();
        window.setWidth(852);
        window.setHeight(413);

        //mjoe: combobox added
        ComboBox<String> saves = savedGamesListBox();
        saves.getItems().add("sample save 1");
        //saves.getItems().addAll(savedGameList);

        name.setPrefWidth(100);
        name.setStyle("-fx-font-size: 2em;-fx-border-color: #ffdb00;" +
        "    -fx-border-radius: 5;" +
                "    -fx-padding: 6 6 6 6; -fx-text-fill: #ffdb00");

        name.setBackground(null);


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

        Button saveButton = new Button("SAVE");
        saveButton.setOnAction(e -> onKeyPressed());
        saveButton.setAlignment(Pos.BOTTOM_CENTER);
        saveButton.setBackground(background);
        saveButton.setStyle("-fx-font-size: 2em;-fx-border-color: #ffdb00;" +
                "    -fx-border-radius: 5;" +
                "    -fx-padding: 3 6 6 6; -fx-text-fill: #ffdb00");
        saveButton.setPrefSize(200, 50);
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
        HBox hbox = new HBox(saveButton, cancelButton);
        hbox.setSpacing(10);
       // hbox.setPadding(new Insets(15,12,15,12));
        input.getChildren().addAll(saves, name, hbox);

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

        setupDbManager();

         if (alreadyExistInDb()) {
            showDialogBox();
        } else {
             dbManager.savePlayer(player);
             window.close();
         }
    }

    private boolean alreadyExistInDb() {
        setupDbManager();
        boolean isExist = false; //TODO change from true to false after implemented
        //TODO check if already exist the given name in db - végigiterálunk listán és átállítom falseról truera ha van találat

        List<PlayerModel> list = dbManager.getAll();
        String saveName = name.getText();

        for (PlayerModel savedmodel: list) {
            if (saveName.equals(savedmodel.getSavedName())) {
                isExist = true;
            }
        }


        return isExist;
    }

    private void showDialogBox() {
        //show dialog box with question: Would you like to overwrite the already existing state? YES / NO
        /**If the given username already exist in the db the system shows a dialogbox with a question: Would you like to overwrite the already existing state?
         Choosing Yes: the already existing state is updated and all modal window closes
         Choosing No: the dialog closes and the name input field content on the saving dialog is selected again*/

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("This name is already exists!");
        alert.setHeaderText("Would you like to overwrite the already existing state?");
        alert.setContentText("YES: the already exist state will be updated."
                + System.lineSeparator()
                + "NO: you can choose another name for save.");


        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");


        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes){
            // ... user chose "Yes"
            dbManager.update(player); //TODO implement update method in PlayerDaoJDBC
            alert.close();
            window.close();

        } else if (result.get() == buttonTypeNo) {
            // ... user chose "No": close dialog
        alert.close();
        name.requestFocus();
        }
    }

    private void setupDbManager(){
//TODO onKeyPressed event and setup db implement correctly
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
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
