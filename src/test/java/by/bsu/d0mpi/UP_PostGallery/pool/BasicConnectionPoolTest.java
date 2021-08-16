package by.bsu.d0mpi.UP_PostGallery.pool;

import org.junit.*;

import java.sql.Connection;

public class BasicConnectionPoolTest {
    private static final BasicConnectionPool POOL = BasicConnectionPool.getInstance();
    public static final String TEST_DATABASE_PROPERTIES = "test_database.properties";

    @BeforeClass
    public static void launchPool() {
        POOL.init(TEST_DATABASE_PROPERTIES);
    }

    @AfterClass
    public static void destroyPool() {
        POOL.destroy();
    }

    @Test
    public void getConnection_notNull() {
        final ProxyConnection connection = POOL.getConnection();
        Assert.assertNotNull(connection);
        POOL.releaseConnection(connection);
    }

    @Test
    public void releaseConnection_expected_emptyUsedConnectionsList(){
        final ProxyConnection connection = POOL.getConnection();
        POOL.releaseConnection(connection);
        Assert.assertEquals(0, POOL.getNumberOfUsedConnections());
    }

    @Test
    public void getConnection_expected_usedConnectionsListWithOneElement(){
        final ProxyConnection connection = POOL.getConnection();
        Assert.assertEquals(1, POOL.getNumberOfUsedConnections());
        POOL.releaseConnection(connection);
    }

    @Test
    public void getResizeNumberOfConnection_expected_resizingOfFreeConnectionsList(){
        final ProxyConnection connection1 = POOL.getConnection();
        final ProxyConnection connection2 = POOL.getConnection();
        final ProxyConnection connection3 = POOL.getConnection();

        Assert.assertEquals(POOL.getConfig().DB_GROW_SIZE - 1,POOL.getNumberOfFreeConnections());
        POOL.releaseConnection(connection1);
        POOL.releaseConnection(connection2);
        POOL.releaseConnection(connection3);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(POOL.getConfig().DB_INIT_POOL_SIZE,POOL.getNumberOfFreeConnections());
    }

    @Test
    public void checkReducingTask_expected_initPoolSize(){
        final ProxyConnection connection1 = POOL.getConnection();
        final ProxyConnection connection2 = POOL.getConnection();
        final ProxyConnection connection3 = POOL.getConnection();
        POOL.releaseConnection(connection1);
        POOL.releaseConnection(connection2);
        POOL.releaseConnection(connection3);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(POOL.getConfig().DB_INIT_POOL_SIZE,POOL.getNumberOfFreeConnections());
    }



}
