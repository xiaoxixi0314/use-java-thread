package aqs;

public class SelfReentrantLockTest {

    private static final SelfReentrantLock lock = new SelfReentrantLock();

    public void test(int level) {
        try {
            lock.lock();
            level = level + 1;
            System.out.println("递归层级：" + level);
            if (level >= 3) {
                return;
            }
            test(level);
        } finally {
            lock.unlock();
        }
    }

    static class TestThread extends Thread {

        private SelfReentrantLockTest test = new SelfReentrantLockTest();

        public TestThread(SelfReentrantLockTest test) {
            this.test = test;
        }
        @Override
        public void run() {
            test.test(0);
        }
    }

    public static void main(String[] args) {
        SelfReentrantLockTest test = new SelfReentrantLockTest();
        for (int i = 0; i < 2; i ++) {
            new TestThread(test).start();
        }
    }
}
