package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;
import by.bsu.d0mpi.UP_PostGallery.model.User;

import java.util.List;

public interface Dao<K extends Number, T extends DatabaseEntity> {
    List<T> findAll();

    T findEntityById(K id);

    boolean delete(K id);

    boolean delete(T entity);

    T create(T entity);

    T update(T entity);

    int getEntriesCount();
}
