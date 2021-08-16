package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySQLUserDaoTest {
    public static final String TEST_DATABASE_PROPERTIES = "test_database.properties";
    private static final BasicConnectionPool POOL = BasicConnectionPool.getInstance();
    private static final UserService USER_SERVICE = UserService.simple();
    private static final PostDao POST_DAO = PostDao.MySQL();
    private static final List<User> initUserList = new ArrayList<>();
    private static final List<Post> initPostList = new ArrayList<>();


    @BeforeClass
    public static void launchPool() {
        POOL.init(TEST_DATABASE_PROPERTIES);
        User user;
        user = USER_SERVICE.createEntity(new User(1, "admin1", "admin", Role.ADMIN, new Date(System.currentTimeMillis())));
        initUserList.add(user);
        user = USER_SERVICE.createEntity(new User(1, "admin2", "admin", Role.ADMIN, new Date(System.currentTimeMillis())));
        initUserList.add(user);
        user = USER_SERVICE.createEntity(new User(1, "admin3", "admin", Role.ADMIN, new Date(System.currentTimeMillis())));
        initUserList.add(user);
        user = USER_SERVICE.createEntity(new User(1, "admin4", "admin", Role.ADMIN, new Date(System.currentTimeMillis())));
        initUserList.add(user);
        user = USER_SERVICE.createEntity(new User(1, "admin5", "admin", Role.ADMIN, new Date(System.currentTimeMillis())));
        initUserList.add(user);

        Post post;
        post = POST_DAO.create(new Post(1, "model1", "fighter", new BigDecimal("1.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin1", List.of("first", "second")));
        initPostList.add(post);
        post = POST_DAO.create(new Post(2, "model2", "fighter", new BigDecimal("2.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin1", List.of("first", "second")));
        initPostList.add(post);
        post = POST_DAO.create(new Post(3, "model3", "fighter", new BigDecimal("3.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin1", List.of("first", "second")));
        initPostList.add(post);
        post = POST_DAO.create(new Post(4, "model4", "fighter", new BigDecimal("4.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin4", List.of("first", "second")));
        initPostList.add(post);
        post = POST_DAO.create(new Post(5, "model5", "fighter", new BigDecimal("5.00"), new BigDecimal("2.00"), new BigDecimal("3.00"), "US", 1, new BigDecimal("10.00"), new BigDecimal("11.00"), new BigDecimal("12.00"), new Date(System.currentTimeMillis()), "admin5", List.of("first", "second")));
        initPostList.add(post);
    }

    @AfterClass
    public static void destroyPool() {
        initUserList.forEach(USER_SERVICE::delete);
        initUserList.clear();
        initPostList.forEach(POST_DAO::delete);
        initPostList.clear();
        POOL.destroy();
    }

    @Test
    public void findUserByLogin_expected_notNull() {
        Assert.assertNotNull(USER_SERVICE.findUserByLogin("admin1").orElse(null));
    }

    @Test
    public void getCountOfUsers_expected_Five() {
        Assert.assertEquals(5, USER_SERVICE.getEntriesCount());
    }

    @Test
    public void findAll_expected_FiveUsers() {
        List<User> users = USER_SERVICE.findAll();
        Assert.assertArrayEquals(initUserList.toArray(), users.toArray());
    }

    @Test
    public void canUserLogIn_expected_False(){
        Assert.assertFalse(USER_SERVICE.canLogIn(new User("admin", "admin", Role.USER)));
    }

    @Test
    public void getNumberOfPostsByAuthor_expected_Three(){
        Assert.assertEquals(3, USER_SERVICE.getNumberOfPosts("admin1"));
    }

    @Test
    public void getNumberOfLikesByAuthor_expected_Zero(){
        Assert.assertEquals(0, USER_SERVICE.getRating("admin1"));
    }
}
