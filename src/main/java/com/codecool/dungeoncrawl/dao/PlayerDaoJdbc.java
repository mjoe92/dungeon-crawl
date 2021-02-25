package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.*;
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
    public void createPlayerTable() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS player (\n" +
                    "    id serial NOT NULL PRIMARY KEY,\n" +
                    "    player_name text NOT NULL,\n" +
                    "    hp integer NOT NULL,\n" +
                    "    x integer NOT NULL,\n" +
                    "    y integer NOT NULL,\n" +
                    "    strength integer NOT NULL,\n" +
                    "    speed integer NOT NULL,\n" +
                    "    game_name text NOT NULL,\n" +
                    "    tile_name text NOT NULL\n" +
                    ");";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createInventoryTable() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS inventory (\n" +
                    "    id serial NOT NULL PRIMARY KEY,\n" +
                    "    player_id integer NOT NULL,\n" +
                    "    item_name text NOT NULL,\n" +
                    ");";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(PlayerModel playerModel) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y, strength, speed, game_name, tile_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, playerModel.getPlayerName());
            statement.setInt(2, playerModel.getHealth());
            statement.setInt(3, playerModel.getX());
            statement.setInt(4, playerModel.getY());
            statement.setInt(5, playerModel.getStrength());
            statement.setInt(6, playerModel.getSpeed());
            statement.setString(7, playerModel.getSavedName());
            statement.setString(8, playerModel.getTileName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            playerModel.setId(resultSet.getInt(1));
            System.out.println("PlayerDaoJdBC add method started, fields added: " + playerModel.getPlayerName() + " "
            + playerModel.getX()
            + playerModel.getY()
            + playerModel.getStrength()
            + playerModel.getSpeed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel playerModel) {
        //int id = getIdBySavedName(playerModel.getSavedName());
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE player " +
                    "SET player_name = ?, " +
                    "hp = ?, " +
                    "x = ?, " +
                    "y = ?, " +
                    "strength = ?, " +
                    "speed = ?, " +
                    "tile_name = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, playerModel.getPlayerName());
            statement.setInt(2, playerModel.getHealth());
            statement.setInt(3, playerModel.getX());
            statement.setInt(4, playerModel.getY());
            statement.setInt(5, playerModel.getStrength());
            statement.setInt(6, playerModel.getSpeed());
            statement.setString(7, playerModel.getTileName());
            statement.setInt(8, playerModel.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getIdBySavedName(String savedName) {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, " +
                    "player_name " +
                    "FROM player " +
                    "WHERE game_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, savedName);
            ResultSet rs = st.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            return id;
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
                    "game_name, " +
                    "tile_name " +
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
            String savedName = rs.getString(8);
            String tileName = rs.getString(9);

            PlayerModel player = new PlayerModel(name, x, y);
            player.setId(playerId);
            player.setHealth(hp);
            player.setStrength(strength);
            player.setSpeed(speed);
            player.setSavedName(savedName);
            player.setTileName(tileName);
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
                    "game_name, " +
                    "tile_name " +
                    "FROM player";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<PlayerModel> result = new ArrayList<>();
            while (rs.next()) {
                int playerId = rs.getInt(1);
                String playerName = rs.getString(2);
                int hp = rs.getInt(3);
                int x = rs.getInt(4);
                int y = rs.getInt(5);
                int strength = rs.getInt(6);
                int speed = rs.getInt(7);
                String savedName = rs.getString(8);
                String tileName = rs.getString(9);

                PlayerModel player = new PlayerModel(playerName, x, y);
                player.setId(playerId);
                player.setHealth(hp);
                player.setStrength(strength);
                player.setSpeed(speed);
                player.setSavedName(savedName);
                player.setTileName(tileName);
                result.add(player);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addItem(PlayerModel playerModel) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO inventory (player_id, item_name) " +
                    "VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, playerModel.getPlayerName());
            statement.setInt(2, playerModel.getHealth());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            playerModel.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Items> getAllItemsForPlayer(PlayerModel pm) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, " +
                    "player_id, " +
                    "item_name " +
                    "FROM inventory " +
                    "WHERE player_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, pm.getId());
            statement.executeUpdate();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Items> result = new ArrayList<>();
            while (rs.next()) {

                int playerId = rs.getInt(2);
                String itemName = rs.getString(3);

                Items item = null;
                switch (itemName) {
                    case "key":
                        item = new Key();
                        break;
                    case "sword":
                        item = new Sword();
                        break;
                }
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}