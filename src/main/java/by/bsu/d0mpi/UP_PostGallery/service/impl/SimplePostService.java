package by.bsu.d0mpi.UP_PostGallery.service.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.PostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SimplePostService implements PostService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile SimplePostService instance;
    private final PostDao postDao = PostDao.MySQL();


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


    @Override
    public boolean createEntity(Post entity) {
        return postDao.create(entity);
    }

    @Override
    public void delete(Post entity) {
        postDao.delete(entity);
    }

    @Override
    public void delete(Integer id) {
        postDao.delete(id);
    }

    @Override
    public List<Post> findAll() {
        return postDao.findAll();
    }

    @Override
    public Post findEntityById(Integer id) {
        return postDao.findEntityById(id);
    }

    @Override
    public Post editEntity(Post entity) {
        return postDao.update(entity);
    }
}
