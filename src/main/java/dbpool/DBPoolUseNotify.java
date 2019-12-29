package dbpool;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * 模拟实现一个等待超时模式的连接池
 */
public class DBPoolUseNotify {

    private LinkedList<Connection> pool = new LinkedList<>();

    /**
     * 初始化数据库连接池
     * @param poolSize
     */
    public DBPoolUseNotify(int poolSize) {
        if (poolSize > 0){
            for (int i = 0; i < poolSize; i ++) {
                pool.addLast(new SqlConnectionImpl());
            }
        }
    }


    /**
     * 获取数据库连接，等待规定时间后仍然没有获取连接池则放弃等待，
     * 返回相应结果或者抛出异常
     * @return
     */
    public Connection getConnection(long waitMills) throws InterruptedException{
        // 小于0表示永不超时模式
        if (waitMills < 0) {
            synchronized (pool) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            }
        }
        // 超时模式
        synchronized (pool) {
            // 超时时刻
            long feature = System.currentTimeMillis() + waitMills;
            // 需要等待的时间
            long reminding = waitMills;
            // 连接池不为空且需要等待的时间大于0才继续等待，否则等待结束
            // 等待结束有两种情况
            // 1. 池子中有连接
            // 2. 等待超时
            while (pool.isEmpty() && reminding > 0) {
                pool.wait();
                reminding = feature - System.currentTimeMillis();
            }
            // 等待结束
            if (!pool.isEmpty()) {
                return pool.removeFirst();
            }
            return null;
        }
    }

    /**
     * 释放数据库连接池，
     * 通知其他等待pool对象的线程可以去尝试拿连接了
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        if (Objects.isNull(connection)) {
            return;
        }
        synchronized (pool) {
            pool.addLast(connection);
            pool.notifyAll();
        }
    }
}
