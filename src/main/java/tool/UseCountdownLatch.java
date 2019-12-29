package tool;

import base.util.SleepTool;

import java.util.concurrent.CountDownLatch;

public class UseCountdownLatch {

    private final static CountDownLatch countdown = new CountDownLatch(6);

    private static class CountdownAwaitThread implements Runnable {
        @Override
        public void run() {
            try {
                long threadId = Thread.currentThread().getId();
                SleepTool.sleepMills(100);
                System.out.println("["+threadId+"] will wait....");
                countdown.await();
                SleepTool.sleepMills(100);
                System.out.println("["+threadId+"] do biz action...");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class CountDownThread implements Runnable {
        @Override
        public void run() {
            long threadId = Thread.currentThread().getId();
            countdown.countDown();
            System.out.println("["+threadId+"] count down 1st time.");
            SleepTool.sleepMills(200);
            countdown.countDown();
            System.out.println("["+threadId+"] count down 2nd time.");
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 2; i++) {
            new Thread(new CountdownAwaitThread()).start();
        }
        SleepTool.sleepMills(300);
        for(int i = 0; i < 3; i++) {
            new Thread(new CountDownThread()).start();
        }
        SleepTool.sleepMills(500);
    }
}
