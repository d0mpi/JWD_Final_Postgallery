package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;

import java.util.List;

public interface Dao<K extends Number, T extends DatabaseEntity> {
    List<T> findAll() throws DAOException;

    T findEntityById(K id);

    boolean delete(K id) throws DAOException;

    boolean delete(T entity) throws DAOException;

    boolean create(T entity) throws DAOException;

    void update(T entity);
}
