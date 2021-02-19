package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.display.*;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Items;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.serialize.DeserializeJSON;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

import com.codecool.dungeoncrawl.AI.AttackMovement;
import com.codecool.dungeoncrawl.AI.RandomMovement;
import com.codecool.dungeoncrawl.logic.*;

import com.codecool.dungeoncrawl.logic.actors.Actor;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Scorpion;
import com.codecool.dungeoncrawl.logic.items.Items;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.codecool.dungeoncrawl.display.GameOver.displayLose;

public class Main extends Application {
    GameMap map = MapLoader.loadMap("map.txt"); //kezdő map
    Canvas canvas = new Canvas(
            Tiles.TILE_WIDTH * 21,
            Tiles.TILE_WIDTH * 21);  // fix canvas méret. Egyelőre így sikerült megoldanom, hogy kulturáltan nézzen ki.
    GraphicsContext context = canvas.getGraphicsContext2D();

    ArrayList<Items> playerInventory = map.getPlayer().getInventory(); //inventory itemek

    Canvas inventoryCanvas = new Canvas(Tiles.TILE_WIDTH * 4,  //canvas felvett inventoryknak
            Tiles.TILE_WIDTH * 5);

    GraphicsContext inventoryContext = inventoryCanvas.getGraphicsContext2D();

    //TilePane inventoryTilePane = new TilePane();
    BorderPane inventoryPane = new BorderPane();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label monsterType = new Label();
    Label monsterHealthLabel = new Label();
    Label monsterstrengthLabel = new Label();
    GameDatabaseManager dbManager;
    MenuBar menuBar = new MenuBar();
    SaveTheGame saveTheGame = new SaveTheGame(map);
    String path;

    static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        map.getPlayer().setCurrentMap("map01.txt");
    //    setupDbManager();


        window = primaryStage;
        window.initStyle(StageStyle.UTILITY);

        //Menuelemek
        Menu menu = new Menu("Menu");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem importMenuItem = new MenuItem("Import Game");
        MenuItem exportMenuItem = new MenuItem("Export Game");

        menu.getItems().add(saveMenuItem);
        menu.getItems().add(loadMenuItem);
        menu.getItems().add(importMenuItem);
        menu.getItems().add(exportMenuItem);

        menuBar.getMenus().add(menu);

        //Menuelem event TODO load, export, import event
        Load load = new Load(map);
        Import importGame = new Import(map);
        Export exportGame;


        saveMenuItem.setOnAction(e -> saveTheGame.displaySaveWindow());
        loadMenuItem.setOnAction(e -> load.displayLoadWindow());

        exportGame = new Export(map);
        exportMenuItem.setOnAction(e -> exportGame.createSaveDialog());
        importMenuItem.setOnAction(e -> createLoadDialog());
      /*  menuBar.setStyle("-fx-background-color: #472D3C;");
        menu.setStyle("-fx-font-size: 1em; -fx-background-color:#472D3C; -fx-text-fill: #CFC6B8; -fx-border-radius: 5; -fx-padding: 6 12 12 12; -fx-border-color: #F4B41B; -fx-my-menu-color: #F4B41B;" +
                "-fx-my-menu-color-highlighted: #CFC6B8;");*/
        //never get focus on menubar - másképp lépésseknél fel nyílra fókuszt kap
        menuBar.setFocusTraversable(false); //#CFC6B8                       #F4B41B
       //színkódok: lilás háttér: #472D3C; szürke betű: #CFC6B8 ; sárga keret, gomb betű: #F4B41B

        //beállítások
        Settings settings = new Settings();
        Cheats cheatActivator = new Cheats();

        settings.displayWithSettings();
        cheatActivator.activateCheat(settings.getPlayerName(), map.getPlayer());

        GridPane ui = new GridPane();               //gridbe rendezi, azonos sorban lévők azonos magasak, oszlopban azonos szélesek a leghosszabbhoz igazítva
        ui.setPrefWidth(250);
        ui.setPadding(new Insets(10));


        Label playerLabel = new Label(settings.getPlayerName());       //játékos nevét fogja kiírni, lehet majd csillivillizni
        ui.add(new Label("Player: "), 0, 1);
        ui.add(playerLabel, 1, 1);


        ui.add(new Label("Health: "), 0, 2); //i: hányadik oszlop, i1: hányadik sor
        ui.add(healthLabel, 1, 2);


        ui.add(new Label("Strength: "), 0, 3); //i: hányadik oszlop, i1: hányadik sor
        ui.add(strengthLabel, 1, 3);

        //TODO monster csak attacknál jelenjen meg
        ui.add(new Label("Attacked by:  "), 0, 4);
        ui.add(monsterType, 1, 4);


