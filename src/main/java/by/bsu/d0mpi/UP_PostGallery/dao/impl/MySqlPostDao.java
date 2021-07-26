package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.PostDao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import by.bsu.d0mpi.UP_PostGallery.service.FilterService;
import by.bsu.d0mpi.UP_PostGallery.service.FilterType;
import by.bsu.d0mpi.UP_PostGallery.service.MyPair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MySqlPostDao extends MySqlAbstractDao<Integer, Post> implements PostDao {
    private static final String SQL_UPDATE_POST =
            "UPDATE posts SET post_model = ?, post_type = ?,post_length = ?, post_wingspan = ?, post_height = ?," +
                    " post_origin = ?, post_crew = ?, post_speed = ?, post_distance = ?, post_price = ?," +
                    " post_author = ? WHERE post_id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO posts (post_model ,post_type, post_length, post_wingspan, post_height, post_origin, post_crew," +
                    " post_speed, post_distance, post_price, post_create_date, post_author)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_POST_ID_LIST_BY_AUTHOR =
            "SELECT * FROM posts WHERE posts.post_author = ?";
    private static final String SQL_SELECT_POST_BY_ID =
            "SELECT * FROM posts WHERE posts.post_id = ?";
    public static final String SQL_INSERT_HASHTAGS_WITH_POST_ID =
            "INSERT INTO hashtags (hashtag_text, hashtags_post_id) VALUES (?, ?)";
    private static final String SQL_DELETE_HASHTAGS_BY_POST_ID =
            "DELETE FROM hashtags WHERE hashtags_post_id = ?";
    private static final String SQL_SELECT_HASHTAGS_BY_POST_ID =
            "select hashtags.hashtag_text from posts JOIN hashtags WHERE posts.post_id = hashtags.hashtags_post_id AND posts.post_id = ?";

    private static final Logger LOGGER = LogManager.getLogger();

    private final FilterService filterService;

    private MySqlPostDao(String tableName, String idColumn) {
        super(tableName, idColumn);
        filterService = FilterService.simple();
    }

    private static volatile MySqlPostDao instance;

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

    @Override
    protected List<Post> mapResultSet(Connection connection, ResultSet rs) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String model = rs.getString(2);
            String type = rs.getString(3);
            Float length = rs.getFloat(4);
            Float wingspan = rs.getFloat(5);
            Float height = rs.getFloat(6);
            String origin = rs.getString(7);
            Integer crew = rs.getInt(8);
            Float speed = rs.getFloat(9);
            Float distance = rs.getFloat(10);
            Integer price = rs.getInt(11);
            Date createdAt = rs.getDate(12);
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
                    price, createdAt, author, hashtags));
        }
        return posts;
    }


    @Override
    protected void setDefaultStatementArgs(PreparedStatement statement, Post entity) throws SQLException {
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
        java.util.Date dt = entity.getCreatedAt();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String created_date = sdf.format(dt);
        statement.setString(11, created_date);
        statement.setString(12, entity.getAuthor());
    }

    @Override
    public Post create(Post entity) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
             final PreparedStatement statement1 = connection.prepareStatement(SQL_INSERT_HASHTAGS_WITH_POST_ID)) {
            setDefaultStatementArgs(statement, entity);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                entity.setId(id);
            } else {
                LOGGER.error("No autoincremented index after trying to add record into table user");
                return null;
            }
            for (String hashtag : entity.getHashtags()) {
                statement1.setString(1, hashtag);
                statement1.setInt(2, entity.getId());
                statement1.executeUpdate();
            }
        } catch (SQLException | DAOException e) {
            LOGGER.error("DB connection error", e);
            return null;
        }
        return entity;
    }

    @Override
    public Post update(Post entity) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_POST);
             final PreparedStatement statement1 = connection.prepareStatement(SQL_DELETE_HASHTAGS_BY_POST_ID);
             final PreparedStatement statement2 = connection.prepareStatement(SQL_INSERT_HASHTAGS_WITH_POST_ID)) {
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
            statement.executeUpdate();

            statement1.setInt(1, entity.getId());
            statement1.executeUpdate();
            for (String hashtag : entity.getHashtags()) {
                statement2.setString(1, hashtag);
                statement2.setInt(2, entity.getId());
                statement2.executeUpdate();
            }
            return entity;
        } catch (SQLException | DAOException e) {
            LOGGER.error("DB connection error", e);
            return entity;
        }
    }

    @Override
    public List<Post> getPostsByAuthorLogin(String login) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_POST_ID_LIST_BY_AUTHOR)
        ) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return mapResultSet(connection, resultSet);
        } catch (SQLException | DAOException e) {
            LOGGER.error("DB connection error", e);
            return Collections.emptyList();
        }
    }


    @Override
    public MyPair<List<Post>, Integer> getPage(int startNumber, ArrayList<FilterType> filters, ArrayList<String> filterParams) {
        List<Post> posts;
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement =
                     connection.prepareStatement(filterService.buildAndGetPageWithFiltersRequest(filters));
             final PreparedStatement statement1 = connection.prepareStatement(filterService.buildAndGetPostsCountWithFiltersRequest(filters))
        ) {
            int i = 0;
            if (filters.contains(FilterType.HASHTAG)) {
                statement.setString(i + 1, filterParams.get(i));
                statement1.setString(i + 1, filterParams.get(i));
                i++;
            }
            if (filters.contains(FilterType.AUTHOR)) {
                statement.setString(i + 1, filterParams.get(i));
                statement1.setString(i + 1, filterParams.get(i));
                i++;
            }
            if (filters.contains(FilterType.DATE)) {
                statement.setString(i + 1, filterParams.get(i));
                statement1.setString(i + 1, filterParams.get(i));
                i++;
            }
            statement.setInt(i + 1, startNumber);
            ResultSet resultSet = statement.executeQuery();
            posts = mapResultSet(connection, resultSet);
            ResultSet resultSet1 = statement1.executeQuery();
            resultSet1.next();
            int postCount = resultSet1.getInt(1);
            return new MyPair<>(posts, postCount);
        } catch (SQLException | DAOException e) {
            LOGGER.error("DB connection error", e);
            return new MyPair<>(Collections.emptyList(), 0);
        }
    }
}
