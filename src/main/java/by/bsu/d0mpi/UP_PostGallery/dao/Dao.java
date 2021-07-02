package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;

import java.util.List;

public interface Dao<K extends Number, T extends DatabaseEntity> {
    List<T> findAll();

    T findEntityById(K id);

    boolean delete(K id);

    boolean delete(T entity);

    boolean create(T entity);

    void update(T entity);
}
