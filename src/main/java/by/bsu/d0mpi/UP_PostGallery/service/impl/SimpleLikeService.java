package by.bsu.d0mpi.UP_PostGallery.service.impl;

import by.bsu.d0mpi.UP_PostGallery.command.page.ShowErrorPage;
import by.bsu.d0mpi.UP_PostGallery.dao.LikeDao;
import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import by.bsu.d0mpi.UP_PostGallery.service.LikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Class-service required for interacting with the {@link LikeDao} and performing intermediate actions.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see LikeDao
 */
public class SimpleLikeService implements LikeService {
    private final LikeDao likeDao = LikeDao.MySQL();
    private static final Logger logger = LogManager.getLogger();
    private static volatile SimpleLikeService instance;

    /**
     * Provide a global access point to the instance of the {@link SimpleLikeService} class.
     *
     * @return the only instance of the {@link SimpleLikeService} class
     */
    public static SimpleLikeService getInstance() {
        SimpleLikeService localInstance = instance;
        if (localInstance == null) {
            synchronized (SimpleLikeService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SimpleLikeService();
                }
            }
        }
        return localInstance;
    }


    /**
     * Invokes {@link LikeDao#create(DatabaseEntity)}
     *
     * @param entity the {@link Like} object the information about
     *               which should be added to the database
     * @return {@link Like} object if information about it was updated and null otherwise
     */
    @Override
    public Like createEntity(Like entity) {
        return likeDao.create(entity);
    }

    /**
     * Invokes {@link LikeDao#delete(DatabaseEntity)}
     *
     * @param entity {@link Like} needs to be deleted
     */
    @Override
    public void delete(Like entity) {
        likeDao.delete(entity);
    }

    /**
     * Invokes {@link LikeDao#delete(Number)}
     *
     * @param id ID of the {@link Like} needs to be deleted
     */
    @Override
    public void delete(Integer id) {
        likeDao.delete(id);
    }

    /**
     * Invokes {@link LikeDao#findAll()}
     *
     * @return {@link List} of all likes contained in the database
     * with specified id.
     */
    @Override
    public List<Like> findAll() {
        return likeDao.findAll();
    }

    /**
     * Invokes {@link LikeDao#findEntityById(Number)}
     *
     * @param id entity ID
     * @return {@link List} of all likes contained in the database
     * with specified id.
     */
    @Override
    public Like findEntityById(Integer id) {
        return likeDao.findEntityById(id);
    }

    /**
     * Invokes {@link LikeDao#update(DatabaseEntity)}
     *
     * @param entity the {@link Like} object the information about which should be updated in the database
     * @return {@link Like} object if information about it was updated and null otherwise
     */
    @Override
    public Like editEntity(Like entity) {
        return likeDao.update(entity);
    }

    /**
     * Invokes {@link LikeDao#delete(Integer, String)}
     *
     * @param postId id of the post to remove the like from
     * @param login  the login of the user who removed the like
     */
    @Override
    public void delete(Integer postId, String login) {
        likeDao.delete(postId, login);
    }

    /**
     * Invokes {@link LikeDao#getLikedPostIdList(String)}
     *
     * @param login the name of the user whose list of likes you want to get
     * @return {@link List} of the {@link Like} IDes belong to the specified {@link by.bsu.d0mpi.UP_PostGallery.model.User}
     */
    @Override
    public List<Integer> getLikedPostIdList(String login) {
        return likeDao.getLikedPostIdList(login);
    }

    /**
     * Invokes {@link LikeDao#getEntriesCount()} (String)}
     *
     * @return count of all likes
     */
    @Override
    public int getEntriesCount() {
        return likeDao.getEntriesCount();
    }
}
