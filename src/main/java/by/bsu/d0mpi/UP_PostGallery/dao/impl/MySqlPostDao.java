package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.PostDao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import by.bsu.d0mpi.UP_PostGallery.service.MyPair;
import by.bsu.d0mpi.UP_PostGallery.util.PageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class that provides operations on {@link Post} contained in the MySQL database.
 * Database connections are taken from the {@link BasicConnectionPool}
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see BasicConnectionPool
 * @see MySqlAbstractDao
 * @see by.bsu.d0mpi.UP_PostGallery.service.PostService
 * @see ResultSet
 * @see by.bsu.d0mpi.UP_PostGallery.pool.ProxyConnection
 * @see DAOException
 * @see PreparedStatement
 * @see by.bsu.d0mpi.UP_PostGallery.util.MySQLPageRequest
 */
public class MySqlPostDao extends MySqlAbstractDao<Integer, Post> implements PostDao {
    private static final String SQL_UPDATE_POST =
            "UPDATE posts SET post_model = ?, post_type = ?,post_length = ?, post_wingspan = ?, post_height = ?," +
                    " post_origin = ?, post_crew = ?, post_speed = ?, post_distance = ?, post_price = ?," +
                    " post_author = ? WHERE post_id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO posts (post_model ,post_type, post_length, post_wingspan, post_height, post_origin, post_crew," +
                    " post_speed, post_distance, post_price, post_author, post_create_date)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_POST_ID_LIST_BY_AUTHOR =
            "SELECT * FROM posts WHERE posts.post_author = ?";
    //    private static final String SQL_SELECT_POST_BY_ID =
//            "SELECT * FROM posts WHERE posts.post_id = ?";
    private static final String SQL_DELETE_HASHTAGS_BY_POST_ID =
            "DELETE FROM hashtags WHERE hashtags_post_id = ?";
    public static final String SQL_INSERT_HASHTAGS_WITH_POST_ID =
            "INSERT INTO hashtags (hashtag_text, hashtags_post_id) VALUES (?, ?)";
    private static final String SQL_SELECT_HASHTAGS_BY_POST_ID =
            "SELECT hashtags.hashtag_text FROM posts JOIN hashtags WHERE posts.post_id = hashtags.hashtags_post_id AND posts.post_id = ?";

    private static final Logger logger = LogManager.getLogger();

    private MySqlPostDao(String tableName, String idColumn) {
        super(tableName, idColumn);
    }

    private static volatile MySqlPostDao instance;

