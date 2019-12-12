package dbpool;

import java.sql.Connection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {

    private static AtomicInteger notGet = new AtomicInteger(0);
    private static AtomicInteger geted = new AtomicInteger(0);

    private static DBPool dbPool = new DBPool(20);

    private static class Sqlexec implements Runnable {
        @Override
        public void run(){
            try {
                Connection connection = dbPool.getConnection(20);
                if (Objects.isNull(connection)) {
                    notGet.incrementAndGet();
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