        ui.add(new Label("Monster's Health: "), 0, 5); //i: hányadik oszlop, i1: hányadik sor
        ui.add(monsterHealthLabel, 1, 5);


        ui.add(new Label("Monster's Strength: "), 0, 6); //i: hányadik oszlop, i1: hányadik sor
        ui.add(monsterstrengthLabel, 1, 6);


        Button pickUp = new Button("Pick up");
        pickUp.setFocusTraversable(false);          //focust leveszi a gombról
        pickUp.setAlignment(Pos.TOP_CENTER);
        pickUp.setStyle("-fx-font-size: 2em; -fx-background-color: #472D3C; -fx-text-fill: #F4B41B; -fx-border-radius: 5; -fx-padding: 3 6 6 6; -fx-border-color: #F4B41B;");
        pickUp.setPrefSize(250, 50);

        ui.setHgap(10);                        //grid elemek közötti hely beállítása, ne legyen közvetlenül egymás mellett
        ui.setVgap(10);


        pickUp.setOnAction(click -> pickUpButtonEvent());       //clickre kirajzolja a felvett cuccot

        Label inventoriesLabel = new Label("Inventory");
        inventoriesLabel.setPadding(new Insets(10, 10, 10, 10));

        VBox vBox = new VBox(ui, pickUp, inventoriesLabel, inventoryPane); //így (VBoxban) tudtam megoldani hogy szépen egymás alatt legyenek

        vBox.setStyle("-fx-background-color:#CFC6B8 ; -fx-font-size: 1.4em; -fx-text-fill: #472D3C");

        inventoryPane.getChildren().add(new VBox(inventoryCanvas));





        BorderPane borderPane = new BorderPane();

        borderPane.setTop(menuBar);
        borderPane.setCenter(canvas);
        borderPane.setRight(vBox);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("/menuStyle.css");
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case SPACE:
                pickUpButtonEvent();
                break;
            case S:
                // Player player = map.getPlayer();

