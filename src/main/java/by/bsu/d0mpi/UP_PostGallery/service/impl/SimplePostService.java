package by.bsu.d0mpi.UP_PostGallery.service.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.LikeDao;
import by.bsu.d0mpi.UP_PostGallery.dao.PostDao;
import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.MyPair;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.util.PageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class-service required for interacting with the {@link PostDao} and performing intermediate actions.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see PostDao
 */
public class SimplePostService implements PostService {
    private static final Logger logger = LogManager.getLogger();
    private static volatile SimplePostService instance;
    private final PostDao postDao = PostDao.MySQL();

    /**
     * Provide a global access point to the instance of the {@link SimplePostService} class.
     *
     * @return the only instance of the {@link SimplePostService} class
     */
    public static SimplePostService getInstance() {
        SimplePostService localInstance = instance;
        if (localInstance == null) {
            synchronized (SimplePostService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SimplePostService();
                }
            }
        }
        return localInstance;
    }

    /**
     * Invokes {@link PostDao#create(DatabaseEntity)}
     *
     * @param entity the {@link Post} object the information about which should be added to the database
     * @return {@link Post} object if information about it was updated and null otherwise
     */
    @Override
    public Post createEntity(Post entity) {
        return postDao.create(entity);
    }

    /**
     * Invokes {@link PostDao#delete(Number)}
     *
     * @param entity ID of the {@link Post} needs to be deleted
     */
    @Override
    public void delete(Post entity) {
        postDao.delete(entity);
    }

    /**
     * Invokes {@link PostDao#delete(Number)}
     *
     * @param id ID of the {@link Post} needs to be deleted
     */
    @Override
    public void delete(Integer id) {
        postDao.delete(id);
    }

    /**
     * Invokes {@link PostDao#findAll()}
     *
     * @return {@link List} of all posts contained in the database
     * with specified id.
     */
    @Override
    public List<Post> findAll() {
        return postDao.findAll();
    }

    /**
     * Invokes {@link PostDao#findEntityById(Number)}
     *
     * @param id entity ID
     * @return {@link List} of all posts contained in the database
     * with specified id.
     */
    @Override
    public Post findEntityById(Integer id) {
        return postDao.findEntityById(id);
    }

    /**
     * Invokes {@link PostDao#update(DatabaseEntity)}
     *
     * @param entity the {@link Post} object the information about which should be updated in the database
     * @return {@link Post} object if information about it was updated and null otherwise
     */
    @Override
    public Post editEntity(Post entity) {
        return postDao.update(entity);
    }

    /**
     * Invokes {@link PostDao#getEntriesCount()} (String)}
     *
     * @return count of all posts
     */
    @Override
    public int getEntriesCount() {
        return postDao.getEntriesCount();
    }


    /**
     * Checks whether the post belongs to the specified user.
     *
     * @param postId post ID
     * @param login  the username of the user you want to get information about
     * @return true - if post belongs to user, otherwise returns false
     */
    @Override
    public boolean postDoesNotBelongToUser(Integer postId, String login) {
        return !postDao.getPostsByAuthorLogin(login).stream().map(DatabaseEntity::getId).
                collect(Collectors.toList()).contains(postId);
    }

    /**
     * Invokes {@link PostDao#getPage(PageRequest)}
     *
     * @param pageRequest constructed query string
     * @return {@link MyPair<>} contained received {@link List} of posts and total number of posts
     */
    @Override
    public MyPair<List<Post>, Integer> getPage(PageRequest pageRequest) {
        return postDao.getPage(pageRequest);
    }


}
