package by.bsu.d0mpi.UP_PostGallery.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import by.bsu.d0mpi.UP_PostGallery.dao.UserDao;
import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlUserDao;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;
import static java.nio.charset.StandardCharsets.UTF_8;


public class SimpleUserService implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile SimpleUserService instance;
    private final MySqlUserDao userDao = UserDao.MySQL();

    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifier;

    SimpleUserService() {
        this.hasher = BCrypt.withDefaults();
        this.verifier = BCrypt.verifyer();
    }

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

    @Override
    public User createEntity(User user) {
        final char[] rawPassword = user.getPassword().toCharArray();
        final String encryptedPassword = hasher.hashToString(MIN_COST, rawPassword);
        return userDao.create(new User(user.getLogin(), encryptedPassword, user.getRole()));
    }

    @Override
    public void delete(User entity) {
        userDao.delete(entity);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findEntityById(Integer id) {
        return userDao.findEntityById(id);
    }

    @Override
    public User editEntity(User entity) {
        return userDao.update(entity);
    }

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

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

    @Override
    public boolean isLoginPresented(String login) {
        return userDao.isLoginPresented(login);
    }

    @Override
    public int getEntriesCount() {
        return userDao.getEntriesCount();
    }

    @Override
    public int getNumberOfPosts(String login){
        return userDao.getNumberOfPostsByAuthor(login);
    }

    @Override
    public int getRating(String login){
        return userDao.getNumberOfLikesByAuthor(login);
    }
}
