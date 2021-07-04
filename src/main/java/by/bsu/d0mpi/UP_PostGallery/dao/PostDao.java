package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;

public interface PostDao {

    boolean addLike(Integer postId, String author) throws DAOException;

    boolean removeLike(Integer postId, String author) throws DAOException;
}
