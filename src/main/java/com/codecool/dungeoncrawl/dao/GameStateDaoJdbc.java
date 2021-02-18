package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {

    private DataSource dataSource;
    private PlayerDao playerDao;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getCurrentMap());
            statement.setDate(2, state.getSavedAt());
            statement.setString(3, state.getPlayer().getPlayerName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_state " +
                    "SET current_map = ?, " +
                    "saved_at = ?, " +
                    "player_id = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, state.getCurrentMap());
            statement.setDate(2, state.getSavedAt());
            statement.setInt(3, state.getPlayer().getId());
            statement.setInt(4, state.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameState get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT current_map, " +
                    "saved_at, " +
                    "player_id " +
                    "WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String mapName = rs.getString(1);
            Date savedDate = rs.getDate(2);
            int playerId = rs.getInt(3);

            GameState state = new GameState(mapName, savedDate, playerDao.get(playerId));
            state.setId(id);
            return state;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameState> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, " +
                    "current_map, " +
                    "saved_at, " +
                    "player_id " +
                    "FROM player";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<GameState> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String mapName = rs.getString(2);
                Date savedDate = rs.getDate(3);
                int playerId = rs.getInt(4);

                GameState state = new GameState(mapName, savedDate, playerDao.get(id));
                state.setId(playerId);
                result.add(state);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
