package lock;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁的标准使用范式
 * finally中unlock掉
 */
public class LockCase {

    private static Lock lock = new ReentrantLock();
    private static Integer age = 0;

    private static Integer THREAD_NUMS = 100;
    private static CountDownLatch countDown = new CountDownLatch(THREAD_NUMS);

    private static class LockCaseThread extends Thread {

        private LockCase lockCase;

        public LockCaseThread(LockCase lockCase, String name) {
            super(name);
            this.lockCase = lockCase;
        }

        @Override
        public void run() {
            try {
                countDown.await();
                for (int i = 1; i <= 1000; i ++) {
                    lockCase.test();
                }

                System.out.println("["+Thread.currentThread().getName()+"]" +", age:" + lockCase.getAge());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Integer getAge() {
        return age;
    }

    public static void test(){
        try {
            lock.lock();
            age ++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        LockCase lockCase = new LockCase();
        for (int i = 0; i < THREAD_NUMS; i ++) {
            LockCaseThread thread = new LockCaseThread(lockCase, "LOCK_CASE_" + i);
            countDown.countDown();
            thread.start();
        }
        Thread.sleep(2000);
        System.out.println("final result age is:" + lockCase.getAge());

    }

}
