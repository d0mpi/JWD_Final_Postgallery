package by.bsu.d0mpi.UP_PostGallery.pool;

import by.bsu.d0mpi.UP_PostGallery.exception.CouldNotInitConnectionPoolException;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();

    void releaseConnection(Connection connection);

    void init() throws DAOException, CouldNotInitConnectionPoolException;

    void destroy();
}
