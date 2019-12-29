package dbpool;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore实现数据库连接池
 */
public class DBPoolSemaphore {

    private LinkedList<Connection> pool = new LinkedList<>();
    // 可用连接
    private Semaphore useful;
    // 已使用的连接
    private Semaphore used;

    /**
     * 初始化数据库连接池
     * @param poolSize
     */
    public DBPoolSemaphore(int poolSize) {
        if (poolSize > 0){
            for (int i = 0; i < poolSize; i ++) {
                pool.addLast(new SqlConnectionImpl());
            }
        }
        useful = new Semaphore(poolSize);
        used = new Semaphore(0);
    }

    public Connection getConnection() throws InterruptedException{
        useful.acquire();
        Connection connection;
        synchronized (pool) {
            connection = pool.removeFirst();
        }
        used.release();
        return connection;
    }

    /**
     * 释放数据库连接池，
     * 通知其他等待pool对象的线程可以去尝试拿连接了
     * @param connection
     */
    public void releaseConnection(Connection connection) throws InterruptedException{
        if (Objects.isNull(connection)) {
            return;
        }
        used.acquire();
        synchronized (pool) {
            pool.addLast(connection);
        }
        useful.release();
    }

}
