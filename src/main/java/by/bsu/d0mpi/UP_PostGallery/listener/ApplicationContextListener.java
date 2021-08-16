package by.bsu.d0mpi.UP_PostGallery.listener;

import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener which initializes {@link BasicConnectionPool} when the application is launched.
 *
 * @author d0mpi
 * @version 1.0
 * @see ServletContextListener
 * @see BasicConnectionPool
 */
@WebListener
public class ApplicationContextListener implements ServletContextListener {

    public static final String DATABASE_PROPERTIES = "database.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicConnectionPool.getInstance().init(DATABASE_PROPERTIES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        BasicConnectionPool.getInstance().destroy();
    }
}
