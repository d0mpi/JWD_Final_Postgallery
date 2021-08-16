package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySQLPostDaoTest {
    public static final String TEST_DATABASE_PROPERTIES = "test_database.properties";
    private static final BasicConnectionPool POOL = BasicConnectionPool.getInstance();
    private static final PostDao POST_DAO = PostDao.MySQL();
    private static final UserDao USER_DAO = UserDao.MySQL();
    private static final List<Post> initPostList = new ArrayList<>();

    @BeforeClass
    public static void launchPool() {
        POOL.init(TEST_DATABASE_PROPERTIES);
        Post post;
        USER_DAO.create(new User(1, "admin", "admin", Role.ADMIN, new Date(System.currentTimeMillis())));
        post = POST_DAO.create(new Post(1, "model1", "fighter", new BigDecimal("1.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin", List.of("first", "second")));
        initPostList.add(post);
        post = POST_DAO.create(new Post(2, "model2", "fighter", new BigDecimal("2.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin", List.of("first", "second")));
        initPostList.add(post);
        post = POST_DAO.create(new Post(3, "model3", "fighter", new BigDecimal("3.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin", List.of("first", "second")));
        initPostList.add(post);
        post = POST_DAO.create(new Post(4, "model4", "fighter", new BigDecimal("4.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin", List.of("first", "second")));
        initPostList.add(post);
        post = POST_DAO.create(new Post(5, "model5", "fighter", new BigDecimal("5.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin", List.of("first", "second")));
        initPostList.add(post);
    }

    @AfterClass
    public static void destroyPool() {
        initPostList.forEach(POST_DAO::delete);
        initPostList.clear();
        POOL.destroy();
    }

    @Test
    public void getCountOfPosts_expected_Five() {
        Assert.assertEquals(5, POST_DAO.getEntriesCount());
    }

    @Test
    public void findAll_expected_FivePosts() {
        List<Post> postList = POST_DAO.findAll();
        Assert.assertArrayEquals(initPostList.stream().map(DatabaseEntity::getId).toArray(), postList.stream().map(DatabaseEntity::getId).toArray());
    }

    @Test
    public void update_expected_updatedPostModel(){
        Post localPost = initPostList.get(0);
        localPost.setModel("new model");
        Assert.assertEquals(localPost, POST_DAO.update(localPost));
    }

    @Test
    public void getPostsByAuthorLogin_expected_fivePosts(){
        List<Post> postList = POST_DAO.getPostsByAuthorLogin("admin");
        Assert.assertArrayEquals(initPostList.stream().map(DatabaseEntity::getId).toArray(), postList.stream().map(DatabaseEntity::getId).toArray());
    }
}
