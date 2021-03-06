package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlLikeDao;
import by.bsu.d0mpi.UP_PostGallery.model.Like;

import java.util.List;

public interface LikeDao extends Dao<Integer, Like>{
    static LikeDao MySQL() {
        return MySqlLikeDao.getInstance();
    }

    void delete(Integer postId, String userLogin);

    List<Integer> getLikedPostIdList(String login);

}
