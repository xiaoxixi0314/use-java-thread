package base;

/**
 * 测试sleep对锁的影响
 */
public class SleepLock {

    private Object object = new Object();

    private class ThreadSleep extends Thread {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("["+threadName+"] will take the lock, time:" + System.currentTimeMillis());
            try {
                synchronized (object) {
                    System.out.println("["+threadName+"] start to work");
                    Thread.sleep(2000);
                    System.out.println("["+threadName+"] work done");
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private class ThreadNotSleep extends Thread {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("["+threadName+"] will take the lock, time:" + System.currentTimeMillis());
            synchronized (object) {
                System.out.println("["+threadName+"] start to work");
                System.out.println("["+threadName+"] work done");
            }
        }
    }


    public static void main(String[] args) {
        SleepLock sleepLock = new SleepLock();

        ThreadSleep threadSleep = sleepLock.new ThreadSleep();
        threadSleep.setName("thread-sleep");

        ThreadNotSleep threadNotSleep = sleepLock.new ThreadNotSleep();
        threadNotSleep.setName("thread-not-sleep");

        threadSleep.start();
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadNotSleep.start();
    }
}
