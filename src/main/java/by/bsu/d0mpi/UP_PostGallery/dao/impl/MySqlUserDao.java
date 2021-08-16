package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.UserDao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class that provides operations on {@link User} contained in the MySQL database.
 * Database connections are taken from the {@link BasicConnectionPool}
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see BasicConnectionPool
 * @see MySqlAbstractDao
 * @see by.bsu.d0mpi.UP_PostGallery.service.UserService
 * @see ResultSet
 * @see by.bsu.d0mpi.UP_PostGallery.pool.ProxyConnection
 * @see DAOException
 * @see PreparedStatement
 */
public class MySqlUserDao extends MySqlAbstractDao<Integer, User> implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_UPDATE_USER =
            "UPDATE users SET user_password = ? WHERE user_login = ?";
    private static final String SQL_INSERT_USER =
            "INSERT INTO users (user_login, user_password, user_role_id, user_registration_time) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT * FROM users WHERE user_login = ?";
    private static final String SQL_COUNT_OF_POSTS_BY_AUTHOR =
            "SELECT COUNT(post_id) FROM posts WHERE post_author = ?";
    private static final String SQL_COUNT_OF_LIKES_BY_AUTHOR =
            "SELECT COUNT(post_id) FROM posts JOIN likes ON post_id = likes_post_id where post_author = ? AND post_author!=likes_user_login";


    private static MySqlUserDao instance;

    private MySqlUserDao(String tableName, String idColumn) {
        super(tableName, idColumn);
    }

    /**
     * Provide a global access point to the instance of the {@link MySqlUserDao} class.
     *
     * @return the only instance of the {@link MySqlUserDao} class
     */
    public static MySqlUserDao getInstance() {
        MySqlUserDao localInstance = instance;
        if (localInstance == null) {
            synchronized (MySqlUserDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySqlUserDao("users", "user_id");
                }
            }
        }
        return localInstance;
    }

    /**
     * @param connection connection with the MySQL database
     * @param resultSet  the object to which the received data will be written
     * @return {@link List} of all {@link User} objects received from the database
     * @throws SQLException if something goes wrong during receiving the next entity in the {@link ResultSet}
     */
    @Override
    protected List<User> getRequestResult(Connection connection, ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String login = resultSet.getString(2);
            String password = resultSet.getString(3);
            Role role = Role.getRoleByOrdinalNumber(resultSet.getInt(4));
            Date date = resultSet.getDate(5);
            users.add(new User(id, login, password, role, date));
        }
        return users;
    }

    /**
     * sets all {@link User} parameters necessary for creating entity in the database
     */
    @Override
    protected void setCreateStatementArgs(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getLogin());
        statement.setString(2, entity.getPassword());
        statement.setInt(3, entity.getRole().ordinal() + 1);
        java.util.Date dt = entity.getCreatedDate();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String created_date = sdf.format(dt);
        statement.setString(4, created_date);
    }

    /**
     * sets all {@link User} parameters necessary for updating entity in the database
     */
    @Override
    protected void setUpdateStatementArgs(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getPassword());
        statement.setString(2, entity.getLogin());
    }

    /**
     * adds {@link User} entity to the MySQL database
     *
     * @param entity the {@link User} object the information about
     *               which should be added to the database
     * @return {@link User} object if it was added to the database and null otherwise
     */
    @Override
    public User create(User entity) {
        return createEntityWithoutDependencies(entity, SQL_INSERT_USER);
    }

    /**
     * update information of the {@link User} entity in the MySQL database
     *
     * @param entity the {@link User} object the information about which should be updated in the database
     * @return {@link User} object if information about it was updated and null otherwise
     */
    @Override
    public User update(User entity) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            setUpdateStatementArgs(statement, entity);
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("DB connection error", e);
            return entity;
        }
    }

    /**
     * Checks whether the login is presented in the database.
     *
     * @param login user login
     * @return true - if login presented in the database, otherwise returns false
     */
    @Override
    public boolean isLoginPresented(String login) {
        return findUserByLogin(login).isPresent();
    }

    /**
     * Checks whether the {@link User} eith the specified login is presented in the database.
     *
     * @param login user login
     * @return {@link Optional} object with {@link User} or null
     */
    @Override
    public Optional<User> findUserByLogin(String login) {
        return findPreparedEntities(SQL_SELECT_BY_LOGIN, statement -> {
            try {
                statement.setString(1, login);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).stream().findFirst();
    }

    /**
     * Gets the number of posts of the specified author
     *
     * @param login user login
     * @return number of posts belong to the specified {@link User}
     */
    @Override
    public int getNumberOfPostsByAuthor(String login) {
        return getAuthorStatistic(login, SQL_COUNT_OF_POSTS_BY_AUTHOR);
    }

    /**
     * Gets the number of likes of the specified author
     *
     * @param login user login
     * @return number of likes belong to the specified {@link User}
     */
    @Override
    public int getNumberOfLikesByAuthor(String login) {
        return getAuthorStatistic(login, SQL_COUNT_OF_LIKES_BY_AUTHOR);
    }

    /**
     * Gets one stat of the specified author
     *
     * @param login user login
     * @return stat belongs to the specified {@link User}
     */
    protected int getAuthorStatistic(String login, String sql) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("DB connection error", e);
            return 0;
        }
    }
}
