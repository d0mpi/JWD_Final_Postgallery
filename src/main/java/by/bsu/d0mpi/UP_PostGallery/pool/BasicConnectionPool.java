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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BasicConnectionPool implements ConnectionPool {

    private static final Logger logger = LogManager.getLogger(BasicConnectionPool.class);
    private final Lock locker = new ReentrantLock();
    private final Condition condition = locker.newCondition();
    private final BlockingDeque<ProxyConnection> freeConnections;
    private final BlockingDeque<ProxyConnection> usedConnections;
    private static BasicConnectionPool instance;
    private final PoolConfiguration config;

    private final AtomicBoolean isActive;

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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        locker.unlock();
        return connection;
    }

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
