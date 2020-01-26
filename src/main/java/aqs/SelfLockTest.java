package aqs;

import base.util.SleepTool;

import java.util.concurrent.locks.Lock;

public class SelfLockTest {

    private static final int THREAD_NUMS = 4;

    private static class SelfLockTestThread extends Thread {

        private Lock lock;

        public SelfLockTestThread(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                SleepTool.sleepMills(1000);
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Lock lock = new SelfLock();
        for (int i = 0; i < THREAD_NUMS; i ++) {
            new SelfLockTestThread(lock).start();
        }
    }
}
