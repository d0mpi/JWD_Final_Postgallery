package by.bsu.d0mpi.UP_PostGallery.pool;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlLikeDao;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe implementation of {@link ConnectionPool}.
 * Connection pool is a well-known data access pattern, whose main
 * purpose is to reduce the overhead involved in performing database
 * connections and read/write database operations.
 *
 * @author d0mpi
 * @version 2.0
 * @see PoolConfiguration
 * @see ProxyConnection
 * @see Lock
 * @see Condition
 * @see Timer
 */
public class BasicConnectionPool implements ConnectionPool {

    private static final Logger logger = LogManager.getLogger(BasicConnectionPool.class);

    private final Lock locker = new ReentrantLock();
    private final Condition condition = locker.newCondition();
    private final BlockingDeque<ProxyConnection> freeConnections;
    private final BlockingDeque<ProxyConnection> usedConnections;
    private static BasicConnectionPool instance;
    private final PoolConfiguration config;
    private final AtomicBoolean isActive;

    /**
     * Provide a global access point to the instance of the {@link BasicConnectionPool} class.
     * Аt the first time, it starts the constructor, which starts a {@link TimerTask}
     * that is executed every specified time interval.
     * This task checks the number of free connections and removes the extra ones if necessary.
     *
     * @return the only instance of the {@link BasicConnectionPool} class
     */
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
        usedConnections = new LinkedBlockingDeque<>();
        isActive = new AtomicBoolean(false);

        final Timer timer = new Timer(true);
        final CheckPool checkPool = new CheckPool();
        timer.schedule(checkPool, config.DB_INTERVAL, config.DB_INTERVAL);
    }

    /**
     * Initializes the pool with the initial number of connections by placing then
     * in the {@link BasicConnectionPool#freeConnections}
     * Also registers the driver for connecting to the database.
     * Uses {@link ReentrantLock} to avoid concurrency problems.
     */
    public void init() {
        locker.lock();
        if (isActive.get())
            this.destroy();
        try {
            registerDrivers();
            for (int counter = 0; counter < config.DB_INIT_POOL_SIZE; counter++) {
                freeConnections.put(createConnection());
            }
            isActive.set(true);
        } catch (SQLException | InterruptedException e) {
            logger.fatal("Impossible to initialize connection pool", e);
        } finally {
            locker.unlock();
        }
    }

    /**
     * Checks whether the connection can be used
     *
     * @param conn connection with the database
     * @return true - if connection is valid, otherwise returns false
     * @throws SQLException - if database access occurs
     */
    private Boolean isValidConnection(Connection conn) throws SQLException {
        return conn != null && !conn.isClosed();
    }

    /**
     * If there is free connection in the pool, it returns it.
     * If there is no free connection then it expands the pool until the maximum allowed size is reached.
     * When the maximum number of connection is reached, new connections go into the waiting state
     * until connections become available.
     * Uses {@link ReentrantLock} to avoid concurrency problems.
     *
     * @return {@link ProxyConnection} from the {@link BasicConnectionPool}
     * @throws DAOException - if something goes wrong with DAO
     */
    @Override
    public ProxyConnection getConnection() throws DAOException {
        locker.lock();
        ProxyConnection connection = null;

        if (this.usedConnections.size() < this.config.DB_MAX_POOL_SIZE) {
            if (this.freeConnections.size() > 0) {
                connection = this.freeConnections.pollFirst();

                try {
                    if (this.isValidConnection(connection)) {
                        this.usedConnections.add(connection);
                    } else {
                        connection = this.getConnection();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    connection = this.createConnection();
                    this.usedConnections.add(connection);
                    for (int i = 0; i < config.DB_GROW_SIZE - 1; i++) {
                        this.freeConnections.add(this.createConnection());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            long startTime = System.currentTimeMillis();

            try {
                boolean isAwait = condition.await(config.DB_INTERVAL, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            if (this.config.DB_TIME_OUT != 0) {
//                if (System.currentTimeMillis() - startTime > config.DB_TIME_OUT)
//                    throw new DAOException("GG");
//            }
            connection = this.getConnection();
        }

        logger.info(String.format("Connection was gotten from pool. Current pool size: %d used connections; %d free connection%n", usedConnections.size(), freeConnections.size()));
        System.out.printf("Connection was gotten from pool. Current pool size: %d used connections; %d free connection%n", usedConnections.size(), freeConnections.size());

        locker.unlock();
        return connection;
    }

    /**
     * Creates a new database connection if possible
     * Uses {@link ReentrantLock} to avoid concurrency problems.
     *
     * @return new {@link ProxyConnection}
     * @throws SQLException - if database access occurs
     */
    private ProxyConnection createConnection() throws SQLException {
        locker.lock();
        try {
            locker.unlock();
            return new ProxyConnection(DriverManager.getConnection(config.DB_URL, config.DB_USER_NAME, config.DB_PASSWORD));
        } catch (SQLException e) {
            condition.signalAll();
            e.printStackTrace();
            locker.unlock();
            throw new SQLException("Connection not available", e);
        }
    }

    /**
     * Release connection - removes it from {@link #usedConnections}.
     * If connection is valid, adds it to {@link #freeConnections},
     * otherwise adds new connection to the connection pool.
     * Signal all waiting connection about the appearance of a free connection.
     * Uses {@link ReentrantLock} to avoid concurrency problems.
     *
     * @param connection connection with the database
     */
    @Override
    public void releaseConnection(ProxyConnection connection) {
        locker.lock();
        usedConnections.remove(connection);

        try {
            if (isValidConnection(connection)) {
                freeConnections.add(connection);
            } else {
                freeConnections.add(this.createConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        condition.signalAll();
        locker.unlock();

        System.out.printf("Connection was returned into pool. Current pool size: %d used connections; %d free connection%n", usedConnections.size(), freeConnections.size());
        logger.debug(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
    }

    /**
     * Destroys {@link BasicConnectionPool} and clears {@link #freeConnections} and {@link #usedConnections}
     * Uses {@link ReentrantLock} to avoid concurrency problems.
     */
    public void destroy() {
        locker.lock();
        for (ProxyConnection connection : usedConnections) {
            connection.close();
        }
        this.isActive.set(false);
        freeConnections.clear();
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
        this.isActive.set(true);

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
        this.isActive.set(false);
        AbandonedConnectionCleanupThread.uncheckedShutdown();
    }


    private class CheckPool extends TimerTask {
        @Override
        public void run() {
            if (isNeedToReducePool()) {
                for (int i = config.DB_GROW_SIZE; i > 0; i--) {
                    try {
                        final Connection connection = freeConnections.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        private boolean isNeedToReducePool() {
            return freeConnections.size() > Math.max(config.DB_INIT_POOL_SIZE + 1, config.DB_GROW_SIZE + 1);
        }
    }

}
