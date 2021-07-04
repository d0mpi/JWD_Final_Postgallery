package by.bsu.d0mpi.UP_PostGallery.pool;

import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection() throws DAOException;

    void releaseConnection(ProxyConnection connection);

    void init();

    void destroy();
}