                saveTheGame.displaySaveWindow();
                //dbManager.savePlayer(player);
                break;
        }
        if ((keyEvent.getCode() == KeyCode.UP
                || keyEvent.getCode() == KeyCode.DOWN
                || keyEvent.getCode() == KeyCode.LEFT
                || keyEvent.getCode() == KeyCode.RIGHT
                || keyEvent.getCode() == KeyCode.SPACE)
                && map.getPlayer().getPrevCell() != map.getPlayer().getCell()
            //  && 0 <= map.getPlayer().getX()
        ) {
            reaction();
            gameOver();
        }
    }

    private void reaction() {

        if (map.getPlayer().isCanMove()) {
            for (Actor monster : map.getMonsters()) {

              /*  if (monster.getHealth() == 0) {
                    // System.out.println("Monster to remove:" + monster);
                    //System.out.println("Monsters before remove:" + map.getMonsters().toString());

                    map.removeMonster(monster);

                    //   System.out.println("Monsters after removed:" + map.getMonsters().toString());
                }*/

                if (monster.getHealth() > 0) {
                    RandomMovement monsterMove = new RandomMovement();
                    if (monster instanceof Scorpion) {
                        AttackMovement monsterAttack = new AttackMovement(map, monster);
                        monster.move(monsterAttack.getDx(), monsterAttack.getDy());
                    } else {
                        monster.move(monsterMove.getDx(), monsterMove.getDy());
                    }
                } else {
                    monster.setUnderAttack(false);              //kiírja a nullát ha meghalt az attacknál és csak utána vegye le
                }
            }
        }
        map.getPlayer().setCanMove(true);

        Cell stair = map.getCell(map.getPlayer().getCell().getX(), map.getPlayer().getCell().getY());
        if (stair.getType() == CellType.STAIRS) {
            ArrayList<Items> copyInventory = map.getPlayer().getInventory(); //le kellett másolni hogy később hozzá tudjuk adni (E)
            int playerStrengthcopy = map.getPlayer().getStrength();     //player strength is marad
            int playerHealthCopy = map.getPlayer().getHealth();
            map = MapLoader.loadMap("map2.txt"); //lépcső -> következő map
            map.getPlayer().setInventory(copyInventory);    //új map új játékosához be kell állítani a dolgokat, inventoryt itt állítottam (E)
            playerInventory = map.getPlayer().getInventory();   //a kirajzoláshoz új maphez be kell állítani az új inventoryt
            map.getPlayer().setStrength(playerStrengthcopy);
            map.getPlayer().setHealth(playerHealthCopy);
            map.getPlayer().setCurrentMap("map02.txt");

        }

        refresh(); //lépés + szint váltása után. Mivel nálam a pickup még nincs bent, ez lehet, hogy bekavar.
        drawInventory();
    }

    private void refresh() {

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Actor player = map.getPlayer();
        for (int x = -10; x <= 10; x++) {        // a konstans értékek a canvas méretétől függőek
            for (int y = -10; y <= 10; y++) {
                Cell cell = map.getCell(player.getCell().getX() + x, player.getCell().getY() + y);
                if (cell != null) {
                    if (cell.getActor() != null) {
                        Tiles.drawTile(context, cell.getActor(), 10 + x, 10 + y); //az eltolás miatt, ez nem szép, majd kap egy változót
                    } else if (cell.getItem() != null) {
                        Tiles.drawTile(context, cell.getItem(), 10 + x, 10 + y);
                    } else if (cell.getDoor() != null) {
                        Tiles.drawTile(context, cell.getDoor(), 10 + x, 10 + y);
                    } else {
                        Tiles.drawTile(context, cell, 10 + x, 10 + y);
                    }
                } else {
                    Tiles.drawTile(context, cell, 10 + x, 10 + y); //ha a karaktertől számított 10 mező a mapen kívülre esik
                }
            }
        }
        drawAttackedMonstersProperties();
        healthLabel.setText("" + map.getPlayer().getHealth());
        strengthLabel.setText("" + map.getPlayer().getStrength());
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup(map);
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void gameOver() {
        if (map.getPlayer().getHealth() <= 0) {
            displayLose();
        }
    }

    private void pickUpButtonEvent() {

        map.getPlayer().addItemToInventory();       //clickre beteszi listába az itemet
        drawInventory(); //kirajzolja az inventoryt
    }

    private void drawInventory() {

        inventoryContext.clearRect(0, 0, inventoryCanvas.getWidth(), inventoryCanvas.getHeight());
        if (playerInventory.size() > 0) {
            int x = -1;
            int y = 0;
            for (int i = 0; i < playerInventory.size(); i++) {
                Items item = playerInventory.get(i);
                x++;
                if (x >= (int) inventoryCanvas.getWidth() / Tiles.TILE_WIDTH) {
                    x = 0;
                    y++;
                }

                Tiles.drawTile(inventoryContext, item, x, y);       //xy pozíciót ad meg
            }
        }
    }

    private void drawAttackedMonstersProperties() {

  //      System.out.println("drawAttackedMonstersProperties started");
        boolean isAttack = true;
        for (Actor monster : map.getMonsters()) {
       //     System.out.println("foreach started actual monster: " + monster.getTileName() + " under attack: " + monster.isUnderAttack());
            if (monster.isUnderAttack()) {
           //     System.out.println("attackedmonster found set label");
                monsterType.setText("" + monster.getTileName());
                monsterHealthLabel.setText("" + monster.getHealth());
                monsterstrengthLabel.setText("" + monster.getStrength());
                isAttack = false;
            }
        }
        if (isAttack) {
    //        System.out.println("no attacked monster found");
            monsterType.setText("");
            monsterHealthLabel.setText("");
            monsterstrengthLabel.setText("");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

    public static void exitGame() {
        window.close();
    }

    private static void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Írási hiba!");
        }
    }

    public static String createSaveDialog() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        fc.setInitialFileName("export.txt");
        File f = fc.showSaveDialog(window);
        if (f != null) {
            //saveTextToFile("Ez itt a JSON adatok helye\nEz itt a JSON adatok helye", f);
            System.out.println("Path to save: " + f.getAbsolutePath());
            return f.getAbsolutePath();
        }
        return null;
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

        for (Actor mon:map.getMonsters()) {

            mon.setCell(map.getCell(mon.getX(),mon.getY()));
            map.getCell(mon.getX(),mon.getY()).setActor(mon);
           // map.putMonster(mon);

        }



        player.setPlayerName(playerModelToLoad.getPlayerName());
        player.setCurrentMap(currentmap);
        player.setInventory(new ArrayList<>(playerModelToLoad.getInventory()));

        Cell cell = map.getCell(player.getX(), player.getY());
        Cell nextCell = map.getCell(playerModelToLoad.getX(), playerModelToLoad.getY());
        cell.setActor(null);
        nextCell.setActor(player);
        player.setCell(nextCell);

        player.setHealth(playerModelToLoad.getHealth());
        player.setStrength(playerModelToLoad.getStrength());
        player.setSpeed(playerModelToLoad.getSpeed());
        player.setCanPassWall(playerModelToLoad.isCanPassWall());

        refresh();

    }

}
