package by.bsu.d0mpi.UP_PostGallery.service.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.LikeDao;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.service.LikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SimpleLikeService implements LikeService {
    private final LikeDao likeDao = LikeDao.MySQL();
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile SimpleLikeService instance;

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


    @Override
    public boolean createEntity(Like entity) {
        return likeDao.create(entity);
    }

    @Override
    public void delete(Like entity) {
        likeDao.delete(entity);
    }

    @Override
    public void delete(Integer id) {
        likeDao.delete(id);
    }

    @Override
    public List<Like> findAll() {
        return likeDao.findAll();
    }

    @Override
    public Like findEntityById(Integer id) {
        return likeDao.findEntityById(id);
    }

    @Override
    public Like editEntity(Like entity) {
        return likeDao.update(entity);
    }

    @Override
    public void delete(Integer postId, String login) {
        likeDao.delete(postId, login);
    }

    @Override
    public List<Integer> getLikedPostIdList(String login) {
        return likeDao.getLikedPostIdList(login);
    }

    @Override
    public int getEntriesCount() {
        return likeDao.getEntriesCount();
    }
}
