package dbpool;

import java.sql.Connection;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolNotifyTest {

    // 初始化一个20个连接的连接池
    private static DBPoolUseNotify dbPoolUseNotify = new DBPoolUseNotify(20);

    private static final int THREAD_NUMS = 50;
    private static final CountDownLatch count_down = new CountDownLatch(THREAD_NUMS);


    private static class SqlexecThread implements Runnable {
        private int count;
        private AtomicInteger notGet;
        private AtomicInteger geted;

        public SqlexecThread(int count, AtomicInteger notGet, AtomicInteger geted) {
            this.count = count;
            this.notGet = notGet;
            this.geted = geted;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (count > 0) {
                try {
                    Connection connection = dbPoolUseNotify.getConnection(1000);
                    if (Objects.isNull(connection)) {
                        notGet.incrementAndGet();
                        System.out.println("[" + threadName + " " + count + "st]get sql connection timeout。");
                    } else {
                        connection.createStatement();
                        connection.commit();
                        geted.incrementAndGet();
                        dbPoolUseNotify.releaseConnection(connection);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }
            count_down.countDown();
        }
    }

    public static void main(String[] args) throws Exception{

        // 统计没有拿到连接的次数
        AtomicInteger notGet = new AtomicInteger(0);
        // 统计拿到连接的次数
        AtomicInteger geted = new AtomicInteger(0);
        int COUNT = 20;

        for (int i = 0; i < THREAD_NUMS; i ++) {
            Thread thread = new Thread(new SqlexecThread(COUNT, notGet, geted));
            thread.setName("sql-exec-thread" + i);
            thread.start();
        }
        count_down.await();
        System.out.println("[main] start get connection ....");
        System.out.println("get connection:" + geted.get());
        System.out.println("miss connection:" + notGet.get());
    }
}
