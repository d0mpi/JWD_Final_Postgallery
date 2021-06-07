package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;

import java.util.List;

public abstract class AbstractDao<K extends Number, T extends DatabaseEntity>{

    public abstract List<T> findAll();

    public abstract T findEntityById(K id);

    public abstract boolean delete(K id);

    public abstract boolean delete(T entity);

    public abstract boolean create(T entity);

    public abstract void update(T entity);
}
