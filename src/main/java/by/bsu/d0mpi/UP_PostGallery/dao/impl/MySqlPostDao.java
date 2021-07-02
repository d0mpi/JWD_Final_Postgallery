package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.PostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlPostDao extends MySqlAbstractDao<Integer, Post> implements PostDao {
    public static final String SQL_SELECT_ALL_POSTS = "SELECT * FROM posts";
    public static final String SQL_SELECT_POST_ID = "SELECT * FROM posts WHERE post_id = ?";
    private static final String SQL_UPDATE_POST =
            "UPDATE posts SET post_model = ?, post_type = ?,post_length = ?, post_wingspan = ?, post_height = ?," +
                    " post_origin = ?, post_crew = ?, post_speed = ?, post_distance = ?, post_price = ?," +
                    " post_create_date = ?, post_photo = ?, post_author_id = ? WHERE post_id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO posts (post_model ,post_type, post_length, post_wingspan, post_height, post_origin, post_crew," +
                    " post_speed, post_distance, post_price, post_create_date, post_photo, post_author_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_HASHTAGS_WITH_POST_ID =
            "INSERT INTO hashtags (hashtag_text, hashtags_post_id) VALUES (?, ?)";
    private static final String SQL_DELETE_LIKES_BY_POST_ID = "DELETE FROM likes WHERE likes_post_id = ?";
    private static final String SQL_DELETE_HASHTAGS_BY_POST_ID = "DELETE FROM hashtags WHERE hashtags_post_id = ?";
    private static final String SQL_DELETE_POST_BY_ID =
            "DELETE FROM posts WHERE post_id = ?";
    private static final String SQL_DISABLE_LIKE_FROM_POST_BY_AUTHOR = "DELETE FROM likes WHERE likes_post_id = ? AND likes_user_id = ?";
    private static final String SQL_ENABLE_LIKE_FROM_POST_BY_AUTHOR = "INSERT INTO likes (likes_user_id, likes_post_id) VALUES (?, ?)";
    private static final String SQL_SELECT_HASHTAGS_BY_POST_ID = "select hashtags.hashtag_text from posts JOIN hashtags WHERE posts.post_author_id = hashtags.hashtags_post_id AND posts.post_id = ?";
    private static final String SQL_SELECT_LIKES_AUTHORS_BY_POST_ID = "select likes.likes_user_id from posts JOIN likes WHERE posts.post_author_id = likes.likes_post_id AND posts.post_id = ?";

    private static final Logger LOGGER = LogManager.getLogger();

    private static volatile MySqlPostDao instance;

    protected MySqlPostDao(String tableName, String idColumn) {
        super(tableName, idColumn);
    }

    public static MySqlPostDao getInstance() {
        MySqlPostDao localInstance = instance;
        if (localInstance == null) {
            synchronized (MySqlPostDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySqlPostDao("posts","post_id");
                }
            }
        }
        return localInstance;
    }

    @Override
    protected Post mapResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Post> findAll() {
        ArrayList<Post> posts = new ArrayList<>();
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_POSTS);
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
                LocalDate createdAt = rs.getDate(12).toLocalDate();
                String author = MySqlUserDao.getInstance().findEntityById((rs.getInt(13))).getLogin();
                String photoLink = rs.getString(14);
                PreparedStatement statement2 = connection.prepareStatement(SQL_SELECT_HASHTAGS_BY_POST_ID);
                statement2.setInt(1, id);
                ResultSet rsHash = statement2.executeQuery();
                List<String> hashtags = new LinkedList<>();
                while (rsHash.next()) {
                    hashtags.add(rsHash.getString(1));
                }
                statement2.close();
                PreparedStatement statement3 = connection.prepareStatement(SQL_SELECT_LIKES_AUTHORS_BY_POST_ID);
                statement3.setInt(1, id);
                ResultSet rsLike = statement3.executeQuery();
                List<String> likeList = new LinkedList<>();
                while (rsLike.next()) {
                    likeList.add(rsLike.getString(1));
                }
                statement3.close();
                posts.add(new Post(id, model, type, length, wingspan, height, origin, crew, speed, distance,
                        price, createdAt, author, photoLink, hashtags, likeList));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }

    @Override
    public Post findEntityById(Integer id) {
        Post post = null;
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_POST_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
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
                LocalDate createdAt = rs.getDate(12).toLocalDate();
                String author = MySqlUserDao.getInstance().findEntityById((rs.getInt(13))).getLogin();
                String photoLink = rs.getString(14);
                PreparedStatement statement2 = connection.prepareStatement(SQL_SELECT_HASHTAGS_BY_POST_ID);
                statement2.setInt(1, id);
                ResultSet rsHash = statement2.executeQuery();
                List<String> hashtags = new LinkedList<>();
                while (rsHash.next()) {
                    hashtags.add(rsHash.getString(1));
                }
                statement2.close();
                PreparedStatement statement3 = connection.prepareStatement(SQL_SELECT_LIKES_AUTHORS_BY_POST_ID);
                statement3.setInt(1, id);
                ResultSet rsLike = statement3.executeQuery();
                List<String> likeList = new LinkedList<>();
                while (rsLike.next()) {
                    likeList.add(rsLike.getString(1));
                }
                statement3.close();
                post = new Post(id, model, type, length, wingspan, height, origin, crew, speed, distance,
                        price, createdAt, author, photoLink, hashtags, likeList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return post;
    }

    @Override
    public boolean delete(Integer id) {
        boolean deleted = false;
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_HASHTAGS_BY_POST_ID);
             PreparedStatement statement2 = connection.prepareStatement(SQL_DELETE_LIKES_BY_POST_ID);
             PreparedStatement statement3 = connection.prepareStatement(SQL_DELETE_POST_BY_ID)) {
            statement.setInt(1, id);
            statement2.setInt(1, id);
            statement3.setInt(1, id);
            statement.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            deleted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public boolean delete(Post entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean addLike(Integer postId, String author) {
        boolean disbled = false;
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DISABLE_LIKE_FROM_POST_BY_AUTHOR);
        ) {
            statement.setInt(1, postId);
            statement.setString(2, author);
            statement.executeUpdate();
            disbled = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disbled;
    }

    @Override
    public boolean removeLike(Integer postId, String author) {
        boolean enabled = false;
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ENABLE_LIKE_FROM_POST_BY_AUTHOR);
        ) {
            statement.setString(1, author);
            statement.setInt(2, postId);
            statement.executeUpdate();
            enabled = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enabled;
    }

    @Override
    public boolean create(Post entity) {
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statement1 = connection.prepareStatement(SQL_INSERT_HASHTAGS_WITH_POST_ID)) {
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
            statement.setString(11, String.valueOf(entity.getCreatedAt()));
            statement.setString(12, entity.getAuthor());
            statement.setString(13, entity.getPhotoLink());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                entity.setId(id);
            } else {
                LOGGER.error("No autoincremented index after trying to add record into table user");
                return false;
            }
            for (String hashtag : entity.getHashtags()) {
                statement1.setString(1, hashtag);
                statement1.setInt(2, entity.getId());
                statement1.executeUpdate();
            }
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
            return false;
        }
        return true;
    }

    @Override
    public void update(Post entity) {
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_POST);
             PreparedStatement statement1 = connection.prepareStatement(SQL_DELETE_HASHTAGS_BY_POST_ID);
             PreparedStatement statement2 = connection.prepareStatement(SQL_INSERT_HASHTAGS_WITH_POST_ID)) {
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
            statement.setString(11, String.valueOf(entity.getCreatedAt()));
            statement.setString(12, entity.getAuthor());
            statement.setString(13, entity.getPhotoLink());
            statement.setInt(14, entity.getId());
            statement.executeUpdate();

            statement1.setInt(1,entity.getId());
            statement1.executeUpdate();

            for (String hashtag : entity.getHashtags()) {
                statement2.setString(1, hashtag);
                statement2.setInt(2, entity.getId());
                statement2.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("DB connection error", e);
        }
    }

}