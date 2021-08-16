package by.bsu.d0mpi.UP_PostGallery.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import by.bsu.d0mpi.UP_PostGallery.dao.PostDao;
import by.bsu.d0mpi.UP_PostGallery.dao.UserDao;
import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlUserDao;
import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Class-service required for interacting with the {@link UserDao} and performing intermediate actions.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see UserDao
 */
public class SimpleUserService implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static volatile SimpleUserService instance;
    private final MySqlUserDao userDao = UserDao.MySQL();

    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifier;

    SimpleUserService() {
        this.hasher = BCrypt.withDefaults();
        this.verifier = BCrypt.verifyer();
    }

    /**
     * Provide a global access point to the instance of the {@link SimpleUserService} class.
     *
     * @return the only instance of the {@link SimpleUserService} class
     */
    public static SimpleUserService getInstance() {
        SimpleUserService localInstance = instance;
        if (localInstance == null) {
            synchronized (SimpleUserService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SimpleUserService();
                }
            }
        }
        return localInstance;
    }

    /**
     * Invokes {@link PostDao#create(DatabaseEntity)} and hashes the password.
     *
     * @param user the {@link User} object the information about
     *             *               which should be added to the database
     * @return {@link User} object if information about it was updated and null otherwise
     */
    @Override
    public User createEntity(User user) {
        final char[] rawPassword = user.getPassword().toCharArray();
        final String encryptedPassword = hasher.hashToString(MIN_COST, rawPassword);
        return userDao.create(new User(user.getLogin(), encryptedPassword, user.getRole()));
    }

    /**
     * Invokes {@link UserDao#delete(Number)}
     *
     * @param entity ID of the {@link User} needs to be deleted
     */
    @Override
    public void delete(User entity) {
        userDao.delete(entity);
    }

    /**
     * Invokes {@link UserDao#delete(Number)}
     *
     * @param id ID of the {@link User} needs to be deleted
     */
    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    /**
     * Invokes {@link UserDao#findAll()}
     *
     * @return {@link List} of all users contained in the database with specified id.
     */
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * Invokes {@link UserDao#findEntityById(Number)}
     *
     * @param id entity ID
     * @return {@link List} of all users contained in the database with specified id.
     */
    @Override
    public User findEntityById(Integer id) {
        return userDao.findEntityById(id);
    }

    /**
     * Invokes {@link UserDao#update(DatabaseEntity)}
     *
     * @param entity the {@link User} object the information about which should be updated in the database
     * @return {@link User} object if information about it was updated and null otherwise
     */
    @Override
    public User editEntity(User entity) {
        return userDao.update(entity);
    }

    /**
     * Checks whether specified user can log in.
     *
     * @param user user
     * @return true - if user can be logged in, otherwise returns false
     */
    @Override
    public boolean canLogIn(User user) {
        final byte[] enteredPassword = user.getPassword().getBytes(UTF_8);
        final User persistedUser = this.findUserByLogin(user.getLogin()).orElse(null);
        if (persistedUser == null) {
            return false;
        }
        final byte[] encryptedPasswordFromDb = persistedUser.getPassword().getBytes(UTF_8);
        return verifier.verify(enteredPassword, encryptedPasswordFromDb).verified;

    }

    /**
     * Invokes {@link UserDao#findUserByLogin(String)}
     *
     * @param login user login
     * @return {@link Optional} object with {@link User} or null
     */
    @Override
    public Optional<User> findUserByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

    /**
     * Invokes {@link UserDao#isLoginPresented(String)}
     *
     * @param login user login
     * @return true - if login presented in the database, otherwise returns false
     */
    @Override
    public boolean isLoginPresented(String login) {
        return userDao.isLoginPresented(login);
    }

    /**
     * Invokes {@link UserDao#getEntriesCount()} (String)}
     *
     * @return count of all users
     */
    @Override
    public int getEntriesCount() {
        return userDao.getEntriesCount();
    }

    /**
     * Invokes {@link UserDao#getNumberOfPostsByAuthor(String)}
     *
     * @param login user login
     * @return number of posts belong to the specified {@link User}
     */
    @Override
    public int getNumberOfPosts(String login) {
        return userDao.getNumberOfPostsByAuthor(login);
    }

    /**
     * Invokes {@link UserDao#getNumberOfLikesByAuthor(String)}
     *
     * @param login user login
     * @return number of likes belong to the specified {@link User}
     */
    @Override
    public int getRating(String login) {
        return userDao.getNumberOfLikesByAuthor(login);
    }

    /**
     * Changes password of the specified user
     *
     * @param newUser user with new password
     * @return user with new password
     */
    @Override
    public User changePassword(User newUser) {
        final char[] rawPassword = newUser.getPassword().toCharArray();
        final String encryptedPassword = hasher.hashToString(MIN_COST, rawPassword);
        newUser.setPassword(encryptedPassword);
        return userDao.update(newUser);
    }
}
