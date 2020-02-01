package aqs;

import base.util.SleepTool;

public class SelfSemaphoreTest {

    private static final SelfSemaphore lock = new SelfSemaphore();

    public void test() {
        try {
            lock.lock();
            SleepTool.sleepMills(1000);
            System.out.println(Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }

    static class TestThread extends Thread {

        private SelfSemaphoreTest test = new SelfSemaphoreTest();

        public TestThread(SelfSemaphoreTest test) {
            this.test = test;
        }
        @Override
        public void run() {
            test.test();
        }
    }

    public static void main(String[] args) {
        SelfSemaphoreTest test = new SelfSemaphoreTest();
        for (int i = 0; i < 12; i ++) {
            new TestThread(test).start();
        }
        SleepTool.sleepMills(3000);
    }
}