package voli;

import base.util.SleepTool;

/**
 * 演示volatile可见性
 */
public class VolatileVisibility {

    private static boolean ready;
    private static int number;

//    // 有volatile保证线程间的可见性
//    private volatile static boolean ready;
//    private volatile static int number;

    private static class VisibilityThread extends Thread {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("["+threadName+"] is running...");

            while(!ready){}// 无限循环

            System.out.println("[number] is "+ number);
            System.out.println("["+threadName+"] is over....");
        }
    }

    public static void main(String[] args) {
        VisibilityThread thread = new VisibilityThread();
        thread.setName("visibility-thread");
        thread.start();

        SleepTool.sleep(1);
        number = 51;
        ready = true;
        SleepTool.sleep(5);

        System.out.println("main thread is over....");
    }

}

