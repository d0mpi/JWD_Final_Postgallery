package by.bsu.d0mpi.UP_PostGallery.pool;

import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BasicConnectionPool implements ConnectionPool {

    private static final Logger logger = LogManager.getLogger(BasicConnectionPool.class);
    private final Lock locker = new ReentrantLock();
    private final BlockingDeque<ProxyConnection> freeConnections;
    private final Set<ProxyConnection> usedConnections;
    private static BasicConnectionPool instance;
    private final PoolConfiguration config;

    public static BasicConnectionPool getInstance() {
        BasicConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (BasicConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BasicConnectionPool();
                }
            }
        }
        return localInstance;
    }

    private BasicConnectionPool() {
        config = PoolConfiguration.getInstance();
        freeConnections = new LinkedBlockingDeque<>();
        usedConnections = new ConcurrentSkipListSet<>();
    }

    public void init() {
        locker.lock();
        try {
            destroy();
            registerDrivers();
            for (int counter = 0; counter < config.DB_INITIAL_POOL_SIZE; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (SQLException | InterruptedException e) {
            logger.fatal("Impossible to initialize connection pool", e);
        } finally {
            locker.unlock();
        }
    }

    private Boolean isValidConnection(Connection conn) throws SQLException {
        try {
            if (conn == null || conn.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return true;
    }

    @Override
    public Connection getConnection() throws DAOException {
        locker.lock();
        ProxyConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if (!connection.isValid(5)) {
                        connection.getConnection().close();
                        connection = null;
                    }
                } else if (usedConnections.size() < config.DB_MAX_POOL_SIZE) {
                    for (int i = 0; i < config.DB_GROW_SIZE; i++) {
                        final ProxyConnection proxyConnection = createConnection();
                        freeConnections.add(proxyConnection);
                    }
                    connection = freeConnections.take();
                } else {
                    logger.error("Number of database connections is exceeded");
                    locker.unlock();
                    throw new DAOException();
                }
            } catch (InterruptedException | SQLException e) {
                logger.error("Impossible to connect to a database", e);
                locker.unlock();
                throw new DAOException();
            }
        }
        usedConnections.add(connection);

        logger.info(String.format("Connection was gotten from pool. Current pool size: %d used connections; %d free connection%n", usedConnections.size(), freeConnections.size()));
        System.out.printf("Connection was gotten from pool. Current pool size: %d used connections; %d free connection%n", usedConnections.size(), freeConnections.size());
        locker.unlock();
        return connection;
    }

    private ProxyConnection createConnection() throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(config.DB_URL, config.DB_USER_NAME, config.DB_PASSWORD));
    }

    @Override
    public void releaseConnection(ProxyConnection connection) {
        locker.lock();
        try {
            if (connection.isValid(5)) {
                usedConnections.remove(connection);
                freeConnections.add(connection);
                System.out.printf("Connection was returned into pool. Current pool size: %d used connections; %d free connection%n", usedConnections.size(), freeConnections.size());
                logger.debug(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
            }
        } catch (SQLException e) {
            logger.warn("Impossible to return connection into pool", e);
            try {
                connection.getConnection().close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        locker.unlock();
    }

    public void destroy() {
        locker.lock();
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (ProxyConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
                locker.unlock();
                e.printStackTrace();
            }
        }
        usedConnections.clear();
        deregisterDrivers();
        locker.unlock();
    }


    private void registerDrivers() {
        System.out.println("sql drivers registration start...");
        try {
            Class.forName(config.DB_DRIVER);
            System.out.println("registration successful");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deregisterDrivers() {
        System.out.println("sql drivers unregistering start...");
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("unregistering drivers failed");
            }
        }
        AbandonedConnectionCleanupThread.uncheckedShutdown();
    }
}
