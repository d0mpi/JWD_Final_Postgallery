package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.AbstractDao;
import by.bsu.d0mpi.UP_PostGallery.dao.ConnectorDB;
import by.bsu.d0mpi.UP_PostGallery.dao.UserDao;
import by.bsu.d0mpi.UP_PostGallery.model.User;

import java.sql.*;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySqlUserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
    public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    public static final String SQL_SELECT_USER_ID =
            "SELECT * FROM users WHERE id=?";
    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD =
            "SELECT id FROM users WHERE login = ? AND password=?";
    private static final String SQL_UPDATE_USER =
            "UPDATE users SET login = ?, password = ? WHERE id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO users (login, password) VALUES (?, ?)";
    private static final String SQL_DELETE_USER_BY_ID =
            "DELETE FROM users WHERE id = ?";
    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT * FROM users WHERE login = ?";
    private static final Logger LOGGER = LogManager.getLogger();

    private static volatile MySqlUserDaoImpl instance;

    public static MySqlUserDaoImpl getInstance() {
        MySqlUserDaoImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (MySqlUserDaoImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySqlUserDaoImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (rs.next()) {
                int id = rs.getInt(1);
                String login = rs.getString(2);
                String password = rs.getString(3);
                users.add(new User(id, login, password));
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User findEntityById(Integer id) {
        User user = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String login = rs.getString(2);
                String password = rs.getString(3);
                user = new User(id, login, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public boolean delete(Integer id) {
        boolean deleted = false;
        try (PreparedStatement statement = ConnectorDB.getConnection().
                prepareStatement(SQL_DELETE_USER_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            deleted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public boolean delete(User entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean create(User entity) {
        try (PreparedStatement statement = ConnectorDB.getConnection().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, new String(entity.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                entity.setId(id);
                return true;
            } else {
                LOGGER.error("No autoincremented index after trying to add record into table user");
                return false;
            }
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
            return false;
        }
    }

    @Override
    public void update(User entity) {
        try (PreparedStatement statement = ConnectorDB.getConnection().prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("DB connection error", e);
        }
    }

    public boolean isLoginPresented(String login) {
        try (PreparedStatement statement = ConnectorDB.getConnection().prepareStatement(SQL_SELECT_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
        }
        return false;
    }

    public User getUserByLogin(String login) {
        User user = null;
        try (PreparedStatement statement = ConnectorDB.getConnection().prepareStatement(SQL_SELECT_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String u_login = resultSet.getString(2);
                String password = resultSet.getString(3);
                user = new User(id, u_login, password);
            }
            return user;
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
        }
        return user;
    }
}