    /**
     * Provide a global access point to the instance of the {@link MySqlPostDao} class.
     *
     * @return the only instance of the {@link MySqlPostDao} class
     */
    public static MySqlPostDao getInstance() {
        MySqlPostDao localInstance = instance;
        if (localInstance == null) {
            synchronized (MySqlPostDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySqlPostDao("posts", "post_id");
                }
            }
        }
        return localInstance;
    }

    /**
     * @param connection connection with the MySQL database
     * @param rs         the object to which the received data will be written
     * @return {@link Post} of all {@link Post} objects received from the database
     * @throws SQLException if something goes wrong during receiving the next entity in the {@link ResultSet}
     */
    @Override
    protected List<Post> getRequestResult(Connection connection, ResultSet rs) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String model = rs.getString(2);
            String type = rs.getString(3);
            BigDecimal length = rs.getBigDecimal(4);
            BigDecimal wingspan = rs.getBigDecimal(5);
            BigDecimal height = rs.getBigDecimal(6);
            String origin = rs.getString(7);
            Integer crew = rs.getInt(8);
            BigDecimal speed = rs.getBigDecimal(9);
            BigDecimal distance = rs.getBigDecimal(10);
            BigDecimal price = rs.getBigDecimal(11);
            Date createdDate = rs.getDate(12);
            String author = rs.getString(13);
            PreparedStatement statement2 = connection.prepareStatement(SQL_SELECT_HASHTAGS_BY_POST_ID);
            statement2.setInt(1, id);
            ResultSet rsHash = statement2.executeQuery();
            List<String> hashtags = new LinkedList<>();
            while (rsHash.next()) {
                hashtags.add(rsHash.getString(1));
            }
            statement2.close();
            posts.add(new Post(id, model, type, length, wingspan, height, origin, crew, speed, distance,
                    price, createdDate, author, hashtags));
        }
        return posts;
    }


    /**
     * sets all {@link Like} parameters necessary for creating entity in the database
     */
    @Override
    protected void setCreateStatementArgs(PreparedStatement statement, Post entity) throws SQLException {
        setUpdateStatementArgs(statement, entity);
        java.util.Date dt = entity.getCreatedDate();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String created_date = sdf.format(dt);
        statement.setString(12, created_date);
    }

    /**
     * sets all {@link Like} parameters necessary for updating entity in the database
     */
    @Override
    protected void setUpdateStatementArgs(PreparedStatement statement, Post entity) throws SQLException {
        statement.setString(1, entity.getModel());
        statement.setString(2, entity.getType());
        statement.setString(3, String.valueOf(entity.getLength()));
        statement.setString(4, String.valueOf(entity.getWingspan()));
        statement.setString(5, String.valueOf(entity.getHeight()));
        statement.setString(6, entity.getOrigin());
        statement.setString(7, String.valueOf(entity.getCrew()));
        statement.setString(8, String.valueOf(entity.getSpeed()));
        statement.setString(9, String.valueOf(entity.getDistance()));
        statement.setString(10, String.valueOf(entity.getPrice()));
        statement.setString(11, entity.getAuthor());
        statement.setInt(12, entity.getId());
    }


    protected void setAndExecuteHashtagStatement(PreparedStatement statement, Post entity) throws SQLException {
        for (String hashtag : entity.getHashtags()) {
            statement.setString(1, hashtag);
            statement.setInt(2, entity.getId());
            statement.executeUpdate();
        }
    }

    /**
     * adds {@link Post} entity to the MySQL database and hashtags
     *
     * @param entity the {@link Post} object the information about
     *               which should be added to the database
     * @return {@link Post} object if it was added to the database and null otherwise
     */
    @Override
    public Post create(Post entity) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement postStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
             final PreparedStatement hashtagStatement = connection.prepareStatement(SQL_INSERT_HASHTAGS_WITH_POST_ID)) {
            setCreateStatementArgs(postStatement, entity);
            postStatement.executeUpdate();
            ResultSet resultSet = postStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                entity.setId(id);
            } else {
                logger.error("No autoincremented index after trying to add record into table user");
                return null;
            }
            setAndExecuteHashtagStatement(hashtagStatement, entity);
        } catch (SQLException | DAOException e) {
            logger.error("DB connection error", e);
            return null;
        }
        return entity;
    }

    /**
     * update information of the {@link Post} entity in the MySQL database
     *
     * @param entity the {@link Post} object the information about which should be updated in the database
     * @return {@link Post} object if information about it was updated and null otherwise
     */
    @Override
    public Post update(Post entity) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement postStatement = connection.prepareStatement(SQL_UPDATE_POST);
             final PreparedStatement statement1 = connection.prepareStatement(SQL_DELETE_HASHTAGS_BY_POST_ID);
             final PreparedStatement hashtagStatement = connection.prepareStatement(SQL_INSERT_HASHTAGS_WITH_POST_ID)) {
            setUpdateStatementArgs(postStatement, entity);
            postStatement.executeUpdate();
            statement1.setInt(1, entity.getId());
            statement1.executeUpdate();
            setAndExecuteHashtagStatement(hashtagStatement, entity);
            return entity;
        } catch (SQLException | DAOException e) {
            logger.error("DB connection error", e);
            return entity;
        }
    }

    /**
     * Finds all posts belong to the user with specified login
     *
     * @param login the username of the user you want to get information about
     * @return {@link List} of {@link Post} objects belong to the specified user
     */
    @Override
    public List<Post> getPostsByAuthorLogin(String login) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_POST_ID_LIST_BY_AUTHOR)
        ) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return getRequestResult(connection, resultSet);
        } catch (SQLException | DAOException e) {
            logger.error("DB connection error", e);
            return Collections.emptyList();
        }
    }

    /**
     * Finds all posts with specified filters and sort type according to the page number.
     *
     * @param pageRequest constructed query string
     * @return {@link MyPair<>} contained received {@link List} of posts and total number of posts
     */
    @Override
    public MyPair<List<Post>, Integer> getPage(PageRequest pageRequest) {
        List<Post> posts;
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement postStatement =
                     connection.prepareStatement(pageRequest.getPageRequestString());
             final PreparedStatement countStatement = connection.prepareStatement(pageRequest.getPostsCountRequestString())
        ) {
            int i = 1;
            if (!pageRequest.getHashtagFilter().isEmpty()) {
                postStatement.setString(i, pageRequest.getHashtagFilter());
                countStatement.setString(i, pageRequest.getHashtagFilter());
                i++;
            }
            if (!pageRequest.getAuthorFilter().isEmpty()) {
                postStatement.setString(i, pageRequest.getAuthorFilter());
                countStatement.setString(i, pageRequest.getAuthorFilter());
                i++;
            }
            if (!pageRequest.getDateFilter().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(pageRequest.getDateFilter()));
                postStatement.setString(i, sdf.format(c.getTime()));
                countStatement.setString(i, sdf.format(c.getTime()));
                c.add(Calendar.DATE, 1);
                i++;
                postStatement.setString(i, sdf.format(c.getTime()));
                countStatement.setString(i, sdf.format(c.getTime()));
                i++;
            }
            postStatement.setInt(i, pageRequest.getStartNumber());
            ResultSet resultSet = postStatement.executeQuery();
            posts = getRequestResult(connection, resultSet);
            ResultSet resultSet1 = countStatement.executeQuery();
            resultSet1.next();
            int postCount = resultSet1.getInt(1);
            return new MyPair<>(posts, postCount);
        } catch (SQLException | DAOException | ParseException e) {
            logger.error("DB connection error", e);
            e.printStackTrace();
            return new MyPair<>(Collections.emptyList(), 0);
        }
    }
}
