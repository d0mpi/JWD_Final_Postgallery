package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;

public interface PostDao extends Dao<Integer, Post>{
    static PostDao MySQL() {
        return MySqlPostDao.getInstance();
    }
}
