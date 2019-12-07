package base;

import base.util.SleepTool;

import java.util.Objects;

public class ThreadJoinMethod {

    static class YouRunnable implements Runnable {

        private Thread thread;
        public YouRunnable(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            SleepTool.sleep(1);
            System.out.println("["+threadName+"] You start to do work[you]");
            try {
                if (!Objects.isNull(thread)) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("["+threadName+"] You start work done [you]");
        }
    }

    static class YourGoddess implements Runnable {

        private Thread thread;

        public YourGoddess(Thread thread) {
            this.thread = thread;
        }
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("["+threadName+"] Your goddess start to do work[goddess]");
            SleepTool.sleep(1);
            try {
                if (!Objects.isNull(thread)) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("["+threadName+"] Your goddess work done[goddess]");
        }
    }

    static class YourGoddessBf implements Runnable {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            SleepTool.sleep(2);
            System.out.println("["+threadName+"] Your goddess boyfriend start to do work[goddess boyfriend]");
            System.out.println("["+threadName+"] Your goddess boyfriend work done[goddess boyfriend]");
        }
    }

    public static void main(String[] args){

        Thread goddessBf = new Thread(new YourGoddessBf());
        goddessBf.setName("your-goddess-bf");

        Thread goddess = new Thread(new YourGoddess(goddessBf));
        goddess.setName("your-goddess");

        Thread youThread = new Thread(new YouRunnable(goddess));
        youThread.setName("you-thread");

        youThread.start();
        goddess.start();
        goddessBf.start();

    }
}
