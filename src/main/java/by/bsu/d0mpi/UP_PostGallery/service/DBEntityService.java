package by.bsu.d0mpi.UP_PostGallery.service;

import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;

import java.util.List;

public interface DBEntityService <K extends Number, T extends DatabaseEntity>{
    boolean createEntity(T entity);

    void delete(T entity);

    void delete(K id);

    List<T> findAll();

    T findEntityById(K id);

    T editEntity(T entity);

    int getEntriesCount();
}
