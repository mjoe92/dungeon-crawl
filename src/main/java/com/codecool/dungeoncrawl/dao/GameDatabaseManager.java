package com.codecool.dungeoncrawl.dao;

import static com.codecool.dungeoncrawl.dao.PersonalData.*;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class GameDatabaseManager {

    private PlayerDao playerDao;
    private GameStateDao gameStateDao;
    GameMap map;
    PlayerModel model;
    GameState gameState;

    public void setup(GameMap map) throws SQLException {
        this.map = map;
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        this.model = new PlayerModel(map.getPlayer());
        this.gameState = new GameState(map, model);
    }


    public void savePlayer() {
        playerDao.add(model);

        gameStateDao.add(gameState);
    }

    public void update() {
        playerDao.update(model);

        gameStateDao.update(gameState);
    }

    public List<PlayerModel> getAllPlayerModel() {
        return playerDao.getAll();
    }

    public List<GameState> getAllGameState() {
        return gameStateDao.getAll();
    }

    public PlayerModel getPM(int id) {
        return playerDao.get(id);
    }

    public GameState getGS(int id) {
        return gameStateDao.get(id);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        String dbName = DB_NAME.getPd();
        String user = USER_NAME.getPd();
        String password = PASSWORD.getPd();

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        new PlayerDaoJdbc(dataSource).createPlayerTable();
        new GameStateDaoJdbc(dataSource).createGameStateTable();

        /* TEST
        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");
*/

        return dataSource;
    }

    public void setModel(PlayerModel model) {
        this.model = model;
    }

    public PlayerModel getModel() {
        return model;
    }

    public GameState getGameState() {
        return gameState;
    }


}


