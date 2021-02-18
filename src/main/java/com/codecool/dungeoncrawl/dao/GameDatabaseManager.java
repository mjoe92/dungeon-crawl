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
    PlayerModel model = new PlayerModel(map.getPlayer());
    GameState gameState = new GameState(map);


    public void setup(GameMap map) throws SQLException {
        this.map = map;
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer() {

        playerDao.add(model);
        gameStateDao.add(gameState);

    }

    public void update(){
        playerDao.update(model);
        gameStateDao.add(gameState);
    }

    public List<PlayerModel> getAll() {
        return playerDao.getAll();
    }


    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        String dbName = DB_NAME.getPd();
        String user = USER_NAME.getPd();
        String password = PASSWORD.getPd();


        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        /* TEST
        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");
*/

        return dataSource;
    }
}
