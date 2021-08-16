package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.LikeDao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that provides operations on {@link Like} contained in the MySQL database.
 * Database connections are taken from the {@link BasicConnectionPool}
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see BasicConnectionPool
 * @see MySqlAbstractDao
 * @see by.bsu.d0mpi.UP_PostGallery.service.LikeService
 * @see ResultSet
 * @see by.bsu.d0mpi.UP_PostGallery.pool.ProxyConnection
 * @see DAOException
 * @see PreparedStatement
 */
public class MySqlLikeDao extends MySqlAbstractDao<Integer, Like> implements LikeDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_POSTS_ID_LIKED_BY_USER =
            "SELECT * FROM likes WHERE likes.likes_user_login = ?";
    private static final String SQL_UPDATE_LIKE =
            "UPDATE likes SET likes_post_id = ?, likes_user_login = ? WHERE like_id = ?";
    private static final String SQL_INSERT_LIKE =
            "INSERT INTO likes (likes_post_id, likes_user_login) VALUES (?, ?)";
    private static final String SQL_DELETE_LIKE =
            "DELETE FROM likes WHERE likes_post_id = ? AND likes_user_login = ?";


    private MySqlLikeDao(String tableName, String idColumn) {
        super(tableName, idColumn);
    }

    private static MySqlLikeDao instance;

    /**
     * Provide a global access point to the instance of the {@link MySqlLikeDao} class.
     *
     * @return the only instance of the {@link MySqlLikeDao} class
     */
    public static MySqlLikeDao getInstance() {
        MySqlLikeDao localInstance = instance;
        if (localInstance == null) {
            synchronized (MySqlLikeDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySqlLikeDao("likes", "like_id");
                }
            }
        }
        return localInstance;
    }


    /**
     * @param connection connection with the MySQL database
     * @param resultSet  the object to which the received data will be written
     * @return {@link List} of all {@link Like} objects received from the database
     * @throws SQLException if something goes wrong during receiving the next entity in the {@link ResultSet}
     */
    @Override
    protected List<Like> getRequestResult(Connection connection, ResultSet resultSet) throws SQLException {
        List<Like> likes = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int postId = resultSet.getInt(2);
            String userLogin = resultSet.getString(3);
            likes.add(new Like(id, postId, userLogin));
        }
        return likes;
    }

    /**
     * sets all {@link Like} parameters necessary for creating entity in the database
     */
    @Override
    protected void setCreateStatementArgs(PreparedStatement statement, Like entity) throws SQLException {
        statement.setInt(1, entity.getPostId());
        statement.setString(2, entity.getAuthorLogin());
    }

    /**
     * sets all {@link Like} parameters necessary for updating entity in the database
     */
    @Override
    protected void setUpdateStatementArgs(PreparedStatement statement, Like entity) throws SQLException {
        setCreateStatementArgs(statement, entity);
        statement.setInt(3, entity.getId());
    }

    /**
     * adds {@link Like} entity to the MySQL database
     *
     * @param entity the {@link Like} object the information about
     *               which should be added to the database
     * @return {@link Like} object if it was added to the database and null otherwise
     */
    @Override
    public Like create(Like entity) {
        return createEntityWithoutDependencies(entity, SQL_INSERT_LIKE);
    }

    /**
     * update information of the {@link Like} entity in the MySQL database
     *
     * @param entity the {@link Like} object the information about which should be updated in the database
     * @return {@link Like} object if information about it was updated and null otherwise
     */
    @Override
    public Like update(Like entity) {
        try (PreparedStatement statement = BasicConnectionPool.getInstance().getConnection().prepareStatement(SQL_UPDATE_LIKE)) {
            setUpdateStatementArgs(statement, entity);
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("DB connection error", e);
            return entity;
        }
    }

    /**
     * delete information about the {@link Like} entity from the MySQL database
     *
     * @param postId    id of the post to remove the like from
     * @param userLogin the login of the user who removed the like
     */
    @Override
    public void delete(Integer postId, String userLogin) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_DELETE_LIKE)) {
            statement.setInt(1, postId);
            statement.setString(2, userLogin);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param login the name of the user whose list of likes you want to get
     * @return {@link List} of the {@link Like} IDes belong to the specified {@link by.bsu.d0mpi.UP_PostGallery.model.User}
     */
    @Override
    public List<Integer> getLikedPostIdList(String login) {
        return findPreparedEntities(SQL_SELECT_POSTS_ID_LIKED_BY_USER, statement -> {
            try {
                statement.setString(1, login);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).stream().map(Like::getPostId).collect(Collectors.toList());
    }

}
