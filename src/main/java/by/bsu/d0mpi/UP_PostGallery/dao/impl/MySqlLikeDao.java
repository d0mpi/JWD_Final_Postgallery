package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.LikeDao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySqlLikeDao extends MySqlAbstractDao<Integer, Like> implements LikeDao {
    private static final Logger LOGGER = LogManager.getLogger();

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


    @Override
    protected List<Like> mapResultSet(Connection connection, ResultSet resultSet) throws SQLException {
        List<Like> likes = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int postId = resultSet.getInt(2);
            String userLogin = resultSet.getString(3);
            likes.add(new Like(id, postId, userLogin));
        }
        return likes;
    }

    @Override
    protected void setDefaultStatementArgs(PreparedStatement statement, Like entity) throws SQLException {
        statement.setInt(1, entity.getPostId());
        statement.setString(2, entity.getAuthorLogin());
    }

    @Override
    public Like create(Like entity) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
                final PreparedStatement statement = connection.prepareStatement(SQL_INSERT_LIKE, Statement.RETURN_GENERATED_KEYS)) {
            setDefaultStatementArgs(statement, entity);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                entity.setId(id);
                return entity;
            } else {
                LOGGER.error("No autoincremented index after trying to add record into table user");
                return null;
            }
        } catch (SQLException | DAOException e) {
            LOGGER.error("DB connection error", e);
            return null;
        }
    }

    @Override
    public Like update(Like entity) {
        try (PreparedStatement statement = BasicConnectionPool.getInstance().getConnection().prepareStatement(SQL_UPDATE_LIKE)) {
            setDefaultStatementArgs(statement, entity);
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
            return entity;
        } catch (SQLException | DAOException e) {
            LOGGER.error("DB connection error", e);
            return entity;
        }
    }

    @Override
    public void delete(Integer postId, String userLogin) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_DELETE_LIKE)) {
            statement.setInt(1, postId);
            statement.setString(2, userLogin);
            statement.executeUpdate();
        } catch (SQLException | DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getLikedPostIdList(String login) {
        return findPreparedEntities(SQL_SELECT_POSTS_ID_LIKED_BY_USER,statement -> {
            try {
                statement.setString(1, login);
            } catch (SQLException e) {
                e.printStackTrace();
            }}).stream().map(Like::getPostId).collect(Collectors.toList());
    }

}
