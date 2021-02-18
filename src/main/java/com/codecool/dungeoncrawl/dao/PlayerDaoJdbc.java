package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel playerModel) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y, strength, speed, game_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, playerModel.getPlayerName());
            statement.setInt(2, playerModel.getHealth());
            statement.setInt(3, playerModel.getX());
            statement.setInt(4, playerModel.getY());
            statement.setInt(5, playerModel.getStrength());
            statement.setInt(6, playerModel.getSpeed());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            playerModel.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel playerModel) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE playerModel " +
                    "SET player_name = ?, " +
                    "hp = ?, " +
                    "x = ?, " +
                    "y = ?, " +
                    "strength = ?, " +
                    "speed = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, playerModel.getPlayerName());
            statement.setInt(2, playerModel.getHealth());
            statement.setInt(3, playerModel.getX());
            statement.setInt(4, playerModel.getY());
            statement.setInt(5, playerModel.getStrength());
            statement.setInt(6, playerModel.getSpeed());
            statement.setInt(7, playerModel.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, " +
                    "player_name, " +
                    "hp, " +
                    "x, " +
                    "y, " +
                    "strength, " +
                    "speed, " +
                    "game_name " +
                    "FROM player " +
                    "WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            int playerId = rs.getInt(1);
            String name = rs.getString(2);
            int hp = rs.getInt(3);
            int x = rs.getInt(4);
            int y = rs.getInt(5);
            int strength = rs.getInt(6);
            int speed = rs.getInt(7);
            String gameName = rs.getString(8);

            //PlayerModel-t visszaadni, miután a Playert létrehoztuk
            //Ahhoz Cell kell <- GameMap
/*
            GameMap loadedMap = null;
            Cell cell = new Cell(loadedMap, x, y, CellType.FLOOR);
            Player hero = new Player(null); //cell a gameStateDao-ból lesz
            hero.setPlayerName(name);
            hero.setHealth(hp);
            hero.setStrength(strength);
            hero.setSpeed(speed);
*/
            PlayerModel player = new PlayerModel(name, x, y);
            player.setId(playerId);
            player.setHealth(hp);
            player.setStrength(strength);
            player.setSpeed(speed);
            player.setGameName(gameName);
            return player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, " +
                    "player_name, " +
                    "hp, " +
                    "x, " +
                    "y, " +
                    "strength, " +
                    "speed, " +
                    "game_name " +
                    "FROM player";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<PlayerModel> result = new ArrayList<>();
            while (rs.next()) {
                int playerId = rs.getInt(1);
                String name = rs.getString(2);
                int hp = rs.getInt(3);
                int x = rs.getInt(4);
                int y = rs.getInt(5);
                int strength = rs.getInt(6);
                int speed = rs.getInt(7);

                //PlayerModel-t visszaadni, miután a Playert létrehoztuk
                //Ahhoz Cell kell <- GameMap <- gameStateDao
/*
                GameMap currentMap = null;
                Cell cell = new Cell(currentMap, x, y, CellType.FLOOR);
                Player hero = new Player(cell); //cell a gameStateDao-ból lesz
                hero.setPlayerName(name);
                hero.setHealth(hp);
                hero.setStrength(strength);
                hero.setSpeed(speed);
*/
                PlayerModel player = new PlayerModel(name, x, y);
                player.setId(playerId);
                player.setHealth(hp);
                player.setStrength(strength);
                player.setSpeed(speed);
                result.add(player);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}